package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.ComponentType;
import com.lorgen.calculator.components.NumericalValue;
import com.lorgen.calculator.components.Operator;
import lombok.Getter;

public class NumericalBinaryOperation implements NumericalValue {

    @Getter private Operator operator;
    @Getter private NumericalValue operand1;
    @Getter private NumericalValue operand2;
    @Getter private ComponentType componentType = ComponentType.BINARY_OPERATION;

    public NumericalBinaryOperation(Operator operator, NumericalValue value1, NumericalValue value2) {
        this.operator = operator;
        this.operand1 = value1;
        this.operand2 = value2;
    }

    @Override
    public double getValue() {
        return this.getOperator().calculate(this.getOperand1(), this.getOperand2()).getValue();
    }

    @Override
    public String getRawString() {
        return operand1.getRawString() + " " + operator.getRawString() + " " + operand2.getRawString();
    }
}
