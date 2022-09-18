package app;

import abstractions.prototypes.PrototypesManager;
import abstractions.commands.Command;
import commands.local.ExecuteScript;
import commands.local.Exit;
import commands.local.Help;
import commands.local.History;
import io.Terminal;
import register.CommandRecord;
import register.CommandsChecker;

import java.util.ArrayList;

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
