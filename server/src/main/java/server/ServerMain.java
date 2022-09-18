package server;

import data.management.DataManager;
import io.Terminal;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final DataManager dataManager = new DataManager(new Terminal());
        final ServerExecutionManager serverExecutionManager = new ServerExecutionManager(dataManager);
        final ServerConnector connector = new ServerConnector(serverExecutionManager::executeCommand);
        connector.run();
    }
}
