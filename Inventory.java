import java.util.HashMap;

/**
 * Represents a player's inventory.
 */
public class Inventory {
    private HashMap<String, Item> items;
    private int size;
    private int weight;

    /**
     * Constructor for objects of class Inventory.
     */
    public Inventory() {
        items = new HashMap<>();
        size = 100;
        weight = 0;
    }

    /**
     * Adds an item to this inventory.
     *
     * @param key  Item ID.
     * @param item Item object.
     */
    public boolean addItem(String key, Item item) {
        weight = item.getWeight();
        if (weight <= size && size >= 0) {
            items.put(key, item);
            size -= weight;
            return true;
        } else {
            return false;
        }

    }

    public String getItemInfo() {
        StringBuilder output = new StringBuilder();
        if (items.isEmpty()) {
            output.append("Your bag is empty.");
        } else {
            output.append("Items stored in your bag: ")
                    .append(Util.join(items.keySet().stream().toList()))
                    .append('.')
                    .append(" You still have ")
                    .append(size)
                    .append(" space left.");

        }
        return output.toString();
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }
}
