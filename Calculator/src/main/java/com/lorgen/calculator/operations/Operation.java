package com.lorgen.calculator.operations;

import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.NumericalObject;

import java.util.List;

public interface Operation extends NumericalObject {
    String getEvaluatedString();
    void printComponents();
    boolean containsVariables();

    static Operation of(List<MathematicalObject> objects) {
        return new NumericalOperation(objects);
    }
}
