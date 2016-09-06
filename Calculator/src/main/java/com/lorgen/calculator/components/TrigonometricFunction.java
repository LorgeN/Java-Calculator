package com.lorgen.calculator.components;

import com.lorgen.calculator.exception.UnexpectedResultException;
import com.lorgen.calculator.numerical.NumericalObject;
import lombok.Getter;

import java.util.Arrays;

public enum TrigonometricFunction implements Component {
    SINE("sin") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.sin(Math.toRadians(object.getValue()));
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    COSINE("cos") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.cos(Math.toRadians(object.getValue()));
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    TANGENT("tan") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.tan(Math.toRadians(object.getValue()));
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    };

    @Getter private String string;

    TrigonometricFunction(String str) {
        this.string = str;
    }

    public abstract double calculate(NumericalObject object);

    public static boolean isFunction(String str) {
        return TrigonometricFunction.fromString(str) != null;
    }

    public static TrigonometricFunction fromString(String str) {
        return Arrays.stream(TrigonometricFunction.values()).filter(operator -> operator.getString() == str).findFirst().orElse(null);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TRIGONOMETRIC_FUNCTION;
    }

    @Override
    public String getRawString() {
        return this.getString();
    }
}
