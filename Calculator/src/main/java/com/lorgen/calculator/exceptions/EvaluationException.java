package com.lorgen.calculator.exceptions;

public class EvaluationException extends Exception {

    public EvaluationException(Exception e) {
        super(e);
    }

    public EvaluationException(String str) {
        super(str);
    }
}
