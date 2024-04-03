import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.TreeMap;
import java.util.Map;

public class Pilot extends Person {
    private List<Drone> dronesPiloting;
    //Constructor
    Pilot(String init_username, String init_fname, String init_lname, Integer init_year, 
        Integer init_month, Integer init_date, String init_address, String init_license, Integer init_experience) {
        super(init_username, init_fname, init_lname, init_year, init_month, init_date, init_address);
        this.pilotLicense = init_license;
        this.successfulFlights = init_experience;
        isEmployed = true;
        dronesPiloting = new ArrayList<Drone>(5);
        pilots.put(pilotLicense, this);
    }

    Pilot(Person future) {
        this(future.getUsername(), future.getFirstName(), future.getLastName(), future.getBirthYear(), future.getBirthMonth(), future.getBirthDate(), future.getAddress(), future.getLicense(), future.getSuccessfulFlights());
        servicesWorking = future.servicesWorking;
        pilots.put(pilotLicense, this);
        //dronesPiloting = new ArrayList<Drone>(5);
    }
    //Getters
    public List<Drone> getDronesPiloting() {
        return dronesPiloting;
    }
    static Pilot getPilot(String license) {
        if(pilots.containsKey(license)) {
            return pilots.get(license);
        } else {
            throw new IllegalArgumentException("this pilot does not exist");
        }
    }

    //active methods

    /**
    * Flies a drone to a location
    * @param destination location that the drone wants to fly to
    */
    public void fly(String destination_name, Integer drone_tag, String service_name) {
        Drone drone = Drone.getDrone(service_name, drone_tag);
        Location destination = Location.getLocation(destination_name); // this replaces the functionality of Location.exists()
        if (destination.isFull() || destination.getSpace_lim() - destination.getCurrentNumDrones() <= drone.getSwarm().size()) {
            throw new IllegalArgumentException("This location is full!");
        }
        Location departingLocation = drone.getDroneLocation();
        Location droneHomebase = drone.getHomebase();
        Integer droneFuel = drone.getFuel();
        TreeMap<Integer, Drone> swarm = drone.getSwarm();
        int distance_to_destination = Location.calcDistance(departingLocation, destination);
        int distance_destination_to_home = Location.calcDistance(droneHomebase, destination);
        int total_min_path = distance_to_destination + distance_destination_to_home;
        if (droneFuel < distance_to_destination) {
            throw new IllegalArgumentException("The lead drone does not have enough fuel to get to the destination.");
        }
        if (droneFuel < total_min_path) {
            throw new IllegalArgumentException("The leaddrone does not have enough fuel to get home!");
        }
        if(departingLocation.equals(destination)) {
            throw new IllegalArgumentException("destination is the same as drones current location");
        }
        for (Map.Entry<Integer, Drone> entry : swarm.entrySet()) {
            Integer swarmDroneFuel = entry.getValue().getFuel();
            if (swarmDroneFuel < distance_to_destination) {
                throw new IllegalArgumentException("One of the swarm drones does not have enough fuel to get to the destination.");
            }
            if (swarmDroneFuel < distance_destination_to_home) {
                throw new IllegalArgumentException("One of the swarm drones does not have enough fuel to get home.");
            }
            departingLocation.subtractDrone();
            entry.getValue().setDroneLocation(destination);
            entry.getValue().useFuel(distance_to_destination);
            destination.addDrone();
        }
        departingLocation.subtractDrone();
        drone.setDroneLocation(destination);
        drone.useFuel(distance_to_destination);
        destination.addDrone();
        successfulFlights++;
    }



    public static boolean exists(String license) {
        if(pilots.containsKey(license)) {
            return true;
        }
        return false;
    }

    public String toString() {
        String toReturn = super.toString();
        toReturn += String.format("%s is a certified pilot with license %s and %d successful flights.\n", firstName, pilotLicense, successfulFlights);
        if (dronesPiloting.size() > 0) {
            toReturn += String.format("%s is appointed to the following drones: ", firstName);
            for (int i=0; i < dronesPiloting.size(); i++) {
                toReturn += dronesPiloting.get(i).getDroneID();
                if (i != dronesPiloting.size() - 1) {
                    toReturn += ", ";
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }
}