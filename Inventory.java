import java.util.ArrayList;
import java.util.HashMap;
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
        if(items.isEmpty())
        {
            System.out.println("Your bag is empty");
            return;
        }
        Set<String> itemSet = items.keySet();
        for (String e: itemSet){
            System.out.print (items.get(e).getDescription() + " ");
        }
        System.out.println();
     }
     
    public void addItem(String key, Item item)
    {    
        items.put(key, item);
    }
     
}
