package commands.creation;

import commands.abstractions.ArguedServerCommand;
import data.management.DataManager;
import exception.MessagedRuntimeException;
import io.TextFormatter;
import subject.model.Address;
import subject.model.Organization;

public class RemoveByAddress extends ArguedServerCommand<Address> {

    public RemoveByAddress(DataManager dataManager) {
        super(dataManager);
        this.name = "remove_any_by_official_address {officialAddress}";
        this.description = "удалить из коллекции один элемент, "
                + "значение поля officialAddress которого эквивалентно заданному";
    }

    @Override
    public void execute() {
        try {
            Address address = this.commandArgument;
            Organization organization = findMatchingOrganization(address);
            removeOrganizationFromDataCollection(organization);
            segGoodResult(organization.getName());
        } catch (MessagedRuntimeException e) {
            setBadResult();
        }
    }

    private Organization findMatchingOrganization(Address address) throws MessagedRuntimeException {
        for (Organization organization : dataManager.getCollection()) {
            if (organization.getOfficialAddress().equals(address))
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
