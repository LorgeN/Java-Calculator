package com.lorgen.calculator.ui;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    @Getter private static CommandManager instance = new CommandManager();

    @Getter private List<Command> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();
        this.registerCommand(new Command("help", "View all commands") {
            @Override
            public void execute(String[] args) {
                ConsoleHandler.getInstance().info("All commands:");
                for (Command cmd : getCommands()) ConsoleHandler.getInstance().info("  - " + cmd.getName() + " (" + cmd.getInfo() + ")");
            }
        });
    }

    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    public boolean invokeCommand(String line) {
        String cmd;
        String[] args;
        if (line.contains(" ")) {
            cmd = line.substring(0, line.indexOf(" "));
            args = line.substring(line.indexOf(" ") + 1).split(" ");
        } else {
            cmd = line.trim();
            args = new String[0];
        }

        Command found = this.commands.stream().filter(command -> command.getName().equalsIgnoreCase(cmd)
                || Arrays.stream(command.getAliases()).filter(String -> String.equalsIgnoreCase(cmd))
                .findFirst().isPresent()).findFirst().orElse(null);

        if (found == null) {
            return false;
        } else {
            found.execute(args);
            return true;
        }
    }
}
