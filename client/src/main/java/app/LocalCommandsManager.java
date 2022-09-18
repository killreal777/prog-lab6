package app;

import abstractions.prototypes.PrototypesManager;
import abstractions.commands.Command;
import commands.local.ExecuteScript;
import commands.local.Exit;
import commands.local.Help;
import commands.local.History;
import io.Terminal;

public class LocalCommandsManager extends PrototypesManager<Command> {
    private final LocalHistory history;
    private final Terminal terminal;
    private final String requestsNames;
    // private final CommandHistory history;

    public LocalCommandsManager(Terminal terminal, LocalHistory history, String requestsNames) {
        this.requestsNames = requestsNames;
        this.history = history;
        this.terminal = terminal;
        definePrototypes();
        // this.history = new CommandHistory();
    }

    @Override
    protected void definePrototypes() {
        addPrototype("execute_script", new ExecuteScript(terminal));
        addPrototype("exit", new Exit());
        addPrototype("history", new History(history));
        addPrototype("help", new Help(requestsNames + getPrototypesNames()));
    }
}
