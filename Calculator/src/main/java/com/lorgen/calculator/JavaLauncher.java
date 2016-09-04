package com.lorgen.calculator;

import com.lorgen.calculator.ui.CommandManager;
import com.lorgen.calculator.ui.ConsoleHandler;
import com.lorgen.calculator.ui.commands.CalculateCommand;

public class JavaLauncher {
    public static void main(String[] args) {
        ConsoleHandler.getInstance().info("Hello!");
        CommandManager.getInstance().registerCommand(new CalculateCommand());
    }
}
