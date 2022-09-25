package commands.simple.argless;

import commands.abstractions.ServerCommand;
import data.management.DataManager;
import model.Organization;

import java.util.function.Consumer;


public class Show extends ServerCommand {
    public Show(DataManager dataManager) {
        super(dataManager);
        this.name = "show";
        this.description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute() {
        dataManager.getCollection().stream().sorted().map(Organization::toString).forEach(this::writeResult);
        if (result.equals(""))
            result = "Коллекция пуста";
    }
}
