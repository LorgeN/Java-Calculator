package com.lorgen.calculator.evaluators;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.exception.EvaluationException;
import com.lorgen.calculator.numerical.NumericalObject;
import com.lorgen.calculator.numerical.NumericalParentheses;
import com.lorgen.calculator.ui.TextColor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Evaluator {
    public List<Component> evaluate(String string) throws EvaluationException {
        //TODO: Identify negative numbers in parentheses and create componentalizer class
        Calculator.getConsole().info("New operation " + TextColor.LIGHT_PURPLE + "\"" + string + "\":");

        List<Component> components = new LinkedList<>();
        String leftToEval = string;

        try {
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (ch == '(') {
                    int index = leftToEval.indexOf(ch);
                    if (index != 0) {
                        components.add(NumericalObject.fromDouble(Double.valueOf(leftToEval.substring(0, index))));
                        components.add(Operator.MULTIPLICATION);
                    }

                    Calculator.getConsole().info("Found opening parentheses.");
                    int starting = i + 1, closing, parenthesesWithin = 0;
                    identifyClosing: while (true) {
                        i++;
                        ch = string.charAt(i);
                        if (ch == '(') parenthesesWithin++;
                        else if (ch == ')') {
                            if (parenthesesWithin == 0) {
                                closing = i;
                                break identifyClosing;
                            } else parenthesesWithin--;
                        }
                    }

                    if (components.size() != 0) {
                        if (components.get(components.size() - 1).equals(Operator.SUBTRACTION)) {
                            components.set(components.size() - 1, NumericalObject.fromDouble(-1.0));
                            components.add(Operator.MULTIPLICATION);
                        }
                    }

                    components.add(new NumericalParentheses(string.substring(starting, closing)));

                    if (i + 1 == string.length()) {
                        Calculator.getConsole().info("Completed evaluating operation.");
                    } else {
                        leftToEval = string.substring(closing + 1);
                        Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEval);
                        if (!Operator.isOperator(leftToEval.charAt(0))) {
                            components.add(Operator.MULTIPLICATION);
                        }
                    }
                } else if (Operator.isOperator(ch)) {
                    Calculator.getConsole().info("Operator found: " + TextColor.LIGHT_PURPLE + ch);
                    Operator operator = Operator.fromCharacter(ch);
                    int index = leftToEval.indexOf(ch);
                    if (index != 0) components.add(NumericalObject.fromDouble(Double.valueOf(leftToEval.substring(0, index))));
                    components.add(operator);
                    leftToEval = leftToEval.substring(index + 1);
                    Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEval);
                } else if (i + 1 == string.length()) {
                    double prev = Double.valueOf(leftToEval);
                    components.add(NumericalObject.fromDouble(prev));
                    Calculator.getConsole().info("Completed evaluating operation.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EvaluationException("Unable to evaluate operation " + string);
        }
    }

    private Optional<Component> checkForComponentBefore(String string, char ch) throws EvaluationException {
        try {
            int index = string.indexOf(ch);
            if (index == 0) return Optional.empty();
            else if (index == 1 && Operator.isOperator(string.charAt(0))) return Optional.of(Operator.fromCharacter(string.charAt(0)));
            else return Optional.of(NumericalObject.fromDouble(Double.valueOf(string.substring(0, index))));
        } catch (Exception e) {
            throw new EvaluationException(e);
        }
    }
}
