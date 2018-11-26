package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.pojo.Credentials;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import de.generosoft.mobileComputingBackend.pojo.Messages;
import de.generosoft.mobileComputingBackend.pojo.PostMessageBody;
import de.generosoft.mobileComputingBackend.pojo.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public Messages student(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        final Messages messages = databaseManager.getMessagesForStudent(credentials);
        logger.info(credentials.getEmail() + " queried messages");
        return messages;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Success post(@RequestBody final PostMessageBody body) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        logger.info(body.getEmail() + " posted message in classroom " + body.getClassroomName());
        databaseManager.postMessage(body);
        return new Success();
    }

}
