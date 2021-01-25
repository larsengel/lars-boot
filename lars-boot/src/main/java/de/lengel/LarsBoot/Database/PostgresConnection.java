package de.lengel.LarsBoot.Database;


import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class PostgresConnection {

    public void connect() throws SQLException {
        String url = "jdbc:postgresql://localhost/lars-framework";
        Properties props = new Properties();
        props.setProperty("user","lars");
        props.setProperty("password","password");
        Connection conn = DriverManager.getConnection(url, props);


    }

}
