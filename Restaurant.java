import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Restaurant implements Comparable<Restaurant> {
    //Variables
    private int rating;
    private String name;
    private Location location;
    private int moneySpent;
    private int funding;

    //Defaults
    final static private int DEFAULT_RATING = 3;

    //Static collection
    private static TreeMap<String, Restaurant> restaurants = new TreeMap<>();

    //Constructors
    public Restaurant(String name, int rating, String location_name) {
        this(name,rating,location_name,0);
    }

    public Restaurant(String name, String location_name) {
        this(name, DEFAULT_RATING, location_name, 0);
    }
    public Restaurant(String name, int rating, String location_name, int moneySpent) {
        if (Restaurant.exists(name)) {
            throw new IllegalArgumentException("There is already a restaurant with this name! Please reenter with a different name.");
        }
        if (!(Location.exists(location_name))) {
            throw new IllegalArgumentException("The desired location does not exist!");
        }
        this.name = name;
        this.rating = rating;
        this.location = Location.getLocation(location_name);
        this.moneySpent = moneySpent;
        restaurants.put(name, this);
    }

    //Active Methods

    /**
     * Purchases an ingredient for the restaurant from the drone by checking conditions.
     * Calls removeIngredient in drone class
     * @param drone drone with ingredient on it
     * @param barcode barcode of ingredient being purchased
     * @param quantitiy amount of ingredient being purchased
     * @param ingredientObject object of the ingredient
     */
    void purchaseIngredient(String service_name, Integer drone_tag, String barcode, Integer quantity) {
        if (!DeliveryService.exists(service_name)) {
            throw new IllegalArgumentException("The service does not exist.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity of ingredient cannot be negative.");
        }

        Drone drone = Drone.getDrone(service_name, drone_tag);

        if (!(Drone.exists(service_name, drone_tag))) {
            throw new IllegalArgumentException("There does not exist a drone with this tag.");
        }
        if (!Ingredient.exists(barcode)) {
            throw new IllegalArgumentException("This ingredient does not exist in any capacity whatsoever!");
        }
        if (!(this.getLocation().equals(drone.getDroneLocation()))) {
            throw new IllegalArgumentException("Drone not located at restaurant!");
        }
        //if all cases passes the transaction happens
        int price = drone.removeIngredient(barcode, quantity);
        transferMoney(price * quantity, drone);
    }

    /**
     * Transfer of money from the restaurant to the drone
     * @param amount amount of money getting transfered
     * @param drone drone to transfer revenue to
     */
    void transferMoney(int amount, Drone drone) {
        drone.setRevenue(drone.getRevenue() + amount);
        moneySpent += amount;
    }

    public static boolean exists(String name) {
        if(restaurants.containsKey(name)) {
            return true;
        }
        return false;
    }

    public static String getDisplayRestaurantsAsString() {
        String guiText = "";
        for (Map.Entry<String,Restaurant> entry: restaurants.entrySet()) {
            guiText += entry.getValue().toString() + "\n";
        }
        return guiText;
    }

    public static void displayRestaurants() {
        for (Map.Entry<String,Restaurant> entry: restaurants.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    //Getters
    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public int getFunding() {
        return funding;
    }

    public static TreeMap<String, Restaurant> getRestaurants() {
        return restaurants;
    }

    public static Restaurant getRestaurant(String name) {
        if(restaurants.containsKey(name)) {
            return restaurants.get(name);
        }
        throw new IllegalArgumentException("This restaurant does not exist");
    }

    public static List<String> getRestaurantNames() {
        List<String> names = new ArrayList<>(restaurants.size());
        for (Entry<String, Restaurant> entry : restaurants.entrySet()) {
            names.add(entry.getKey());
        }
        return names;
    }




    //Setters
    public void setRating(int rating) {
        if (!(rating <= 5 && rating >= 1)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5!!");
        }
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFunding(int funding) {
        this.funding = funding;
    }

    //toString Override
    @Override
    public String toString() {
        return String.format("Restaurant %s at location %s with money spent %d.", name, location.getName(), moneySpent);
    }

    //Comparable Requirement
    public int compareTo(Restaurant other) {
        if (!(name.compareTo(other.getName()) == 0)) {
            return name.compareTo(other.getName());
         }
        if (rating - other.getRating() != 0) {
            return rating - other.getRating();
        }
        return location.compareTo(other.getLocation());
    }
}
