package com.lorgen.calculator.components;

import lombok.Getter;

import java.util.Arrays;

public enum Operator implements Component {
    ADDITION(Priority.STANDARD, '+') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            return NumericalValue.fromDouble(operand1.getValue() + operand2.getValue());
        }
    },
    SUBTRACTION(Priority.STANDARD, '-') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            return NumericalValue.fromDouble(operand1.getValue() - operand2.getValue());
        }
    },
    MULTIPLICATION(Priority.ABOVE_STANDARD, '*') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            return NumericalValue.fromDouble(operand1.getValue() * operand2.getValue());
        }
    },
    DIVISION(Priority.ABOVE_STANDARD, '/') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            return NumericalValue.fromDouble(operand1.getValue() / operand2.getValue());
        }
    },
    EXPONENT(Priority.HIGH, '^') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            return NumericalValue.fromDouble(Math.pow(operand1.getValue(), operand2.getValue()));
        }
    };

    @Getter private char[] characters;
    @Getter private Priority priority;
    @Getter private ComponentType componentType = ComponentType.OPERATOR;
    @Getter private String rawString;

    Operator(Priority priority, char... chars) {
        this.characters = chars;
        this.priority = priority;
        this.rawString = chars[0] + "";
    }
    
    public abstract NumericalValue calculate(NumericalValue operand1, NumericalValue operand2);

    public static boolean isOperator(char ch) {
        return Operator.fromCharacter(ch) != null;
    }

    public static Operator fromCharacter(char ch) {
        return Arrays.stream(Operator.values()).filter(operator -> Arrays.asList(operator).contains(ch)).findFirst().get();
    }
}
