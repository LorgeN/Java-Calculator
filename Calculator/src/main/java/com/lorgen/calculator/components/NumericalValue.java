package com.lorgen.calculator.components;

public interface NumericalValue extends Component {
    double getValue();

    default ComponentType getComponentType() {
        return ComponentType.VALUE_NUMERICAL;
    }

    static NumericalValue fromDouble(double value) {
        return new NumericalValue() {
            @Override
            public double getValue() {
                return value;
            }

            @Override
            public String getRawString() {
                return value + "";
            }
        };
    }
}
