import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ingredient implements Comparable<Ingredient> {
    //Variables
    private String barcode;
    private String name;
    private Integer weight;

    //Static collection
    private static TreeMap<String, Ingredient> ingredients = new TreeMap<>();

    // Constructors
    public Ingredient(String barcode, String name, Integer weight) {
        if (Ingredient.exists(barcode)) {
            throw new IllegalArgumentException("An ingredient with this barcode already exists!");
        }
        this.barcode = barcode;
        this.name = name;
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a nonnegative integer!");
        }
        this.weight = weight;
        ingredients.put(barcode, this);;
    }

    // Setters
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    // Getters
    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public static TreeMap<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public static List<String> getIngredientBarcodes() {
        List<String> barcodes = new ArrayList<>(ingredients.size());
        for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()) {
            barcodes.add(entry.getKey());
        }
        return barcodes;
    }

    public static boolean exists(String barcode) {
        if(ingredients.containsKey(barcode)) {
            return true;
        }
        return false;
    }

    public static Ingredient getIngredient(String barcode) {
        if(ingredients.containsKey(barcode)) {
            return ingredients.get(barcode);
        } else {
            throw new IllegalArgumentException("This ingredient does not exist");
        }
    }

    public static String displayIngredients() {
        String guiText = "";
        for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()) {
            System.out.println(entry.getValue());
            guiText += entry.getValue() + "\n";
        }
        return guiText;
    }

    //toString
    @Override
    public String toString() {
        return String.format("Ingredient barcode: %s, name: %s, weight: %d", barcode, name, weight);
    }

    //Comparable Requirement
    public int compareTo(Ingredient other) {
        if (!(barcode.compareTo(other.getBarcode()) == 0)) {
            return barcode.compareTo(other.getBarcode());
        }
        if (!(name.compareTo(other.getName()) == 0)) {
            return name.compareTo(other.getName());
        }
        return weight - other.getWeight();
    }
}