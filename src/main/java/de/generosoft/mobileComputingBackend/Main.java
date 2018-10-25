package de.generosoft.mobileComputingBackend;

import de.generosoft.mobileComputingBackend.db.DatabaseManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.sql.SQLException;


@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.execute("D:\\Studium\\Master\\MobileComputing\\Backend\\src\\main\\resources\\database\\database.sql");
        SpringApplication.run(Main.class, args);
    }
}