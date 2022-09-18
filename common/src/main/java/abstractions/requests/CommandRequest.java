package abstractions.requests;


import abstractions.prototypes.CloneablePrototype;
import exception.ArgumentAmountException;

import java.io.Serializable;


public abstract class CommandRequest extends CloneablePrototype implements Serializable {
    protected String commandName;


    public CommandRequest(String commandName) {
        this.commandName = commandName;
    }


    /**
     * Children of ArguedCommandRequest must override this method
     * ArglessCommandRequest also overrides this method but just for checking arguments absence
     */
    abstract public void setCommandArgs(String[] args);


    protected void checkArgumentsAmount(String[] args, int expectedAmount) {
        int inputtedAmount = args.length;
        if (inputtedAmount != expectedAmount)
            throw new ArgumentAmountException(inputtedAmount, expectedAmount);
    }


    public String getCommandName() {
        return commandName;
    }
}