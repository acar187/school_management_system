package com.school_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SchoolManagerTest {

    private SchoolManager manager;
    private Student s1;
    private Student s2;
    private Teacher t1;
    private Course c1;

    @BeforeEach
    void setup() {
        manager = new SchoolManager();
        s1 = new Student("Anna Müller", "anna@example.com", "M2025-001");
        s2 = new Student("Max Schmidt", "max@example.com", "M2025-002");
        t1 = new Teacher("Dr. Meier", "meier@example.com", "Mathematik");
        c1 = new Course("Mathematik");

        manager.addStudent(s1);
        manager.addStudent(s2);
        manager.addTeacher(t1);
        manager.addCourse(c1);
    }

    @Test
    void testAddStudentAndFindById() {
        Student found = manager.findStudentById(s1.getId());
        assertNotNull(found);
        assertEquals("Anna Müller", found.getName());
    }

    @Test
    void testAddTeacherAndFindById() {
        Teacher found = manager.findTeacherById(t1.getId());
        assertNotNull(found);
        assertEquals("Mathematik", found.getSubject());
    }

    @Test
    void testAssignStudentToCourse() {
        manager.assignStudentToCourse(s1.getId(), c1.getId());
        assertTrue(c1.getStudents().contains(s1));
    }

    @Test
    void testAssignTeacherToCourse() {
        manager.assignTeacherToCourse(t1.getId(), c1.getId());
        assertEquals(t1, c1.getTeacher());
    }

    @Test
    void testFindNonexistentStudent() {
        Student missing = manager.findStudentById(999);
        assertNull(missing);
    }

    @Test
    void testAddAndFindCourseWithMap() {
        Course c = new Course("Biologie");
        manager.addCourse(c);

        Course found = manager.findCourseById(c.getId());
        assertNotNull(found);
        assertEquals("Biologie", found.getName());
    }

    @Test
    void testStudentNotAddedTwice() {
        Course c = new Course("Physik");
        Student s = new Student("Lena", "lena@example.com", "M2025-003");

        c.addStudent(s);
        c.addStudent(s); // zweites Mal

        assertEquals(1, c.getStudents().size()); // sollte nur einmal drin sein
    }

//     @Test
//     void testAssignNonexistentStudent() {
//         Course c = new Course("Chemie");
//         manager.addCourse(c);

//         Exception exception = assertThrows(StudentNotFoundException.class, () -> {
//             manager.assignStudentToCourse(999, c.getId());
//         });

//         assertTrue(exception.getMessage().contains("Student mit ID 999 nicht gefunden"));
// }

//     @Test
//     void testAssignToNonexistentCourse() {
//         Student s = new Student("Lukas", "lukas@example.com", "M2025-010");
//         manager.addStudent(s);

//         Exception exception = assertThrows(CourseNotFoundException.class, () -> {
//             manager.assignStudentToCourse(s.getId(), 999);
//         });

//         assertTrue(exception.getMessage().contains("Kurs mit ID 999 nicht gefunden"));
// }

    @Test
    void testFindStudentByName(){
        Student s = new Student("Max","max@gmail.com","M2025-002"); 
        manager.addStudent(s);

        Student found = manager.findStudentByName("Max");
        assertNotNull(found);
        assertEquals("Max", found.getName());
    }

    

}
