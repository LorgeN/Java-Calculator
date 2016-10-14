package com.lorgen.calculator.operations.binary;

import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.objects.Operator;
import com.lorgen.calculator.operations.Operation;

/**
 * Same as {@link Operation}, but defining the differences between binary operations
 * and normal operations is important for the sorting algorithms
 */
public interface BinaryOperation extends NumericalObject {
    String getEvaluatedString();
    void printComponents();
    boolean containsVariables();

    static BinaryOperation of(Operator operator, MathematicalObject value1, MathematicalObject value2) {
        if (value1 instanceof NumericalObject && value2 instanceof NumericalObject) return new NumericalBinaryOperation(operator, (NumericalObject) value1, (NumericalObject) value2);
        else return null;
    }
}
