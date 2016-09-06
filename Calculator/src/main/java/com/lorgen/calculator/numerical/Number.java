package com.lorgen.calculator.numerical;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Number implements NumericalObject {

    @Getter private double value;

    @Override
    public String getRawString() {
        return this.getValue() + "";
    }

    public boolean isInteger() {
        return NumberUtils.isInteger(this.getValue());
    }

    public boolean isNatural() {
        return NumberUtils.isNatural(this.getValue());
    }

    public boolean isPrime() {
        if (!this.isInteger()) throw new IllegalArgumentException("Number isn't integer!");
        return NumberUtils.isPrime((long) this.getValue());
    }

    public Long[] factorize() {
        if (!this.isInteger()) throw new IllegalArgumentException("Number isn't integer!");
        return NumberUtils.factorize((long) this.getValue());
    }

    public ComponentType getComponentType() {
        return ComponentType.NUMBER;
    }
}
