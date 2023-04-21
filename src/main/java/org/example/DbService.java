package org.example;

import java.sql.*;
/**
 * This class handles the connection to the database
 * @author I551381
 * @version 1.0
 */
public class DbService {
    private static Connection connection;

    /**
     * This method starts the connection to the database with the according credentials
     * @throws SQLException
     */
    public void connectToDB() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/travel-agency-service_db",
                "root", "password"
        );
    }

    public Connection getConnection() {
        return connection;
    }
}

