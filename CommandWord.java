public enum CommandWord {
    PICK("pickup"),
    GO("go"),
    LOOK("look"),
    QUIT("quit"),
    HELP("help"),
    BAG("bag"),
    MAP("map"),
    JUMP("jump"),
    UNKNOWN;

    public final String label;

    CommandWord() {
        label = "";
    }

    CommandWord(String label) {
        this.label = label;
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
