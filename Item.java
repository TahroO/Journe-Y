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

    /**
     * Constructor for objects of class Item
     */
    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Item(String description)
    {
        this(null,null,description);
    }

    public String getDescription() {
        return description;
    }

}
