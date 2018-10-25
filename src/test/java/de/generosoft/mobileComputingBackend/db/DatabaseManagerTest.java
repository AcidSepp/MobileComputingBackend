package de.generosoft.mobileComputingBackend.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

    @Test
    public void testDatabaseConnection() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();
        dm.closeDatabase();
    }

    @Test
    public void testGetAllClassroomsForStudent() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();
        final List<Classroom> allClassroomsForStudent = dm.getAllClassroomsForStudent("student@haw-landshut.de");
        Assert.assertEquals(allClassroomsForStudent.size(), 3);
        dm.closeDatabase();
    }

    @Test
    public void testValidateCredentials() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();

        final String validation1 = dm.validateCredentials("student@haw-landshut.de", "password");
        Assert.assertEquals("valid credentials are seen as invalid", validation1, "student");
        final String validation2 = dm.validateCredentials("student@haw-landshut.de", "pasword");
        Assert.assertEquals("invalid password was seen as valid", validation2, "invalid credentials");
        final String validation3 = dm.validateCredentials("student@haw-ladshut.de", "password");
        Assert.assertEquals("invalid password was seen as valid", validation3, "invalid credentials");

        dm.closeDatabase();
    }

}