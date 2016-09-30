package com.lorgen.calculator.operations;

import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.objects.MathematicalObject;

import java.util.List;

public interface Operation extends NumericalObject {
    String getEvaluatedString();
    void printComponents();
    boolean containsVariables();

    static Operation of(List<MathematicalObject> components) {
        return new NumericalOperation(components);
    }
}
