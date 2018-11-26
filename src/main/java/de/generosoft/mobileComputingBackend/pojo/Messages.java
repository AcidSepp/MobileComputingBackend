package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class Messages {

    private final @NotNull Message[] messages;

    public Messages(final @NotNull Message[] messages) {
        this.messages = messages;
    }

    public @NotNull Message[] getMessages() {
        return messages;
    }
}
