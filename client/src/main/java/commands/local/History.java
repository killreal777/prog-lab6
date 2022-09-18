package commands.local;

import abstractions.commands.Command;
import app.LocalHistory;


public class History extends Command {
    private final LocalHistory history;

    public History(LocalHistory history) {
        this.name = "history";
        this.description = "вывести последние 10 команд (без их аргументов)";
        this.history = history;
    }

    @Override
    public void execute() {
        this.result = history.toString();
    }
}
