package de.generosoft.mobileComputingBackend.rest.subscription;

import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class SubscriptionController {

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void subscribe(@RequestBody SubscriptionBody subscriptionBody) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.subscribe(subscriptionBody.getEmail(), subscriptionBody.getPassword(), subscriptionBody.getClassRoomName());
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void unsubscribe(@RequestBody SubscriptionBody subscriptionBody)
            throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.unsubscribe(subscriptionBody.getEmail(), subscriptionBody.getPassword(), subscriptionBody.getClassRoomName());
    }

}
