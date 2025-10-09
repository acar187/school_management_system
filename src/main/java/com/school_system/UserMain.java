package com.school_system;

public class UserMain {
    public static void main(String[] args) {
        
        // Test UserDAO
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsername("Paulmann");
        if (user != null) {
            System.out.println("Gefundener Benutzer: " + user);
        } else {
            System.out.println("Benutzer nicht gefunden.");
        }
    }
    
}
