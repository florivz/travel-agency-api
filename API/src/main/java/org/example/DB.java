package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private static Connection connection;
    List<String> addresses;

    public void connectToDB() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/travel-agency-service_db",
                "root", "password"
        );
    }

    public List<String> getAddresses() {
        addresses = new ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM address
                """)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                addresses.add(resultSet.getString(2));
            }
            System.out.println("Send addresses");
            return addresses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

