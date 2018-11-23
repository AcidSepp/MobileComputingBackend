package de.generosoft.mobileComputingBackend.db;

import java.lang.IllegalArgumentException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;

public class DatabaseManagerTest {

    private DatabaseManager databaseManager;

    @Before
    public void before() throws SQLException, ClassNotFoundException, FileNotFoundException {
        databaseManager = new DatabaseManager();
        databaseManager
                .executeSqlFile("D:\\Studium\\Master\\MobileComputing\\Backend\\src\\test\\resources\\database.sql");
    }

    @After
    public void after() throws SQLException {
        databaseManager.closeDatabase();
    }

    @Test
    public void testDatabaseConnection() throws SQLException, ClassNotFoundException {
        DatabaseManager dm = new DatabaseManager();
        dm.closeDatabase();
    }

    @Test
    public void testGetSubscribedClassroomsForStudent() throws SQLException {
        final Classrooms classroomsForStudent = databaseManager.getSubscribedClassroomsForStudent("student@haw-landshut.de", "password");
        Assert.assertEquals(classroomsForStudent.getClassrooms().length, 3);
    }

    @Test
    public void testValidCredentials() throws SQLException {
        final String student = databaseManager.validateCredentials("student@haw-landshut.de", "password");
        Assert.assertEquals("valid credentials are seen as invalid", student, "student");
        final String lecturer = databaseManager.validateCredentials("lecturer@haw-landshut.de", "password");
        Assert.assertEquals("valid credentials are seen as invalid", lecturer, "lecturer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() throws SQLException {
        final String validation1 = databaseManager.validateCredentials("student@haw-landshu.de", "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPassword() throws SQLException {
        final String validation1 = databaseManager.validateCredentials("student@haw-landshut.de", "passwrd");
    }

    @Test
    public void testGetMessagesForStudent() throws SQLException {
        final Messages messagesForStudent = databaseManager.getMessagesForStudent("student@haw-landshut.de", "password");
        Assert.assertEquals(messagesForStudent.getMessages().length, 4);
    }

    @Test
    public void testSubscribe() throws SQLException {
        databaseManager.subscribe("student@haw-landshut.de", "password", "Generic Empty Classroom");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubscribeWithInvalidRole() throws SQLException {
        databaseManager.subscribe("lecturer@haw-landshut.de", "password", "Generic Empty Classroom");
    }

    @Test(expected = SQLException.class)
    public void testSubscribeNonExistentClassroom() throws SQLException {
        databaseManager.subscribe("student@haw-landshut.de", "password", "Generic Nonexistent Classroom");
    }

    @Test(expected = SQLException.class)
    public void testAlreadySubscribed() throws SQLException {
        databaseManager.subscribe("student@haw-landshut.de", "password", "Bildverstehen");
    }

    @Test
    public void testUnsubscribe() throws SQLException {
        databaseManager.unsubscribe("student@haw-landshut.de", "password", "Bildverstehen");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsubscribeWithInvalidCredentials() throws SQLException {
        databaseManager.unsubscribe("sterdurnt@haw-landshut.de", "password", "Bildverstehen");
    }

    @Test
    public void testUnsubscribeNotSubscribed() throws SQLException {
        databaseManager.unsubscribe("student@haw-landshut.de", "password", "Generic Empty Classroom");
    }

    @Test
    public void testUnsubscribeNonExistentClassroom() throws SQLException {
        databaseManager.unsubscribe("student@haw-landshut.de", "password", "Generic Nonexistent Classroom");
    }

    @Test
    public void testGetAllClassrooms() throws SQLException {
        final Classrooms classrooms = databaseManager.getAllClassroomsForStudent("student@haw-landshut.de", "password");
        Assert.assertTrue("subscribed classrooms received as unsubscribed", classrooms.getClassrooms()[0].getSubscribed());
        Assert.assertTrue("subscribed classrooms received as unsubscribed", classrooms.getClassrooms()[1].getSubscribed());
        Assert.assertTrue("subscribed classrooms received as unsubscribed", classrooms.getClassrooms()[2].getSubscribed());
        Assert.assertTrue("unsubscribed classrooms received as subscribed", !classrooms.getClassrooms()[3].getSubscribed());
    }

}