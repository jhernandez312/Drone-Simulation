import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class Person implements Comparable<Person> {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected Integer birthYear;
    protected Integer birthMonth;
    protected Integer birthDate;
    protected String address;
    protected boolean isEmployed;
    protected String pilotLicense;
    protected Integer successfulFlights;
    protected SortedMap<String, DeliveryService> servicesWorking = new TreeMap<>();

    static SortedMap<String, Person> allPersons  = new TreeMap<>(); //key is username
    static SortedMap<String, Person> unemployed = new TreeMap<>(); //key is username
    static SortedMap<String,Person> warehouseWorkers = new TreeMap<>();
    static SortedMap<String,Pilot> pilots = new TreeMap<>();
    static SortedMap<String,Person> managers = new TreeMap<>();
    static SortedMap<String,Person> owners = new TreeMap<>();


    //Constructor
    Person(String init_username, String init_fname, String init_lname, Integer init_year,
        Integer init_month, Integer init_date, String init_address) {
        if (exists(init_username)) {
            throw new IllegalArgumentException("A person with this username already exists!");
        }
        this.username = init_username;
        this.firstName = init_fname;
        this.lastName = init_lname;
        this.birthYear = init_year;
        if (init_month < 1 || init_month > 12) {
            throw new IllegalArgumentException("This is an invalid month!");
        }
        this.birthMonth = init_month;
        if (init_date < 1) {
            throw new IllegalArgumentException("Negative or zero day is not allowed!");
        }
        if (init_month == 1 || init_month == 3 || init_month == 5 || init_month == 7 || init_month == 8 || init_month == 10 || init_month == 12) {
            if (init_date > 31) {
                throw new IllegalArgumentException("Day is invalid for 31 day month!");
            }
        } else if (init_month == 4 || init_month == 6 || init_month == 9 || init_month == 11) {
            if (init_date > 30) {
                throw new IllegalArgumentException("Day is invalid for 30 day month!");
            }
        } else {
            if (init_year % 4 == 0) {
                if (init_year % 100 == 0) {
                    if (init_year % 400 == 0) {
                        if (init_date > 29) {
                            throw new IllegalArgumentException("Invalid date for February during a leap year!");
                        }
                    } else {
                        if (init_date > 28) {
                            throw new IllegalArgumentException("Invalid date for February during a non-leap year!");
                        }
                    }
                }
            } else {
                if (init_date > 28) {
                    throw new IllegalArgumentException("Invalid date for February during a non-leap year!");
                }
            }
        }
        this.birthDate = init_date;
        this.pilotLicense = null;
        this.address = init_address;
        this.isEmployed = false;
        allPersons.put(this.username, this);
        unemployed.put(this.username, this);

        this.servicesWorking = new TreeMap<>();
    }

    public static String getDisplayPersonsAsString() {
        String guiText = "";
        for (Map.Entry<String, Person> entry : allPersons.entrySet()) {
            guiText += entry.getValue().toString() + "\n";
        }
        return guiText;
    }

    public static void displayPersons() {
        for (Map.Entry<String, Person> entry : allPersons.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public static boolean exists(String username) {
        if (allPersons.containsKey(username)) {
            return true;
        }
        return false;
    }

    public static List<String> getPersonNames() {
        List<String> names = new ArrayList<>(allPersons.size());
        for (Map.Entry<String, Person> entry : allPersons.entrySet()) {
            names.add(entry.getKey());
        }
        return names;
    }

    public static List<String> getPersonNamesNotEmployedAt(DeliveryService service) {
        List<String> usernames = new ArrayList<>(allPersons.keySet());
        for (Map.Entry<String, Person> entry : allPersons.entrySet()) {
            if (service.getEmployees().containsKey(entry.getKey())) {
                usernames.remove(entry.getKey());
            }
        }
        return usernames;
    }

    //Getters
    public String getUsername() {
        return username;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Integer getBirthYear() {
        return birthYear;
    }
    public Integer getBirthMonth() {
        return birthMonth;
    }
    public Integer getBirthDate() {
        return birthDate;
    }
    public String getAddress() {
        return address;
    }
    static SortedMap<String, Person> getPersons() {
        return allPersons;
    }
    static SortedMap<String, Person> getWarehouseWorkers() {
        return warehouseWorkers;
    }
    static SortedMap<String, Pilot> getPilots() {
        return pilots;
    }
    static SortedMap<String, Person> getManagers() {
        return managers;
    }
    static SortedMap<String, Person> getOwners() {
        return owners;
    }
    static SortedMap<String, Person> getUnemployed() {
        return unemployed;
    }
    static Person getPerson(String username) {
        if (allPersons.containsKey(username)) {
            return allPersons.get(username);
        }
       throw new IllegalArgumentException("This person does not exist!");
    }

    public String getLicense() {
        return pilotLicense;
    }
    public Integer getSuccessfulFlights() {
        return successfulFlights;
    }
    public SortedMap<String, DeliveryService> getServicesWorking() {
        return servicesWorking;
    }
    public boolean getIsEmployed() {
        return isEmployed;
    }


    //Setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }
    public void setBirthDate(Integer birthDate) {
        this.birthDate =  birthDate;
    }
    public void setAddress(String address) {
        this.address =  address;
    }
    public void setIsEmployed(Boolean employ) {
        this.isEmployed = employ;
    }
    public void setPilotLicense(String p) {
        pilotLicense = p;
    }
    public void setSuccessfulFlights(Integer f) {
        successfulFlights = f;
    }
    public void setServicesWorking(DeliveryService service, Boolean add) {
        if (add) {
            this.servicesWorking.put(service.getName(), service);

        } else {
            this.servicesWorking.remove(service.getName());
        }
    }

    //toString
    public String toString() {
        String toReturn = String.format("Username: %s, Name: %s %s, Birthday: %d-%d-%d, Address: %s\n", username, firstName, lastName, birthYear, birthMonth, birthDate, address);
        if (isEmployed) {
            toReturn += String.format("%s works at the following services: ", firstName);

            for (Map.Entry<String, DeliveryService> entry : servicesWorking.entrySet()) {
                toReturn += entry.getValue().getName();
                if (entry.getKey() != servicesWorking.lastKey()) {
                    toReturn += ", ";
                }
            }
            toReturn += "\n";
        } else {
            toReturn += String.format("%s is unemployed!\n", firstName);
        }
        if (!(this instanceof Pilot) && this.pilotLicense != null) {
            toReturn += String.format("%s has a pilot's license (%s) and %d successful flights.\n", firstName, pilotLicense, successfulFlights);
        }
        return toReturn;
    }


    //Comparable requirement
    public int compareTo(Person other) {
        if (!username.equals(other.getUsername())) {
            return username.compareTo(other.getUsername());
        }
        if (!firstName.equals(other.getFirstName())) {
            return firstName.compareTo(other.getFirstName());
        }
        if (!lastName.equals(other.getLastName())) {
            return lastName.compareTo(other.getLastName());
        }
        if (birthYear != other.getBirthYear()) {
            return birthYear - other.getBirthYear();
        }
        if (birthMonth != other.getBirthMonth()) {
            return birthMonth - other.getBirthMonth();
        }
        if (birthDate != other.getBirthDate()) {
            return birthDate - other.getBirthDate();
        }
        return address.compareTo(other.getAddress());
    }
}
