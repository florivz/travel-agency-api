package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.sql.SQLException;

import static org.example.Authenticator.*;

public class LocalServer {

    private static final DbService dbService = new DbService();
    private static final RestServiceImpl restServiceImpl = new RestServiceImpl();
    private static final Authenticator authenticator = new Authenticator();
    private static String username;
    private static String password;


    public static void main(String[] args) throws IOException, SQLException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);

        HttpContext flightsContext = server.createContext("/flights");
        flightsContext.setHandler(LocalServer::handleFlightsRequest);

        HttpContext hotelsContext = server.createContext("/hotels");
        hotelsContext.setHandler(LocalServer::handleHotelsRequest);

        server.start();

        dbService.connectToDB();
    }

    private static void handleHotelsRequest(HttpExchange exchange) throws IOException {
        String response = restServiceImpl.getHotels(dbService.getConnection());

        username = extractUsername(exchange.getRequestURI().toString());
        password = extractPassword(exchange.getRequestURI().toString());

        if (checkCredentials(authenticator.getCredentialsMap(), username, password)) {
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(401, -1);
            OutputStream os = exchange.getResponseBody();
            os.write("Invalid credentials".getBytes());
            os.close();
        }
    }

    private static void handleFlightsRequest(HttpExchange exchange) throws IOException {
        String response = restServiceImpl.getFlightConnections(dbService.getConnection());

        username = extractUsername(exchange.getRequestURI().toString());
        password = extractPassword(exchange.getRequestURI().toString());

        if (checkCredentials(authenticator.getCredentialsMap(), username, password)) {
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(401, -1);
            OutputStream os = exchange.getResponseBody();
            os.write("Invalid credentials".getBytes());
            os.close();
        }
    }
}