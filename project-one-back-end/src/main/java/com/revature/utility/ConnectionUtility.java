package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());

        String url = System.getenv("db_url");
        String user = System.getenv("db_user");
        String pass = System.getenv("db_pass");

        return DriverManager.getConnection(url, user, pass);
    }

}
