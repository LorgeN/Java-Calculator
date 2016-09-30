package com.lorgen.calculator.operations.binary;

import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.Operator;
import com.lorgen.calculator.operations.Operation;

public interface BinaryOperation extends Operation {
    static BinaryOperation of(Operator operator, MathematicalObject operand1, MathematicalObject operand2) {
        return new NumericalBinaryOperation(operator, (NumericalObject) operand1, (NumericalObject) operand2);
    }
}
