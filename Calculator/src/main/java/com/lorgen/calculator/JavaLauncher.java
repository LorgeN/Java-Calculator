package com.lorgen.calculator;

import com.lorgen.calculator.ui.commands.CalculateCommand;
import com.lorgen.calculator.ui.commands.FactorizeCommand;
import com.lorgen.calculator.ui.commands.FindPrimesCommand;
import com.lorgen.calculator.ui.commands.IsPrimeCommand;

public class JavaLauncher {
    public static void main(String[] args) {
        Calculator.getConsole().info("Hello!");
        Calculator.getConsole().info("Please input a command. Type \"help\" for help.");

        Calculator.getCommandManager().registerCommand(new CalculateCommand());
        Calculator.getCommandManager().registerCommand(new IsPrimeCommand());
        Calculator.getCommandManager().registerCommand(new FactorizeCommand());
        Calculator.getCommandManager().registerCommand(new FindPrimesCommand());
    }
}
