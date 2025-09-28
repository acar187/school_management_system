package com.school_system;

public class AppSearch {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        //CREATE Students
        Student s1 = new Student("Anna Schmidt", "anna@gmail.de", "W2023-001");
        studentDAO.addStudent(s1);

        Course c1 = new Course("Physik");
        courseDAO.addCourse(c1);

        Course c2 = new Course("Chemie");
        courseDAO.addCourse(c2);

        //CREATE Enrollments
        Enrollment e1 = new Enrollment(s1.getId(), c1.getId());
        enrollmentDAO.addEnrollment(e1);
        Enrollment e2 = new Enrollment(s1.getId(), c2.getId());
        enrollmentDAO.addEnrollment(e2);

        //SEARCH 
        System.out.println("Kurse von Anna");
        for (String c : enrollmentDAO.getCoursesByStudentId(s1.getId())) {
            System.out.println(c);
        }

        System.out.println("Studenten in Physik");
        for (String s : enrollmentDAO.getStudentsByCourseId(c1.getId())) {
            System.out.println(s);
        }
    }
}
