package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.exception.UnexpectedResultException;

public interface NumericalValue extends Component {
    double getValue() throws UnexpectedResultException;

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
