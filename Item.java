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
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param y a sample parameter for a method
     * @return the sum of x and y
     */

    public String getDescription() {
        return description;
    }

    public void printItem() {
        System.out.println(" " + description);
    }
}
