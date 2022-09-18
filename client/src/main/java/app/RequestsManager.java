package app;

import abstractions.prototypes.PrototypesManager;
import abstractions.requests.CommandRequest;
import io.Terminal;
import requestes.creation.AddIfMaxRequest;
import requestes.creation.AddRequest;
import requestes.creation.RemoveByAddressRequest;
import requestes.creation.UpdateRequest;
import requestes.simple.FilterStartsWithNameRequest;
import requestes.simple.RemoveByIdRequest;
import requestes.simple.ArglessCommandRequest;


public class RequestsManager extends PrototypesManager<CommandRequest> {
    private final Terminal terminal;
    //private final CommandHistory history;

    public RequestsManager(Terminal terminal) {
        this.terminal = terminal;
        definePrototypes();
        //this.history = new CommandHistory();
    }

    @Override
    protected void definePrototypes() {
        // server creation command requests
        addPrototype("add", new AddRequest(terminal));
        addPrototype("add_if_max", new AddIfMaxRequest(terminal));
        addPrototype("update", new UpdateRequest(terminal));
        addPrototype("remove_any_by_official_address", new RemoveByAddressRequest(terminal));

        // server simple arged command requests
        addPrototype("remove_by_id", new RemoveByIdRequest());
        addPrototype("filter_starts_with_name", new FilterStartsWithNameRequest());

        // server simple argless command requests
        addPrototype("info", new ArglessCommandRequest("info"));
        addPrototype("clear", new ArglessCommandRequest("clear"));
        addPrototype("show", new ArglessCommandRequest("show"));
        addPrototype("head", new ArglessCommandRequest("head"));
        addPrototype("print_ascending", new ArglessCommandRequest("print_ascending"));
    }
}
