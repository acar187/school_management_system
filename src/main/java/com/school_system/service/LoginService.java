package com.school_system.service;

import com.school_system.dao.UserDAO;
import com.school_system.model.User;
import com.school_system.util.PasswordUtil;

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
