package com.lorgen.calculator.evaluators;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.components.MathematicalFunction;
import com.lorgen.calculator.components.MathematicalObject;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.exception.EvaluationException;
import com.lorgen.calculator.numerical.Number;
import com.lorgen.calculator.numerical.NumericalObject;
import com.lorgen.calculator.numerical.NumericalParentheses;
import com.lorgen.calculator.ui.TextColor;
import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

public class Evaluator {
    public List<MathematicalObject> evaluate2(String string) throws EvaluationException {
        Calculator.getConsole().info("Evaluating string " + TextColor.LIGHT_PURPLE + "\"" + string + "\":");
        Matcher matcher = Calculator.getComponentManager().getPattern().matcher(string);
        Calculator.getConsole().info("Compiled matcher based on pattern \"" + Calculator.getComponentManager().getPattern().pattern() + "\".");
        LinkedMap<Integer, Integer> objectIndexes = new LinkedMap<>();
        while (matcher.find()) {
            if (objectIndexes.size() != 0) {
                int lastValue = objectIndexes.get(objectIndexes.lastKey());
                if (lastValue != matcher.start()) objectIndexes.put(lastValue, matcher.start());
            }
            objectIndexes.put(matcher.start(), matcher.end());
        }

        LinkedList<MathematicalObject> components = new LinkedList<>();
        for (OrderedMapIterator<Integer, Integer> iterator = objectIndexes.mapIterator(); iterator.hasNext(); iterator.next()) {
            String found = string.substring(iterator.getKey(), iterator.getValue());
            if (found.equals("(")) {

            } else {
                Optional<MathematicalObject> mathematicalObjectOptional = this.getComponent(found);
                if (mathematicalObjectOptional.isPresent()) components.add(mathematicalObjectOptional.get());
                else throw new EvaluationException("Matcher found string \"" + found + "\" that was not a mathematical object!");
            }
        }

        return components;
    }

    public Number getValue(String string) {
        try {
            return (new Operation(this.evaluate(string))).getValue();
        } catch (EvaluationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MathematicalObject> evaluate(String string) throws EvaluationException {
        Calculator.getConsole().info("Evaluating string " + TextColor.LIGHT_PURPLE + "\"" + string + "\":");

        List<MathematicalObject> components = new LinkedList<>();
        String leftToEvaluate = string;

        try {
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (ch == '(') {
                    Calculator.getConsole().info("Found opening parentheses.");
                    Optional<MathematicalObject> previous = this.getPreviousComponent(leftToEvaluate, ch);
                    if (previous.isPresent()) {
                        components.add(previous.get());
                        components.add(Operator.MULTIPLICATION);
                    }

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

                    Optional<MathematicalObject> check = this.getPreviousComponent(leftToEvaluate, ch);
                    if (check.isPresent()) components.add(check.get());
                    Operator operator = Operator.fromCharacter(ch);
                    components.add(operator);

                    leftToEvaluate = leftToEvaluate.substring(leftToEvaluate.indexOf(ch) + 1);
                    Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEvaluate);
                } else if (i + 1 == string.length()) {
                    Optional<MathematicalObject> component = this.getComponent(leftToEvaluate);
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

    private Optional<MathematicalObject> getPreviousComponent(String string, char ch) throws EvaluationException {
        try {
            int index = string.indexOf(ch);
            if (index == 0) return Optional.empty();
            else return this.getComponent(string.substring(0, index));
        } catch (Exception e) {
            throw new EvaluationException(e);
        }
    }

    private Optional<MathematicalObject> getComponent(String string) throws EvaluationException {
        if (string.matches("-\\d+|\\d+")) return Optional.of(NumericalObject.fromDouble(Double.parseDouble(string)));
        else if (string.length() == 1) {
            char ch = string.charAt(0);
            return Operator.isOperator(ch) ? Optional.of(Operator.fromCharacter(ch)) : Optional.empty();
        } else if (MathematicalFunction.isFunction(string)) return Optional.of(MathematicalFunction.fromString(string));
        else return Optional.empty();
    }
}
