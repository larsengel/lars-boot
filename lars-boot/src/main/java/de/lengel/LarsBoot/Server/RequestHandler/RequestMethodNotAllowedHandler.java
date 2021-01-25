package de.lengel.LarsBoot.Server.RequestHandler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class RequestMethodNotAllowedHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String response = "Request method " + exchange.getRequestMethod() + " not allowed";
        exchange.sendResponseHeaders(405, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        exchange.close();
    }
}
