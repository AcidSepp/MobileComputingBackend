package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class CreateClassroomBody extends Credentials {

    private @NotNull String classroomName;

    public CreateClassroomBody() {
    }

    public CreateClassroomBody(final @NotNull String email, @NotNull final String password, final @NotNull String classroomName) {
        super(email, password);
        this.classroomName = classroomName;
    }

    public @NotNull String getClassroomName() {
        return classroomName;
    }
}
