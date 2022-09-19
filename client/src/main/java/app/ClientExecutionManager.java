package app;

import abstractions.command.Command;
import abstractions.requests.CommandRequest;
import exceptions.MessagedRuntimeException;
import io.Terminal;

import java.io.IOException;


public class ClientExecutionManager {
    private final LocalHistory history;
    private final Terminal terminal;
    private final CommandReader commandReader;
    private final LocalCommandManager localCommandManager;
    private final RequestsManager requestsManager;
    private final ClientConnector connector;


    public ClientExecutionManager() {
        this.history = new LocalHistory();
        this.terminal = new Terminal();
        this.commandReader = new CommandReader(terminal);
        this.requestsManager = new RequestsManager(terminal);
        this.localCommandManager = new LocalCommandManager(terminal, history);
        this.connector = new ClientConnector();
        try {
            connector.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void executeNextCommand() {
        try {
            CommandReader.UserInput input = commandReader.readCommand();
            String name = input.getCommandName();
            String[] args = input.getCommandArgs();
            String commandResult = executeCommand(name, args);
            terminal.print(commandResult);
        } catch (MessagedRuntimeException e) {
            terminal.print(e.getMessage());
        }
    }

    private String executeCommand(String commandName, String[] commandArgs) {
        if (localCommandManager.contains(commandName))
            return executeCommandOnClient(commandName, commandArgs);
        else if (requestsManager.contains(commandName))
            return executeCommandOnServer(commandName, commandArgs);
        else
            return "COMMAND NOT FOUND";
    }

    private String executeCommandOnClient(String commandName, String[] commandArgs) {
        Command command = localCommandManager.clonePrototype(commandName);
        history.addCommand(command);
        command.setArgs(commandArgs);
        command.execute();
        return command.getResult();
    }

    private String executeCommandOnServer(String commandName, String[] commandArgs) {
        CommandRequest request = requestsManager.clonePrototype(commandName);
        request.setCommandArgs(commandArgs);
        try {
            return connector.interact(request);
        } catch (IOException | ClassNotFoundException e) {
            return "Сервер временно недоступен";
        }
    }
}
