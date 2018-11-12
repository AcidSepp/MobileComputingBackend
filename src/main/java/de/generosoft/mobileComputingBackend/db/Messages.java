package de.generosoft.mobileComputingBackend.db;

import de.generosoft.mobileComputingBackend.db.Message;
import org.jetbrains.annotations.NotNull;

public class Messages {

    private final @NotNull Message[] messages;

    public Messages(final Message[] messages) {
        this.messages = messages;
    }

    public @NotNull Message[] getMessages() {
        return messages;
    }
}
