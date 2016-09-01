package com.lorgen.calculator.ui;

import lombok.Getter;

public abstract class Command {
    @Getter private String name;
    @Getter private String usage;
    @Getter private String[] aliases;

    public Command(String name, String usage, String... aliases) {
        this.name = name;
        this.usage = usage;
        this.aliases = aliases;
    }

    public abstract void execute(String[] args);
}
