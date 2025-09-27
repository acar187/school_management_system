package com.school_system;

public class AppCourse {
    public static void main(String[] args) {
        CourseDAO courseDAO = new CourseDAO();

        //CREATE 
        Course c1 = new Course("Mathematik");
        courseDAO.addCourse(c1);

        //READ
        System.out.println("Alle Kurse in der Datenbank:");
        for (Course c : courseDAO.getAllCourses()) {
            System.out.println(c);
        }

        //UPDATE
        c1.setName("Mathematik 101");
        courseDAO.updateCourse(c1);

        //DELETE
        courseDAO.deleteCourse(c1.getId());
    }
}
