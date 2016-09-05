package com.lorgen.calculator.components;

import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.numerical.NumericalValue;
import lombok.Getter;

import java.util.Arrays;

public enum Operator implements Component {
    ADDITION(Priority.STANDARD, '+') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            try {
                return NumericalValue.fromDouble(operand1.getValue() + operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    SUBTRACTION(Priority.STANDARD, '-') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            try {
                return NumericalValue.fromDouble(operand1.getValue() - operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    MULTIPLICATION(Priority.ABOVE_STANDARD, '*') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            try {
                return NumericalValue.fromDouble(operand1.getValue() * operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    DIVISION(Priority.ABOVE_STANDARD, '/') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            try {
                return NumericalValue.fromDouble(operand1.getValue() / operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    EXPONENT(Priority.HIGH, '^') {
        @Override
        public NumericalValue calculate(NumericalValue operand1, NumericalValue operand2) {
            try {
                return NumericalValue.fromDouble(Math.pow(operand1.getValue(), operand2.getValue()));
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    };

    @Getter private char character;
    @Getter private Priority priority;
    @Getter private ComponentType componentType = ComponentType.OPERATOR;
    @Getter private String rawString;

    Operator(Priority priority, char ch) {
        this.character = ch;
        this.priority = priority;
        this.rawString = ch + "";
    }
    
    public abstract NumericalValue calculate(NumericalValue operand1, NumericalValue operand2);

    public static boolean isOperator(char ch) {
        return Operator.fromCharacter(ch) != null;
    }

    public static Operator fromCharacter(char ch) {
        return Arrays.stream(Operator.values()).filter(operator -> operator.getCharacter() == ch).findFirst().orElse(null);
    }
}
