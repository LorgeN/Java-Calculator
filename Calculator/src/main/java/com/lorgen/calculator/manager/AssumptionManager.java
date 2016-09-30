package com.lorgen.calculator.manager;

import com.lorgen.calculator.objects.DefaultMultiply;
import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.Operator;

import java.util.LinkedList;
import java.util.List;

public class AssumptionManager {
    public void insertAssumedOperators(List<MathematicalObject> list) {
        List<MathematicalObject> copy = new LinkedList<>(list);
        list.clear();
        for (int i = 0; i < copy.size(); i++) {
            MathematicalObject object = copy.get(i);
            if (object.getClass().isAnnotationPresent(DefaultMultiply.class)) {
                if (list.size() > 0) if (!(list.get(list.size() - 1) instanceof Operator)) list.add(Operator.MULTIPLICATION);
                list.add(object);
                if (i + 1 < copy.size()) if (!(copy.get(i + 1) instanceof Operator)) list.add(Operator.MULTIPLICATION);
            } else list.add(object);
        }
    }
}
