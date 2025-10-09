package com.school_system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "schooluser";
    private static final String PASSWORD = "schoolpass";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Verbindung zu MySQL hergestellt.");
            return conn;
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler bei der Verbindung: " + e.getMessage());
            return null;
        }
    }
}
