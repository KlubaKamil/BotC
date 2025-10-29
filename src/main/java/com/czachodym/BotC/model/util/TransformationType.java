package com.czachodym.BotC.model.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransformationType {
    BECOME("Stał się"),
    GOT_ABILITY("Zdobył zdolność"),
    THOUGHT_THAT_WAS("Myślał, że był");

    private final String label;

    private TransformationType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return this.label;
    }

    @JsonCreator
    public static TransformationType fromLabel(String label) {
        for(TransformationType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown TransformationType: " + label);
    }
}