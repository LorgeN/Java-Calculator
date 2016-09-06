package com.lorgen.calculator.ui.commands;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.numerical.NumberUtils;
import com.lorgen.calculator.ui.Command;
import com.lorgen.calculator.ui.TextColor;

public class FactorizeCommand extends Command {

    public FactorizeCommand() {
        super("factorize", "Factorize given number into primes", "f");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0 || !args[0].matches("[0-9]+")) Calculator.getConsole().err("Please input a number!");
        else {
            Long number = Long.valueOf(args[0]);
            StringBuilder builder = new StringBuilder();
            Long[] factors = NumberUtils.factorize(number);
            for (long l : factors) builder.append(" * " + l);
            String str = builder.toString().substring(3);
            Calculator.getConsole().info("Factorized using primes: " + TextColor.RED + number + " = " + str);
        }
    }
}
