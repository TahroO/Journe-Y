import javax.swing.JFrame;
import java.util.Map;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private JFrame frame;
    private ImagePanel panel;
    private Inventory inventory;
    private MusicPlayer player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        inventory = new Inventory();
        parser = new Parser();
        frame = new JFrame();
        panel = new ImagePanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        player = new MusicPlayer();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        MapImporter mapImporter = new MapImporter();
        Map<String, Room> rooms = mapImporter.getRooms();
        currentRoom = rooms.get("kitchen");
        // Room caveStart, caveMiddle, caveOut;
        // Room waterfall, train, jungleWay, grave;
        // Room oldHangar, villEntrance, townHall, villYard, portalWay, portal;
        // Room machine, hangar, launchPad; 
        // Room teleport, holo, gangway, locker, bar;
        // Room kitchen, cryo, medic, bioReactor, cabin42, pool;
        // Room bridge, meeting, cpu;
        // Item treasure;
        // // create the rooms
        // // cave start, middle and out
        // caveStart = new Room("waking up in a cave on an ancient alien planet.\n" +
        // "The unfamiliar surroundings and lack of landmarks makes it difficult to orient yourself. \n" +
        // "It seems to be a good idea to look around and search for a way out of the cave." ,
        // "resources/caveStart.png");
        // caveMiddle = new Room("making your way through the mysterious cave. \n" +
        // "It seems to be important to carefully examine the cave, looking for any items, \n" +
        // "such as those glowing stones near you to help navigate out of the cave.",
        // "resources/caveMiddle.png");
        // caveOut = new Room("at an entrance of a cave.\n" +
        // "The unfamiliar landscape, with its strange rock formations and unusual flora and fauna,\n" +
        // "is a sight unlike anything you have ever seen before. \n" +
        // "The jungle is waiting for you!",
        // "resources/caveOut.png");
        // // way to village waterfall, train, jungleway, grave
        // waterfall = new Room("exploring a stunning waterfall cascading down \n" +
        // "the green walls of the jungle, its mist rising high into the air. \n" +
        // "The sound of the rushing water fills the air, \n" +
        // "echoing through the surrounding ancient alien trees.", 
        // "resources/waterfall.png");
        // train = new Room("reaching an abandoned train station. \n" +
        // "The tracks were rusted and overgrown, \n" +
        // "They are disappearing into the dense jungle.\n" +
        // "You have to decide which tracks you will follow.",
        // "resources/train.png");
        // jungleWay = new Room("feeling how determination fuels you every step, \n" +
        // "You slash your way through the dense foliage, following the faint glow of the sun. \n" +
        // "Will you find a way out?", 
        // "resources/jungleWay.png");
        // grave = new Room("conquer an alien like grave yard. \n" +
        // "The jungle that surroundes it is eerily quiet and open. \n" +
        // "This seems a good place to rest for a while.",
        // "resources/grave.png");
        // // way to portal, oldHangar, villEntrance, TownHall, village yard,
        // // portal gang, portal
        // oldHangar = new Room("ending up in an abandoned space hangar, \n" +
        // "its walls and ceiling rusted and crumbling. \n" +
        // "As you make your way through the debris-strewn floor, \n" +
        // "you notice a rusted spaceship in the center of this room.",
        // "resources/hangar1.png");
        // villEntrance = new Room("emerging from the dense jungle and couldn't believe \n" +
        // "your eyes when seeing the ancient, lost village in front of you. \n" +
        // "The stone buildings are weathered and worn, but the intricate carvings \n" +
        // "and symbols etched into the walls spoke to the incredible sophistication \n" +
        // "and advanced technology of the civilization that had once inhabited this place.",
        // "resources/villEntrance.png");
        // townHall = new Room("startled to come upon an ancient, abandoned town hall. \n" +
        // "The building is surrounded by vines and weeds, \n" +
        // "and it looks as if it is forgotten for many years. \n" +
        // "You notice a hidden stairway which leads into another building.",
        // "resources/townHall1.png");
        // villYard = new Room("walking through a strange, alien yard. \n" +
        // "Your attention falls to a bright blue glowing door. \n" +
        // "It seems to radiate a pulsing energy that calls on you, \n" +
        // "beckoning you to come closer.",
        // "resources/villYard.png");
        // portalWay = new Room("approaching the next room and the glow intensifies, \n" +
        // "enveloping you in a warm, electric embrace. \n" +
        // "Now take a deep breath and push the next door open, step into the unknown!",
        // "resources/portalWay.png");
        // portal = new Room("in front of a teleporter which was a marvel of technology,\n" +
        // "capable of transporting beings instantaneously to yet unknown locations.\n" +
        // "It was a popular mode of transportation for the native alien species.\n" +
        // "However, the teleporter has some limitations, as it could only transport organic material \n" +
        // "within the planet's atmosphere and is not able to reach other planets or galaxies.",
        // "resources/portal.png");
        // // deck 1 hangar and machine lower deck
        // machine = new Room("in the hearth of the ship, the machine Room." +
        // "It seems all machines are working as intendet.",
        // "resources/machineRoom.png");
        // hangar = new Room("hangar", 
        // "resources/hangar.png");
        // launchPad = new Room("launch pad", 
        // "resources/launchPad.png");
        // // deck 2 teleporter holo bar middle deck
        // teleport = new Room("materialize on a teleporter pad inside a spaceship", 
        // "resources/teleportRoom.png");
        // holo = new Room("holodeck", 
        // "resources/holo.png");
        // gangway = new Room("gangway", 
        // "resources/gangway.png");
        // locker = new Room("locker", 
        // "resources/locker.png");
        // bar = new Room("bar", 
        // "resources/bar.png");
        // // deck 3 medic cryo cabin pool upper deck
        // kitchen = new Room("kitchen", 
        // "resources/replicator.png");
        // cryo = new Room("cryo", 
        // "resources/cryo.png");
        // medic = new Room("medic", 
        // "resources/medic.png");
        // bioReactor = new Room("bio reactor", 
        // "resources/bioReactor.png");
        // cabin42 = new Room("cabin42", 
        // "resources/cabin42.png");
        // pool = new Room("pool", 
        // "resources/pool.png");
        // // deck 4 bridge and CPU captains deck
        // bridge = new Room("bridge", 
        // "resources/bridge.png");
        // meeting = new Room("meeting", 
        // "resources/meetingRoom.png");
        // cpu = new Room("cpu", 
        // "resources/cpuUnit.png");
        // // initialise room exits
        // caveStart.setExit("north", caveMiddle);

        // caveMiddle.setExit("north", caveOut);
        // caveMiddle.putItem("stone", new Item("a strange glowing stone, which...glows!"));

        // caveOut.setExit("south", caveMiddle);
        // caveOut.setExit("east", waterfall);

        // // way to village waterfall, train, jungleway, grave
        // waterfall.setExit("west", caveOut);
        // waterfall.setExit("east", train);

        // train.setExit("west", waterfall);
        // train.setExit("north", villEntrance);
        // train.setExit("east", jungleWay);

        // jungleWay.setExit("west", train);
        // jungleWay.setExit("east", grave);

        // grave.setExit("west", jungleWay);

        // // way to portal, abandonedHangar, villageEntrance, village stairs, village yard,
        // // portal gang, portal
        // oldHangar.setExit("east", villEntrance);

        // villEntrance.setExit("west", oldHangar);
        // villEntrance.setExit("south", train);
        // villEntrance.setExit("east", townHall);

        // townHall.setExit("west", villEntrance);
        // townHall.setExit("east", villYard);

        // villYard.setExit("west", townHall);
        // villYard.setExit("east", portalWay);

        // portalWay.setExit("west", villYard);
        // portalWay.setExit("east", portal);

        // portal.setExit("teleport", teleport);      
        // portal.putItem("teleporter", new Item("a teleporter, try it out!"));

        // machine.setExit("east", hangar);

        // hangar.setExit("west", machine);
        // hangar.setExit("east", launchPad);
        // hangar.setExit("up", gangway);

        // launchPad.setExit("west", hangar);

        // teleport.setExit("up", cryo);
        // teleport.setExit("east", holo);

        // holo.setExit("west", teleport);
        // holo.setExit("up", medic);
        // holo.setExit("east", gangway);

        // gangway.setExit("west", holo);
        // gangway.setExit("up", bioReactor);
        // gangway.setExit("down", hangar);
        // gangway.setExit("east", locker);

        // locker.setExit("west", gangway);
        // locker.setExit("up", cabin42);
        // locker.setExit("east", bar);

        // bar.setExit("west", locker);
        // bar.setExit("up", pool);

        // pool.setExit("down", bar);

        // kitchen.setExit("east", cryo);

        // cryo.setExit("west", kitchen);
        // cryo.setExit("up", meeting);
        // cryo.setExit("down", teleport);
        // cryo.setExit("east", medic);

        // medic.setExit("west", cryo);
        // medic.setExit("down", holo);
        // medic.setExit("east", bioReactor);

        // bioReactor.setExit("west", medic);
        // bioReactor.setExit("down", gangway);

        // cabin42.setExit("down", locker);

        // pool.setExit("down", bar);

        // bridge.setExit("east", meeting);

        // meeting.setExit("west", bridge);
        // meeting.setExit("down", cryo);
        // meeting.setExit("east", cpu);

        // cpu.setExit("west", meeting);

        // currentRoom = caveStart;  // start game in a cave
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        frame.setVisible(true);

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Game Journe:Y");
        System.out.println("Journe:Y is a textbased adventure Game.");
        System.out.println("Type 'help' to see all possible commands.");
        System.out.println();
        currentRoom.printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look"))
        {
            currentRoom.printItemInfo();
        }
        else if (commandWord.equals("pick"))
        {
            pickUp(command);
        }
        else if (commandWord.equals("bag"))
        {
            inventory.printItems();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
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
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            currentRoom.printLocationInfo();
            panel.setImage(currentRoom.getImagePath());
            String audio = currentRoom.getAudio();
            if(audio != null)
            {
                player.stop();
                player.startPlaying(audio);
            }
        }
    }

    private void pickUp(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pick up what?");
            return;
        }

        String itemKey = command.getSecondWord();

        Item item = currentRoom.pickUpItem(itemKey);
        if(item != null)
        {
            inventory.addItem(itemKey, item);
            System.out.println("You picked up " + itemKey);
        }

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }

        else {
            return true;  // signal that we want to quit
        }
    }
}