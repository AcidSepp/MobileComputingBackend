package de.generosoft.mobileComputingBackend.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class DatabaseManager {

    private static @Nullable DatabaseManager INSTANCE;

    public static @NotNull DatabaseManager getInstance() throws SQLException, ClassNotFoundException {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager();
        }
        return INSTANCE;
    }

    private static final @NotNull String DATABASE_URL = "jdbc:postgresql:mobileComputing";
    private static final @NotNull String USERNAME = "postgres";
    private static final @NotNull String PASSWORD = "wabern";

    private final @NotNull Connection database;

    public DatabaseManager() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        database = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public @NotNull Classrooms getSubscribedClassroomsForStudent(final @NotNull String studentMail,
                                                                 final @NotNull String password) throws SQLException {
        final String role = validateCredentials(studentMail, password);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final LinkedList<Classroom> classrooms = new LinkedList<>();
        final String query = "select subscribes.ClassRoomName, ClassRooms.LecturerMail from \n" +
                "subscribes join ClassRooms on ClassRooms.ClassRoomName = subscribes.ClassRoomName\n" +
                "where subscribes.Subscriber = ?";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, studentMail);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            final Classroom classroom = new Classroom(resultSet.getString(1), resultSet.getString(2), true);
            classrooms.add(classroom);
        }
        final Classroom[] classroomsArray = new Classroom[classrooms.size()];
        classrooms.toArray(classroomsArray);
        return new Classrooms(classroomsArray);
    }

    public @NotNull String validateCredentials(final @NotNull String email, final @NotNull String password)
            throws SQLException {
        final String query = "select Persons.role from Persons where Email = ? and PWord = ?;";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        final ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            throw new IllegalArgumentException("invalid credentials");
        }
    }

    public @NotNull Messages getMessagesForStudent(final @NotNull String studentMail, final @NotNull String password) throws SQLException {
        final String role = validateCredentials(studentMail, password);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final LinkedList<Message> messages = new LinkedList<>();
        final String query =
                "select MessageID, subscribes.classRoomName, payload from (subscribes join messages on subscribes.ClassRoomName = messages.ClassRoomName) where Subscriber = ?;";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, studentMail);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            final int messageID = resultSet.getInt(1);
            final String classRoomName = resultSet.getString(2);
            final String payload = resultSet.getString(3);
            final Message message = new Message(messageID, classRoomName, payload);
            messages.add(message);
        }
        final Message[] messagesArray = new Message[messages.size()];
        messages.toArray(messagesArray);
        return new Messages(messagesArray);
    }

    public void closeDatabase() throws SQLException {
        database.close();
    }

    public void executeSqlFile(final @NotNull String path) throws SQLException, FileNotFoundException {
        final File file = new File(path);
        final String sql = new Scanner(file).useDelimiter("\\Z").next();
        final PreparedStatement preparedStatement = database.prepareStatement(sql);
        preparedStatement.execute();
    }

    public void subscribe(final @NotNull String studentMail, final @NotNull String password,
                          final @NotNull String classroom) throws SQLException {
        final String role = validateCredentials(studentMail, password);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final PreparedStatement preparedStatement = database.prepareStatement("insert into subscribes values(?, ?);");
        preparedStatement.setString(1, studentMail);
        preparedStatement.setString(2, classroom);
        preparedStatement.execute();
    }

    public void unsubscribe(final @NotNull String studentMail, final @NotNull String password,
                            final @NotNull String classroom) throws SQLException {

        final String role = validateCredentials(studentMail, password);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final PreparedStatement preparedStatement =
                database.prepareStatement("delete from subscribes where subscriber = ? and classroomname = ?");
        preparedStatement.setString(1, studentMail);
        preparedStatement.setString(2, classroom);
        preparedStatement.execute();
    }

    public Classrooms getAllClassroomsForStudent(final @NotNull String studentMail, final @NotNull String password)
            throws SQLException {

        final String role = validateCredentials(studentMail, password);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("user is not of role student");
        }

        final LinkedList<Classroom> classrooms = new LinkedList<>();
        final PreparedStatement preparedStatement = database.prepareStatement(
                "select ClassRooms.ClassRoomName, ClassRooms.LecturerMail, subscribes.subscriber from subscribes right join ClassRooms on ClassRooms.ClassRoomName = subscribes.ClassRoomName where subscriber = ? or subscriber is null");
        preparedStatement.setString(1, studentMail);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            final String classRoomName = resultSet.getString(1);
            final String lecturer = resultSet.getString(2);
            final boolean subscribed = resultSet.getString(3) != null;
            classrooms.add(new Classroom(classRoomName, lecturer, subscribed));
        }
        final Classroom[] classroomsArray = new Classroom[classrooms.size()];
        classrooms.toArray(classroomsArray);
        return new Classrooms(classroomsArray);
    }

}
