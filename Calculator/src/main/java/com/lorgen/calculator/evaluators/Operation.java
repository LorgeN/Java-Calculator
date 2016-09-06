package com.lorgen.calculator.evaluators;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.components.Component;
import com.lorgen.calculator.components.Operator;
import com.lorgen.calculator.components.Operator.Priority;
import com.lorgen.calculator.components.TrigonometricFunction;
import com.lorgen.calculator.exception.EvaluationException;
import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.numerical.NumericalBinaryOperation;
import com.lorgen.calculator.numerical.NumericalObject;
import com.lorgen.calculator.numerical.NumericalParentheses;
import com.lorgen.calculator.ui.TextColor;
import lombok.Getter;

import java.util.*;

public class Operation implements NumericalObject {
    @Getter private String rawString;
    @Getter private List<Component> components;

    public Operation(String raw) throws EvaluationException {
        this.rawString = raw;
        this.components = Calculator.getEvaluator().evaluate(raw);
    }
    
    public Operation(List<Component> components) {
        this.components = components;
        this.rawString = getEvaluatedString();
    }

    @Override
    public double getValue() throws UnexpectedResultException {
        this.prioritize();
        return ((NumericalObject) this.getComponents().get(0)).getValue();
    }

    public void printComponents() {
        for (Component component : this.getComponents()) Calculator.getConsole().info("  - " + component.getComponentType() + ": " + TextColor.LIGHT_PURPLE + component.getRawString());
    }

    public String getEvaluatedString() {
        StringBuilder builder = new StringBuilder();
        this.getComponents().forEach(component -> builder.append(component.getRawString()));
        return builder.toString().trim();
    }
    
    private void prioritize() throws UnexpectedResultException {
        Set<Priority> contains = new HashSet<>();
        this.getComponents().parallelStream().filter(component -> component instanceof Operator).forEach(component -> contains.add(((Operator) component).getPriority()));

        List<Component> refreshedComponents = new LinkedList<>();
        refreshedComponents.add(this.components.get(0));
        for (int i = 1; i < this.components.size() - 1; i++) {
            if (this.components.get(i) instanceof TrigonometricFunction) {
                TrigonometricFunction function = (TrigonometricFunction) this.components.get(i);
                Calculator.getConsole().info("Found trigonometric function " + TextColor.LIGHT_PURPLE + function.name() + TextColor .RESET + ".");
                if (!(this.components.get(i + 1) instanceof NumericalObject)) throw new UnexpectedResultException("Trigonometric functions have to be followed by a numerical object!");
                refreshedComponents.add(NumericalObject.fromDouble(function.calculate((NumericalObject) this.components.get(i + 1))));
                i++;
            } else refreshedComponents.add(this.components.get(i));
        }
        refreshedComponents.add(this.components.get(this.components.size() - 1));
        this.components = refreshedComponents;

        if (contains.contains(Priority.HIGH)) {
            this.components = assembleAfterPriority(this.getComponents(), Priority.HIGH);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "HIGH " + TextColor.PURPLE + "(\"(--)\", \"x^y\")" + TextColor.RESET + " in operation " + TextColor.LIGHT_PURPLE + this.getRawString() + TextColor.RESET + ":");
            this.printComponents();
        }

        if (contains.contains(Priority.ABOVE_STANDARD)) {
            this.components = assembleAfterPriority(this.getComponents(), Priority.ABOVE_STANDARD);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "ABOVE_STANDARD " + TextColor.PURPLE + "(\"x*y\", \"x/y\")" + TextColor.RESET + " in operation " + TextColor.LIGHT_PURPLE + this.getRawString() + TextColor.RESET + ":");
            this.printComponents();
        }

        if (contains.contains(Priority.STANDARD)) {
            this.components = assembleAfterPriority(this.getComponents(), Priority.STANDARD);
            Calculator.getConsole().info("Sorted components for priority level " + TextColor.LIGHT_PURPLE + "STANDARD " + TextColor.PURPLE + "(\"x-y\", \"x+y\")" + TextColor.RESET + " in operation " + TextColor.LIGHT_PURPLE + this.getRawString() + TextColor.RESET + ":");
            this.printComponents();
        }

        if (this.getComponents().size() > 1) throw new UnexpectedResultException("More components than expected!");
        if (!(this.getComponents().get(0) instanceof NumericalObject)) throw new UnexpectedResultException("Final component isn't a numerical value!");
    }

    private List<Component> assembleAfterPriority(List<Component> list, Priority priority) {
        if (list.size() == 1) return list;
        List<Component> refreshedComponents = new LinkedList<>();
        refreshedComponents.add(list.get(0));
        for (int i = 1; i < list.size() - 1; i++) {
            if (list.get(i) instanceof Operator && list.get(i - 1) instanceof NumericalObject && list.get(i + 1) instanceof NumericalObject) {
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
        refreshedComponents.add(list.get(list.size() - 1));
        return refreshedComponents;
    }
}
