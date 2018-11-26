package de.generosoft.mobileComputingBackend.db;

import de.generosoft.mobileComputingBackend.pojo.*;
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

    DatabaseManager() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        database = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public @NotNull Classrooms getSubscribedClassroomsForStudent(final @NotNull Credentials credentials) throws SQLException {
        final String role = validateCredentials(credentials);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final LinkedList<Classroom> classrooms = new LinkedList<>();
        final String query = "select subscribes.ClassRoomName, ClassRooms.LecturerMail from \n" +
                "subscribes join ClassRooms on ClassRooms.ClassRoomName = subscribes.ClassRoomName\n" +
                "where subscribes.Subscriber = ?";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, credentials.getEmail());
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            final Classroom classroom = new Classroom(resultSet.getString(1), resultSet.getString(2), true);
            classrooms.add(classroom);
        }
        final Classroom[] classroomsArray = new Classroom[classrooms.size()];
        classrooms.toArray(classroomsArray);
        return new Classrooms(classroomsArray);
    }

    public @NotNull String validateCredentials(final @NotNull Credentials credentials)
            throws SQLException {
        final String query = "select Persons.role from Persons where Email = ? and PWord = ?;";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, credentials.getEmail());
        preparedStatement.setString(2, credentials.getPassword());
        final ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            throw new IllegalArgumentException("invalid credentials");
        }
    }

    public @NotNull Messages getMessagesForStudent(final @NotNull Credentials credentials) throws SQLException {
        final String role = validateCredentials(credentials);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final LinkedList<Message> messages = new LinkedList<>();
        final String query =
                "select MessageID, subscribes.classRoomName, payload from (subscribes join messages on subscribes.ClassRoomName = messages.ClassRoomName) where Subscriber = ?;";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, credentials.getEmail());
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

    public void subscribe(final @NotNull SubscriptionBody body) throws SQLException {
        final String role = validateCredentials(body);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final PreparedStatement preparedStatement = database.prepareStatement("insert into subscribes values(?, ?);");
        preparedStatement.setString(1, body.getEmail());
        preparedStatement.setString(2, body.getClassRoomName());
        preparedStatement.execute();
    }

    public void unsubscribe(final @NotNull SubscriptionBody body) throws SQLException {

        final String role = validateCredentials(body);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("subscriber is not of role student");
        }

        final PreparedStatement preparedStatement =
                database.prepareStatement("delete from subscribes where subscriber = ? and classroomname = ?");
        preparedStatement.setString(1, body.getEmail());
        preparedStatement.setString(2, body.getClassRoomName());
        preparedStatement.execute();
    }

    public @NotNull Classrooms getAllClassroomsForStudent(final @NotNull Credentials credentials)
            throws SQLException {

        final String role = validateCredentials(credentials);
        if (!role.equals("student")) {
            throw new IllegalArgumentException("user is not of role student");
        }

        final LinkedList<Classroom> classrooms = new LinkedList<>();
        final PreparedStatement preparedStatement = database.prepareStatement(
                "select ClassRooms.ClassRoomName, ClassRooms.LecturerMail, subscribes.subscriber from subscribes right join ClassRooms on ClassRooms.ClassRoomName = subscribes.ClassRoomName where subscriber = ? or subscriber is null");
        preparedStatement.setString(1, credentials.getEmail());
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

    public void createClassroom(final @NotNull CreateClassroomBody body) throws SQLException {
        final String role = validateCredentials(body);
        if (!"lecturer".equals(role)) {
            throw new IllegalArgumentException("user is not of role lecturer");
        }
        final PreparedStatement preparedStatement = database.prepareStatement("insert into ClassRooms values(?, ?);");
        preparedStatement.setString(1, body.getClassroomName());
        preparedStatement.setString(2, body.getEmail());
        preparedStatement.execute();
    }

    public void postMessage(final @NotNull PostMessageBody body) throws SQLException {
        final String role = validateCredentials(body);
        if (!"lecturer".equals(role)) {
            throw new IllegalArgumentException("user is not of role lecturer");
        }
        final PreparedStatement statement = database.prepareStatement("select lecturermail from ClassRooms where ClassroomName = ?;");
        statement.setString(1, body.getClassroomName());
        final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            throw new IllegalArgumentException("Classroom " + body.getClassroomName() + " does not exist");
        }
        if (!resultSet.getString(1).equals(body.getEmail())) {
            throw new IllegalArgumentException("Classroom  " + body.getClassroomName() + " does not belong to lecturer " + body.getEmail());
        }
        final PreparedStatement preparedStatement = database.prepareStatement("insert into Messages(ClassRoomName, Payload) values(?,?);");
        preparedStatement.setString(1, body.getClassroomName());
        preparedStatement.setString(2, body.getPayload());
        preparedStatement.execute();
    }

    public void deleteMessage(final @NotNull DeleteMessageBody body) throws SQLException {
        final String role = validateCredentials(body);
        if (!"lecturer".equals(role)) {
            throw new IllegalArgumentException("user is not of role lecturer");
        }
        final PreparedStatement statement = database.prepareStatement("select lecturermail from Messages join Classrooms on Messages.ClassroomName = Classrooms.ClassroomName where MessageId = ?;");
        statement.setInt(1, body.getMessageId());
        final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            throw new IllegalArgumentException("Message #" + body.getMessageId() + " does not exist");
        }
        if (!resultSet.getString(1).equals(body.getEmail())) {
            throw new IllegalArgumentException("Message  #" + body.getMessageId() + " does not belong to lecturer " + body.getEmail());
        }
        final PreparedStatement preparedStatement = database.prepareStatement("delete from Messages where messageId = ?;");
        preparedStatement.setInt(1, body.getMessageId());
        preparedStatement.execute();
    }

    public void deleteClassroom(final @NotNull DeleteClassroomBody body) throws SQLException {
        final String role = validateCredentials(body);
        if (!"lecturer".equals(role)) {
            throw new IllegalArgumentException("user is not of role lecturer");
        }
        final PreparedStatement statement = database.prepareStatement("select lecturermail from ClassRooms where ClassroomName = ?;");
        statement.setString(1, body.getClassroomName());
        final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            throw new IllegalArgumentException("Classroom " + body.getClassroomName() + " does not exist");
        }
        if (!resultSet.getString(1).equals(body.getEmail())) {
            throw new IllegalArgumentException("Classroom  " + body.getClassroomName() + " does not belong to lecturer " + body.getEmail());
        }
        final PreparedStatement preparedStatement = database.prepareStatement("delete from Messages where classroomName = ?;");
        preparedStatement.setString(1, body.getClassroomName());
        preparedStatement.execute();
    }
}
