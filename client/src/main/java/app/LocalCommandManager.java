package app;

import abstractions.prototypes.PrototypesManager;
import abstractions.command.Command;
import commands.ExecuteScript;
import commands.Exit;
import commands.Help;
import commands.History;
import io.Terminal;
import register.CommandRecord;
import register.CommandsChecker;

public class LocalCommandManager extends PrototypesManager<Command> {
    private final LocalHistory history;
    private final Terminal terminal;
    // private final CommandHistory history;

    public LocalCommandManager(Terminal terminal, LocalHistory history) {
        this.history = history;
        this.terminal = terminal;
        definePrototypes();
        CommandsChecker.check(CommandRecord.CommandType.LOCAL, getPrototypesNameList(), "LocalCommandManager");
    }

    @Override
    protected void definePrototypes() {
        addPrototype("execute_script", new ExecuteScript(terminal));
        addPrototype("exit", new Exit());
        addPrototype("history", new History(history));
        addPrototype("help", new Help());
    }
}
