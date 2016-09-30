package com.lorgen.calculator.components;

import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.numerical.NumericalObject;
import lombok.Getter;

import java.util.Arrays;

public enum Operator implements MathematicalObject, Delimiter {
    ADDITION(Priority.STANDARD, '+') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getPrimitiveValue() + operand2.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    SUBTRACTION(Priority.STANDARD, '-') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getPrimitiveValue() - operand2.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    MULTIPLICATION(Priority.HIGHER, '*') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getPrimitiveValue() * operand2.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    DIVISION(Priority.HIGHER, '/') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getPrimitiveValue() / operand2.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    EXPONENT(Priority.HIGHEST, '^') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(Math.pow(operand1.getPrimitiveValue(), operand2.getPrimitiveValue()));
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    MODULO(Priority.HIGHEST, '%') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getPrimitiveValue() % operand2.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    };

    @Getter private char character;
    @Getter private Priority priority;
    @Getter private String string;

    Operator(Priority priority, char ch) {
        this.character = ch;
        this.priority = priority;
        this.string = ch + "";
    }
    
    public abstract NumericalObject calculate(NumericalObject operand1, NumericalObject operand2);

    public String getRegex() {
        return this.getCharacter() + "";
    }

    public static boolean isOperator(char ch) {
        return Arrays.stream(Operator.values()).filter(operator -> operator.getCharacter() == ch).findFirst().isPresent();
    }

    public static Operator fromCharacter(char ch) {
        return Arrays.stream(Operator.values()).filter(operator -> operator.getCharacter() == ch).findFirst().orElse(null);
    }
}
