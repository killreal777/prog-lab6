package app;

import abstractions.command.Command;

/**
 * Class for storing last 10 executed Commands
 */

public class LocalHistory {
    private final Command[] historyCommands;

    LocalHistory() {
        this.historyCommands = new Command[10];
    }

    public void addCommand(Command command) {
        shiftCommandsOnePositionToThePast();
        historyCommands[0] = command;
    }

    private void shiftCommandsOnePositionToThePast() {
        for (int i = 9; i > 0; i--)
            historyCommands[i] = historyCommands[i - 1];
        historyCommands[0] = null;
    }

    public String toString() {
        if (historyCommands[0] == null)
            return "History is empty";
        else
            return historyCommandsToString();
    }

    private String historyCommandsToString() {
        String out = "";
        for (int index = 0; index < 10; index++) {
            if (historyCommands[index] == null)
                break;
            out = addLineFeed(out) + commandToString(index);
        }
        return out;
    }

    private String commandToString(int commandIndexInArray) { // for beautiful history output
        int stepIntoThePast = commandIndexInArray + 1; // 1 - the previous, 2 - the command before the previous...
        Command command = historyCommands[commandIndexInArray];
        String commandName = command.toString().split(" ")[0];
        return String.format("-%2d: %s", stepIntoThePast, commandName);
    }

    private String addLineFeed(String string) {
        if (!string.equals(""))
            string += "\n";
        return string;
    }
}
