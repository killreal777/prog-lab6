package app;

import data.management.DataManager;
import io.Format;
import io.Terminal;
import io.TextFormatter;
import server.Server;

import java.io.IOException;


public class ServerMain {
    public static void main(String[] args) throws IOException {
        final Terminal terminal = new Terminal();
        final DataManager dataManager = new DataManager(terminal);
        final ServerExecutionManager serverExecutionManager = new ServerExecutionManager(terminal, dataManager);
        final ServerTerminalInputManager terminalInputManager = new ServerTerminalInputManager(terminal, serverExecutionManager::executeSaveCommand);
        final Server server = new Server(serverExecutionManager::executeCommand, terminalInputManager::checkTerminalRequest);
        terminal.print(TextFormatter.format("Доступные команды: save, exit", Format.YELLOW));
        server.run();
    }
}
