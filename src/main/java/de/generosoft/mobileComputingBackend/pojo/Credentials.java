package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class Credentials {

    private @NotNull String email;
    private @NotNull String password;

    public Credentials() {
    }

    public Credentials(final @NotNull String email, final @NotNull  String password) {
        this.email = email;
        this.password = password;
    }

    public @NotNull  String getEmail() {
        return email;
    }

    public @NotNull String getPassword() {
        return password;
    }
}
