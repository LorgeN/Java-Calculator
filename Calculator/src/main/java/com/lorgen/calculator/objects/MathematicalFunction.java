package com.lorgen.calculator.objects;

import com.lorgen.calculator.objects.Delimiters.Delimiter;
import com.lorgen.calculator.exceptions.UnexpectedResultException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@DefaultMultiply
@AllArgsConstructor
public enum MathematicalFunction implements MathematicalObject, Delimiter {
    SINE("sin") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.sin(Math.toRadians(object.getPrimitiveValue()));
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
                return Math.cos(Math.toRadians(object.getPrimitiveValue()));
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
                return Math.tan(Math.toRadians(object.getPrimitiveValue()));
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    INVERSE_SINE("asin") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.asin(object.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    INVERSE_COSINE("acos") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.acos(object.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    INVERSE_TANGENT("atan") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.atan(object.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    COMMON_LOGARITHM("lg") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.log10(object.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    },
    NATURAL_LOGARITHM("ln") {
        @Override
        public double calculate(NumericalObject object) {
            try {
                return Math.log(object.getPrimitiveValue());
            } catch (UnexpectedResultException e) {
                e.printStackTrace();
                return 0;
            }
        }
    };

    @Getter private String string;

    public abstract double calculate(NumericalObject object);

    @Override
    public String getRegex() {
        return this.getString();
    }

    public static boolean isFunction(String str) {
        return Arrays.stream(MathematicalFunction.values()).filter(operator -> Objects.equals(operator.getString(), str)).findFirst().isPresent();
    }

    public static MathematicalFunction fromString(String str) {
        return Arrays.stream(MathematicalFunction.values()).filter(operator -> Objects.equals(operator.getString(), str)).findFirst().orElse(null);
    }
}
