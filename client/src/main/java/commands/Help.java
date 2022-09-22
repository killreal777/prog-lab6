package commands;

import abstractions.command.Command;
import io.Format;
import io.TextFormatter;
import register.CommandRecord;


public class Help extends Command {
    public Help() {
        writeResult();
    }

    private void writeResult() {
        for (CommandRecord record : CommandRecord.values()) {
            String name = TextFormatter.format(record.getName(), Format.YELLOW);
            String args = record.getArgumentsNames();
            if (!args.equals(""))
                args = TextFormatter.format(TextFormatter.format(
                        " " + args, Format.WHITE), Format.BOLD);
            String description = TextFormatter.format(record.getHelp(), Format.ITALIC);
            result = result + String.format("%s%s: %s\n", name, args, description);
        }
    }

    @Override
    public void execute() {
        // nothing to do
    }

    private String highlightCommandName(String help) {
        String divider = ": ";
        String name = help.split(divider)[0];
        String description = help.split(divider)[1];

        name = TextFormatter.format(name, Format.YELLOW); // highlight

        return name + divider + description;
    }
}
