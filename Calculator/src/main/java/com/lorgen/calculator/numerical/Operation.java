package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.components.NumericalValue;
import com.lorgen.calculator.components.Operator;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

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
    public double getValue() {
        //TODO: Perform operation
        return 0;
    }
}
