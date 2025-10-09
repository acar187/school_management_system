package com.school_system.main;

import com.school_system.util.PasswordUtil;

public class PasswordUtilMain {
    public static void main(String[] args) {
        String password = "password";
        String hashed = PasswordUtil.hashPassword(password);
        System.out.println("Hashed Password: " + hashed);

        System.out.println("Password matches: " + PasswordUtil.checkPassword(password, hashed));
    }
    
}
