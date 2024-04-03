import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Drone implements Comparable<Drone> {
  //Variables
  private DeliveryService service;
  private Location homebase;
  private Integer droneID;
  private int capacity;
  private int fuel;
  private Location location;
  private int revenue;
  private TreeMap<Ingredient, Integer[]> ingredients;
  private int current_num_ingredients;
  private boolean hasInstructor; //does it have a pilot or leader drone
  private Integer swarmDroneTag; //drone tag of leader
  private String pilotLicense;
  private TreeMap<Integer, Drone> swarm;

  //Defaults
  private static final int DEFAULT_REVENUE = 0;

  //Static collection
  private static TreeMap<Integer, Drone> drones = new TreeMap<>();


  //Constructors
  public Drone (String service_name, Integer droneID, int capacity, int fuel, int revenue) {
    //Check to make sure service exists
    DeliveryService droneService = DeliveryService.getService(service_name);
    if (!DeliveryService.exists(service_name)) {
        throw new IllegalArgumentException("The desired delivery service does not exist! Please reenter with a different name.");
    } else if (droneService.getLocation().isFull()) {
        throw new IllegalArgumentException("This location is full.");
    }
    //Check whether service already has a drone with this tag
    if (droneService.hasDroneID(droneID)) {
      throw new IllegalArgumentException("The service already has a drone with this ID!");
  }
    if (capacity < 0) {
        throw new IllegalArgumentException("Drone capacity cannot be negative.");
    }
    if (fuel < 0) {
        throw new IllegalArgumentException("Drone fuel cannot be negative.");
    }
    this.service = droneService;
    this.droneID = droneID;
    this.capacity = capacity;
    this.fuel = fuel;
    this.location = service.getLocation();
    this.revenue = revenue;
    this.ingredients = new TreeMap<Ingredient, Integer[]>();
    this.current_num_ingredients = 0;
    this.hasInstructor = false;
    this.swarmDroneTag = null;
    this.pilotLicense = null;
    this.location.addDrone();
    droneService.addDrone(this);
    this.homebase = droneService.getLocation();
    drones.put(droneID, this);
    swarm = new TreeMap<Integer, Drone>();
  }

  public Drone(String service_name, Integer droneID, int capacity, int fuel) {
    this(service_name, droneID, capacity, fuel, DEFAULT_REVENUE);
  }

  //Active Methods

  /**
    * Loads an ingredient onto the drone
    * @param barcode barcode of the ingredient being loaded
    * @param quantity amount of the ingredient being loaded
    * @param unit_price price of the ingredient being loaded
    * @param ingredientObject object of the ingredient to be loaded
  */
  public void loadIngredients(String barcode, Integer quantity, Integer unit_price) {
    if (capacity - current_num_ingredients < quantity) {
      throw new IllegalArgumentException("This drone does not have sufficient space for this quantity of ingredient!");
    }
    Ingredient ingredient = Ingredient.getIngredient(barcode); // it is EXTREMELY IMPORTANT that this calls the static ingredient method
    //if ingredient exist, update the quantity
    if (ingredients.containsKey(ingredient)) {
      int new_quantity = ingredients.get(ingredient)[0] + quantity;
      Integer[] new_arr = {new_quantity, ingredients.get(ingredient)[1]};
      ingredients.put(ingredient, new_arr);
    } else {
      //create new key if the ingredient does not exist in the drone
      Integer[] new_quantity_price = {quantity, unit_price};
      //adding to hashmap 'ingredients' with values quantity,unit_price
      this.ingredients.put(ingredient, new_quantity_price);
    }
    current_num_ingredients += quantity;
  }

  /**
    * Removes an ingredient from the drone
    * @param barcode barcode of the ingredient being removed
    * @param quantity amount of the ingredient being removed
    * @param ingredientObject object of the ingredient to be removed
    * @return the price of the removed ingredient
  */
  public int removeIngredient (String barcode, int quantity) {
    Ingredient ingredient = getIngredient(barcode);
    int price = getIngredientPrice(ingredient);
    int oldQuantity = getIngredientQuantity(ingredient);
    int new_quantity = oldQuantity - quantity;
    if (new_quantity < 0) {
      throw new IllegalArgumentException("Drone does not have sufficient quantity of this ingredient!");
    } else if (new_quantity == 0) {
      ingredients.remove(ingredient);
      //Temporarily, removes the ingredientObject from the object list
      //ingredientObjects.remove(ingredientObject); still need??
    } else {
      Integer[] new_quantity_price = {new_quantity, ingredients.get(ingredient)[1]};
      ingredients.put(ingredient, new_quantity_price);
    }
    current_num_ingredients -= quantity;
    return price;
  }

  /**
     * Adds a drone to a swarm
     * @param swarmDrone drone to join the swarm
     * @throws IllegalArgumentException if the drones aren't in the same location
     * or the lead drone is already part of a swarm
     */
  public void joinSwarm(String service_name, Integer swarm_drone_tag) {
    DeliveryService service = DeliveryService.getService(service_name);
    Drone swarmDrone = service.getDrone(swarm_drone_tag);
    if (swarmDrone == null) {
      throw new IllegalArgumentException("The swarm drone provided does not exist!");
    }
    if (swarmDrone.getSwarm().size() > 0) {
      throw new IllegalArgumentException("Swarm drone is leaading a different swarm.");
    }
    if (this.equals(swarmDrone)) {
      throw new IllegalArgumentException("A drone cannot join its own swarm!");
    }
    if (this.pilotLicense == null) {
      throw new IllegalArgumentException("The lead drone is not connected to a pilot!");
    }
    if (!location.equals(swarmDrone.getDroneLocation())) {
      throw new IllegalArgumentException("These drones are not at the same location!");
    }
    if (swarmDrone.inSwarm()) {
      Drone oldLeadDrone = getDrone(service.getName(), swarmDrone.getSwarmDroneTag());
      oldLeadDrone.removeSwarmDrone(swarm_drone_tag);
    }
    if (swarmDrone.getPilotLicense() != null) {
      Pilot currentPilot = Pilot.getPilot(swarmDrone.getPilotLicense());
      currentPilot.getDronesPiloting().remove(swarmDrone);
    }
    swarm.put(swarm_drone_tag, swarmDrone);
    swarmDrone.setHasInstructor(true);
    swarmDrone.setPilotLicense(null);
    swarmDrone.setSwarmDroneTag(droneID);
  }

  /**
     * Removes a drone from a swarm
     * This is called on the swarm drone!
     * @param service_name name of the service the drone belongs to
     * @throws IllegalArgumentException if the drone is the lead drone
     */
  public void leaveSwarm(String service_name) {
    if (swarm.size() > 0) {
      throw new IllegalArgumentException("This drone is the lead drone!");
    }
    if (!inSwarm()) {
      throw new IllegalArgumentException("This drone is not currently in a swarm!");
    }
    Drone leadDrone = getDrone(service_name, swarmDroneTag);
    leadDrone.removeSwarmDrone(this.droneID);
    hasInstructor = true;
    pilotLicense = leadDrone.getPilotLicense();
    Pilot newPilot = Pilot.getPilot(pilotLicense);
    newPilot.getDronesPiloting().add(this);
    swarmDroneTag = null;
  }

  public boolean inSwarm() {
    return swarmDroneTag != null;
  }

  /**
     * Removes a drone from a swarm List
     * This is called on the lead drone!
     * @param swarmDrone drone to be removed from the lsit
     * @throws IllegalArgumentException if the drone isn't in the swarm
     */
  public void removeSwarmDrone(Integer swarmDroneTag) {
    if (!this.getSwarm().containsKey(swarmDroneTag)) {
      throw new IllegalArgumentException("This drone isn't in the lead drone's swarm!");
    }
    swarm.remove(swarmDroneTag);
  }

  
  public static String displayDrones(String service_name) {
    if (!DeliveryService.exists(service_name)) {
      throw new IllegalArgumentException("This service does not exist.");
    }
    DeliveryService service = DeliveryService.getService(service_name);
    return service.displayMyDrones();
  }

  public static String displayDrones() {
    //efficiency thing
    String guiText = "";
    if (DeliveryService.getServices().size() == 0) {
    } else {
      for (Map.Entry<String, DeliveryService> entry : DeliveryService.getServices().entrySet()) {
          guiText += entry.getKey() + ":\n";
          System.out.println(entry.getKey());
          for (Map.Entry<Integer, Drone> droneEntry : entry.getValue().getDrones().entrySet()) {
              System.out.println(droneEntry.getValue());
              guiText += droneEntry.getValue().toString() + "\n";
          }
      }
    }
    return guiText;
  }

  public static List<Integer> getDroneNames() {
    List<Integer> IDs = new ArrayList<>(drones.size());
    for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
        IDs.add(entry.getKey());
    }
    return IDs;
}

public List<String> getIngredientBarcodes() {
  List<String> barcodes = new ArrayList<>(ingredients.keySet().size());
  for (Ingredient ingredient : ingredients.keySet()) {
    barcodes.add(ingredient.getBarcode());
  }
  return barcodes;
}

  /**
     * Subtracts the fuel from the current drone
     * @param amount amount of fuel to subtract
     * @throws IllegalArgumentException if the drone doesn't have enough fuel
     */
  public void useFuel(int amount) {
    if (amount > fuel) {
      throw new IllegalArgumentException("The drone has insufficient fuel!");
    }
    fuel -= amount;
  }

  public boolean hasValidPilot() {
    if (pilotLicense == null || !Pilot.exists(pilotLicense)) {
      return false;
    }
    return true;
  }

  public static boolean exists(String service_name, int drone_tag) {
    DeliveryService service = DeliveryService.getService(service_name);
    return service.hasDroneID(drone_tag);
  }

  public static Drone getDrone(String service_name, int drone_tag) {
    DeliveryService droneService = DeliveryService.getService(service_name);
    return droneService.getDrone(drone_tag);
  }

  public int getIngredientQuantity(Ingredient ingredient) {
    /*returning the quantity only for the ingredient which is in the 0st index of the list.
    (1st index contains price)*/
    return ingredients.get(ingredient)[0];
  }

  public int getIngredientPrice(Ingredient ingredient) {
    /*returning the price only for the ingredient which is in the 1st index of the list.
    (0th index contains quantity)*/
    return ingredients.get(ingredient)[1];
  }

  public int collectRevenue() {
    int toReturn = revenue;
    revenue = 0;
    return toReturn;
  }

  public void acceptFuel(int amount) {
    fuel += amount;
  }
  public boolean isAtBase() {
    return location.equals(homebase);
  }


  //Getters
  public DeliveryService getService() {
    return this.service;
  }

  public Location getDroneLocation() {
    return this.location;
  }

  public int getDroneID() {
    return this.droneID;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getFuel(){
    return this.fuel;
  }

  public int getRevenue() {
    return revenue;
  }

  public int getCurrentNumIngredients() {
    return current_num_ingredients;
  }

  public boolean getHasInstructor() {
    return hasInstructor;
  }

  public Integer getSwarmDroneTag() {
    return swarmDroneTag;
  }

  public String getPilotLicense() {
    return pilotLicense;
  }

  public static TreeMap<Integer,Drone> getDrones() {
    return drones;
  }




  /**
     * Gets objects on the drone
     * @return List of ingredients on the drone
     */
  public List<Ingredient> getIngredientObjects() {
    List<Ingredient> toReturn = new ArrayList<>(ingredients.keySet());
    return toReturn;
  }

  public Ingredient getIngredient(String barcode) {
    for (Ingredient ingredient : getIngredientObjects()) {
      if (ingredient.getBarcode().equals(barcode)) {
        return ingredient;
      }
    }
    throw new IllegalArgumentException("An ingredient with this barcode does not exist on this drone!");
  }

  public TreeMap<Integer, Drone> getSwarm() {
    return swarm;
  }
  public Location getHomebase() {
    return homebase;
  }

  //Setters
  public void setDroneLocation (Location location) {
    this.location = location;
  }

  public void setFuel(int fuel) {
    this.fuel = fuel;
  }

  public void setRevenue(int revenue) {
    this.revenue = revenue;
  }

  public void setHasInstructor(boolean hasInstructor) {
    this.hasInstructor = hasInstructor;
  }

  public void setSwarmDroneTag(Integer swarmDroneTag) {
    this.swarmDroneTag = swarmDroneTag;
  }

  public void setPilotLicense(String pilotLicense) {
    this.pilotLicense = pilotLicense;
  }

  //toString
  @Override
  public String toString() {
    String toReturn = String.format("Drone with ID: %d, Capacity: %d, Remaining Capacity: %d, Fuel level: %d, Sales: %d, Location: %s.\n",  droneID, capacity, capacity - current_num_ingredients, fuel, revenue, location.getName());
    if (hasValidPilot()) {
      toReturn += "Pilot: ";
      Pilot dronePilot = Pilot.getPilot(getPilotLicense());
      toReturn += dronePilot.getUsername();
      toReturn += "\n";
    }
    if (swarm.size() != 0) {
      toReturn += String.format("This drone is the lead drone of the swarm containing the following drones: ");

      for (Map.Entry<Integer, Drone> entry : swarm.entrySet()) {
        toReturn += entry.getValue().getDroneID();
        if (entry.getKey() != swarm.lastKey()) {
          toReturn += ", ";
        }
      }
      toReturn += "\n";
    }
    //if (inSwarm()) {
    // toReturn += String.format("This drone is part of a swarm with lead drone tag %d.\n", swarmDroneTag);
    //}
    if (current_num_ingredients == 0) {
      return toReturn;
    }
    toReturn += String.format("This drone contains the following ingredients:\n\n");
    for (int i=0; i<getIngredientObjects().size(); i++) {
      Ingredient ingredient = getIngredientObjects().get(i);
      String barcode = ingredient.getBarcode();
      int quantity = getIngredientQuantity(getIngredientObjects().get(i));
      int price = getIngredientPrice(getIngredientObjects().get(i));
      toReturn += String.format("%d. Barcode: %s, name: %s, quantity: %d, price: %d, total weight: %d\n", i+1, barcode, ingredient.getName(), quantity, price, ingredient.getWeight() * quantity);
    }
    return toReturn;
  }

  //Comparable Requirement
  public int compareTo(Drone other) {
    if ((droneID - other.getDroneID() != 0)) {
        return droneID - other.getDroneID();
    }
    if ((service.getName().compareTo(other.getService().getName()) != 0)) {
      return service.getName().compareTo(other.getService().getName());
    }
    if ((capacity - other.getCapacity()) != 0) {
      return capacity - other.getCapacity();
    }
    if (((capacity - current_num_ingredients) - (other.getCapacity() - other.getCurrentNumIngredients()) != 0)) {
      return (capacity - current_num_ingredients) - (other.getCapacity() - other.getCurrentNumIngredients());
    }
    if ((fuel - other.getFuel()) != 0) {
      return fuel - other.getFuel();
    }
    return revenue - other.getRevenue();
  }


}
