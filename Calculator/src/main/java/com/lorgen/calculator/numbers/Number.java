package com.lorgen.calculator.numbers;

import com.lorgen.calculator.objects.NumericalObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Number implements NumericalObject {

    @Getter private double primitiveValue;

    @Deprecated
    public Number getValue() {
        return this;
    }

    public String getString() {
        return this.getPrimitiveValue() + "";
    }

    public long longValue() {
        return Math.round(this.getPrimitiveValue());
    }

    public int intValue() {
        return (int) longValue();
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
        return NumberUtils.factorize(this.longValue());
    }
}
