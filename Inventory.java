import java.util.HashMap;

/**
 * Represents a player's inventory.
 */
public class Inventory {
    private HashMap<String, Item> items;

    /**
     * Constructor for objects of class Inventory.
     */
    public Inventory() {
        items = new HashMap<>();
    }

    /**
     * Adds an item to this inventory.
     * @param key Item ID.
     * @param item Item object.
     */
    public void addItem(String key, Item item) {
        items.put(key, item);
    }

    public String getItemInfo() {
        StringBuilder output = new StringBuilder();
        if (items.isEmpty()) {
            output.append("Your bag is empty.");
        } else {
            output.append("Your bag contains ")
                    .append(Util.join(items.values().stream().map(Item::getDescription).toList()))
                    .append('.');

        }
        return output.toString();
    }

}
