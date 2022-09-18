package app;

public class ClientMain {
    public static void main(String[] args) {
        ClientExecutionManager executionManager = new ClientExecutionManager();
        while (true) {
            executionManager.executeNextCommand();
        }
    }
}
