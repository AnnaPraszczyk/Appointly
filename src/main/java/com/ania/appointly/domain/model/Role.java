package com.ania.appointly.domain.model;
import lombok.Getter;

@Getter
public enum Role {
    CLIENT("Client"),
    PROVIDER("Provider"),
    ADMIN("Admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }
}
