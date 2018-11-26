package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.pojo.*;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController()
@RequestMapping(value = "/classrooms")
public class ClassroomsController {

    private final Logger logger = LoggerFactory.getLogger(ClassroomsController.class);

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Classrooms allClassrooms(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        final Classrooms classrooms = databaseManager.getAllClassroomsForStudent(credentials);
        logger.info(credentials.getEmail() + " queried all classrooms");
        return classrooms;
    }

    @RequestMapping(value = "/subscribed", method = RequestMethod.POST)
    public Classrooms subscribedClassrooms(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        logger.info(credentials.getEmail() + " queried subscribed classrooms");
        final Classrooms classrooms =
                databaseManager.getSubscribedClassroomsForStudent(credentials);
        return classrooms;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Success createClassroom(@RequestBody final CreateClassroomBody body) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.createClassroom(body);
        logger.info(body.getEmail() + " created classroom " + body.getClassroomName());
        return new Success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Success deleteClassroom(@RequestBody final DeleteClassroomBody body) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.deleteClassroom(body);
        logger.info(body.getEmail() + " created classroom " + body.getClassroomName());
        return new Success();
    }

}