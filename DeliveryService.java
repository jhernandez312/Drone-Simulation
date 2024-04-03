import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DeliveryService implements Comparable<DeliveryService> {

    //Variables
    private String name;
    private int revenue;
    private Location location;
    private int identifier;
    //private SortedMap<String, Person> employees  = new TreeMap<>(); //key of map is username
    //private SortedMap<Integer, Drone> drones  = new TreeMap<>(); //key of map is the droneID
    private SortedMap<Integer, Drone> drones;
    private SortedMap<String, Person> employees;
    private Manager manager;

    //Static collection
    private static SortedMap<String, DeliveryService> services  = new TreeMap<>(); //key of map is name of delivery service


    //Constructors
    DeliveryService(String name, int revenue, String location_name, int identifier) {
        Location location = Location.getLocation(location_name); //This checks if there is location already
        if (exists(name)) {
            throw new IllegalArgumentException("There is already a delivery service with this name! Please reenter with a different name.");
        }
        if (revenue < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative.");
        }
        this.name = name;
        this.revenue = revenue;
        this.location = location;
        this.identifier = identifier;
        this.drones = new TreeMap<Integer, Drone>();
        services.put(name, this);
        this.employees = new TreeMap<String, Person>();
        this.manager = null;
    }

    DeliveryService(String name, int revenue, String location_name) {
        this(name,revenue, location_name, 0);
    }

    /**
     * Determnes if there is a drone with this ID that the delivery service controlls
     * @param droneID ID of the drone to be located
     * @return boolean value that is true if the drone is in the list, false otherwise
     */
    public boolean hasDroneID(int droneID) {
        for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
            if (entry.getKey() == droneID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds drone to the drones the delivery services has
     * @param drone Drone to be added to the list of current drones
     */
    public void addDrone(Drone drone) {
        drones.put(drone.getDroneID(), drone);
    }

    public static boolean exists(String name) {
        if (services.containsKey(name)) {
            return true;
        }
        return false;
    }

    public String displayMyDrones() {
        String droneString = "";
        for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
            droneString += entry.getValue() + "\n";
        }
        System.out.println(droneString);
        return droneString;
    }

    public static String getDisplayServicesString() {
        String sillyLittleServiceString = "";
        for (Map.Entry<String, DeliveryService> entry : services.entrySet()) {
            sillyLittleServiceString += entry.getValue() + "\n";
        }
        return sillyLittleServiceString;
    }

    public static void displayServices() {
        for (Map.Entry<String, DeliveryService> entry : services.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public static DeliveryService getService(String name) {
        if (services.containsKey(name)) {
            return services.get(name);
        }
        throw new IllegalArgumentException("This service does not exist!");
    }

    public static List<String> getServiceNames() {
        List<String> names = new ArrayList<>(services.size());
        for (Map.Entry<String, DeliveryService> entry : services.entrySet()) {
            names.add(entry.getKey());
        }
        return names;
    }

    public List<String> getEmployeeNames() {
        List<String> names = new ArrayList<>(employees.size());
        for (Map.Entry<String, Person> entry : employees.entrySet()) {
            names.add(entry.getKey());
        }
        return names;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getRevenue() {
        return revenue;
    }

    public Location getLocation() {
        return location;
    }

    public int getIdentifier() {
        return identifier;
    }

    public SortedMap<Integer, Drone> getDrones() {
        return drones;
    }

    public Drone getDrone(Integer drone_tag) {
        if (drones.containsKey(drone_tag)) {
            return drones.get(drone_tag);
        }
        throw new IllegalArgumentException("This service does not have a drone with this tag!");
    }

    public static SortedMap<String, DeliveryService> getServices() {
        return services;
    }

    public SortedMap<String, Person> getEmployees() {
        return employees;
    }

    public Manager getManager() {
        return manager;
    }

    public List<String> getDroneTagsAsStrings() {
        List<String> tags = new ArrayList<>(drones.size());
        for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
            tags.add(Integer.toString(entry.getKey()));
        }
        return tags;
    }

    public List<String> getLicensedEmployeeNames() {
        List<String> usernames = new ArrayList<>(employees.size());
        for (Map.Entry<String, Person> entry : employees.entrySet()) {
            if (entry.getValue().getLicense() != null) {
                usernames.add(entry.getKey());
            }
        }
        return usernames;
    }

    public List<String> getTrainableEmployeeNames() {
        List<String> usernames = new ArrayList<>(employees.size());
        for (Map.Entry<String, Person> entry : employees.entrySet()) {
            if (!(entry.getValue() instanceof Manager)) {
                usernames.add(entry.getKey());
            }
        }
        return usernames;
    }

    public List<String> getHomeBaseDroneTagsAsStrings() {
        List<String> droneTags = new ArrayList<>(drones.size());
        for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
            if (entry.getValue().isAtBase()) {
                droneTags.add(((Integer)entry.getKey()).toString());
            }
        }
        return droneTags;
    }

    //Setters
    public void setName(String newName) {
        name = newName;
    }

    public void setRevenue(int newRevenue) {
        revenue = newRevenue;
    }

    public void setLocation(Location newLocation) {
        location = newLocation;
    }

    public void setIdentifier(int newIdentifier) {
        identifier = newIdentifier;
    }

    public void setManager(Manager newManager) {
        manager = newManager;
    }

    //toString
    @Override
    public String toString() {
         return String.format("Delivery service %s with current revenue %d at location %s.", name, revenue, location.getName());
    }

    //Comparable Requirement
    public int compareTo(DeliveryService other) {
        if (!(name.compareTo(other.getName()) == 0)) {
            return name.compareTo(other.getName());
        }
        if (!(location.compareTo(other.getLocation()) == 0)) {
            return location.compareTo(other.getLocation());
        }
        if (!(revenue - other.getRevenue() == 0)) {
            return revenue - other.getRevenue();
        }
        return identifier - other.getIdentifier();
    }

    public static void displayDrones(String service_name) {
        DeliveryService service = getService(service_name);
        System.out.println("service name is " + "[" + service_name + "]" + " drones: \n");
        for (Map.Entry<Integer, Drone> entry : service.drones.entrySet()) {
            System.out.println(entry.getValue());
       }
    }

    //display all drones
    public static void displayDrones() {
        
        for(Map.Entry<String, DeliveryService> entry: services.entrySet()) {
            displayDrones(entry.getKey());
        }
    }

    /**
     * Hires a person to a specific service
     * @param service_name service the person should be hired to
     * @param username user name of worker to be hired
     * @throws IllegalArgumentException if the person already works here or
     * is currently a manager or pilot at another service
     */
    public void hireWorker(String service_name, String username) {
        DeliveryService service = getService(service_name);
        Person hire = Person.getPerson(username);

        //checks if it is a manager
        if (Person.managers.containsKey(username)) { //This didn't work, but why?????
            throw new IllegalArgumentException("The new hire is a manager!");
        }
        //checks if it is already in the service
        if (this.employees.containsKey(username)) {
            throw new IllegalArgumentException("The new hire is already working for your company!");
        }
        //checks if it is a pilot for another service. already checked if it was an employee in current service
        if (hire.getLicense() != null ) {
            if (Person.pilots.containsKey(hire.getLicense())) {
                throw new IllegalArgumentException("The new hire is a pilot!");
            }
        }
        
        if (hire instanceof Manager) {
            throw new IllegalArgumentException("This new hire is a manager!");
        }

        if(!hire.getIsEmployed()) {
            Person.allPersons.remove(username);
            Person.unemployed.remove(username);
            WarehouseWorker newWorker = new WarehouseWorker(hire);
            hire = newWorker;
        }
        employees.put(username, hire); //adding to the employee list in Delivery Service class
        hire.setServicesWorking(service, true);

    }

    /**
     * Fires a person to a specific service
     * @param service_name service the person should be fired fron
     * @param username user name of worker to be fired
     * @throws IllegalArgumentException if the person doesn't work here
     * is currently a manager or pilot at another service
     */
    public void fireWorker(String service_name, String username) {
        Person fire = Person.getPerson(username);
        if (fire == null) {
            throw new IllegalArgumentException("The person you want to fire does not exist");
        }
        if (!employees.containsKey(username)) {
            throw new IllegalArgumentException("This person does not work for this service");
        }
        if (fire.equals(getManager())) {
            throw new IllegalArgumentException("You can't fire a manager!");
        }
        if (getPilot() != null && getPilot().contains(fire)) {
            throw new IllegalArgumentException("You can't fire a pilot!");
        }
        if (fire.getServicesWorking().size() <= 1) {
            Person.unemployed.put(username, fire);
            Person.warehouseWorkers.remove(username);
            fire.setIsEmployed(false);
        }
        this.employees.remove(username);
        fire.setServicesWorking(getService((service_name)), false);
    }

    //returns all pilots in the delivery service as a list
    public List<Person> getPilot() {
        List<Person> pilots = new ArrayList<>();

        for (Map.Entry<String, Person> entry : employees.entrySet()) {
            Person pilot = entry.getValue();
            if (pilot instanceof Pilot) {
                pilots.add(pilot);
            }
        }
        if (pilots.size() == 0) {
            return null;
        }
        return pilots;
    }

    public boolean hasManager() {
        return getManager() != null;
    }

    public void collectRevenue() {
        if (!hasManager()) {
            throw new IllegalArgumentException("This service does not have a manager!");
        }
        for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
            Drone drone = entry.getValue();
            revenue += drone.collectRevenue();
        }
    }

    /**
     * Trains a pilot
     * Calls serviceTrainPilot in deliveryservice
     * @param person person to be trained
     * @param license  licence of the person
     * @param experience  experience of the person
     * @throws IllegalArgumentException if the person is not employeed or is a manager
     */
    public void serviceTrainPilot(Person person, String license, Integer experience) {
        if(!this.employees.containsKey(person.getUsername())) {
            throw new IllegalArgumentException("person is not employed by service");
        }
        if(person instanceof Manager) {
            throw new IllegalArgumentException("managers cannot be trained");
        }
        if(!hasManager()) {
            throw new IllegalArgumentException("manager must be present at service for pilot training");
        }
        person.setPilotLicense(license);
        person.setSuccessfulFlights(experience);
    }

    /**
     * Appoints a pilot
     * @param person person to be appointed pilot
     * @param drone drone pilot is being assigned to
     * @throws IllegalArgumentException if the person isn't trained or isn't employeed by the service
     */
    public void serviceAppointPilot(String service_name, String user_name, Integer drone_tag) { //a person is only a pilot if they have drones under their command.
        Person person = Person.getPerson(user_name); //throws exception if doesn't exist
        Drone drone =  this.getDrone(drone_tag);
        if(drone.getPilotLicense() != null && drone.getPilotLicense().equals(person.getLicense())) {
            return;
        }
        if(!this.employees.containsKey(person.getUsername())) {
            throw new IllegalArgumentException("person is not employed by service");
        }
        if(person instanceof Manager) {
            throw new IllegalArgumentException("managers cannot be pilots");
        }
        if(person.getLicense() == null) {
            throw new IllegalArgumentException("person must have license");
        }
        if(person.getServicesWorking().size() > 1) {
            throw new IllegalArgumentException("person cannot be working at more than one service");
        }

        String oldPilotLicense = drone.getPilotLicense();
        if(oldPilotLicense != null) {
            Pilot oldPilot = Pilot.getPilot(oldPilotLicense);
            oldPilot.getDronesPiloting().remove(drone);

            //set to warehouse worker if pilot has no drones
            if (oldPilot.getDronesPiloting().size() == 0) {
                Person.pilots.remove(oldPilot.getLicense());
                Person.allPersons.remove(oldPilot.getUsername());
                employees.remove(oldPilot.getUsername());
                WarehouseWorker pilotToWorker = new WarehouseWorker(oldPilot);
                employees.put(pilotToWorker.getUsername(), pilotToWorker);
                Person.allPersons.put(pilotToWorker.getUsername(), pilotToWorker);
                Person.warehouseWorkers.put(pilotToWorker.getUsername(), pilotToWorker);
            }
        }

        //set drone pilot license to new pilot's

        //we're changing the person to a pilot class
        if (!(person instanceof Pilot)) {
            employees.remove(person.getUsername());
            Person.allPersons.remove(person.getUsername());
            Pilot newPilot = new Pilot(person);
            employees.put(newPilot.getUsername(), newPilot);
            drone.setPilotLicense(newPilot.getLicense());
            newPilot.getDronesPiloting().add(drone);
            Person.pilots.put(newPilot.getLicense(), newPilot);
        } else {
            drone.setPilotLicense(person.getLicense());
            Pilot thisPilot = Pilot.getPilot(person.getLicense());
            thisPilot.getDronesPiloting().add(drone);
        }
        if (drone.getSwarmDroneTag() != null) {
            Drone leadDrone = Drone.getDrone(drone.getService().getName(), drone.getSwarmDroneTag());
            leadDrone.getSwarm().remove(drone.getDroneID());
        }
        drone.setSwarmDroneTag(null);
        drone.setHasInstructor(true);
    }

    /**
     * Appoints a person to manager in a specific service
     * @param person to be appointed manager
     * @throws IllegalArgumentException if the person already doesn't exist or they work at a different service
     */
    public void serviceAppointManager(String user_name) {
        if (!this.employees.containsKey(user_name)) {
            throw new IllegalArgumentException("person is not employed by service");
        }
        Person person = employees.get(user_name);
        if (person.getServicesWorking().size() > 1) {
            throw new IllegalArgumentException("person cannot be working at more than one service");
        }
        if (person instanceof Pilot) {
            Pilot pilot = (Pilot) person;
            if(pilot.getDronesPiloting().size() != 0) {
                throw new IllegalArgumentException("person cannot be piloting any drones");
            }
        }
        //overriding the old manager
        if (hasManager()) {
            Person oldManager = getManager();
            employees.remove(oldManager.getUsername());
            Person.allPersons.remove(oldManager.getUsername());
            Person.managers.remove(oldManager.getUsername());
            WarehouseWorker managerToWorker = new WarehouseWorker(oldManager);
            employees.put(managerToWorker.getUsername(), managerToWorker);
        }
        //appointing new manager
        employees.remove(user_name);
        Person.allPersons.remove(user_name);
        Manager newManager = new Manager(person, this);
        employees.put(user_name, newManager);
        Person.managers.put(user_name, newManager);
        setManager(newManager);
    }

    public boolean hasWarehouseWorker() {
        for (Map.Entry<String, Person> entry : employees.entrySet()) {
            if (entry.getValue() instanceof WarehouseWorker) {
                return true;
            }
        }
        return false;
    }
}
