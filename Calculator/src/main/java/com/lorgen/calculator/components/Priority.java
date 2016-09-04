package com.lorgen.calculator.components;

import lombok.Getter;

public enum Priority {
    HIGH(2),
    ABOVE_STANDARD(1),
    STANDARD(0);

    @Getter private int level;

    Priority(int level) {
        this.level = level;
    }
}
