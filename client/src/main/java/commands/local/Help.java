package commands.local;

import abstractions.commands.Command;
import io.TextFormatter;

import java.util.HashMap;


public class Help extends Command {
    private final String help;

    public Help(String help) {
        this.help = help;
        this.name = "help";
        this.description = "вывести справку по доступным командам";
    }


    @Override
    public void execute() {
        result = "add {element}: добавить новый элемент в коллекцию\n" +
                "execute_script file_name: считать и исполнить скрипт из указанного файла\n" +
                "remove_any_by_official_address {officialAddress}: удалить из коллекции один элемент, значение поля officialAddress которого эквивалентно заданному\n" +
                "clear: очистить коллекцию\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "save: сохранить коллекцию в файл\n" +
                "update id {element}: обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id:  удалить элемент из коллекции по его id\n" +
                "history: вывести последние 10 команд (без их аргументов)\n" +
                "add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "print_ascending: вывести элементы коллекции в порядке возрастания\n" +
                "head: вывести первый элемент коллекции\n" +
                "help: вывести справку по доступным командам\n" +
                "exit: завершить программу (без сохранения в файл)\n" +
                "filter_starts_with_name name: вывести элементы, значение поля name которых начинается с заданной подстроки\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }


    private String highlightCommandName(String help) {
        String divider = ": ";
        String name = help.split(divider)[0];
        String description = help.split(divider)[1];

        name = TextFormatter.format(name, TextFormatter.Format.YELLOW);     // highlight

        return name + divider + description;
    }
}
