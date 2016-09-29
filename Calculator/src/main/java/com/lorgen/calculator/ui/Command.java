package com.lorgen.calculator.ui;

import lombok.Getter;

import java.util.Arrays;

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

    public String toString(String[] args) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(args).forEach(str -> builder.append(" " + str));
        return builder.toString().trim();
    }
}
