package com.lorgen.calculator.components;

public interface Component {
    ComponentType getComponentType();
    String getRawString();

    enum ComponentType {
        OPERATOR,
        VALUE_NUMERICAL,
        BINARY_OPERATION,
        NUMBER,
        VARIABLE,
        UNKNOWN
    }
}
