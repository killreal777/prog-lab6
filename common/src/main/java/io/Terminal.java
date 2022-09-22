package io;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;


public class Terminal {
    private final ScannerManager scannerManager;
    private final ScriptExecutionManager scriptExecutionManager;


    public Terminal() {
        this.scannerManager = new ScannerManager();
        this.scriptExecutionManager = new ScriptExecutionManager(scannerManager);
    }


    public String[] readLineSplit() {
        return readLineSplit(">>> ");
    }

    public String[] readLineSplit(String invitationMessage) {
        return readLineEntire(invitationMessage).split("\\s+");
    }

    public String readLineEntire() {
        return readLineEntire(">>> ");
    }

    public String readLineEntire(String invitationMessage) {
        scriptExecutionManager.checkIfScriptIsEnded();
        if (scriptExecutionManager.isScriptNotExecuting())
            System.out.print(invitationMessage);
        return readLine(invitationMessage);
    }

    private String readLine(String invitationMessage) {
        try {
            return scannerManager.getCurrentScanner().nextLine().trim();
        } catch (NoSuchElementException e) {
            if (scriptExecutionManager.isScriptExecuting()) // means that script ended incorrectly
                return readLineEntire(invitationMessage);
            System.out.println(TextFormatter.format("Ctrl+D", Format.RED)); // user entered Ctrl+D
            System.exit(0);
            throw new RuntimeException();
        }
    }


    public void readScript(String fileName) throws FileNotFoundException {
        scriptExecutionManager.createScriptScanner(fileName);
    }

    public void print(String message) {
        System.out.print(message + "\n");
    }
}
