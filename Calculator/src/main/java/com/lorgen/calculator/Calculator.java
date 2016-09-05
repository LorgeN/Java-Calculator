package com.lorgen.calculator;

import com.lorgen.calculator.ui.CommandManager;
import com.lorgen.calculator.ui.ConsoleHandler;
import lombok.Getter;

public class Calculator {
    @Getter private static CommandManager commandManager = new CommandManager();
    @Getter private static ConsoleHandler console = new ConsoleHandler();
}
