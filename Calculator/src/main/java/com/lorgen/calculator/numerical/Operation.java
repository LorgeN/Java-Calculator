package com.lorgen.calculator.numerical;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.components.Priority;
import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.ui.ConsoleHandler;
import com.lorgen.calculator.ui.TextColor;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Operation implements NumericalValue {
    @Getter private String rawString;
    @Getter private List<Component> components;

    private boolean containsHigh = false;
    private boolean containsAboveStandard = false;
    private boolean containsStandard = false;

    public Operation(String raw) {
        this.rawString = raw;
        Calculator.getConsole().info("New operation! " + TextColor.LIGHT_PURPLE + "(" + raw + ")");

        this.components = new LinkedList<>();
        String leftToEval = raw;

        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            if (ch == '(') {
                Calculator.getConsole().info("Found opening parentheses.");
                int starting = i + 1, closing, parenthesesWithin = 0;
                identifyClosing : while (true) {
                    i++;
                    ch = raw.charAt(i);
                    if (ch == '(') {
                        parenthesesWithin++;
                    } else if (ch == ')') {
                        if (parenthesesWithin == 0) {
                            closing = i;
                            break identifyClosing;
                        } else parenthesesWithin--;
                    }
                }

                this.components.add(new NumericalParentheses(raw.substring(starting, closing)));
                if (i + 1 == raw.length()) {
                    Calculator.getConsole().info("Completed evaluating operation.");
                } else {
                    leftToEval = raw.substring(closing + 1);
                    Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEval);
                }

                this.containsHigh = true;
            } else if (Operator.isOperator(ch)) {
                Calculator.getConsole().info("Operator found: " + TextColor.LIGHT_PURPLE + ch);
                Operator operator = Operator.fromCharacter(ch);
                int index = leftToEval.indexOf(ch);
                if (index != 0) this.components.add(NumericalValue.fromDouble(Double.valueOf(leftToEval.substring(0, index))));
                this.components.add(operator);
                leftToEval = leftToEval.substring(index + 1);
                Calculator.getConsole().info("Left to evaluate: " + TextColor.LIGHT_PURPLE + leftToEval);

                switch (operator.getPriority()) {
                    case HIGH:
                        this.containsHigh = true;
                        break;
                    case ABOVE_STANDARD:
                        this.containsAboveStandard = true;
                        break;
                    case STANDARD:
                        this.containsStandard = true;
                        break;
                }
            } else if (i + 1 == raw.length()) {
                double prev = Double.valueOf(leftToEval);
                this.components.add(NumericalValue.fromDouble(prev));
                Calculator.getConsole().info("Completed evaluating operation.");
            }
        }

        Calculator.getConsole().info("Components in operation " + TextColor.LIGHT_PURPLE + this.getRawString() + ":");
        this.printComponents();
        Calculator.getConsole().info("Re-evaluated string: " + TextColor.LIGHT_PURPLE + this.getEvaluatedString());
    }
    
    public Operation(List<Component> components) {
    }

    @Override
    public double getValue() throws UnexpectedResultException {
        if (this.containsHigh) {
            this.components = sort(this.getComponents(), Priority.HIGH);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "HIGH" + TextColor.RESET + ":");
            this.printComponents();
        }

        if (this.containsAboveStandard) {
            this.components = sort(this.getComponents(), Priority.ABOVE_STANDARD);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "ABOVE_STANDARD" + TextColor.RESET + ":");
            this.printComponents();
        }

        if (this.containsStandard) {
            this.components = sort(this.getComponents(), Priority.STANDARD);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "STANDARD" + TextColor.RESET + ":");
            this.printComponents();
        }

        if (this.getComponents().size() > 1) throw new UnexpectedResultException("More components than expected!");
        if (!(this.getComponents().get(0) instanceof NumericalValue)) throw new UnexpectedResultException("Final component isn't a numerical value!");

        return ((NumericalValue) this.getComponents().get(0)).getValue();
    }

    private void printComponents() {
        for (Component component : this.getComponents()) Calculator.getConsole().info("  - " + component.getComponentType() + ": " + TextColor.LIGHT_PURPLE + component.getRawString());
    }

    private String getEvaluatedString() {
        StringBuilder builder = new StringBuilder();
        this.getComponents().forEach(component -> builder.append(component.getRawString() + " "));
        return builder.toString().trim();
    }

    private List<Component> sort(List<Component> list, Priority priority) {
        if (list.size() == 1) return list;
        List<Component> refreshedComponents = new LinkedList<>();
        refreshedComponents.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) instanceof Operator) {
                Operator operator = (Operator) list.get(i);
                if (operator.getPriority() == priority) {
                    NumericalBinaryOperation operation = new NumericalBinaryOperation(operator,
                            (NumericalValue) refreshedComponents.get(refreshedComponents.size() - 1),
                            (NumericalValue) list.get(i + 1));
                    refreshedComponents.set(refreshedComponents.size() - 1, operation);
                    i++;
                } else refreshedComponents.add(operator);
            } else refreshedComponents.add(list.get(i));
        }
        return refreshedComponents;
    }
}
