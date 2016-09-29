package com.lorgen.calculator.evaluators;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.components.MathematicalFunction;
import com.lorgen.calculator.components.MathematicalObject;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.numerical.Number;
import com.lorgen.calculator.numerical.NumericalBinaryOperation;
import com.lorgen.calculator.numerical.NumericalObject;
import com.lorgen.calculator.ui.TextColor;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Operation implements NumericalObject {
    @Getter private String string;
    @Getter private List<MathematicalObject> components;
    
    protected Operation(List<MathematicalObject> components) {
        this.components = components;
        this.string = getEvaluatedString();
    }

    @Override
    public double getPrimitiveValue() throws UnexpectedResultException {
        this.prioritize();
        return ((NumericalObject) this.getComponents().get(0)).getPrimitiveValue();
    }

    public void printComponents() {
        for (MathematicalObject component : this.getComponents()) Calculator.getConsole().info("  - " + component.getClass().getSimpleName() + ": " + TextColor.LIGHT_PURPLE + component.getString());
    }

    public String getEvaluatedString() {
        StringBuilder builder = new StringBuilder();
        this.getComponents().forEach(component -> builder.append(component.getString()));
        return builder.toString().trim();
    }
    
    private void prioritize() throws UnexpectedResultException {
        Set<Priority> contains = new HashSet<>();
        this.getComponents().parallelStream().filter(component -> component instanceof Operator).forEach(component -> contains.add(((Operator) component).getPriority()));

        List<MathematicalObject> refreshedComponents = new LinkedList<>();
        refreshedComponents.add(this.components.get(0));
        for (int i = 1; i < this.components.size() - 1; i++) {
            if (this.components.get(i) instanceof MathematicalFunction) {
                MathematicalFunction function = (MathematicalFunction) this.components.get(i);
                Calculator.getConsole().info("Found trigonometric function " + TextColor.LIGHT_PURPLE + function.name() + TextColor .RESET + ".");
                if (!(this.components.get(i + 1) instanceof NumericalObject)) throw new UnexpectedResultException("Trigonometric functions have to be followed by a numerical object!");
                refreshedComponents.add(NumericalObject.fromDouble(function.calculate((NumericalObject) this.components.get(i + 1))));
                i++;
            } else refreshedComponents.add(this.components.get(i));
        }
        refreshedComponents.add(this.components.get(this.components.size() - 1));
        this.components = refreshedComponents;

        if (contains.contains(Priority.HIGHEST)) {
            this.components = assembleAfterPriority(this.getComponents(), Priority.HIGHEST);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "HIGHEST " + TextColor.PURPLE + "(\"(--)\", \"x^y\")" + TextColor.RESET + " in operation " + TextColor.LIGHT_PURPLE + this.getString() + TextColor.RESET + ":");
            this.printComponents();
        }

        if (contains.contains(Priority.HIGHER)) {
            this.components = assembleAfterPriority(this.getComponents(), Priority.HIGHER);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "HIGHER " + TextColor.PURPLE + "(\"x*y\", \"x/y\")" + TextColor.RESET + " in operation " + TextColor.LIGHT_PURPLE + this.getString() + TextColor.RESET + ":");
            this.printComponents();
        }

        if (contains.contains(Priority.STANDARD)) {
            this.components = assembleAfterPriority(this.getComponents(), Priority.STANDARD);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "STANDARD " + TextColor.PURPLE + "(\"x-y\", \"x+y\")" + TextColor.RESET + " in operation " + TextColor.LIGHT_PURPLE + this.getString() + TextColor.RESET + ":");
            this.printComponents();
        }

        if (this.getComponents().size() > 1) throw new UnexpectedResultException("More components than expected!");
        if (!(this.getComponents().get(0) instanceof NumericalObject)) throw new UnexpectedResultException("Final component isn't a numerical value!");
    }

    private List<MathematicalObject> assembleAfterPriority(List<MathematicalObject> list, Priority priority) {
        if (list.size() == 1) return list;
        List<MathematicalObject> refreshedComponents = new LinkedList<>();
        refreshedComponents.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (i + 1 == list.size()) refreshedComponents.add(list.get(i));
            else if (list.get(i) instanceof MathematicalFunction) {

            } else if (list.get(i) instanceof Operator && list.get(i - 1) instanceof NumericalObject && list.get(i + 1) instanceof NumericalObject) {
                Operator operator = (Operator) list.get(i);
                if (operator.getPriority() == priority) {
                    NumericalBinaryOperation operation = new NumericalBinaryOperation(operator,
                            (NumericalObject) refreshedComponents.get(refreshedComponents.size() - 1),
                            (NumericalObject) list.get(i + 1));
                    refreshedComponents.set(refreshedComponents.size() - 1, operation);
                    i++;
                } else refreshedComponents.add(operator);
            } else refreshedComponents.add(list.get(i));
        }

        return refreshedComponents;
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
}
