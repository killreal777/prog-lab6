package abstractions.command;

import abstractions.prototypes.CloneablePrototype;
import exceptions.ArgumentAmountException;

/**
 * Superclass for all Commands
 */

public abstract class Command extends CloneablePrototype {
    protected String name;
    protected String description;
    protected String result;

    public Command() {
        this.name = "no name";
        this.description = "no description";
        this.result = "";
    }

    abstract public void execute();

    public void setArgs(String[] args) {
        checkArgumentsAmount(args, 0); // command doesn't have any arguments by default
    }

    protected void checkArgumentsAmount(String[] args, int amount) {
        if (args.length != amount)
            throw new ArgumentAmountException(args.length, amount);
    }
    
    public void writeResult(String newResult) {
        if (!result.equals(""))
            result = result + "\n";
        result = result + newResult;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
