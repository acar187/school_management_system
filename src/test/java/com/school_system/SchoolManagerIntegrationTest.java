package com.school_system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SchoolManagerIntegrationTest {

    @Test
    void testEndToEndStudentEnrollment() {
        SchoolManager manager = new SchoolManager();

        // Schritt 1: Student + Kurs anlegen
        Student s = new Student("Lisa", "lisa@example.com", "M2025-030");
        manager.addStudent(s);

        Course c = new Course("Biologie");
        manager.addCourse(c);

        // Schritt 2: Student in Kurs einschreiben
        manager.assignStudentToCourse(s.getId(), c.getId());

        // Schritt 3: Überprüfung – Student ist im Kurs eingeschrieben
        Course foundCourse = manager.findCourseById(c.getId());
        assertTrue(foundCourse.getStudents().contains(s), 
                   "Student sollte im Kurs eingeschrieben sein");

        // Schritt 4: Suche nach Name funktioniert
        Student foundStudent = manager.findStudentByName("Lisa");
        assertNotNull(foundStudent, "Student sollte gefunden werden");
        assertEquals("Lisa", foundStudent.getName());

        Course foundByName = manager.findCourseByName("Biologie");
        assertNotNull(foundByName, "Kurs sollte gefunden werden");
        assertEquals("Biologie", foundByName.getName());
    }
}
