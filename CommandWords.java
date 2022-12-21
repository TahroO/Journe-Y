/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    enum Command {
        PICK("pickup"),
        GO("go"),
        LOOK("look"),
        QUIT("quit"),
        HELP("help"),
        BAG("bag"),
        MAP("map");        

        public final String label;

        Command(String label) {
            this.label = label;
        }

        public static Command valueOfLabel(String label) {
            for (Command cmd : values()) {
                if (cmd.label.equals(label)) {
                    return cmd;
                }
            }
            return null;
        }
    }

    /**
     * Checks whether a given string is a valid command word.
     * @return True if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        if (Command.valueOfLabel(aString) != null) {
            return true;
        }
        return false;
    }
    /**
     * Returns all valid commands in one string.
     */
    public String getValidCommands(){
        String s = "   ";
        for (CommandWords.Command c : CommandWords.Command.values()){
            s = s + " " + c.label;
        }
        return s;
    }

}
