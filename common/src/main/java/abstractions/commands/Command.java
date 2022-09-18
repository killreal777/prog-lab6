package abstractions.commands;


import abstractions.prototypes.CloneablePrototype;
import exception.ArgumentAmountException;

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
        checkArgumentsAmount(args, 0);   // command doesn't have any arguments by default
    }

    protected void checkArgumentsAmount(String[] args, int amount) {
        if (args.length != amount)
            throw new ArgumentAmountException(args.length, amount);
    }


    public String getResult() {
        return result;
    }

    public String getHelp() {
        return String.format("%s: %s", name, description);
    }


    @Override
    public String toString() {
        return name;
    }
}
