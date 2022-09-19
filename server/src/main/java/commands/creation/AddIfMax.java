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
        if (isOrganizationMax(organization)) {
            defineAutogenFields(organization);
            addOrganizationToTheCollection(organization);
            setGoodResult();
        } else {
            setBadResult();
        }
    }

    private boolean isOrganizationMax(Organization newOrganization) {
        for (Organization organizationFormCollection : dataManager.getCollection()) {
            if (newOrganization.compareTo(organizationFormCollection) <= 0) {
                return false;
            }
        }
        return true;
    }

    private void setGoodResult() {
        this.result = TextFormatter.format("Элемент успешно добавлен", TextFormatter.Format.GREEN);
    }

    private void setBadResult() {
        this.result = "Значение элемента не превышает значение наибольщего элемента в коллекции";
        this.result = TextFormatter.format(result, TextFormatter.Format.RED);
    }
}
