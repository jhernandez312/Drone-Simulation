public class Manager extends Person {
    // Variables

    // Constructors
    public Manager(String init_username, String init_fname, String init_lname, Integer init_year,
        Integer init_month, Integer init_date, String init_address) {
        super(init_username, init_fname, init_lname, init_year, init_month, init_date, init_address); //currently, restuarant owner does not have any additional attributess
    }

    public Manager(Person future, DeliveryService appointer) {
        this(future.getUsername(), future.getFirstName(), future.getLastName(), future.getBirthYear(), future.getBirthMonth(), future.getBirthDate(), future.getAddress());
        this.isEmployed = true;
        this.servicesWorking = future.getServicesWorking();
        this.pilotLicense = future.getLicense();
        this.successfulFlights = future.getSuccessfulFlights();
        Person.unemployed.remove(future.getUsername());
    }

    public String toString() {
        String toReturn = super.toString();
        toReturn += String.format("%s is the very important very bossy manager of %s!\n", firstName, servicesWorking.firstKey());
        return toReturn;
    }
}