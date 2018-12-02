package de.generosoft.mobileComputingBackend.db;

import java.lang.IllegalArgumentException;

import de.generosoft.mobileComputingBackend.pojo.*;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DatabaseManagerTest {

    private DatabaseManager databaseManager;
    private final @NotNull Credentials validStudent = new Credentials("student@haw-landshut.de", "password");
    private final @NotNull Credentials validLecturer = new Credentials("lecturer@haw-landshut.de", "password");

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
        final Classrooms classroomsForStudent = databaseManager.getSubscribedClassroomsForStudent(validStudent);
        Assert.assertEquals(classroomsForStudent.getClassrooms().length, 3);
    }

    @Test
    public void testValidCredentials() throws SQLException {
        final String student = databaseManager.validateCredentials(validStudent);
        Assert.assertEquals("valid credentials are seen as invalid", student, "student");
        final String lecturer = databaseManager.validateCredentials(validLecturer);
        Assert.assertEquals("valid credentials are seen as invalid", lecturer, "lecturer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() throws SQLException {
        final Credentials credentials = new Credentials("student@haw-landshu.de", "password");
        databaseManager.validateCredentials(credentials);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPassword() throws SQLException {
        final Credentials credentials = new Credentials("student@haw-landshut.de", "passwrd");
        databaseManager.validateCredentials(credentials);
    }

    @Test
    public void testGetMessagesForStudent() throws SQLException {
        final Messages messagesForStudent = databaseManager.getMessagesForStudent(validStudent);
        Assert.assertEquals(4, messagesForStudent.getMessages().length);
    }

    @Test
    public void testGetMessagesForClassroom() throws SQLException {
        final MessagesInClassroomBody body =
                new MessagesInClassroomBody(validLecturer.getEmail(), validLecturer.getPassword(),
                        "Mobile Computing");
        final Messages messages = databaseManager.getMessagesForClassroom(body);
        Assert.assertEquals(3, messages.getMessages().length);
    }

    @Test
    public void testSubscribe() throws SQLException {
        final SubscriptionBody subscriptionBody =
                new SubscriptionBody("student@haw-landshut.de", "password", "Generic Empty Classroom");
        databaseManager.subscribe(subscriptionBody);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubscribeWithInvalidRole() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("lecturer@haw-landshut.de", "password", "Generic Empty Classroom");
        databaseManager.subscribe(body);
}

    @Test(expected = SQLException.class)
    public void testSubscribeNonExistentClassroom() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("student@haw-landshut.de", "password", "Generic Nonexistent Classroom");
        databaseManager.subscribe(body);
    }

    @Test(expected = SQLException.class)
    public void testAlreadySubscribed() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("student@haw-landshut.de", "password", "Bildverstehen");
        databaseManager.subscribe(body);
    }

    @Test
    public void testUnsubscribe() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("student@haw-landshut.de", "password", "Bildverstehen");
        databaseManager.unsubscribe(body);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsubscribeWithInvalidCredentials() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("sterdurnt@haw-landshut.de", "password", "Bildverstehen");
        databaseManager.unsubscribe(body);
    }

    @Test
    public void testUnsubscribeNotSubscribed() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("student@haw-landshut.de", "password", "Generic Empty Classroom");
        databaseManager.unsubscribe(body);
    }

    @Test
    public void testUnsubscribeNonExistentClassroom() throws SQLException {
        final SubscriptionBody body =
                new SubscriptionBody("student@haw-landshut.de", "password", "Generic Nonexistent Classroom");
        databaseManager.unsubscribe(body);
    }

    @Test
    public void testGetAllClassrooms() throws SQLException {
        final Classrooms classrooms = databaseManager.getAllClassroomsForStudent(validStudent);
        Assert.assertTrue("subscribed classrooms received as unsubscribed", classrooms.getClassrooms()[0].getSubscribed());
        Assert.assertTrue("subscribed classrooms received as unsubscribed", classrooms.getClassrooms()[1].getSubscribed());
        Assert.assertTrue("subscribed classrooms received as unsubscribed", classrooms.getClassrooms()[2].getSubscribed());
        Assert.assertTrue("unsubscribed classrooms received as subscribed", !classrooms.getClassrooms()[3].getSubscribed());
    }

    @Test
    public void testCreateClassroom() throws SQLException {
        final CreateClassroomBody body =
                new CreateClassroomBody(validLecturer.getEmail(), validLecturer.getPassword(), "Generic New Classroom");
        databaseManager.createClassroom(body);
    }

    @Test(expected = SQLException.class)
    public void testCreateAlreadyExistingClassroom() throws SQLException {
        final CreateClassroomBody body =
                new CreateClassroomBody(validLecturer.getEmail(), validLecturer.getPassword(), "Generic Empty Classroom");
        databaseManager.createClassroom(body);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStudentCreatesClassroom() throws SQLException {
        final CreateClassroomBody body =
                new CreateClassroomBody(validStudent.getEmail(), validStudent.getPassword(), "Generic New Classroom");
        databaseManager.createClassroom(body);
    }

    @Test
    public void testPostMessage() throws SQLException {
        final PostMessageBody body =
                new PostMessageBody(validLecturer.getEmail(), validLecturer.getPassword(), "Generic Empty Classroom",
                        "Generic Message");
        databaseManager.postMessage(body);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostMessageNonexistentClassroom() throws SQLException {
        final PostMessageBody body =
                new PostMessageBody(validLecturer.getEmail(), validLecturer.getPassword(), "Generic Nonexistent Classroom",
                        "Generic Message");
        databaseManager.postMessage(body);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostMessageNotBelongingToLecturer() throws SQLException {

        final PostMessageBody body =
                new PostMessageBody(validLecturer.getEmail(), validLecturer.getPassword(), "Generic Not Belonging Classroom",
                        "Generic Message");
        databaseManager.postMessage(body);
    }

    @Test()
    public void testDeleteMessage() throws SQLException {
        final DeleteMessageBody body =
                new DeleteMessageBody(validLecturer.getEmail(), validLecturer.getPassword(), 1);
        databaseManager.deleteMessage(body);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotBelongingMessage() throws SQLException {
        final DeleteMessageBody body =
                new DeleteMessageBody(validLecturer.getEmail(), validLecturer.getPassword(), 5);
        databaseManager.deleteMessage(body);
    }

    @Test
    public void testDeleteClassroom() throws SQLException {
        final int lengthBefore = databaseManager.getClassroomsForLecturer(validLecturer).getClassrooms().length;
        final DeleteClassroomBody body =
                new DeleteClassroomBody(validLecturer.getEmail(), validLecturer.getPassword(),
                        "Generic Empty Classroom");
        databaseManager.deleteClassroom(body);
        final int lengthAfter = databaseManager.getClassroomsForLecturer(validLecturer).getClassrooms().length;
        Assert.assertEquals("did not delete the right amount of classrooms", lengthBefore - lengthAfter, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotBelongingClassroom() throws SQLException {
        final DeleteClassroomBody body =
                new DeleteClassroomBody(validLecturer.getEmail(), validLecturer.getPassword(),
                        "Generic Not Belonging Classroom");
        databaseManager.deleteClassroom(body);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistentClassroom() throws SQLException {
        final DeleteClassroomBody body =
                new DeleteClassroomBody(validLecturer.getEmail(), validLecturer.getPassword(),
                        "Generic Non Existent Classroom");
        databaseManager.deleteClassroom(body);
    }
}