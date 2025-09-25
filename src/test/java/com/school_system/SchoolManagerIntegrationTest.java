package com.school_system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
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

    @Test
    void testCountStudentsInCourse() {
        SchoolManager manager = new SchoolManager();

        Student s1 = new Student("Mia", "mia@example.com", "M2025-300");
        Student s2 = new Student("Jonas", "jonas@example.com", "M2025-301");
        manager.addStudent(s1);
        manager.addStudent(s2);

        Course c = new Course("Geschichte");
        manager.addCourse(c);

        manager.assignStudentToCourse(s1.getId(), c.getId());
        manager.assignStudentToCourse(s2.getId(), c.getId());

        assertEquals(2, manager.countStudentsInCourse(c.getId()));
    }

    @Test
    void testGetCourseWithMostStudents() {
        SchoolManager manager = new SchoolManager();
        
        Course c1 = new Course("Biologie");
        Course c2 = new Course("Chemie");
        manager.addCourse(c1);
        manager.addCourse(c2);

        Student s = new Student("Eva", "eva@example.com", "M2025-302");
        manager.addStudent(s);
        manager.assignStudentToCourse(s.getId(), c1.getId());

        Course top = manager.getCourseWithMostStudents();
        assertEquals("Biologie", top.getName());
    }

    @Test
    void testExportImportStudents() {
        SchoolManager manager = new SchoolManager();
        Student s = new Student("Paul", "paul@example.com", "M2025-400");
        manager.addStudent(s);

        String file = "students_test.csv";
        manager.exportStudentsToCSV(file);

        // Neue Instanz vom Manager zum Importieren
        SchoolManager newManager = new SchoolManager();
        newManager.importStudentsFromCSV(file);

        Student imported = newManager.findStudentByName("Paul");
        assertNotNull(imported);
        assertEquals("Paul", imported.getName());
}
    @Test
    void testExportSchoolReport() {
        SchoolManager manager = new SchoolManager();
        Teacher t = new Teacher("Frau Schmidt", "schmidt@example.com", "Mathematik");
        manager.addTeacher(t);

        Student s = new Student("Anna", "anna@example.com", "M2025-500");
        manager.addStudent(s);

        Course c = new Course("Mathematik");
        manager.addCourse(c);
        manager.assignTeacherToCourse(t.getId(), c.getId());
        manager.assignStudentToCourse(s.getId(), c.getId());

        String file = "school_report.txt";
        manager.exportSchoolReport(file);

        File f = new File(file);
        assertTrue(f.exists(), "Report-Datei sollte existieren");
    }


}
