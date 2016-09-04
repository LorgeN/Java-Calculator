package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.components.NumericalValue;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.components.Priority;
import lombok.Getter;

import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Operation implements NumericalValue {
    @Getter private String rawString;
    @Getter private List<Component> components;

    public Operation(String raw) {
        this.rawString = raw;
        this.components = new LinkedList<>();
        String fromPreviousOperator = raw;
        char ch;
        if (raw.indexOf('(') != -1) {
            for (int i = 0; i < raw.length(); i++) {
                ch = raw.charAt(i);
                if (ch == '(') {
                    i++;
                    int starting = i;
                    int closing = 0;
                    boolean foundClosing = false;
                    int parenthesesWithin = 0;

                    while (!foundClosing) {
                        ch = raw.charAt(i);
                        if (ch == '(') parenthesesWithin++;
                        else if (ch == ')')
                            if (parenthesesWithin == 0) {
                                closing = i;
                                foundClosing = true;
                            } else {
                                parenthesesWithin--;
                            }
                        i++;
                    }

                    components.add(new NumericalParentheses(raw.substring(starting, closing)));
                    fromPreviousOperator = fromPreviousOperator.substring(closing + 1);
                } else if (Operator.isOperator(ch)) {
                    Operator operator = Operator.fromCharacter(ch);
                    int index = fromPreviousOperator.indexOf(ch);
                    double prev = Double.valueOf(fromPreviousOperator.substring(0, index));
                    fromPreviousOperator = fromPreviousOperator.substring(index + 2);
                    this.components.add(NumericalValue.fromDouble(prev));
                    this.components.add(operator);
                }
            }
        }

        System.out.println("Components in operation " + this.getRawString() + ":");
        StringBuilder builder = new StringBuilder();
        this.getComponents().stream().forEach(component -> builder.append(component.getRawString() + " "));
        this.rawString = builder.toString().trim();
        System.out.println("Recompiled string: " + this.getRawString());
        for (Component component : this.getComponents())
            System.out.println("  - " + component.getComponentType() + " (" + component.getRawString() + ")");
    }

    @Override
    public double getValue() throws UnexpectedException {
        this.components = sort(this.getComponents(), Priority.HIGH);
        this.components = sort(this.getComponents(), Priority.ABOVE_STANDARD);
        this.components = sort(this.getComponents(), Priority.STANDARD);

        if (this.getComponents().size() > 1) throw new UnexpectedException("More components than expected!");
        if (!(this.getComponents().get(0) instanceof NumericalValue)) throw new UnexpectedException("Final component isn't a numerical value!");

        return ((NumericalValue) this.getComponents().get(0)).getValue();
    }

    /**
     * Internal sorting algorithm
     * @param list List to be sorted
     * @param priority Sort operators with this priority
     * @return Sorted list
     */
    private List<Component> sort(List<Component> list, Priority priority) {
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
                }
            }
        }
    }
}
