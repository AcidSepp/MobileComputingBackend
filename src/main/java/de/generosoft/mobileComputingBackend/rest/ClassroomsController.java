package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.db.Classrooms;
import de.generosoft.mobileComputingBackend.db.Credentials;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ClassroomsController {

    @RequestMapping(value = "/classrooms", method = RequestMethod.GET)
    public Classrooms classrooms(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        final String role = databaseManager.validateCredentials(credentials.getEmail(), credentials.getPassword());
        if (role == null) {
            throw new IllegalArgumentException("invalid credentials");
        }
        return databaseManager.getClassroomsForStudent(credentials.getEmail());
    }

}