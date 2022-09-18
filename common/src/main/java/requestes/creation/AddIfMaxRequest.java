package requestes.creation;

import io.Terminal;

public class AddIfMaxRequest extends AddRequest {
    public AddIfMaxRequest(Terminal terminal) {
        super(terminal);
        this.commandName = "add_if_max";
        // commandName field is the only difference between AddRequest and AddIfMaxRequest
    }
}
