package de.generosoft.mobileComputingBackend.db;

import de.generosoft.mobileComputingBackend.db.Message;

public class Messages {

    private Message[] messages;

    public Messages(final Message[] messages) {
        this.messages = messages;
    }

    public Message[] getMessages() {
        return messages;
    }
}
