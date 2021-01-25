package de.lengel.LarsBoot.Server;

import com.sun.net.httpserver.HttpContext;
import de.lengel.LarsBoot.Annotations.Configuration;
import de.lengel.LarsBoot.Server.RequestHandler.RequestMethodNotAllowedHandler;
import de.lengel.LarsBoot.Annotations.GetRequestHandler;
import de.lengel.LarsBoot.Annotations.PostRequestHandler;
import de.lengel.LarsBoot.Annotations.RequestEndpoint;
import com.sun.net.httpserver.HttpServer;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Arrays.stream;

public class LarsHttpServer {

    public void startServer(String packageName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Reflections ref = new Reflections(packageName);

        var serverConfigClass = ref.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .filter(HttpServerConfig.class::isAssignableFrom)
                .findFirst()
                .orElseThrow();

        var serverConfig = (HttpServerConfig) serverConfigClass.getDeclaredConstructor().newInstance();

        HttpServer server = HttpServer.create(serverConfig.GetInetAddress(), 0);

        for (Class<?> cl : ref.getTypesAnnotatedWith(RequestEndpoint.class)) {
            RequestEndpoint requestHandler = cl.getAnnotation(RequestEndpoint.class);

            var obj = cl.getDeclaredConstructor().newInstance();

            HttpContext context = server.createContext(requestHandler.value(), httpExchange -> {

                Class<? extends Annotation> annotation;

                switch (httpExchange.getRequestMethod()) {
                    case "GET":
                        annotation = GetRequestHandler.class;
                        break;
                    case "POST":
                        annotation = PostRequestHandler.class;
                        break;
                    default:
                        RequestMethodNotAllowedHandler.handle(httpExchange);
                        return;
                }

                Method method = stream(cl.getDeclaredMethods()).filter(x -> x.isAnnotationPresent(annotation)).findFirst().orElse(null);
                if (method != null) {
                    try {
                        method.invoke(obj, httpExchange);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    RequestMethodNotAllowedHandler.handle(httpExchange);
                }

                httpExchange.close();
            });

            context.setAuthenticator(serverConfig.GetSecurityConfig().GetAuthenticator());
        }

        server.start();

        System.out.println("----- SERVER STARTED ON " + serverConfig.GetInetAddress().toString() +" -----");
    }
}
