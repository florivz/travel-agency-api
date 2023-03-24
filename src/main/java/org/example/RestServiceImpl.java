package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestServiceImpl implements RestService{
    private List<String> addresses;
    private static GsonConverter gsonConverter;

    public String getAddresses(Connection connection) {
        addresses = new ArrayList<>();
        gsonConverter = new GsonConverter();
        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM address
                """)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                addresses.add(resultSet.getString(2));
            }
            System.out.println("Addresses sent as JSON");
            return gsonConverter.listToJSON(addresses);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
