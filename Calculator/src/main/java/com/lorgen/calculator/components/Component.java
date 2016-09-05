package com.lorgen.calculator.components;

public interface Component {
    ComponentType getComponentType();
    String getRawString();

    enum ComponentType {
        OPERATOR,
        VALUE_NUMERICAL,
        UNKNOWN_VARIABLE,
        BINARY_OPERATION
    }
}
