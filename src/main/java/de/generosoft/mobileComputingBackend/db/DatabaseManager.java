package de.generosoft.mobileComputingBackend.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManager {

    private static @Nullable
    DatabaseManager INSTANCE;

    public static @NotNull DatabaseManager getInstance() throws SQLException, ClassNotFoundException {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager();
        }
        return INSTANCE;
    }

    private static final @NotNull
    String DATABASE_URL = "jdbc:postgresql:mobileComputing";
    private static final @NotNull
    String USERNAME = "postgres";
    private static final @NotNull
    String PASSWORD = "wabern";

    private final @NotNull
    Connection database;

    public DatabaseManager() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        database = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public @NotNull List<Classroom> getAllClassroomsForStudent(final @NotNull String studentMail) throws SQLException {
        final LinkedList<Classroom> classrooms = new LinkedList<>();
        final String query =
                "select subscribes.ClassRoomName, ClassRooms.LecturerMail from \n" +
                        "subscribes join ClassRooms on ClassRooms.ClassRoomName = subscribes.ClassRoomName\n" +
                        "where subscribes.Subscriber = ?";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, studentMail);
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            final Classroom classroom = new Classroom(resultSet.getString(1), resultSet.getString(2));
            classrooms.add(classroom);
        }
        return classrooms;
    }

    public @Nullable String validateCredentials(final @NotNull String email, final @NotNull String password) throws SQLException {
        final String query = "select Persons.role from Persons where Email = ? and PWord = ?;";
        final PreparedStatement preparedStatement = database.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        final ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return "invalid credentials";
        }
    }

    public void closeDatabase() throws SQLException {
        database.close();
    }
}
