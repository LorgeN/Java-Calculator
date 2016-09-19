package com.lorgen.calculator.components;

import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.numerical.NumericalObject;
import lombok.Getter;

import java.util.Arrays;

public enum BinaryOperator implements Component {
    ADDITION(Priority.STANDARD, '+') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getValue() + operand2.getValue());
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
                return NumericalObject.fromDouble(operand1.getValue() - operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    MULTIPLICATION(Priority.ABOVE_STANDARD, '*') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getValue() * operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    DIVISION(Priority.ABOVE_STANDARD, '/') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(operand1.getValue() / operand2.getValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return null;
            }
        }
    },
    EXPONENT(Priority.HIGH, '^') {
        @Override
        public NumericalObject calculate(NumericalObject operand1, NumericalObject operand2) {
            try {
                return NumericalObject.fromDouble(Math.pow(operand1.getValue(), operand2.getValue()));
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

    BinaryOperator(Priority priority, char ch) {
        this.character = ch;
        this.priority = priority;
        this.rawString = ch + "";
    }
    
    public abstract NumericalObject calculate(NumericalObject operand1, NumericalObject operand2);

    public static boolean isOperator(char ch) {
        return BinaryOperator.fromCharacter(ch) != null;
    }

    public static BinaryOperator fromCharacter(char ch) {
        return Arrays.stream(BinaryOperator.values()).filter(operator -> operator.getCharacter() == ch).findFirst().orElse(null);
    }

    public enum Priority {
        HIGH(2),
        ABOVE_STANDARD(1),
        STANDARD(0);

        @Getter private int level;

        Priority(int level) {
            this.level = level;
        }
    }
}
