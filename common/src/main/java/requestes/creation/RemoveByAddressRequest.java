package requestes.creation;

import creators.AddressCreator;
import io.Terminal;
import abstractions.requests.CreationCommandRequest;
import subject.model.Address;

public class RemoveByAddressRequest extends CreationCommandRequest<Address> {

    public RemoveByAddressRequest(Terminal terminal) {
        super(terminal, "remove_by_address");
    }

    @Override
    public void setCommandArgs(String[] args) {
        checkArgumentsAmount(args, 0);
        initAddress();
    }

    private void initAddress() {
        AddressCreator addressCreator = new AddressCreator(terminal);
        this.commandArgument = addressCreator.create();
    }
}
