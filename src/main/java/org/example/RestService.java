package org.example;

import java.sql.Connection;

public interface RestService {

    /**
     * Retrieves all addresses from DB and sends it as a response in JSON format
     * @param connection
     * @return Addresses as JSON
     */
    String getHotels(Connection connection);

    String getFlightConnections(Connection connection);
}
