package org.example;

import java.sql.*;

public class DbService {
    private static Connection connection;

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

