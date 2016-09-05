package com.lorgen.calculator.numerical;

import com.lorgen.calculator.evaluators.Operation;
import com.lorgen.calculator.exception.EvaluationException;

public class NumericalParentheses extends Operation {

    public NumericalParentheses(String raw) throws EvaluationException {
        super(raw);
    }

    public String getRawString() {
        return "(" + super.getRawString() + ")";
    }
}
