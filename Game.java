import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This class is the main class of "Journe:>Y".
 * Journe:Y is a simple textbased adventure. Rooms are shown in an Image Viewer.
 *
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author chervbyn, Tahro
 * @version 2022.12.21
 */

public class Game implements ActionListener {
    private Parser parser;
    private Room currentRoom;
    private Map<String, Room> rooms;
    private ImageView view;
    private Inventory inventory;

    /**
     * Create the game and initialise its internal map, the inventory and the image viewer.
     */
    public Game() {
        createRooms();
        inventory = new Inventory();
        parser = new Parser();
        view = new ImageView(this);
    }

    /**
     * Create all the rooms by importing yml file data and set the start room.
     */
    private void createRooms() {
        MapImporter mapImporter = new MapImporter();
        rooms = mapImporter.getRooms();
        currentRoom = rooms.get("start");
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        view.show();
        view.changeRoom(null, currentRoom);
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     */
    private void processCommand(Command command) {
        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case HELP -> help(command);
            case GO -> go(command);
            case QUIT -> quit(command);
            case LOOK -> look(command);
            case PICK -> pickUp(command);
            case BAG -> bag(command);
            case MAP -> map(command);
            case JUMP -> jump(command);
            case RELOAD -> reload(command);
            default -> view.setText(command, "I don't know what you mean ...");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        ((JTextField) e.getSource()).setText(null);
        Command cmd = parser.parseInput(input);
        processCommand(cmd);
    }

    // Implementations of user commands.

    /**
     * Print out help information.
     */
    private void help(Command command) {
        view.setText("Hi my Name is Y, your personal assistant.",
                "I will try my best to help you.",
                "",
                "Your command words are:",
                parser.getValidCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void go(Command command) {
        if (!command.hasSecondWord()) {
            // If there is no second word, we don't know where to go...
            view.setText(command, "Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            view.setText(command, "There is no exit!");
        } else {
            currentRoom = nextRoom;
            view.changeRoom(command, currentRoom);
        }
    }

    /**
     * Performs jump to a specific room (for debugging).
     */
    private void jump(Command command) {
        if (!command.hasSecondWord()) {
            // If there is no second word, we don't know where to go...
            view.setText(command, "Jump where?");
            return;
        }

        String roomId = command.getSecondWord();
        Room nextRoom = rooms.get(roomId);
        if (nextRoom == null) {
            view.setText(command, "This room doesn't exist!");
        } else {
            currentRoom = nextRoom;
            view.changeRoom(command, currentRoom);
        }
    }

    /**
     * Performs reload of YML data (for debugging).
     */
    private void reload(Command command) {
        String currentRoomId = currentRoom.getId();
        createRooms();
        currentRoom = rooms.get(currentRoomId);
        view.changeRoom(command, currentRoom);
    }

    /**
     * Performs look around command.
     */
    private void look(Command command) {
        view.setText(command, currentRoom.getItemInfo());
    }

    /**
     * Show bag content command, shows inventory.
     */
    private void bag(Command command) {
        view.setText(command, inventory.getItemInfo());
    }

    /**
     * Show exits of current room.
     */
    private void map(Command command) {
        view.setText(command, currentRoom.getExitInfo());
    }

    /**
     * Performs pick up command.
     */
    private void pickUp(Command command) {
        if (!command.hasSecondWord()) {
            // If there is no second word, we don't know where to go...
            view.setText(command, "Pick up what?");
            return;
        }

        String itemKey = command.getSecondWord();
        Item item = currentRoom.getItem(itemKey);
        if (item == null) {
            view.setText(command, "This cannot be picked up!");
        } else {
            if (inventory.addItem(itemKey, item)) {
                view.setText(command, "You picked up " + item.getDescription());
                currentRoom.removeItem(itemKey);
            } else {
                view.setText(command, "Your bag is too full!");
            }
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private void quit(Command command) {
        if (command.hasSecondWord()) {
            view.setText(command, "Quit what?");
        } else {
            System.out.println("Thank you for playing.  Good bye.");
            view.quit();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                game.play();
            }
        });
    }

}
