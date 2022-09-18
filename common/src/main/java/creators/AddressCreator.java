package creators;

import io.Terminal;
import subject.model.Address;
import subject.model.FieldDefinitionException;

/**
 * Class for creating and validation Addresses
 */

public class AddressCreator extends Creator<Address> {
    private final LocationCreator locationCreator;

    private enum AddressArgument {
        ZIP_CODE, TOWN
    }

    private AddressArgument lastSetArgument;

    public AddressCreator(Terminal terminal) {
        super(terminal);
        this.locationCreator = new LocationCreator(terminal);
        this.lastSetArgument = AddressArgument.TOWN;
    }

    @Override
    protected Address createNewInstance() {
        return new Address();
    }

    @Override
    protected void defineFields() throws FieldDefinitionException {
        switch (lastSetArgument) {
        case TOWN:
            defineZipCode();
        case ZIP_CODE:
            defineTown();
        }
        // break statement MUST NOT be here
    }

    private void defineZipCode() {
        String requirements = formatRequirements("String, not null, length <= 16");
        String invitationMessage = "Введите Zip Code организации: " + requirements;
        String input = terminal.readLineEntire(invitationMessage);
        creatingObject.setZipCode(input);
        this.lastSetArgument = AddressArgument.ZIP_CODE;
    }

    private void defineTown() {
        creatingObject.setTown(locationCreator.create());
        this.lastSetArgument = AddressArgument.TOWN;
    }
}
