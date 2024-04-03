import java.util.Scanner;

public class InterfaceLoop {
    /**
     * Constructor for Interface loop which does nothing.
     */
    InterfaceLoop() {} //should this ever do anything?

    /**
     * Calls the constructor for the ingredient in order to create one
     * @param init_barcode initial barcode the ingredint holds
     * @param init_name initial name the ingredint holds
     * @param init_weight initial weight the ingredint holds
     * @throws IllegalArgumentException if the ingredient with this barcode already exists
     */
    static void makeIngredient(String init_barcode, String init_name, Integer init_weight) {
        new Ingredient(init_barcode, init_name, init_weight);
        goodFinish();
    }

    /**
     * Displays ingredients on the terminal
     */
    static String displayIngredients() {
        String toReturn = Ingredient.displayIngredients();
        goodDisplayFinish();
        return toReturn;
    }

    /**
     * Calls the constructor for the Location in order to create one
     * @param init_name initial name the Location holds
     * @param init_x_coord initial x coordinate of the location
     * @param init_y_coord initial y coordinate of the location
     * @param init_space_limit initial space limit of the location
     * @throws IllegalArgumentException if the location already exists
     */
    static void makeLocation(String init_name, Integer init_x_coord, Integer init_y_coord, Integer init_space_limit) {
        new Location(init_name, init_x_coord, init_y_coord, init_space_limit);
        goodFinish();
    }

    /**
     * Displays locations on the terminal
     */
    static void displayLocations() {
        Location.displayLocations();
        goodDisplayFinish();
    }

    /**
     * Checks the distance between two points
     * Calls calcDistance in Location
     * @param departure_point Departure (current) location of the drone
     * @param arrival_point (Hoped) Final locaiton of the drone
     */
    static int checkDistance(String departure_point, String arrival_point) {
        //Determine distance and print out what it is
        Location locationOne = Location.getLocation(departure_point);
        Location locationTwo = Location.getLocation(arrival_point);
        //Handing departure error here because would throw a NullPointer in the call to calcDistance

        int yeet = Location.calcDistance(locationOne, locationTwo);
        goodFinish();
        return yeet;
    }

    /**
     * Calls the constructor for the DeliveryService in order to create one
     * @param init_name initial name the Delivery Service holds
     * @param init_revenue initial reveue of the Delivery Service
     * @param located_at Where the Delivery Service is located at
     * @throws IllegalArgumentException if the revenue is negative, the service already exists, or the location doesn't exist
     */
    static void makeDeliveryService(String init_name, Integer init_revenue, String located_at) {
        new DeliveryService(init_name, init_revenue, located_at);
        goodFinish();
    }

    /**
     * Displays Delivery Services on the terminal
     */
    static void displayServices() {
        DeliveryService.displayServices();
        goodDisplayFinish();
    }

    /**
     * Calls the constructor for the restaurant in order to create one
     * @param init_name initial name restaurant holds
     * @param located_at the name of the location it is at
     * @throws IllegalArgumentException if the restaurant already exists or the location doesn't exist
     */
    static void makeRestaurant(String init_name, String located_at) {
        new Restaurant(init_name, located_at);
        goodFinish();
    }

    /**
     * Displays Restaurants on the terminal
     */
    static void displayRestaurants() {
        Restaurant.displayRestaurants();
        goodDisplayFinish();
    }

    /**
     * Calls the constructor for the Location in order to create one
     * @param service_name name of the service making the drone
     * @param init_tag initial tag of the drone
     * @param init_capacity initial capacity of the drone
     * @param init_fuel initial fuel level of the drone
     * @throws IllegalArgumentException if the service doesn't exist, capacity or fuel is negative, the service already has
     * this drone, or the location is too full
     */
    static void makeDrone(String service_name, Integer init_tag, Integer init_capacity, Integer init_fuel) {
        new Drone(service_name, init_tag, init_capacity, init_fuel); //NOTE: Currently passing a string not an object
        goodFinish();
    }

    /**
     * Displays drones for a specific service on the terminal
     * @param service_name name of the service that drones should be displayed for
     */
    static void displayDrones(String service_name) {
        Drone.displayDrones(service_name);
        goodDisplayFinish();
    }

    /**
     * Displays all Drones on the terminal
     */
    static void displayAllDrones() {
        Drone.displayDrones();
        goodDisplayFinish();
    }

    /**
     * Flies a specific drone to a certain location by checking conditions
     * Calls fly in Pilot class
     * @param service_name name of the service flying the drone
     * @param drone_tag tag number of the drone being flown
     * @param destination_name location that the drone wants to fly to
     * @throws IllegalArgumentException if the drone or location doesn't exist
     */
    static void flyDrone(String service_name, Integer drone_tag, String destination_name) {
        Drone drone = Drone.getDrone(service_name, drone_tag);
        if (!drone.hasValidPilot()) {
            throw new IllegalArgumentException("This drone is not assigned to a valid pilot.");
        }
        Pilot currentPilot = Pilot.getPilot(drone.getPilotLicense());
        currentPilot.fly(destination_name, drone_tag, service_name);
        goodFinish();
    }

    /**
     * Loads an ingredient onto the drone by checking conditions
     * Then calls loadIngredient in the drone class.
     * checks if service, drone exists, if drone is at homebase, and and there are employees at homebase before passing
     * off loading fuel to warehouse worker
     * @param service_name name of the service operating the drone
     * @param drone_tag tag number of the drone being operated
     * @param barcode barcode of the ingredient being loaded
     * @param quantity amount of the ingredient being loaded
     * @param unit_price price of the ingredient being loaded
     * @throws IllegalArgumentException if the price is negative, the service doesn't exist, the drone doesn't exist,
     * the ingredient doesn't exist, or the drone isn't at home base
     */
    static void loadIngredient(String service_name, Integer drone_tag, String barcode, Integer quantity, Integer unit_price) {
        if (!DeliveryService.exists(service_name)) {
            throw new IllegalArgumentException("This delivery service does not exist!");
        }
        if (!Drone.exists(service_name, drone_tag)) {
            throw new IllegalArgumentException("There does not exist a drone with this tag.");
        }

        Drone drone = Drone.getDrone(service_name, drone_tag);
        DeliveryService service = DeliveryService.getService(service_name);

        WarehouseWorker.workerLoad(service, drone, barcode, quantity, unit_price);
        goodFinish();
    }

    /**
     * Loads fuel into a drone
     * Calls method in warehouse worker then in drone
     * @param service_name name of the service operating the drone
     * @param drone_tag tag number of the drone being operated
     * @param petrol amount of fuel to be loaded
     * @throws IllegalArgumentException if the fuel is negative, the drone is not at home base, or the drone doesn't exist
     */
    static void loadFuel(String service_name, Integer drone_tag, Integer petrol) {
        if (!DeliveryService.exists(service_name)) {
            throw new IllegalArgumentException("The service does not exist.");
        }
        if (!Drone.exists(service_name, drone_tag)) {
            throw new IllegalArgumentException("There does not exist a drone with this tag.");
        }
        DeliveryService service = DeliveryService.getService(service_name);
        Drone drone = Drone.getDrone(service_name, drone_tag);

        WarehouseWorker.workerFuel(service, drone, petrol);
        goodFinish();
    }

    /**
     * Purchases an ingredient for the restaurant from the drone by checking conditions.
     * Calls purchaseIngredient in restaurant class
     * @param restaurant_name name of the restaurant paying for the ingredient
     * @param service_name name of the service operating the drone
     * @param drone_tag number of the drone being operated
     * @param barcode barcode of ingredient being purchased
     * @param quantity amount of ingredient being purchased
     * @throws IllegalArgumentException if the restaurant doesn't exist, the drone doesn't exist, or the
     * ingredient doesn't exist
     */
    static void purchaseIngredient(String restaurant_name, String service_name, Integer drone_tag, String barcode, Integer quantity) {
        if (!(Restaurant.exists(restaurant_name))) {
            throw new IllegalArgumentException("This restaurant does not exist.");
        }
        Restaurant restaurant = Restaurant.getRestaurant(restaurant_name);
        restaurant.purchaseIngredient(service_name, drone_tag, barcode, quantity);
        goodFinish();
    }

    /**
     * Makes a person
     * Calls person constructor
     * @param init_username initial username of the person
     * @param init_fname initial first name of the person
     * @param init_lname initial last name of the person
     * @param init_year initial birth year of the person
     * @param init_month initial birth month of the person
     * @param init_date initial birth date of the person
     * @param init_address initial address of the person
     * @throws IllegalArgumentException if the person already exists or the date is inputed incorrectly
     */
    static void makePerson(String init_username, String init_fname, String init_lname,
        Integer init_year, Integer init_month, Integer init_date, String init_address) {
        new Person(init_username, init_fname, init_lname, init_year, init_month, init_date, init_address);
        goodFinish();
    }

    /**
     * Displays all Persons on the terminal
     */
    static void displayPersons() {
        Person.displayPersons();
        goodDisplayFinish();
    }

    /**
     * Hires a person to a specific service
     * Calls hireWorker in DeliveryService
     * @param service_name service the person should be hired to
     * @param user_name user name of worker to be hired
     * @throws IllegalArgumentException if the person already works here or
     * is currently a manager or pilot at another service
     */
    static void hireWorker(String service_name, String user_name) {
        DeliveryService service = DeliveryService.getService(service_name);
        service.hireWorker(service_name, user_name);
        goodFinish();
    }

    /**
     * Fires a person to a specific service
     * Calls fireWorker in DeliveryService
     * @param service_name service the person should be fired fron
     * @param user_name user name of worker to be fired
     * @throws IllegalArgumentException if the person doesn't work here
     * is currently a manager or pilot at another service
     */
    static void fireWorker(String service_name, String user_name) {
        DeliveryService service = DeliveryService.getService(service_name);
        service.fireWorker(service_name, user_name);
        goodFinish();
    }

    /**
     * Appoints a person to manager in a specific service
     * Calls serviceAppointManager in DeliveryService
     * @param service_name service the person should be appointed to manager at
     * @param user_name user name of worker to be appointed
     * @throws IllegalArgumentException if the person already doesn't exist or they work at a different servic
     */
    static void appointManager(String service_name, String user_name) {
        DeliveryService service = DeliveryService.getService(service_name);//throws exception if doesn't exist
        service.serviceAppointManager(user_name);
        goodFinish();

    }

    /**
     * Trains a pilot
     * Calls serviceTrainPilot in deliveryservice
     * @param service_name name of the service training the person
     * @param user_name username of the person to be trained
     * @param init_license initial licence of the person
     * @param init_experience initial experience of the person
     * @throws IllegalArgumentException if the person is not employeed or is a manager
     */
    static void trainPilot(String service_name, String user_name, String init_license, Integer init_experience) {
        DeliveryService service = DeliveryService.getService(service_name);//throws exception if doesn't exist
        Person person = Person.getPerson(user_name); //throws exception if doesn't exist
        service.serviceTrainPilot(person, init_license, init_experience);
        goodFinish();

    }

    /**
     * Appoints a pilot
     * Calls serviceAppointPilot in deliveryservice
     * @param service_name name of the service appointing the pilot
     * @param user_name username of the person to be appointed
     * @param drone_tag drone being assigned to pilot
     * @throws IllegalArgumentException if the person isn't trained or isn't employeed by the service
     */
    static void appointPilot(String service_name, String user_name, Integer drone_tag) {
        DeliveryService service = DeliveryService.getService(service_name);//throws exception if doesn't exist
        service.serviceAppointPilot(service_name, user_name, drone_tag);
        goodFinish();
    }

    /**
     * Adds a drone to a swarm
     * Calls joinSwarm in drone class
     * @param service_name name of the service the drone belongs to
     * @param lead_drone_tag drone to lead the swarm
     * @param swarm_drone_tag drone being assigned to swarm
     * @throws IllegalArgumentException if the drones aren't in the same location
     * or the lead drone is already part of a swarm
     */
    static void joinSwarm(String service_name, Integer lead_drone_tag, Integer swarm_drone_tag) {
        Drone leadDrone = Drone.getDrone(service_name, lead_drone_tag);
        leadDrone.joinSwarm(service_name, swarm_drone_tag);
        goodFinish();
    }

    /**
     * Removes a drone to a swarm
     * Calls leaveSwarm in drone class
     * @param service_name name of the service the drone belongs to
     * @param swarm_drone_tag drone leaving swarm
     * @throws IllegalArgumentException if the drone is the lead drone
     */
    static void leaveSwarm(String service_name, Integer swarm_drone_tag) {
        Drone swarmDrone = Drone.getDrone(service_name, swarm_drone_tag);
        swarmDrone.leaveSwarm(service_name);
        goodFinish();
    }

    /**
     * Collects revenue for a service
     * Calls collectRevenue in a service
     * @param service_name name of the service to collect revenue
     */
    static void collectRevenue(String service_name) {
        DeliveryService.getService(service_name).collectRevenue();
        goodFinish();
    }

    /**
     * Displays a success message on the terminal
     * To be used after successfuly completing an action
     */
    static void goodFinish() {
        System.out.println("Success!");
    }

    /**
     * Displays a success message on the terminal
     * To be used after successfuly completing an display
     */
    static void goodDisplayFinish() {
        System.out.println("Display Success!");
    }

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if (tokens[0].indexOf("//") == 0) {
                    // deliberate empty body to recognize and skip over comments

                } else if (tokens[0].equals("make_ingredient")) {
                    makeIngredient(tokens[1], tokens[2], Integer.parseInt(tokens[3]));

                } else if (tokens[0].equals("display_ingredients")) {
                    displayIngredients();

                } else if (tokens[0].equals("make_location")) {
                    makeLocation(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));

                } else if (tokens[0].equals("display_locations")) {
                    displayLocations();

                } else if (tokens[0].equals("check_distance")) {
                    checkDistance(tokens[1], tokens[2]);

                } else if (tokens[0].equals("make_service")) {
                    makeDeliveryService(tokens[1], Integer.parseInt(tokens[2]), tokens[3]);

                } else if (tokens[0].equals("display_services")) {
                    displayServices();

                } else if (tokens[0].equals("make_restaurant")) {
                    makeRestaurant(tokens[1], tokens[2]);

                } else if (tokens[0].equals("display_restaurants")) {
                    displayRestaurants();

                } else if (tokens[0].equals("make_drone")) {
                    makeDrone(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));

                } else if (tokens[0].equals("display_drones")) {
                    displayDrones(tokens[1]);

                } else if (tokens[0].equals("display_all_drones")) {
                    displayAllDrones();

                } else if (tokens[0].equals("fly_drone")) {
                    flyDrone(tokens[1], Integer.parseInt(tokens[2]), tokens[3]);

                } else if (tokens[0].equals("load_ingredient")) {
                    loadIngredient(tokens[1], Integer.parseInt(tokens[2]), tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));

                } else if (tokens[0].equals("load_fuel")) {
                    loadFuel(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));

                } else if (tokens[0].equals("purchase_ingredient")) {
                    purchaseIngredient(tokens[1], tokens[2], Integer.parseInt(tokens[3]), tokens[4], Integer.parseInt(tokens[5]));

                } else if (tokens[0].equals("make_person")) {
                    makePerson(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), tokens[7]);

                } else if (tokens[0].equals("display_persons")) {
                    displayPersons();

                } else if (tokens[0].equals("hire_worker")) {
                    hireWorker(tokens[1], tokens[2]);

                } else if (tokens[0].equals("fire_worker")) {
                    fireWorker(tokens[1], tokens[2]);

                } else if (tokens[0].equals("appoint_manager")) {
                    appointManager(tokens[1], tokens[2]);

                } else if (tokens[0].equals("train_pilot")) {
                    trainPilot(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]));

                } else if (tokens[0].equals("appoint_pilot")) {
                    appointPilot(tokens[1], tokens[2], Integer.parseInt(tokens[3]));

                } else if (tokens[0].equals("join_swarm")) {
                    joinSwarm(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));

                } else if (tokens[0].equals("leave_swarm")) {
                    leaveSwarm(tokens[1], Integer.parseInt(tokens[2]));

                } else if (tokens[0].equals("collect_revenue")) {
                    collectRevenue(tokens[1]);

                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }

    void displayMessage(String status, String text_output) {
        System.out.println(status.toUpperCase() + ":" + text_output.toLowerCase());
    }
}
