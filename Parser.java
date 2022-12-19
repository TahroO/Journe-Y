import java.util.Scanner;

/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 * <p>
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Parser {
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() {
        // Print prompt.
        System.out.print("> ");
        return parseInput(reader.nextLine());
    }

    public Command parseInput(String inputLine) {
        String word1 = null;
        String word2 = null;
        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            // Get first word.
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) {
                // Get second word.
                word2 = tokenizer.next();
                // Note: we just ignore the rest of the input line.
            }
        }
        return new Command(CommandWords.Command.valueOfLabel(word1), word2, inputLine);
    }

    /**
     * Returns all valid commands in one string.
     */
    public String getValidCommands() {
        return commands.getValidCommands();
    }
}
