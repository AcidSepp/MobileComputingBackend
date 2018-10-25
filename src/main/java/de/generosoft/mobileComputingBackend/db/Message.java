package de.generosoft.mobileComputingBackend.db;

import org.jetbrains.annotations.NotNull;

public class Message {

    private final @NotNull String classRoom;
    private final @NotNull int id;
    private final @NotNull String payload;

    public Message(final @NotNull int id, final @NotNull String classRoom, final @NotNull String payload) {
        this.classRoom = classRoom;
        this.id = id;
        this.payload = payload;
    }

    public @NotNull String getClassRoom() {
        return classRoom;
    }

    public @NotNull int getId() {
        return id;
    }

    public @NotNull String getPayload() {
        return payload;
    }
}
