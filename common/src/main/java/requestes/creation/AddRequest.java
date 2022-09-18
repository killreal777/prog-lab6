package requestes.creation;

import creators.OrganizationCreator;
import io.Terminal;
import abstractions.requests.CreationCommandRequest;
import subject.model.Organization;

import java.time.LocalDateTime;

public class AddRequest extends CreationCommandRequest<Organization> {
    protected Organization organization;

    public AddRequest(Terminal terminal) {
        super(terminal, "add");
    }

    @Override
    public void setCommandArgs(String[] args) {
        checkArgumentsAmount(args, 0);
        initOrganization();
    }

    protected void initOrganization() {
        OrganizationCreator creator = new OrganizationCreator(terminal);
        this.commandArgument = creator.create();
        terminal = null;
    }
}
