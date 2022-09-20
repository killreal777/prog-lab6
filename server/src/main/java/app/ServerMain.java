package app;

import data.management.DataManager;
import io.Terminal;
import server.Server;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final DataManager dataManager = new DataManager(new Terminal());
        final ServerExecutionManager serverExecutionManager = new ServerExecutionManager(dataManager);
        final Server connector = new Server(serverExecutionManager::executeCommand);
        connector.run();
    }
}
