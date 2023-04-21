package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.sql.SQLException;

import static org.example.Authenticator.*;

/**
 * This class creates & starts the local REST-API server and coordinates the requests and responses.
 * This REST-API checks the credentials of the requester for every sent request and is able to
 * response with hotel and flight connection data. If the requester sends a wrong request a 400 http status code
 * is sent.
 *
 * @author I551393
 * @version 1.0
 */
public class LocalServer {

    private static final DbService dbService = new DbService();
    private static final RestServiceImpl restServiceImpl = new RestServiceImpl();
    private static final Authenticator authenticator = new Authenticator();
    private static String username;
    private static String password;


    public static void main(String[] args) throws IOException, SQLException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);

        HttpContext flightsContext = server.createContext("/getFlightConnections");
        flightsContext.setHandler(LocalServer::handleFlightsRequest);

        HttpContext hotelsContext = server.createContext("/getHotels");
        hotelsContext.setHandler(LocalServer::handleHotelsRequest);

        server.start();

        dbService.connectToDB();
    }

    private static void handleRequest(HttpExchange exchange, String requestType) throws IOException {
        username = extractUsername(exchange.getRequestURI().toString());
        password = extractPassword(exchange.getRequestURI().toString());
        if (checkCredentials(authenticator.getCredentialsMap(), username, password)) {
            String response = "";
            if (requestType.equals("hotels")) {
                response = restServiceImpl.getHotels(dbService.getConnection());
            } else if (requestType.equals("flights")) {
                response = restServiceImpl.getFlightConnections(dbService.getConnection());
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(401, -1);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("Invalid credentials provided".getBytes());
            }
        }
    }

    public static void handleHotelsRequest(HttpExchange exchange) throws IOException {
        handleRequest(exchange, "hotels");
    }

    public static void handleFlightsRequest(HttpExchange exchange) throws IOException {
        handleRequest(exchange, "flights");
    }
}
