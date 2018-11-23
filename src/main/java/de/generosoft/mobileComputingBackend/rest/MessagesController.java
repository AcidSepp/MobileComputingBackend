package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.db.Credentials;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import de.generosoft.mobileComputingBackend.db.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class MessagesController {

    private final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public Messages messages(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        logger.info(credentials.getEmail() + " queried messages");
        return databaseManager.getMessagesForStudent(credentials.getEmail(), credentials.getPassword());
    }

}
