package server;

import abstractions.command.Command;
import abstractions.requests.ArguedCommandRequest;
import abstractions.requests.CommandRequest;
import commands.abstractions.ArguedServerCommand;
import data.management.DataManager;


public class ServerExecutionManager {
    private final ServerCommandManager commandsManager;

    public ServerExecutionManager(DataManager dataManager) {
        this.commandsManager = new ServerCommandManager(dataManager);
    }

    public String executeCommand(CommandRequest request) {
        String commandName = request.getCommandName();
        Command command = commandsManager.clonePrototype(commandName);
        if (command instanceof ArguedServerCommand<?>)
            defineCommandArgument(command, request);
        command.execute();
        return command.getResult();
    }

    private void defineCommandArgument(Command command, CommandRequest request) {
        ArguedServerCommand arguedCommand = (ArguedServerCommand) command;
        ArguedCommandRequest arguedCommandRequest = (ArguedCommandRequest) request;
        arguedCommand.extractArgumentFromRequest(arguedCommandRequest);
    }
}
