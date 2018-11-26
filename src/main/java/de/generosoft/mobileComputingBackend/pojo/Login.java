package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class Login {

    private final @NotNull String role;

    public Login(final @NotNull String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
