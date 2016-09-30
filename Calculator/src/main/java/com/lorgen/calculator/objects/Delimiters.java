package com.lorgen.calculator.objects;

public class Delimiters {
    public static final Delimiter ADDITION = Operator.ADDITION;
    public static final Delimiter SUBTRACTION = Operator.SUBTRACTION;
    public static final Delimiter MULTIPLICATION = Operator.MULTIPLICATION;
    public static final Delimiter DIVISION = Operator.DIVISION;
    public static final Delimiter EXPONENT = Operator.EXPONENT;
    public static final Delimiter MODULO = Operator.MODULO;
    public static final Delimiter SINE = MathematicalFunction.SINE;
    public static final Delimiter COSINE = MathematicalFunction.COSINE;
    public static final Delimiter TANGENT = MathematicalFunction.TANGENT;
    public static final Delimiter INVERSE_SINE = MathematicalFunction.INVERSE_SINE;
    public static final Delimiter INVERSE_COSINE = MathematicalFunction.INVERSE_COSINE;
    public static final Delimiter INVERSE_TANGENT = MathematicalFunction.INVERSE_TANGENT;
    public static final Delimiter COMMON_LOGARITHM = MathematicalFunction.COMMON_LOGARITHM;
    public static final Delimiter NATURAL_LOGARITHM = MathematicalFunction.NATURAL_LOGARITHM;
    public static final Delimiter OPENING_PARENTHESES = Delimiter.of("\\(");
    public static final Delimiter CLOSING_PARENTHESES = Delimiter.of("\\)");

    public static Delimiter[] values() {
        return new Delimiter[] {ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, EXPONENT,
                MODULO, SINE, COSINE, TANGENT, INVERSE_SINE, INVERSE_COSINE, INVERSE_TANGENT,
                COMMON_LOGARITHM, NATURAL_LOGARITHM, OPENING_PARENTHESES, CLOSING_PARENTHESES};
    }

    /**
     * Simple interface for delimiters
     */
    public interface Delimiter {
        String getRegex();

        static Delimiter of(String str) {
            return () -> str;
        }
    }
}
