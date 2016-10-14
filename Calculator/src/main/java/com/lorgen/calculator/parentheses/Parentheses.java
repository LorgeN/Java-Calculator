package com.lorgen.calculator.parentheses;

import com.lorgen.calculator.objects.MathematicalObject;

import java.util.List;

public interface Parentheses extends MathematicalObject {
    String getEvaluatedString();
    void printComponents();

    static Parentheses of(List<MathematicalObject> objects) {
        return new NumericalParentheses(objects);
    }
}
