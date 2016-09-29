package com.lorgen.calculator.components;

import lombok.Getter;

public interface MathematicalObject {
    /**
     * Gets a text representation of the object.
     * To be used in console output
     * @return Text representation
     */
    String getString();

    enum Priority {
        HIGHEST(2),
        HIGHER(1),
        STANDARD(0);

        /**
         * Priority as {@link Integer} which makes for simpler comparisons between different priorities
         */
        @Getter private int level;

        Priority(int level) {
            this.level = level;
        }
    }
}
