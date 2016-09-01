package com.lorgen.calculator.numerical;

import com.lorgen.calculator.abstracts.NumericalValue;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Operation implements NumericalValue {
    @Getter private String rawString;
    @Getter private List<NumericalParentheses> parentheses;
    @Getter private List<BinaryOperation> operations;

    public Operation(String raw) {
        this.rawString = raw;
        this.parentheses = new LinkedList<>();
        this.operations = new LinkedList<>();

        if (raw.indexOf('(') != -1) {
            Map<Integer, Integer> parenthesesLocations = new LinkedHashMap<>();

            char ch;
            for (int i = 0; i < raw.length(); i++) {
                ch = raw.charAt(i);
                if (ch == '(') {
                    int starting = i;
                    int closing = 0;
                    boolean foundClosing = false;
                    int parenthesesWithin = 0;
                    i++;

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

                    parenthesesLocations.put(starting, closing);
                }
            }

            this.parentheses.addAll(parenthesesLocations.entrySet().stream().map(entry ->
                    new NumericalParentheses(raw.substring(entry.getKey() + 1, entry.getValue() - 1)))
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public double getValue() {
        return 0;
    }
}
