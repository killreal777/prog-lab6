package io;


import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.NoSuchElementException;


/**
 * Class for input from Scanner managed by ScannerManager and output to System.out
 */


public class Terminal {
    private final ScannerManager scannerManager;
    private final ScriptExecutionManager scriptExecutionManager;


    public Terminal() {
        this.scannerManager = new ScannerManager();
        this.scriptExecutionManager = new ScriptExecutionManager(scannerManager);
    }


    /**
     * Read line with splitting by whitespaces and default invitation message (">>> ")
     */
    public String[] readLineSplit() {
        return readLineSplit(">>> ");
    }

    /**
     * Read line with splitting by whitespaces and custom invitation message
     */
    public String[] readLineSplit(String invitationMessage) {
        return readLineEntire(invitationMessage).split("\\s+");
    }

    /**
     * Read line without splitting by whitespaces (however, with trimming) and default invitation message (">>> ")
     */
    public String readLineEntire() {
        return readLineEntire(">>> ");
    }

    /**
     * Read line without splitting by whitespaces (however, with trimming) and custom invitation message
     */
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
            if (scriptExecutionManager.isScriptExecuting())                       // means that script ended incorrectly
                return readLineEntire(invitationMessage);
            System.out.println(TextFormatter.format("Ctrl+D", TextFormatter.Format.RED));    // user entered Ctrl+D
            System.exit(0);
            throw new RuntimeException();
        }
    }


    /**
     * Read and execute script
     */
    public void readScript(String fileName) throws FileNotFoundException {
        scriptExecutionManager.createScriptScanner(fileName);
    }


    /**
     * Print text to System.out
     */
    public void print(String message) {
        System.out.print(message + "\n");
    }
}
