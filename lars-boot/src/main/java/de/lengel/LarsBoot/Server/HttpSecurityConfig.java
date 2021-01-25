package de.lengel.LarsBoot.Server;


import com.sun.net.httpserver.Authenticator;

public abstract class HttpSecurityConfig {
    protected abstract Authenticator GetAuthenticator();
}
