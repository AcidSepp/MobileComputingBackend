package de.generosoft.mobileComputingBackend.db;

import org.jetbrains.annotations.NotNull;

public class Classrooms {

    private Classroom[] classrooms;

    public Classrooms(final @NotNull Classroom[] classrooms) {
        this.classrooms = classrooms;
    }

    public Classroom[] getClassrooms() {
        return classrooms;
    }
}
