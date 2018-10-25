package de.generosoft.mobileComputingBackend.rest;

public class Messages {

    private Message[] messages;

    public Messages(final Message[] messages) {
        this.messages = messages;
    }

    public Message[] getMessages() {
        return messages;
    }
}
