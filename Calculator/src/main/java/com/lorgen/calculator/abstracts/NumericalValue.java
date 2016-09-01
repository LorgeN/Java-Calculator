package com.lorgen.calculator.abstracts;

public interface NumericalValue {
    double getValue();

    static NumericalValue fromDouble(double value) {
        return () -> value;
    }
}
