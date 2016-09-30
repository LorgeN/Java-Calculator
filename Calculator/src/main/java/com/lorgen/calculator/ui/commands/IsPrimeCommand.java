package com.lorgen.calculator.ui.commands;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.numbers.NumberUtils;
import com.lorgen.calculator.ui.Command;
import com.lorgen.calculator.ui.TextColor;

public class IsPrimeCommand extends Command {

    public IsPrimeCommand() {
        super("isprime", "Checks whether if a number is prime or not", "ip");
    }

    @Override
    public void executeInternal(String[] args) {
        if (args.length == 0 || !args[0].matches("[0-9]+")) Calculator.getConsole().err("Please input a number!");
        else Calculator.getConsole().result(TextColor.LIGHT_PURPLE + args[0] + TextColor.RESET + " is" + (NumberUtils.isPrime(Integer.valueOf(args[0])) ? "" : "n't") + " a prime number");
    }
}
