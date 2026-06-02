package org.saasify.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/ferrogest";
    private static final String user = "postgres";
    private static final String password = "Maagd12005";

    public static Connection getConnection() throws SQLException{
        Connection conexion = null;
            conexion = DriverManager.getConnection(URL, user, password);
            System.out.println("Succesfully connected to database");
        return conexion;
    }

}
