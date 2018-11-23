package de.generosoft.mobileComputingBackend.db;

import org.jetbrains.annotations.NotNull;

public class Classroom {

    private final @NotNull String classroomName;
    private final @NotNull String lecturer;
    private final boolean subscribed;

    public Classroom(@NotNull String classroomName, @NotNull String lecturer, final boolean subscribed) {
        this.classroomName = classroomName;
        this.lecturer = lecturer;
        this.subscribed = subscribed;
    }

    @NotNull
    public String getClassroomName() {
        return classroomName;
    }

    @NotNull
    public String getLecturer() {
        return lecturer;
    }

    public boolean getSubscribed() {
        return subscribed;
    }
}
