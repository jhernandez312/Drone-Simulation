import java.util.SortedMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Location implements Comparable<Location> {
    // Variables
    private String name;
    private int x_coord;
    private int y_coord;
    private int space_lim;
    private int curr_num_drones;

    // Static Collection

    private static SortedMap<String, Location> locations = new TreeMap<>(); //key is the location name

    // Constructors
    public Location(String name, int x_coord, int y_coord, int space_lim) {
        if (exists(name)) {
            throw new IllegalArgumentException("Location with this name already exists! Please reenter with a different name.");
        }
        if (space_lim < 0) {
            throw new IllegalArgumentException("Initial space limit cannot be negative");
        }
        this.name = name;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.space_lim = space_lim;
        this.curr_num_drones = 0;
        //Add to collection
        locations.put(name, this);
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getX_coord() {
        return x_coord;
    }

    public int getY_coord() {
        return y_coord;
    }

    public int getSpace_lim() {
        return space_lim;
    }

    public int getCurrentNumDrones() {
        return curr_num_drones;
    }

    public boolean isFull() {
        return curr_num_drones >= space_lim;
    }

    public static Location getLocation(String location_name) {

        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            if (entry.getKey().equals(location_name)) {
                return entry.getValue();
            }
        }
        throw new IllegalArgumentException("The desired location does not exist!");
    }

    public static SortedMap<String, Location> getLocations() {

        return locations;
    }

    public static List<String> getLocationNames() {
        List<String> names = new ArrayList<>(locations.size());
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            names.add(entry.getKey());      
        }
        return names;
    }

    public static boolean exists(String location_name) {
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            if (entry.getKey().equals(location_name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the number of drones at this location
     */
    public void addDrone() {
        curr_num_drones++;
    }

    /**
     * Subtracts the number of drones at this location
     */
    public void subtractDrone() {
        curr_num_drones--;
    }

    /**
     * Subtracts the number of drones at this location
     * @param destination calculates the distance from thsi location to the destination
     * @return integer of the distance
     */
    public static Integer calcDistance(Location start, Location destination) {
        //Handling destination here to maximize encapsulation
        if (start == null) {
            throw new IllegalArgumentException("Departure point does not exist!");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Arrival point does not exist!");
        }
        if (start.equals(destination)) {
            return 0;
        }
        return 1 + (int) Math.floor(Math.sqrt(Math.pow(start.getX_coord() - destination.getX_coord(), 2) + Math.pow(start.getY_coord() - destination.getY_coord(), 2)));
    }

    public static String getDisplayLocationsString() {
        String toReturn = "";
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            toReturn += entry.getValue() + "\n";
        }
        return toReturn;
    }
        
    public static void displayLocations() {
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    // Setters
    public void setX_coord(int x_coord) {
        this.x_coord = x_coord;
    }

    public void setY_coord(int y_coord) {
        this.y_coord = y_coord;
    }

    //toString
    @Override
    public String toString() {
        return String.format("Location %s at coordinates (%d, %d) with space remaining [%d/%d].", name, x_coord, y_coord, space_lim - curr_num_drones, space_lim);
    }

    //Comparable Requirement
    public int compareTo(Location other) {
         if (!(name.compareTo(other.getName()) == 0)) {
            return name.compareTo(other.getName());
         }
         if (!(x_coord - other.getX_coord() == 0)) {
             return x_coord - other.getX_coord();
         }
         if (!(y_coord - other.getY_coord() == 0)) {
            return x_coord - other.getY_coord();
        }
        return space_lim - other.getSpace_lim();
    }
}
