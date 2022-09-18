package commands.simple.argless;

import commands.abstractions.ServerCommand;
import data.management.DataManager;
import io.TextFormatter;

import java.io.FileNotFoundException;


public class Save extends ServerCommand {
    public Save(DataManager dataManager) {
        super(dataManager);
        this.name = "save";
        this.description = "сохранить коллекцию в файл";
    }

    @Override
    public void execute() {
        try {
            this.dataManager.saveData();
            result = "Коллекция сохранена";
            result = TextFormatter.format(result, TextFormatter.Format.GREEN);
        } catch (FileNotFoundException e) {
            result = "Невозможно сохранить коллекцию в файл: недостаточно прав";
            result = TextFormatter.format(result, TextFormatter.Format.RED);
        }
    }
}
