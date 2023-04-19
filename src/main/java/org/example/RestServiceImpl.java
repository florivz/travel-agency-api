package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestServiceImpl implements RestService {

    public String getHotels(Connection connection) {
        List<Map<String, Object>> hotels = new ArrayList<>();
        GsonConverter gsonConverter = new GsonConverter();
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT *
                FROM hotel
            """)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                hotels.add(row);
            }
            System.out.println("Hotels sent as JSON");
            return gsonConverter.listToJSON(hotels);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFlightConnections(Connection connection) {
        List<Map<String, Object>> flightConnections = new ArrayList<>();
        GsonConverter gsonConverter = new GsonConverter();
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT *
                FROM flight_connection
            """)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                flightConnections.add(row);
            }
            System.out.println("Flight connections sent as JSON");
            return gsonConverter.listToJSON(flightConnections);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}