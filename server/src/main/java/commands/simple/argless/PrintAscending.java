package commands.simple.argless;


import commands.abstractions.ServerCommand;
import data.management.DataManager;

public class PrintAscending extends ServerCommand {
    public PrintAscending(DataManager dataManager) {
        super(dataManager);
        this.name = "print_ascending";
        this.description = "вывести элементы коллекции в порядке возрастания";
    }

    @Override
    public void execute() {
        for (Object organization : dataManager.getCollection().stream().sorted().toArray()) {
            if (result != "")
                result += "\n";
            this.result += organization.toString();
        }
        if (result.equals(""))
            this.result = "Коллекция пуста";
    }
}
