package com.lorgen.calculator.numerical;

import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.exception.UnexpectedResultException;
import lombok.Getter;

public class NumericalBinaryOperation implements NumericalObject {

    @Getter private Operator operator;
    @Getter private NumericalObject operand1;
    @Getter private NumericalObject operand2;

    public NumericalBinaryOperation(Operator operator, NumericalObject value1, NumericalObject value2) {
        this.operator = operator;
        this.operand1 = value1;
        this.operand2 = value2;
    }

    @Override
    public double getPrimitiveValue() {
        try {
            return this.getOperator().calculate(this.getOperand1(), this.getOperand2()).getPrimitiveValue();
        } catch (UnexpectedResultException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getString() {
        return "(" + operand1.getString() + " " + operator.getString() + " " + operand2.getString() + ")";
    }
}
