package com.lorgen.calculator.ui;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    @Getter private List<Command> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();
    }
}
