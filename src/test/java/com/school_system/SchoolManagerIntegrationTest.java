package com.school_system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

    @Test
    void testStudentsSortedByName() {
        SchoolManager manager = new SchoolManager();
        Student s1 = new Student("Zoe", "z@example.com", "M2025-100");
        Student s2 = new Student("Anna", "a@example.com", "M2025-101");
        Student s3 = new Student("Markus", "m@example.com", "M2025-102");

        manager.addStudent(s1);
        manager.addStudent(s2);
        manager.addStudent(s3);

        List<Student> sorted = manager.getStudentsSortedByName();

        assertEquals("Anna", sorted.get(0).getName());
        assertEquals("Markus", sorted.get(1).getName());
        assertEquals("Zoe", sorted.get(2).getName());
    }

    @Test
    void testGetCoursesOfStudent() {
        SchoolManager manager = new SchoolManager();
        Student s = new Student("Tom", "tom@example.com", "M2025-200");
        manager.addStudent(s);

        Course c1 = new Course("Physik");
        Course c2 = new Course("Mathematik");
        manager.addCourse(c1);
        manager.addCourse(c2);

        manager.assignStudentToCourse(s.getId(), c1.getId());

        List<Course> result = manager.getCoursesForStudent(s.getId());

        assertEquals(1, result.size());
        assertEquals("Physik", result.get(0).getName());
    }


}
