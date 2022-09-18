package commands.simple.argless;

import commands.abstractions.ServerCommand;
import data.management.DataManager;

public class Info extends ServerCommand {
    public Info(DataManager dataManager) {
        super(dataManager);
        this.name = "info";
        this.description = "вывести в стандартный поток вывода информацию о коллекции "
                + "(тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public void execute() {
        this.result = dataManager.getCollectionInfo().toString();
    }
}
