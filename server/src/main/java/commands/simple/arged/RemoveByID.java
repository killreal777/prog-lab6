package commands.simple.arged;

import commands.abstractions.ArguedServerCommand;
import data.management.DataManager;
import exceptions.MessagedRuntimeException;
import io.TextFormatter;
import model.Organization;

public class RemoveByID extends ArguedServerCommand<Integer> {
    public RemoveByID(DataManager dataManager) {
        super(dataManager);
        this.name = "remove_by_id id";
        this.description = " удалить элемент из коллекции по его id";
    }

    @Override
    public void execute() {
        try {
            Integer id = this.commandArgument;
            Organization organization = findMatchingOrganization(id);
            removeOrganizationFromDataCollection(organization);
            segGoodResult(organization.getName());
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

    private void removeOrganizationFromDataCollection(Organization organization) {
        dataManager.getCollection().remove(organization);
        dataManager.getIdGenerator().setToRemoved(organization.getId());
        dataManager.getCollectionInfo().decrementElementsAmount();
    }

    private void segGoodResult(String removedOrganizationName) {
        result = String.format("Удалена оганизация \"%s\"", removedOrganizationName);
        result = TextFormatter.format(result, TextFormatter.Format.GREEN); // highlighting
    }

    private void setBadResult() {
        result = "В коллекции нет подходящего элемента";
        result = TextFormatter.format(result, TextFormatter.Format.RED); // highlighting
    }
}
