package com.lorgen.calculator.evaluators;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.objects.Delimiters;
import com.lorgen.calculator.objects.MathematicalFunction;
import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.Operator;
import com.lorgen.calculator.exceptions.EvaluationException;
import com.lorgen.calculator.numbers.Number;
import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.operations.NumericalOperation;
import com.lorgen.calculator.operations.Operation;
import com.lorgen.calculator.parentheses.Parentheses;
import com.lorgen.calculator.ui.TextColor;
import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

public class Evaluator {
    public List<MathematicalObject> evaluate(String string) throws EvaluationException {
        Calculator.getConsole().info("Evaluating string " + TextColor.LIGHT_PURPLE + "\"" + string + "\":");

        LinkedList<MathematicalObject> components = new LinkedList<>();

        Optional<MathematicalObject> objectOptional = this.getComponent(string);
        if (objectOptional.isPresent()) {
            components.add(objectOptional.get());
            return components;
        }

        Matcher matcher = Calculator.getComponentManager().getPattern().matcher(string);
        LinkedMap<Integer, Integer> objectIndexes = new LinkedMap<>();
        while (matcher.find()) {
            if (objectIndexes.size() != 0) {
                int lastValue = objectIndexes.get(objectIndexes.lastKey());
                if (lastValue != matcher.start()) objectIndexes.put(lastValue, matcher.start());
            } else if (matcher.start() != 0) objectIndexes.put(0, matcher.start());
            objectIndexes.put(matcher.start(), matcher.end());
        }

        int lastValue = objectIndexes.get(objectIndexes.lastKey());
        if (lastValue != string.length()) objectIndexes.put(lastValue, string.length());

        for (OrderedMapIterator<Integer, Integer> iterator = objectIndexes.mapIterator(); iterator.hasNext();) {
            iterator.next();
            String found = string.substring(iterator.getKey(), iterator.getValue());
            if (found.matches(Delimiters.OPENING_PARENTHESES.getRegex())) {
                int within = 0, starting = iterator.getKey();
                while (true) {
                    if (!iterator.hasNext()) throw new EvaluationException("Uneven amount of parentheses! " + string);
                    iterator.next();
                    String internal = string.substring(iterator.getKey(), iterator.getValue());
                    if (internal.matches(Delimiters.OPENING_PARENTHESES.getRegex())) {
                        within++;
                    } else if (internal.matches(Delimiters.CLOSING_PARENTHESES.getRegex())) {
                        if (within == 0) {
                            components.add(Parentheses.of(this.evaluate(string.substring(starting + 1, iterator.getValue() - 1))));
                            break;
                        } else within--;
                    }
                }
            } else {
                Optional<MathematicalObject> mathematicalObjectOptional = this.getComponent(found);
                if (mathematicalObjectOptional.isPresent()) {
                    components.add(mathematicalObjectOptional.get());
                } else throw new EvaluationException("Matcher found string \"" + found + "\" that was not a mathematical object! " + string);
            }
        }

        return components;
    }

    public Number getValue(String string) {
        try {
            return Operation.of(this.evaluate(string)).getValue();
        } catch (EvaluationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Optional<MathematicalObject> getComponent(String string) throws EvaluationException {
        if (string.matches("-?\\d+\\.?\\d*")) return Optional.of(NumericalObject.fromDouble(Double.parseDouble(string)));
        else if (string.length() == 1) {
            char ch = string.charAt(0);
            return Operator.isOperator(ch) ? Optional.of(Operator.fromCharacter(ch)) : Optional.empty();
        } else if (MathematicalFunction.isFunction(string)) return Optional.of(MathematicalFunction.fromString(string));
        else return Optional.empty();
    }
}
