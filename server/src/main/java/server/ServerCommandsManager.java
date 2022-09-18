package server;

import abstractions.prototypes.PrototypesManager;
import commands.abstractions.ServerCommand;
import commands.creation.Add;
import commands.creation.AddIfMax;
import commands.creation.RemoveByAddress;
import commands.creation.Update;
import commands.simple.arged.FilterStartsWithName;
import commands.simple.arged.RemoveByID;
import commands.simple.argless.*;
import data.management.DataManager;

public class ServerCommandsManager extends PrototypesManager<ServerCommand> {
    private final DataManager dataManager;

    public ServerCommandsManager(DataManager dataManager) {
        this.dataManager = dataManager;
        definePrototypes();

    }

    @Override
    protected void definePrototypes() {
        // server creation commands
        addPrototype("add", new Add(dataManager));
        addPrototype("add_if_max", new AddIfMax(dataManager));
        addPrototype("update", new Update(dataManager));
        addPrototype("remove_any_by_official_address", new RemoveByAddress(dataManager));

        // server simple arged commands
        addPrototype("remove_by_id", new RemoveByID(dataManager));
        addPrototype("filter_starts_with_name", new FilterStartsWithName(dataManager));

        // server simple argless commands
        addPrototype("clear", new Clear(dataManager));
        addPrototype("show", new Show(dataManager));
        addPrototype("head", new Head(dataManager));
        addPrototype("print_ascending", new PrintAscending(dataManager));
        addPrototype("info", new Info(dataManager));
        addPrototype("save", new Save(dataManager));
    }
}
