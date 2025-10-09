package com.school_system.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(int id) {
        super("Kurs mit ID " + id + " nicht gefunden!");
    }
    
}
