package app;

import commands.simple.argless.Save;
import data.management.DataManager;
import io.Terminal;
import server.Server;

import java.io.IOException;


public class ServerMain {
    public static void main(String[] args) throws IOException {
        final Terminal terminal = new Terminal();
        final DataManager dataManager = new DataManager(terminal);
        final ServerExecutionManager serverExecutionManager = new ServerExecutionManager(terminal, dataManager);
        final ServerTerminalInputManager terminalInputManager = new ServerTerminalInputManager(terminal, serverExecutionManager::executeSaveCommand);
        terminalInputManager.checkTerminalRequest();
        final Server server = new Server(serverExecutionManager::executeCommand, terminalInputManager::checkTerminalRequest);
        new Save(dataManager).execute();
        server.run();
    }
}
