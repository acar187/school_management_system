package com.school_system.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(int id) {
        super("Student mit ID " + id + " nicht gefunden!");
    }
    
}
