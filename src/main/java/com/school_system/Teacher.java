package com.school_system;

public class Teacher extends Person {
    
    private String subject; // z.B. "Mathematik"

    public Teacher(String name, String email, String subject) {
        super(name, email);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }   

    @Override
    public String toString() {
        return "Teacher{" + super.toString() + ", subject=" + subject + '}';
    
    }
}
