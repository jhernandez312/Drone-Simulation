public class RestaurantOwner extends Person {
    //NOTE: This was originally requried for phase 1 so is included for completion
    //HOWEVER, it is not currently being used.
    // Variables

    // Constructors
    public RestaurantOwner(String init_username, String init_fname, String init_lname, Integer init_year,
        Integer init_month, Integer init_date, String init_address) {
        super(init_username, init_fname, init_lname, init_year, init_month, init_date, init_address); //currently, restuarant owner does not have any additional attributess
    }

    // Methods
    public void fund(Restaurant restaurant, int fundAmount) {
        restaurant.setFunding(restaurant.getFunding() + fundAmount);
    }
}