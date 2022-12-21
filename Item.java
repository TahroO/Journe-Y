/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item {
    private String id;
    private String name;
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String id, String name, String description, int weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public Item(String description)
    {
        this(null,null,description,0);
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }
}
