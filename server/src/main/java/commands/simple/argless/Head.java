package commands.simple.argless;

import commands.abstractions.ServerCommand;
import data.management.DataManager;
import model.Organization;

import java.util.PriorityQueue;

public class Head extends ServerCommand {
    public Head(DataManager dataManager) {
        super(dataManager);
        this.name = "head";
        this.description = "вывести первый элемент коллекции";
    }

    @Override
    public void execute() {
        PriorityQueue<Organization> collection = dataManager.getCollection();
        if (collection.isEmpty())
            this.result = "Коллекция пуста";
        else
            this.result = collection.peek().toString();
    }
}
