package de.generosoft.mobileComputingBackend.db;

import de.generosoft.mobileComputingBackend.db.Classroom;
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
