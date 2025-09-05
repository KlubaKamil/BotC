package com.czachodym.BotC.model.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    MEMBER("Member"),
    MODERATOR("Moderator"),
    GROUP_ADMIN("Admin"),
    GLOBAL_ADMIN("Admin globalny");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    @JsonValue   // <-- What will be sent in JSON
    public String getLabel() {
        return label;
    }

    @JsonCreator // <-- How JSON value maps back to enum
    public static Role fromLabel(String label) {
        for (Role type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown UserType: " + label);
    }
}
