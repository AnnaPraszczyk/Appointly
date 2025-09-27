package com.ania.appointly.domain.model;
import lombok.Getter;

@Getter
public enum Role {
    CLIENT("Client"),
    PROVIDER("Provider"),
    EMPLOYEE("Employee"),
    ADMIN("Admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }
}
