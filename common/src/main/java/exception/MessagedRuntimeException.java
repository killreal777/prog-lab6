package exception;

import io.TextFormatter;

public class MessagedRuntimeException extends RuntimeException {
    private final String message;

    public MessagedRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return TextFormatter.format(message, TextFormatter.Format.RED);
    }
}
