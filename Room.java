import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String audioPath;
    private HashMap<String,Room> exits;
    private HashMap<String,Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String id, String name, String description, String image, String audio)
    {
        this.id = id;
        this.name = name;
        this.imagePath = image;
        this.audioPath = audio;
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    public Room(String description, String image)
    {
        this(null,null,description,image,null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAudio() 
    {
        return audioPath;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    /**
     * Gets the room for a specific exit
     */
    public Room getExit(String direction){
        return exits.get(direction);
    }

    /**
     * Set exit for a particular direction
     */
    public void setExit(String direction, Room whichRoom){
        exits.put(direction, whichRoom);
    }

    /**
     * Puts an item in a room.
     */
    public void putItem(String key, Item item)
    {
        items.put(key, item);   
    }

    public Item pickUpItem(String itemKey)
    {
        // Item out of HashMap.
        Item item = items.get(itemKey);
        // If item exists, remove it from room.
        if(item == null)
        {
            System.out.println("This cannot be picked up!");
        }
        else
        {
            items.remove(itemKey);
        }
        return item;
    }


    /**
     * Printing the location information
     */
    public void printLocationInfo () {
        System.out.println("You are " + description);
        System.out.print("Exits: ");

        Set<String> exitSet = exits.keySet();
        for (String e: exitSet){
            System.out.print (e + " ");
        }

        System.out.println();
    }

    public void printItemInfo() 
    {
        System.out.print("You see ");
        if (items.size() == 0) 
        {
            System.out.print("an empty room");
        }
        else{
            Set<String> itemSet = items.keySet();
            int i = 0;
            for (String e : itemSet)
            {
                Item item = items.get(e);
                System.out.print (item.getDescription());
                if( i == itemSet.size() - 1 )
                {
                    System.out.print("");
                }
                else if( i == itemSet.size() - 2 )
                {
                    System.out.print(" and ");
                }
                else
                {
                    System.out.print(", ");
                }
                i++;
            }

        }
        System.out.println();        
    }
}
