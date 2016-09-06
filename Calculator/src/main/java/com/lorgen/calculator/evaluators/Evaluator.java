package com.lorgen.calculator.evaluators;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.components.TrigonometricFunction;
import com.lorgen.calculator.exception.EvaluationException;
import com.lorgen.calculator.numerical.NumericalObject;
import com.lorgen.calculator.numerical.NumericalParentheses;
import com.lorgen.calculator.ui.TextColor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Evaluator {
    public List<Component> evaluate(String string) throws EvaluationException {
        Calculator.getConsole().info("Evaluating string " + TextColor.LIGHT_PURPLE + "\"" + string + "\":");

        List<Component> components = new LinkedList<>();
        String leftToEvaluate = string;

        try {
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (ch == '(') {
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

                    components.add(new NumericalParentheses(string.substring(starting, closing)));

                    if (i + 1 == string.length()) {
                        Calculator.getConsole().info("Completed evaluating operation.");
                    } else {
                        leftToEvaluate = string.substring(closing + 1);
                        Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEvaluate);
                        if (!Operator.isOperator(leftToEvaluate.charAt(0))) components.add(Operator.MULTIPLICATION);
                    }
                } else if (Operator.isOperator(ch)) {
                    Calculator.getConsole().info("Operator found: " + TextColor.LIGHT_PURPLE + ch);

                    Optional<Component> check = this.getPreviousComponent(leftToEvaluate, ch);
                    if (check.isPresent()) components.add(check.get());
                    Operator operator = Operator.fromCharacter(ch);
                    components.add(operator);

                    leftToEvaluate = leftToEvaluate.substring(leftToEvaluate.indexOf(ch) + 1);
                    Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEvaluate);
                } else if (i + 1 == string.length()) {
                    Optional<Component> component = this.getComponent(leftToEvaluate);
                    if (component.isPresent()) components.add(component.get());

                    Calculator.getConsole().info("Completed evaluating operation.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EvaluationException("Unable to evaluate operation " + string);
        }

        return components;
    }

    private Optional<Component> getPreviousComponent(String string, char ch) throws EvaluationException {
        try {
            int index = string.indexOf(ch);
            if (index == 0) return Optional.empty();
            else return this.getComponent(string.substring(0, index));
        } catch (Exception e) {
            throw new EvaluationException(e);
        }
    }

    private Optional<Component> getComponent(String string) throws EvaluationException {
        try {
            if (string.length() == 1 && Operator.isOperator(string.charAt(0))) return Optional.of(Operator.fromCharacter(string.charAt(0)));
            else if (string.length() == 3 && TrigonometricFunction.isFunction(string)) return Optional.of(TrigonometricFunction.fromString(string));
            else return Optional.of(NumericalObject.fromDouble(Double.valueOf(string)));
        } catch (Exception e) {
            throw new EvaluationException(e);
        }
    }
}
