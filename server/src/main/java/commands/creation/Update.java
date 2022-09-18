package commands.creation;

import commands.abstractions.ArguedServerCommand;
import data.management.DataManager;
import exception.MessagedRuntimeException;
import io.TextFormatter;
import subject.model.Organization;

import java.time.LocalDateTime;

public class Update extends ArguedServerCommand<Organization> {
    private Integer id;

    public Update(DataManager dataManager) {
        super(dataManager);
        this.name = "update id {element}";
        this.description = "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute() {
        try {
            Organization oldOrganization = findMatchingOrganization(id);
            Organization newOrganization = this.commandArgument;
            updateOrganization(oldOrganization, newOrganization);
            setGoodResult(oldOrganization.getName());
        } catch (MessagedRuntimeException e) {
            setBadResult();
        }
    }

    private Organization findMatchingOrganization(Integer id) throws MessagedRuntimeException {
        for (Organization organization : dataManager.getCollection()) {
            if (organization.getId().equals(id))
                return organization;
        }
        throw new MessagedRuntimeException("Organization not found");
    }

    private void updateOrganization(Organization oldOrganization, Organization newOrganization) {
        dataManager.getCollection().remove(oldOrganization);
        defineAutogenFields(oldOrganization, newOrganization);
        dataManager.getCollection().add(newOrganization);
    }

    private void defineAutogenFields(Organization oldOrganization, Organization newOrganization) {
        newOrganization.setId(oldOrganization.getId());
        newOrganization.setCreationDate(LocalDateTime.now());
    }

    private void setGoodResult(String oldOrganizationName) {
        String message = String.format("Обновлена оганизация \"%s\"", oldOrganizationName);
        result = TextFormatter.format(message, TextFormatter.Format.GREEN);
    }

    private void setBadResult() {
        String message = "В коллекции нет элемента с id " + id;
        result = TextFormatter.format(message, TextFormatter.Format.RED);
    }
}
