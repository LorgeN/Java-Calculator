package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.NumericalValue;
import com.lorgen.calculator.components.Operator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NumericalBinaryOperation implements NumericalValue {

    @Getter private Operator operator;
    @Getter private NumericalValue operand1;
    @Getter private NumericalValue operand2;

    @Override
    public double getValue() {
        return this.getOperator().calculate(this.getOperand1(), this.getOperand2()).getValue();
    }
}
