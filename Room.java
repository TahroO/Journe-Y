import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    private String yamlFile;

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

    public void setSourceFile(String yamlFile) {
        this.yamlFile = yamlFile;
    }

    public String getSourceFile() {
        return yamlFile;
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

    public Item getItem(String itemKey)
    {
        return items.get(itemKey);
    }
    
    public void removeItem(String itemKey)
    {
        items.remove(itemKey);
    }
 
    public String getLocationInfo() {
        StringBuilder output = new StringBuilder();
        output.append(description)
                .append('\n')
                .append("Exits: ")
                .append(Util.join(exits.keySet().stream().collect(Collectors.toList())))
                .append('.');
        return output.toString();
    }

    public String getItemInfo() {
        StringBuilder output = new StringBuilder("You see ");
        if (items.size() == 0) {
            output.append("nothing special.");
        } else {
            List<String> descriptions = items.values().stream().map(Item::getDescription).collect(Collectors.toList());
            output.append(Util.join(descriptions))
                    .append(".\n\n")
                    .append("Items to pick up: ")
                    .append(Util.join(items.keySet().stream().collect(Collectors.toList())))
                    .append(".");
        }
        return output.toString();
    }

    public String getExitInfo() {
        StringBuilder output = new StringBuilder();
        output.append("Your exits are: ")
                .append(Util.join(exits.keySet().stream().collect(Collectors.toList())))
                .append(".");
        return output.toString();
    }
}
