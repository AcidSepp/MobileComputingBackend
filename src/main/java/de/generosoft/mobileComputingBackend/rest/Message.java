package de.generosoft.mobileComputingBackend.rest;

import org.jetbrains.annotations.NotNull;

public class Message {

    private final @NotNull String classRoom;
    private final @NotNull String id;
    private final @NotNull String body;

    public Message(String classRoom, String id, String body) {
        this.classRoom = classRoom;
        this.id = id;
        this.body = body;
    }

    public @NotNull String getClassRoom() {
        return classRoom;
    }

    public @NotNull String getId() {
        return id;
    }

    public @NotNull String getBody() {
        return body;
    }
}
