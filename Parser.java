import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    /**
     * Parses user input into a Command object.
     * @param inputLine User input.
     * @return A Command object.
     */
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
        return new Command(CommandWord.valueOfLabel(word1), word2, inputLine);
    }

    /**
     * Returns all valid commands in one string.
     */
    public String getValidCommands() {
        Stream<String> labels = Arrays.stream(CommandWord.values())
                .filter(cmd -> !cmd.hidden)
                .map(CommandWord::getLabel);
        return Util.join(labels.collect(Collectors.toList()));
    }
}
