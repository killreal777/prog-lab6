package commands.exceptions;

public class CommandNotFoundException extends RuntimeException {
    private final String message;

    public CommandNotFoundException(String commandName) {
        this.message = String.format("Команда \"%s\" не найдена", commandName);
    }

    @Override
    public String getMessage() {
        return "\033[0;91m" + message + "\033[0m"; // red
    }
}
