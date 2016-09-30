package com.lorgen.calculator.objects;

import com.lorgen.calculator.numbers.Number;
import com.lorgen.calculator.exceptions.UnexpectedResultException;

public interface NumericalObject extends MathematicalObject {
    double getPrimitiveValue() throws UnexpectedResultException;
    Number getValue();

    static NumericalObject fromDouble(double value) {
        return new Number(value);
    }
}
