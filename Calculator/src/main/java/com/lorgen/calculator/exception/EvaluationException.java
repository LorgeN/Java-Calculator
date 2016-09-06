package com.lorgen.calculator.exception;

public class EvaluationException extends Exception {

    public EvaluationException(Exception e) {
        super(e);
    }

    public EvaluationException(String str) {
        super(str);
    }
}
