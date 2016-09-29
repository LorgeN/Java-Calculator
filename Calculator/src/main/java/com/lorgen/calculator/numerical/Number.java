package com.lorgen.calculator.numerical;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Number {

    @Getter private double primitiveValue;

    public String getString() {
        return this.getPrimitiveValue() + "";
    }

    @Override
    public String toString() {
        return this.getString();
    }

    public boolean isInteger() {
        return NumberUtils.isInteger(this.getPrimitiveValue());
    }

    public boolean isNatural() {
        return NumberUtils.isNatural(this.getPrimitiveValue());
    }

    public boolean isPrime() {
        if (!this.isInteger()) throw new IllegalArgumentException("Number isn't integer!");
        return NumberUtils.isPrime((long) this.getPrimitiveValue());
    }

    public Long[] factorize() {
        if (!this.isInteger()) throw new IllegalArgumentException("Number isn't integer!");
        return NumberUtils.factorize((long) this.getPrimitiveValue());
    }
}
