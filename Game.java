import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.  Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game implements ActionListener {
    private Parser parser;
    private Room currentRoom;
    private ImageView view;
    private Inventory inventory;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        inventory = new Inventory();
        parser = new Parser();
        view = new ImageView(this);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        MapImporter mapImporter = new MapImporter();
        Map<String, Room> rooms = mapImporter.getRooms();
        currentRoom = rooms.get("start");
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();
        view.show();
        view.changeRoom(null, currentRoom);
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Game Journe:Y");
        System.out.println("Journe:Y is a textbased adventure Game.");
        System.out.println("Type 'help' to see all possible commands.");
        System.out.println();
        currentRoom.printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     */
    private void processCommand(Command command) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            view.setText(command, "I don't know what you mean...");
            return;
        }

        CommandWords.Command commandWord = command.getCommandWord();
        if (commandWord == CommandWords.Command.HELP) {
            help();
        } else if (commandWord == CommandWords.Command.GO) {
            go(command);
        } else if (commandWord == CommandWords.Command.QUIT) {
            quit(command);
        } else if (commandWord == CommandWords.Command.LOOK) {
            look(command);
        } else if (commandWord == CommandWords.Command.PICK) {
            pickUp(command);
        } else if (commandWord == CommandWords.Command.BAG) {
            bag(command);
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
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void help() {
        CommandWords.Command helpCmd = CommandWords.Command.HELP;
        view.setText("Hi my Name is Y, your personal assistant.",
                "I will try my best to help you.",
                "",
                "Your command words are:",
                parser.getValidCommands());
        System.out.println("Hi my Name is Y, your personal assistant.");
        System.out.println("I will try my best to help you.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getValidCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void go(Command command) {
        if (!command.hasSecondWord()) {
            // If there is no second word, we don't know where to go...
            System.out.println("Go where?");
            view.setText(command, "Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
            view.setText(command, "There is no door!");
        } else {
            currentRoom = nextRoom;
            currentRoom.printLocationInfo();
            view.changeRoom(command, currentRoom);
        }
    }

    /**
     * Performs look around command.
     */
    private void look(Command command) {
        currentRoom.printItemInfo();
        view.setText(command, currentRoom.getItemInfo());
    }

    /**
     * Show bag content command.
     */
    private void bag(Command command) {
        inventory.printItemInfo();
        view.setText(command, inventory.getItemInfo());
    }

    /**
     * Performs pick up command.
     */
    private void pickUp(Command command) {
        if (!command.hasSecondWord()) {
            // If there is no second word, we don't know where to go...
            System.out.println("Pick up what?");
            view.setText(command, "Pick up what?");
            return;
        }

        String itemKey = command.getSecondWord();
        Item item = currentRoom.pickUpItem(itemKey);
        if (item == null) {
            System.out.println("This cannot be picked up!");
            view.setText(command, "This cannot be picked up!");
        } else {
            inventory.addItem(itemKey, item);
            System.out.println("You picked up " + item.getDescription());
            view.setText(command, "You picked up " + item.getDescription());
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
            System.out.println("Quit what?");
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
