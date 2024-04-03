
public class WarehouseWorker extends Person {

    WarehouseWorker(String init_username, String init_fname, String init_lname, Integer init_year,
        Integer init_month, Integer init_date, String init_address) {
        super(init_username, init_fname, init_lname, init_year,
        init_month, init_date, init_address);
        isEmployed = true;
            
    }

    public WarehouseWorker(Person future) {
        this(future.getUsername(), future.getFirstName(), future.getLastName(), future.getBirthYear(), future.getBirthMonth(), future.getBirthDate(), future.getAddress());
        this.pilotLicense = future.getLicense();
        this.servicesWorking = future.getServicesWorking();
        Person.unemployed.remove(future.getUsername());
        warehouseWorkers.put(future.getUsername(), this);
    }

    public static void workerLoad(DeliveryService service, Drone drone, String barcode, Integer quantity, Integer unit_price) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be a nonnegative integer!");
        }
        if (unit_price < 0) {
            throw new IllegalArgumentException("Price must be a nonnegative integer!");
        }
        if (!Ingredient.exists(barcode)) {
            throw new IllegalArgumentException("An ingredient with this barcode does not exist!");
        }
        
        if (!drone.isAtBase()) {
            throw new IllegalArgumentException("The drone needs to be at home base to load ingredients!");
        }

        if (!service.hasWarehouseWorker()) {
            throw new IllegalArgumentException("no warehouse workers present at service");
        }

        drone.loadIngredients(barcode, quantity, unit_price);

    }
    public static void workerFuel(DeliveryService service, Drone drone, Integer petrol) {
        if (petrol < 0) {
            throw new IllegalArgumentException("Negative fuel not allowed");
        }
        if (!drone.isAtBase()) {
            throw new IllegalArgumentException("Drone Must be at Home Base to be Fueled");
        }
        if (!service.hasWarehouseWorker()) {
            throw new IllegalArgumentException("no warehouse workers present at service");
        }
        drone.acceptFuel(petrol);
    }

}