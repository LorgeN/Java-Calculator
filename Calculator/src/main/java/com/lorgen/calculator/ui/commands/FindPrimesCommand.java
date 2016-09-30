package com.lorgen.calculator.ui.commands;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.numbers.NumberUtils;
import com.lorgen.calculator.ui.Command;
import com.lorgen.calculator.ui.TextColor;

import java.util.List;

public class FindPrimesCommand extends Command {

    public FindPrimesCommand() {
        super("findprimes", "Find primes within given parameters", "fp");
    }

    @Override
    public void executeInternal(String[] args) {
        if (args.length == 0) Calculator.getConsole().err("Please input a number/operation!");
        else if (args.length == 1) {
            Long number = Calculator.getEvaluator().getValue(args[0]).longValue();
            Long estimatedPrimes = NumberUtils.estimatedPrimeAmount(number);
            if (estimatedPrimes > 1000) {
                Calculator.getConsole().result("Estimated prime amount " + TextColor.LIGHT_PURPLE + "(" + estimatedPrimes + ")" + TextColor.RESET + " too high.");
                return;
            } else {
                Calculator.getConsole().info("Estimated prime amount " + TextColor.LIGHT_PURPLE + estimatedPrimes + TextColor.PURPLE + " (May be inaccurate)" + TextColor.RESET + ".");
                List<Long> primes = NumberUtils.findPrimes(number);
                StringBuilder builder = new StringBuilder();
                for (long prime : primes) builder.append(", " + TextColor.RED + prime + TextColor.RESET);
                String str = builder.toString().substring(2);
                Calculator.getConsole().result("Primes " + TextColor.RED + "(" + primes.size() + ")" + TextColor.RESET + " found between " + TextColor.LIGHT_PURPLE + "0.0" + TextColor.RESET + " and " + TextColor.LIGHT_PURPLE + number + TextColor.RESET + ": " + str);
            }
        } else {
            Long number1 = Calculator.getEvaluator().getValue(args[0]).longValue();
            Long number2 = Calculator.getEvaluator().getValue(args[1]).longValue();
            Long estimatedPrimes = NumberUtils.estimatedPrimeAmount(number1, number2);
            if (estimatedPrimes > 1000 || number1 > number2 || number2 - number1 > 10000) {
                Calculator.getConsole().result("Estimated scan too resource intensive.");
                return;
            } else {
                Calculator.getConsole().info("Estimated prime amount " + TextColor.LIGHT_PURPLE + estimatedPrimes + TextColor.PURPLE + " (May be inaccurate)" + TextColor.RESET + ".");
                List<Long> primes = NumberUtils.findPrimes(number1, number2);
                StringBuilder builder = new StringBuilder();
                for (long prime : primes) builder.append(", " + TextColor.RED + prime + TextColor.RESET);
                String str = builder.toString().substring(2);
                Calculator.getConsole().result("Primes " + TextColor.RED + "(" + primes.size() + ")" + TextColor.RESET + " found between " + TextColor.LIGHT_PURPLE + number1 + TextColor.RESET + " and " + TextColor.LIGHT_PURPLE + number2 + TextColor.RESET + ": " + str);
            }
        }
    }
}
