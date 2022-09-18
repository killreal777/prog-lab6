package commands.exceptions;

import io.TextFormatter;

/**
 * Commands throw this exception if their arguments are wrong to stop the execution
 */

public class CommandArgumentException extends RuntimeException {
    private final String message;

    public CommandArgumentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return TextFormatter.format(message, TextFormatter.Format.RED);
    }
}
