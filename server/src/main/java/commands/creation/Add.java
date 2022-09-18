package commands.creation;

import commands.abstractions.ArguedServerCommand;
import data.management.DataManager;
import io.TextFormatter;
import subject.model.Organization;

import java.time.LocalDateTime;

public class Add extends ArguedServerCommand<Organization> {

    public Add(DataManager dataManager) {
        super(dataManager);
        this.name = "add {element}";
        this.description = "добавить новый элемент в коллекцию";
    }

    @Override
    public void execute() {
        Organization organization = this.commandArgument;
        defineAutogenFields(organization);
        addOrganizationToTheCollection(organization);
        setGoodResult();
    }

    protected void addOrganizationToTheCollection(Organization organization) {
        this.dataManager.getCollection().add(organization);
        this.dataManager.getCollectionInfo().incrementElementsAmount();
    }

    protected void defineAutogenFields(Organization organization) {
        if (dataManager == null)
            System.err.println("DATA MANAGER");
        if (dataManager.getIdGenerator() == null)
            System.err.println("ID GENERATOR");
        organization.setId(dataManager.getIdGenerator().generateId());
        organization.setCreationDate(LocalDateTime.now());
    }

    private void setGoodResult() {
        this.result = TextFormatter.format("Элемент успешно добавлен", TextFormatter.Format.GREEN);
    }
}
