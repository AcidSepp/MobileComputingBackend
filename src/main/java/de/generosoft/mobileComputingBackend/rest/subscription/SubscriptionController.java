package de.generosoft.mobileComputingBackend.rest.subscription;

import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import de.generosoft.mobileComputingBackend.rest.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class SubscriptionController {

    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Success subscribe(@RequestBody SubscriptionBody subscriptionBody) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.subscribe(subscriptionBody.getEmail(), subscriptionBody.getPassword(), subscriptionBody.getClassRoomName());
        logger.info(subscriptionBody.getEmail() + " subscribed to " + subscriptionBody.getClassRoomName());
        return new Success();
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public Success unsubscribe(@RequestBody SubscriptionBody subscriptionBody)
            throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.unsubscribe(subscriptionBody.getEmail(), subscriptionBody.getPassword(), subscriptionBody.getClassRoomName());
        logger.info(subscriptionBody.getEmail() + " unsubscribed from " + subscriptionBody.getClassRoomName());
        return new Success();
    }

}
