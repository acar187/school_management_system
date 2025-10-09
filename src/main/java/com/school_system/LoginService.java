package com.school_system;

public class LoginService {

    //private final UserDAO userDAO;

    // public LoginService(UserDAO userDAO) { 
    //     this.userDAO = userDAO; 
    // }

    public User login(String username, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsername(username);
        if (user != null && PasswordUtil.checkPassword(password, user.getPasswordHash())) {
            return user;
        } else {
            return null;
        }
    }

}
