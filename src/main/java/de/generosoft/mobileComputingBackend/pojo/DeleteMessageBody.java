package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class DeleteMessageBody extends Credentials {

    private int messageId;

    public DeleteMessageBody(final @NotNull String email, final @NotNull String password, final int messageId) {
        super(email, password);
        this.messageId = messageId;
    }

    public DeleteMessageBody() {
    }

    public int getMessageId() {
        return messageId;
    }
}
