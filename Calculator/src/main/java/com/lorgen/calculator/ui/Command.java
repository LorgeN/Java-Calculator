package com.lorgen.calculator.ui;

import lombok.Getter;

public abstract class Command {
    @Getter private String name;
    @Getter private String info;
    @Getter private String[] aliases;

    public Command(String name, String info, String... aliases) {
        this.name = name;
        this.info = info;
        this.aliases = aliases;
    }

    public abstract void execute(String[] args);
}
