package requestes.creation;

import creators.OrganizationCreator;
import exception.ArgumentTypeException;
import io.Terminal;
import abstractions.requests.CreationCommandRequest;
import subject.model.Organization;

public class UpdateRequest extends CreationCommandRequest<Organization> {

    public UpdateRequest(Terminal terminal) {
        super(terminal, "update");
    }

    @Override
    public void setCommandArgs(String[] args) {
        checkArgumentsAmount(args, 1);
        Integer id = parseId(args[0]);
        initOrganization();
        this.commandArgument.setId(id);
    }

    private Integer parseId(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new ArgumentTypeException(ArgumentTypeException.ArgumentType.LONG);
        }
    }

    private void initOrganization() {
        OrganizationCreator organizationCreator = new OrganizationCreator(terminal);
        this.commandArgument = organizationCreator.create();
    }
}
