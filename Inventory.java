import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * Write a description of class Inventory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Inventory
{
    // instance variables - replace the example below with your own
    private HashMap<String, Item> items;
    
    /**
     * Constructor for objects of class Inventory
     */
    public Inventory()
    {
        // initialise instance variables
        items = new HashMap<>();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void printItems()
    {
        System.out.println(getItemInfo());
        System.out.println();
     }
     
    public void addItem(String key, Item item)
    {    
        items.put(key, item);
    }

    public String getItemInfo() {
        List<String> output = new ArrayList<>();
        if(items.isEmpty())
        {
            output.add("Your bag is empty.");
        }
        else {

            Set<String> itemSet = items.keySet();
            for (String e : itemSet) {
                output.add(items.get(e).getDescription());
            }
        }
        return String.join(", ", output);
    }
}
