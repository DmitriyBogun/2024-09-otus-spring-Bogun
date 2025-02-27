package ru.otus.hw.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ConsoleCommands {

    @ShellMethod(value = "Open console with database", key = "OC")
    public String openConsole() {
        try {
            Console.main();
            return "Консоль запущена, пользуйся на здоровье=)";
        } catch (Exception ex) {
            return String.format("Ошибка запуска консоли %s!",
                    ex.getLocalizedMessage());
        }
    }
}
