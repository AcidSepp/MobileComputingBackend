package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class DeleteClassroomBody extends Credentials {

    private @NotNull String classroomName;

    public DeleteClassroomBody() {
    }

    public DeleteClassroomBody(final @NotNull String email, @NotNull final String password, final @NotNull String classroomName) {
        super(email, password);
        this.classroomName = classroomName;
    }

    public @NotNull String getClassroomName() {
        return classroomName;
    }

}
