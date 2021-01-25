package de.lengel.sampleApp.RequestHandlers;

import de.lengel.LarsBoot.Annotations.GetRequestHandler;
import de.lengel.LarsBoot.Annotations.RequestEndpoint;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

@RequestEndpoint("/test")
public class TestRequestHandler {

    @GetRequestHandler()
    public void handleRequest(HttpExchange exchange) throws IOException {

        String response = "Hello!";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
