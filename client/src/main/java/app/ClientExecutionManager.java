package app;

import abstractions.command.Command;
import abstractions.requests.CommandRequest;
import client.ClientIo;
import exceptions.DeserializationException;
import exceptions.MessagedRuntimeException;
import io.Format;
import io.TextFormatter;
import script.UnixScriptedTerminal;

import java.io.IOException;


public class ClientExecutionManager {
    private final LocalHistory history;
    private final UnixScriptedTerminal terminal;
    private final CommandReader commandReader;
    private final LocalCommandManager localCommandManager;
    private final RequestsManager requestsManager;
    private final ClientIo client;


    public ClientExecutionManager() {
        this.history = new LocalHistory();
        this.terminal = new UnixScriptedTerminal();
        this.commandReader = new CommandReader(terminal);
        this.requestsManager = new RequestsManager(terminal);
        this.localCommandManager = new LocalCommandManager(terminal, history);
        this.client = new ClientIo();
        try {
            client.connect();
        } catch (IOException e) {
            reconnect();
        }
    }


    private void reconnect() {
        try {
            terminal.print("Сервер недоступен, введите exit для выхода или reconnect для переподключения");
            String input = terminal.readLineEntire();
            while (!(input.equals("exit") || input.equals("reconnect")))
                input = terminal.readLineEntire("Введите exit для выхода или reconnect для переподключения: ");
            if (input.equals("exit"))
                executeCommand(input, new String[0]);
            else
                client.connect();
            System.out.println(TextFormatter.format("Соединение установлено", Format.GREEN));
        } catch (IOException e) {
            reconnect();
        }
    }

    
    public void executeNextCommand() {
        try {
            CommandReader.UserInput input = commandReader.readCommand();
            String name = input.getCommandName();
            String[] args = input.getCommandArgs();
            String commandResult = executeCommand(name, args);
            terminal.print(commandResult);
        } catch (IOException | DeserializationException e) {
            reconnect();
        } catch (MessagedRuntimeException e) {
            terminal.print(e.getMessage());
        }
    }

    private String executeCommand(String commandName, String[] commandArgs) throws IOException {
        if (localCommandManager.contains(commandName))
            return executeCommandOnClient(commandName, commandArgs);
        else if (requestsManager.contains(commandName))
            return executeCommandOnServer(commandName, commandArgs);
        else
            return TextFormatter.format("Команда не найдена", Format.RED);
    }

    private String executeCommandOnClient(String commandName, String[] commandArgs) {
        Command command = localCommandManager.clonePrototype(commandName);
        history.addCommand(command);
        command.setArgs(commandArgs);
        command.execute();
        return command.getResult();
    }

    private String executeCommandOnServer(String commandName, String[] commandArgs) throws IOException {
        CommandRequest request = requestsManager.clonePrototype(commandName);
        request.setCommandArgs(commandArgs);
        history.addRequest(request);
        try {
            return client.interact(request);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
