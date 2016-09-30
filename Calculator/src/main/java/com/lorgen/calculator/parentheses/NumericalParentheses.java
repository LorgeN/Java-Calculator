package com.lorgen.calculator.parentheses;

import com.lorgen.calculator.objects.DefaultMultiply;
import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.operations.NumericalOperation;

import java.util.List;

@DefaultMultiply
public class NumericalParentheses extends NumericalOperation implements Parentheses {

    public NumericalParentheses(List<MathematicalObject> components) {
        super(components);
    }

    public String getString() {
        return "(" + super.getString() + ")";
    }
}
