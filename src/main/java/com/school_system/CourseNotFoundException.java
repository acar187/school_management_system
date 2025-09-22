package com.school_system;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(int id) {
        super("Kurs mit ID " + id + " nicht gefunden!");
    }
    
}
