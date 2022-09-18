package commands.simple.argless;

import commands.abstractions.ServerCommand;
import data.management.DataManager;
import io.TextFormatter;
import subject.model.Organization;


public class Clear extends ServerCommand {
    public Clear(DataManager dataManager) {
        super(dataManager);
        this.name = "clear";
        this.description = "очистить коллекцию";
    }

    @Override
    public void execute() {
        for (Organization organization : dataManager.getCollection())
            dataManager.getIdGenerator().setToRemoved(organization.getId());
        dataManager.getCollection().clear();
        dataManager.getCollectionInfo().setElementsAmount(0);
        result = TextFormatter.format("Коллекция очищена", TextFormatter.Format.GREEN);
    }
}