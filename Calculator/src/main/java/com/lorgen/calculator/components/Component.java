package com.lorgen.calculator.components;

public interface Component {
    ComponentType getComponentType();
    String getRawString();

    enum ComponentType {
        OPERATOR,
        VALUE_NUMERICAL,
        BINARY_OPERATION,
        TRIGONOMETRIC_FUNCTION,
        NUMBER,
        VARIABLE,
        UNKNOWN
    }
}
