package com.school_system;

public class PasswordUtilMain {
    public static void main(String[] args) {
        String password = "password";
        String hashed = PasswordUtil.hashPassword(password);
        System.out.println("Hashed Password: " + hashed);

        System.out.println("Password matches: " + PasswordUtil.checkPassword(password, hashed));
    }
    
}
