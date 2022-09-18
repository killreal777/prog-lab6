package commands.local;

import abstractions.commands.Command;
import exception.MessagedRuntimeException;
import io.Terminal;

import java.io.FileNotFoundException;

public class ExecuteScript extends Command {
    private final Terminal terminal;
    private String scriptPath;

    public ExecuteScript(Terminal terminal) {
        this.name = "execute_script file_name";
        this.description = "считать и исполнить скрипт из указанного файла";
        this.terminal = terminal;
    }

    @Override
    public void setArgs(String[] args) {
        checkArgumentsAmount(args, 1);
        this.scriptPath = args[0];
    }

    @Override
    public void execute() {
        try {
            terminal.readScript(scriptPath);
            result = String.format("Скрипт %s начал исполняться", scriptPath);
        } catch (FileNotFoundException e) {
            result = String.format("Файл со скриптом %s не найден", scriptPath);
            throw new MessagedRuntimeException(result);
        } catch (MessagedRuntimeException e) {
            result = String.format("Скрипт %s не может быть исполнен: %s", scriptPath, e.getMessage());
            throw new MessagedRuntimeException(result);
        }
    }
}
