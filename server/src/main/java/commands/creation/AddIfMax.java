package commands.creation;

import data.management.DataManager;
import io.TextFormatter;
import model.Organization;


public class AddIfMax extends Add {
    public AddIfMax(DataManager dataManager) {
        super(dataManager);
        this.name = "add_if_max {element}";
        this.description = "добавить новый элемент в коллекцию, "
                + "если его значение превышает значение наибольшего элемента этой коллекции";
    }


    @Override
    public void execute() {
        Organization organization = this.commandArgument;
        if (!isOrganizationMax(organization))
            setBadResult();
        else
            super.execute();
    }

    private boolean isOrganizationMax(Organization newOrganization) {
        return dataManager.getCollection().stream().map(newOrganization::compareTo).allMatch((a) -> a > 0);
    }


    private void setBadResult() {
        this.result = "Значение элемента не превышает значение наибольщего элемента в коллекции";
        this.result = TextFormatter.format(result, TextFormatter.Format.RED);
    }
}
