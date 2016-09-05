package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.exception.UnexpectedResultException;

public interface NumericalObject extends Component {
    double getValue() throws UnexpectedResultException;

    default ComponentType getComponentType() {
        return ComponentType.VALUE_NUMERICAL;
    }

    static Number fromDouble(double value) {
        return new Number(value);
    }
}
