package de.lengel.LarsBoot;

import de.lengel.LarsBoot.Database.PostgresConnection;
import de.lengel.LarsBoot.Server.LarsHttpServer;

public class LarsApplication {

    public void startApplication(Class<?> applicationClass) {
        try {
            LarsHttpServer httpServer = new LarsHttpServer();
            httpServer.startServer(applicationClass.getPackageName());

            PostgresConnection postgresConnection = new PostgresConnection();
            postgresConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
