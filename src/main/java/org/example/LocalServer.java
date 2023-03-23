package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.sql.SQLException;


public class LocalServer {

    private static final DB DB = new DB();

    public static void main(String[] args) throws IOException, SQLException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/addresses");
        context.setHandler(LocalServer::handleRequest);
        server.start();

        DB.connectToDB();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        String response = DB.getAddresses().toString();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
