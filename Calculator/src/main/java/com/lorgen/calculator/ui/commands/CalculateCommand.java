package com.lorgen.calculator.ui.commands;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.exceptions.EvaluationException;
import com.lorgen.calculator.exceptions.UnexpectedResultException;
import com.lorgen.calculator.operations.Operation;
import com.lorgen.calculator.ui.Command;
import com.lorgen.calculator.ui.TextColor;

import java.util.Arrays;

public class CalculateCommand extends Command {

    public CalculateCommand() {
        super("calculate", "Evaluate and calculate an operation", "calc", "c");
    }

    @Override
    public void executeInternal(String[] args) {
        if (args.length == 0) {
            Calculator.getConsole().err("Please input an argument!");
        } else {
            StringBuilder builder = new StringBuilder();
            Arrays.stream(args).forEach(s -> builder.append(s));
            String string = builder.toString().trim();
            Calculator.getConsole().info("Initiating evaluation process for " + TextColor.LIGHT_PURPLE + string + TextColor.RESET + ":");
            try {
                Operation operation = Operation.of(Calculator.getEvaluator().evaluate(string));
                double value = operation.getPrimitiveValue();
                Calculator.getConsole().result(TextColor.RED + operation.getString() + TextColor.PURPLE + " = " + TextColor.RED + value);
            } catch (EvaluationException | UnexpectedResultException e) {
                e.printStackTrace();
            }
        }
    }
}
