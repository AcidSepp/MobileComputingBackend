package de.generosoft.mobileComputingBackend.rest;

import de.generosoft.mobileComputingBackend.db.Credentials;
import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Login login(@RequestBody final Credentials credentials) throws SQLException, ClassNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        final String role = databaseManager.validateCredentials(credentials.getEmail(), credentials.getPassword());
        return new Login(role);
    }

}
