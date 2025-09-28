package com.school_system;

public class AppEnrollment {
    public static void main(String[] args) {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();

        //CREATE Student
        Student s1 = new Student("Max Mustermann", "max@example.com", "M2025-002");
        studentDAO.addStudent(s1);

        //CREATE Course
        Course c1 = new Course("InformatikG");
        courseDAO.addCourse(c1);

        //CREATE Enrollment
        Enrollment e1 = new Enrollment(s1.getId(), c1.getId());
        enrollmentDAO.addEnrollment(e1);

        //Alle Enrollments in der Datenbank:
        System.out.println("Alle Enrollments in der Datenbank:");
        for (Enrollment e : enrollmentDAO.getAllEnrollments()) {
            System.out.println(e);
        }

        //DELETE Enrollment
        //enrollmentDAO.deleteEnrollment(e1.getId());
    }
}
