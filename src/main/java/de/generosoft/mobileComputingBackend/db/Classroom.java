package de.generosoft.mobileComputingBackend.db;

import org.jetbrains.annotations.NotNull;

public class Classroom {

    private final @NotNull String classroomName;
    private final @NotNull String lecturer;

    public Classroom(@NotNull String classroomName, @NotNull String lecturer) {
        this.classroomName = classroomName;
        this.lecturer = lecturer;
    }

    @NotNull
    public String getClassroomName() {
        return classroomName;
    }

    @NotNull
    public String getLecturer() {
        return lecturer;
    }
}
