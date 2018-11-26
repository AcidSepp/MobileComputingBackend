package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import de.generosoft.mobileComputingBackend.pojo.SubscriptionBody;
import de.generosoft.mobileComputingBackend.pojo.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/classrooms")
public class SubscriptionController {

    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Success subscribe(@RequestBody SubscriptionBody subscriptionBody) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.subscribe(subscriptionBody);
        logger.info(subscriptionBody.getEmail() + " subscribed to " + subscriptionBody.getClassRoomName());
        return new Success();
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public Success unsubscribe(@RequestBody SubscriptionBody subscriptionBody)
            throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.unsubscribe(subscriptionBody);
        logger.info(subscriptionBody.getEmail() + " unsubscribed from " + subscriptionBody.getClassRoomName());
        return new Success();
    }

}
