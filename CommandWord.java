import java.util.Arrays;
import java.util.List;

public enum CommandWord {
    PICK("pickup"),
    GO("go"),
    LOOK("look"),
    QUIT("quit"),
    HELP("help"),
    BAG("bag"),
    MAP("map"),
    JUMP("jump", true),
    RELOAD("reload", true),
    UNKNOWN;

    public final String label;
    public final boolean hidden;

    CommandWord() {
        this("", true);
    }

    CommandWord(String label) {
        this(label, false);
    }

    CommandWord(String label, boolean hidden) {
        this.label = label;
        this.hidden = hidden;
    }

    /**
     * Finds a CommandWord by command string.
     * @param label Command string to search for.
     * @return CommandWord object.
     */
    public static CommandWord valueOfLabel(String label) {

        for (CommandWord cmd : values()) {
            if (cmd.label.equals(label)) {
                return cmd;
            }
        }
        return UNKNOWN;
    }

    /**
     * Gets command words string representation.
     * @return Command string.
     */
    public String getLabel() {
        return label;
    }

}
