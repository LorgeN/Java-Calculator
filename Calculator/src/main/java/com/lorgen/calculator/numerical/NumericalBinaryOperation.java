package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.BinaryOperator;
import com.lorgen.calculator.exception.UnexpectedResultException;
import lombok.Getter;

public class NumericalBinaryOperation implements NumericalObject {

    @Getter private BinaryOperator operator;
    @Getter private NumericalObject operand1;
    @Getter private NumericalObject operand2;
    @Getter private ComponentType componentType = ComponentType.BINARY_OPERATION;

    public NumericalBinaryOperation(BinaryOperator operator, NumericalObject value1, NumericalObject value2) {
        this.operator = operator;
        this.operand1 = value1;
        this.operand2 = value2;
    }

    @Override
    public double getValue() {
        try {
            return this.getOperator().calculate(this.getOperand1(), this.getOperand2()).getValue();
        } catch (UnexpectedResultException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getRawString() {
        return "(" + operand1.getRawString() + " " + operator.getRawString() + " " + operand2.getRawString() + ")";
    }
}
