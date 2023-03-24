package org.example;

import java.sql.Connection;
import java.util.List;

public interface RestService {

    /**
     * Retrieves all addresses from DB and sends it as a response in JSON format
     * @param connection
     * @return Addresses as JSON
     */
    public String getAddresses(Connection connection);
}
