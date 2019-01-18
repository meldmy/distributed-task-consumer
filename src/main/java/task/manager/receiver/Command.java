package task.manager.receiver;

public enum Command {

    ADD("ADD "),
    TEST("TEST"),
    REMOVE("REMOVE "),
    SUMMARY("SUMMARY");

    private final String commandPrefix;

    Command(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }
}
