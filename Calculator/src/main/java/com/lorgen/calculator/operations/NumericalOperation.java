package com.lorgen.calculator.operations;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.exceptions.UnexpectedResultException;
import com.lorgen.calculator.numbers.Number;
import com.lorgen.calculator.objects.NumericalObject;
import com.lorgen.calculator.objects.MathematicalObject;
import com.lorgen.calculator.objects.Operator;
import com.lorgen.calculator.operations.binary.BinaryOperation;
import com.lorgen.calculator.ui.TextColor;
import lombok.Getter;
import org.apache.commons.lang3.text.WordUtils;

import java.util.*;

public class NumericalOperation implements Operation {
    @Getter private String string;
    @Getter private List<MathematicalObject> components;

    public NumericalOperation(List<MathematicalObject> components) {
        this.components = components;
        this.string = getEvaluatedString();
        printComponents();
    }

    @Override
    public double getPrimitiveValue() throws UnexpectedResultException {
        this.prioritize();
        return ((NumericalObject) this.getComponents().get(0)).getPrimitiveValue();
    }


    public String getEvaluatedString() {
        StringBuilder builder = new StringBuilder();
        this.getComponents().forEach(component -> builder.append(component.getString()));
        return builder.toString().trim();
    }

    @Override
    public Number getValue() {
        try {
            return new Number(this.getPrimitiveValue());
        } catch (UnexpectedResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean containsVariables() {
        return false;
    }

    @Override
    public void printComponents() {
        for (MathematicalObject component : this.getComponents()) Calculator.getConsole().info("  - " + ((component
                instanceof Enum) ? WordUtils.capitalizeFully(((Enum) component).name(), '_') : component.getClass()
                .getSimpleName()) + ": " + TextColor.LIGHT_PURPLE + component.getString());
    }

    /* Internal */

    private void prioritize() throws UnexpectedResultException {
        Calculator.getFunctionManager().identifyFunctions(this.getComponents());
        Calculator.getAssumptionManager().insertAssumedOperators(this.getComponents());
        processPriority(Priority.HIGHEST);
        processPriority(Priority.HIGHER);
        processPriority(Priority.STANDARD);
        if (this.getComponents().size() > 1) throw new UnexpectedResultException("More components than expected!");
        if (!(this.getComponents().get(0) instanceof NumericalObject)) throw new UnexpectedResultException("Final component isn't a numerical value!");
    }

    private void processPriority(Priority priority) {
        if (this.getComponents().size() < 2) return;
        List<MathematicalObject> copy = new LinkedList<>(this.getComponents());
        this.getComponents().clear();
        this.getComponents().add(copy.get(0));
        for (int i = 1; i < copy.size(); i++) {
            if (i + 1 == copy.size()) this.getComponents().add(copy.get(i));
            else if (copy.get(i) instanceof Operator && copy.get(i - 1) instanceof NumericalObject && copy.get(i + 1) instanceof NumericalObject) {
                Operator operator = (Operator) copy.get(i);
                if (operator.getPriority() == priority) {
                    BinaryOperation operation = BinaryOperation.of(operator, this.getComponents().get(this.getComponents().size() - 1), copy.get(i + 1));
                    this.getComponents().set(this.getComponents().size() - 1, operation);
                    i++;
                } else this.getComponents().add(operator);
            } else this.getComponents().add(copy.get(i));
        }
    }
}
