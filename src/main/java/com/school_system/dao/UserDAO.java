package com.school_system.dao;

import java.sql.SQLException;

import com.school_system.model.User;
import com.school_system.util.DatabaseConnector;

// Make sure to import the Database class if it's in another package
// import com.school_system.Database;

public class UserDAO {

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (var conn = DatabaseConnector.connect();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
