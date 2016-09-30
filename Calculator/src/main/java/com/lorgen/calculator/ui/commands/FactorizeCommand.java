package com.lorgen.calculator.ui.commands;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.numbers.Number;
import com.lorgen.calculator.ui.Command;
import com.lorgen.calculator.ui.TextColor;

public class FactorizeCommand extends Command {

    public FactorizeCommand() {
        super("factorize", "Factorize given number into primes", "f");
    }

    @Override
    public void execute(String[] args) {
            if (args.length == 0) {
                Calculator.getConsole().err("Not enough arguments!");
                return;
            }

            Number number = Calculator.getEvaluator().getValue(toString(args));
            StringBuilder builder = new StringBuilder();
            Long[] factors = number.factorize();
            for (long l : factors) builder.append(" * " + l);
            String str = builder.toString().substring(3);
            Calculator.getConsole().result("Factorized using primes: " + TextColor.RED + number + " = " + str);
    }
}
