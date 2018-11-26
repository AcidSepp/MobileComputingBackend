package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class PostMessageBody extends Credentials {

    private @NotNull String payload;
    private @NotNull String classroomName;

    public PostMessageBody() {
    }

    public PostMessageBody(final @NotNull  String email, final @NotNull  String password, final @NotNull String classroomName, final @NotNull  String payload) {
        super(email, password);
        this.payload = payload;
        this.classroomName = classroomName;
    }

    public @NotNull String getPayload() {
        return payload;
    }

    public @NotNull String getClassroomName() {
        return classroomName;
    }

}
