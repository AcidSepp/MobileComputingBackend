package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class Classrooms {

    private @NotNull Classroom[] classrooms;

    public Classrooms(final @NotNull Classroom[] classrooms) {
        this.classrooms = classrooms;
    }

    public Classroom[] getClassrooms() {
        return classrooms;
    }
}
