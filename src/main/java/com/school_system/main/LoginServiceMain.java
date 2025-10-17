package com.school_system.main;

import com.school_system.model.User;
import com.school_system.service.LoginService;

public class LoginServiceMain {
    public static void main(String[] args) {
        //UserDAO userDAO = new UserDAO();
        LoginService service = new LoginService();
        User logged = service.login("Paulmann", "password");
        if (logged != null)
            System.out.println("Willkommen, " + logged.getUsername());
        else
            System.out.println("Login fehlgeschlagen!");
    }
    //     UserDAO userDAO = new UserDAO();
    //     LoginService loginService = new LoginService(userDAO);

    //     // Test login
    //     String username = "Paulmann";
    //     String password = "password"; // Correct password
    //     User user = loginService.login(username, password);
    //     if (user != null) {
    //         System.out.println("Login successful: " + user);
    //     } else {
    //         System.out.println("Login failed for user: " + username);
    //     }

    //     // Test with incorrect password
    //     password = "wrongpassword";
    //     user = loginService.login(username, password);
    //     if (user != null) {
    //         System.out.println("Login successful: " + user);
    //     } else {
    //         System.out.println("Login failed for user: " + username);
    //     }
    // }
    
}