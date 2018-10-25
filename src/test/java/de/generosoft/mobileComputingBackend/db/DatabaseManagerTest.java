package de.generosoft.mobileComputingBackend.db;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManagerTest {

    @Test
    public void testDatabaseConnection() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();
        dm.closeDatabase();
    }

    @Test
    public void testGetAllClassroomsForStudent() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();
        final Classrooms classroomsForStudent = dm.getClassroomsForStudent("student@haw-landshut.de");
        Assert.assertEquals(classroomsForStudent.getClassrooms().length, 3);
        dm.closeDatabase();
    }

    @Test
    public void testValidCredentials() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();

        final String validation1 = dm.validateCredentials("student@haw-landshut.de", "password");
        Assert.assertEquals("valid credentials are seen as invalid", validation1, "student");
        dm.closeDatabase();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();

        final String validation1 = dm.validateCredentials("student@haw-landshu.de", "password");
        dm.closeDatabase();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPassword() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();

        final String validation1 = dm.validateCredentials("student@haw-landshut.de", "passwrd");
        dm.closeDatabase();
    }

    @Test
    public void testGetMessagesForStudent() throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = new DatabaseManager();
        final Messages messagesForStudent = databaseManager.getMessagesForStudent("student@haw-landshut.de");
        Assert.assertEquals(messagesForStudent.getMessages().length, 4);
        databaseManager.closeDatabase();
    }

}