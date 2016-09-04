package com.lorgen.calculator.numerical;

public class NumericalParentheses extends Operation {

    public NumericalParentheses(String raw) {
        super(raw);
    }

    public String getRawString() {
        return "(" + super.getRawString() + ")";
    }
}
