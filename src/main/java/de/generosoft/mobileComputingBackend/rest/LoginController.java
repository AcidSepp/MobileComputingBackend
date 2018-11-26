package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.pojo.Credentials;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import de.generosoft.mobileComputingBackend.pojo.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Login login(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        final String role = databaseManager.validateCredentials(credentials);
        logger.info(credentials.getEmail() + " logged in as " + role);
        return new Login(role);
    }

}
