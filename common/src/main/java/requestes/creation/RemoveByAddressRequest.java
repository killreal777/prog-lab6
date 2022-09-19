package requestes.creation;

import abstractions.creator.Creator;
import abstractions.requests.CreationCommandRequest;
import model.Address;


public class RemoveByAddressRequest extends CreationCommandRequest<Address> {
    public RemoveByAddressRequest(Creator<Address> creator) {
        super("remove_by_address", creator);
    }

    @Override
    public void setCommandArgs(String[] args) {
        checkArgumentsAmount(args, 0);
        createArgument();
    }
}
