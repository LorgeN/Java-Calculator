package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.MathematicalObject;
import com.lorgen.calculator.exception.UnexpectedResultException;

public interface NumericalObject extends MathematicalObject {
    double getPrimitiveValue() throws UnexpectedResultException;
    Number getValue();

    static NumericalObject fromDouble(double value) {
        return new NumericalObject() {
            @Override
            public double getPrimitiveValue() throws UnexpectedResultException {
                return value;
            }

            @Override
            public Number getValue() {
                return new Number(value);
            }

            @Override
            public String getString() {
                return value + "";
            }

            @Override
            public Priority getPriority() {
                return null;
            }
        };
    }
}
