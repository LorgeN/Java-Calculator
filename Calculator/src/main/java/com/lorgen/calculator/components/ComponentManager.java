package com.lorgen.calculator.components;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ComponentManager {
    @Getter private List<String> delimiters;
    @Getter private Pattern pattern;

    public ComponentManager() {
        this.delimiters = new ArrayList<>();

        Arrays.stream(MathematicalFunction.values()).forEach(function -> this.delimiters.add(function.getSymbol()));
        Arrays.stream(Operator.values()).forEach(operator -> this.delimiters.add(operator.getSymbol()));
        this.delimiters.add("(");
        this.delimiters.add(")");

        StringBuilder patternString = new StringBuilder();
        this.delimiters.forEach(str -> patternString.append("|" + str));
        this.pattern = Pattern.compile(patternString.toString().substring(1));
    }
}
