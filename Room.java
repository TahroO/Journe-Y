import java.util.HashMap;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String audioPath;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open courtyard".
     *
     * @param description The room's description.
     */
    public Room(String id, String name, String description, String image, String audio) {
        this.id = id;
        this.name = name;
        this.imagePath = image;
        this.audioPath = audio;
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    public Room(String description, String image) {
        this(null, null, description, image, null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    /**
     * Gets the room for a specific exit
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Set exit for a particular direction
     */
    public void setExit(String direction, Room whichRoom) {
        exits.put(direction, whichRoom);
    }

    /**
     * Puts an item in a room.
     */
    public void putItem(String key, Item item) {
        items.put(key, item);
    }

    /**
     * Removes an item from this room and returns it.
     *
     * @param itemKey Item ID.
     * @return The removed item object or null.
     */
    public Item pickUpItem(String itemKey) {
        // Item out of HashMap.
        Item item = items.get(itemKey);
        // If item exists, remove it from room.
        if (item != null) {
            items.remove(itemKey);
        }
        return item;
    }

    public String getLocationInfo() {
        StringBuilder output = new StringBuilder();
        output.append(description)
                .append('\n')
                .append("Exits: ")
                .append(Util.join(exits.keySet().stream().toList()))
                .append('.');
        return output.toString();
    }

    public String getItemInfo() {
        StringBuilder output = new StringBuilder("You see ");
        if (items.size() == 0) {
            output.append("nothing special.");
        } else {
            List<String> descriptions = items.values().stream().map(Item::getDescription).toList();
            output.append(Util.join(descriptions))
                    .append('.');
        }
        return output.toString();
    }

}
