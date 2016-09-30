package com.lorgen.calculator.operations.binary;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.exceptions.UnexpectedResultException;
import com.lorgen.calculator.numbers.Number;
import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.objects.Operator;
import lombok.Getter;

public class NumericalBinaryOperation implements BinaryOperation {
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
        return operand1.getString() + " " + operator.getString() + " " + operand2.getString();
    }

    @Override
    public Number getValue() {
        return new Number(this.getPrimitiveValue());
    }

    @Override
    public String getEvaluatedString() {
        return this.getString();
    }

    @Override
    public void printComponents() {
        Calculator.getConsole().info("Components in " + getString() + ":",
                " - " + this.getOperand1().getClass().getSimpleName() + " (" + this.getOperand1().getString() + ")",
                " - " + this.getOperator().getClass().getSimpleName() + " (" + this.getOperator().getString() + ")",
                " - " + this.getOperand2().getClass().getSimpleName() + " (" + this.getOperand2().getString() + ")");
    }

    @Override
    public boolean containsVariables() {
        return false;
    }
}
