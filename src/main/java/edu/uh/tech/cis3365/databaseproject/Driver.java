package edu.uh.tech.cis3365.databaseproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class Driver {

    public static Connection startConnection() {
        Connection conn = null;
        /*String url="jdbc:postgresql://localhost:5432/ladybg?serverTimezone=UTC";
        String driver = "org.postgresql.Driver";
        String username = "postgres";
        String password = "postgres";*/
        String url="jdbc:sqlserver://172.26.54.132:1433;database=LadyBG";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username = "Houston_Vertical_Systems";
        String password = "CoT-CIS3365";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established.");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return conn;
    }
}
