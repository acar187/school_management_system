package com.school_system.main;

import com.school_system.dao.CourseDAO;
import com.school_system.dao.EnrollmentDAO;
import com.school_system.dao.StudentDAO;
import com.school_system.model.Course;
import com.school_system.model.Enrollment;
import com.school_system.model.EnrollmentView;
import com.school_system.model.Student;

public class AppEnrollmentView {
    public static void main(String[] args) {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();

        //CREATE Student
        Student s1 = new Student("Lisa Meier", "lisa@gmail.com", "W2024-001");
        studentDAO.addStudent(s1);
        
        //CREATE Course
        Course c1 = new Course("Mathematik");
        courseDAO.addCourse(c1);

        //CREATE Enrollment
        Enrollment e1 = new Enrollment(s1.getId(), c1.getId());
        enrollmentDAO.addEnrollment(e1);

        //Alle EnrollmentViews in der Datenbank:
        System.out.println("Alle EnrollmentViews in der Datenbank:");
        for (EnrollmentView ev : enrollmentDAO.getAllEnrollmentViews()) {
            System.out.println(ev);
        }
    }
}
