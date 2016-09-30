package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.MathematicalObject;
import com.lorgen.calculator.evaluators.Operation;
import com.lorgen.calculator.exception.EvaluationException;

import java.util.List;

public class NumericalParentheses extends Operation {

    public NumericalParentheses(List<MathematicalObject> raw) throws EvaluationException {
        super(raw);
    }

    public String getString() {
        return "(" + super.getString() + ")";
    }
}
