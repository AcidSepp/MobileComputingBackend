package de.generosoft.mobileComputingBackend.rest.classrooms;

import de.generosoft.mobileComputingBackend.db.Classrooms;
import de.generosoft.mobileComputingBackend.db.Credentials;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ClassroomsController {

    private final Logger logger = LoggerFactory.getLogger(ClassroomsController.class);

    @RequestMapping(value = "/allClassrooms", method = RequestMethod.POST)
    public Classrooms allClassrooms(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        logger.info(credentials.getEmail() + " queried all classrooms");
        return databaseManager.getAllClassroomsForStudent(credentials.getEmail(), credentials.getPassword());
    }

    @RequestMapping(value = "/subscribedClassrooms", method = RequestMethod.POST)
    public Classrooms subscribedClassrooms(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        logger.info(credentials.getEmail() + " queried subscribed classrooms");
        return databaseManager.getSubscribedClassroomsForStudent(credentials.getEmail(), credentials.getPassword());
    }

}