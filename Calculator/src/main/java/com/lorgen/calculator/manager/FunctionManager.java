package com.lorgen.calculator.manager;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.exceptions.UnexpectedResultException;
import com.lorgen.calculator.objects.MathematicalFunction;
import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.ui.TextColor;

import java.util.LinkedList;
import java.util.List;

public class FunctionManager {
    public void identifyFunctions(List<MathematicalObject> list) throws UnexpectedResultException {
        List<MathematicalObject> copy = new LinkedList<>(list);
        list.clear();
        for (int i = 0; i < copy.size(); i++) {
            if (i + 1 == copy.size()) list.add(copy.get(i));
            else if (copy.get(i) instanceof MathematicalFunction) {
                MathematicalFunction function = (MathematicalFunction) copy.get(i);
                Calculator.getConsole().info("Found function " + TextColor.LIGHT_PURPLE + function.name() + TextColor .RESET + ".");
                if (!(copy.get(i + 1) instanceof NumericalObject)) throw new UnexpectedResultException("Functions have to be followed by a numerical object!");
                list.add(NumericalObject.fromDouble(function.calculate((NumericalObject) copy.get(i + 1))));
                i++;
            } else list.add(copy.get(i));
        }
    }
}
