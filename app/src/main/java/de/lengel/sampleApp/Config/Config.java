package de.lengel.sampleApp.Config;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import de.lengel.LarsBoot.Annotations.Configuration;
import de.lengel.LarsBoot.Server.HttpSecurityConfig;
import de.lengel.LarsBoot.Server.HttpServerConfig;

import java.net.InetSocketAddress;


@Configuration
public class Config extends HttpServerConfig {

    private final String url = "localhost";
    private final int port = 8888;

    private String username = "admin";
    private String password = "password";

    @Override
    public InetSocketAddress GetInetAddress() {
        return new InetSocketAddress(url, port);
    }

    @Override
    public HttpSecurityConfig GetSecurityConfig() {
        return new SecurityConfig();
    }

    class SecurityConfig extends HttpSecurityConfig {

        @Override
        protected Authenticator GetAuthenticator() {
            return new BasicAuthenticator("base") {
                @Override
                public boolean checkCredentials(String s, String s1) {
                    return s.equals(username) && s1.equals(password);
                }
            };
        }
    }
}
