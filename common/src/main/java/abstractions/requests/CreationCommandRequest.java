package abstractions.requests;

import io.Terminal;

import java.io.Serializable;

public abstract class CreationCommandRequest<ArgType> extends ArguedCommandRequest<ArgType> {
    protected Terminal terminal;

    public CreationCommandRequest(Terminal terminal, String commandName) {
        super(commandName);
        this.terminal = terminal;
    }
}
