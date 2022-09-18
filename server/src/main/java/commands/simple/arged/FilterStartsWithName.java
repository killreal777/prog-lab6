package commands.simple.arged;

import commands.abstractions.ArguedServerCommand;
import data.management.DataManager;
import io.TextFormatter;
import subject.model.Organization;

import java.util.regex.Pattern;

public class FilterStartsWithName extends ArguedServerCommand<String> {

    public FilterStartsWithName(DataManager dataManager) {
        super(dataManager);
        this.name = "filter_starts_with_name name";
        this.description = "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public void execute() {
        Pattern nameRegex = Pattern.compile("^" + commandArgument + ".*");
        writeAllMatchesToResult(nameRegex);
        if (result.equals(""))
            setBadResult();
    }

    private void writeAllMatchesToResult(Pattern nameRegex) {
        for (Organization organization : dataManager.getCollection()) {
            if (!nameRegex.matcher(organization.getName()).matches())
                continue;
            if (!result.equals(""))
                result += "\n";
            result += organization.toString();
        }
    }

    private void setBadResult() {
        String message = "В коллекции нет элементов, значение поля name которых начинается с заданной подстроки";
        result = TextFormatter.format(message, TextFormatter.Format.RED);
    }
}
