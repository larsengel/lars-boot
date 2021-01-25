package de.lengel.LarsBoot.Server;

import java.net.InetSocketAddress;

public abstract class HttpServerConfig {
    public abstract InetSocketAddress GetInetAddress();
    public abstract HttpSecurityConfig GetSecurityConfig();
}
