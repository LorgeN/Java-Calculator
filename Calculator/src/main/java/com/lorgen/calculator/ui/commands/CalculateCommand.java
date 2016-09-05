package com.lorgen.calculator.ui.commands;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.exception.EvaluationException;
import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.evaluators.Operation;
import com.lorgen.calculator.ui.Command;
import com.lorgen.calculator.ui.TextColor;

import java.util.Arrays;

public class CalculateCommand extends Command {

    public CalculateCommand() {
        super("calculate", "Evaluate and calculate an operation", "calc", "c");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            Calculator.getConsole().err("Please input an argument!");
        } else {
            StringBuilder builder = new StringBuilder();
            Arrays.stream(args).forEach(s -> builder.append(s));
            String string = builder.toString().trim();
            Calculator.getConsole().info("Initiating evaluation process for " + TextColor.LIGHT_PURPLE + string + TextColor.RESET + ":");
            Operation operation;

            try {
                operation = new Operation(string);
            } catch (EvaluationException e) {
                Calculator.getConsole().err(e.getMessage());
                return;
            }

            Calculator.getConsole().info("Initiating calculation...");

            try {
                double value = operation.getValue();
                Calculator.getConsole().info("Value found: " + TextColor.LIGHT_PURPLE + value);
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
            }
        }
    }
}
