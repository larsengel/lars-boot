package de.lengel.LarsBoot;

import de.lengel.LarsBoot.Server.LarsHttpServer;

public class LarsApplication {

    public void startApplication(Class<?> applicationClass) {
        try {
            LarsHttpServer httpServer = new LarsHttpServer();
            httpServer.startServer(applicationClass.getPackageName());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
