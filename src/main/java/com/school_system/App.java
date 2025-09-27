package com.school_system;

public class App {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();

        //CREATE 
        Student s1 = new Student("Anna", "anna@example.com", "M2025-001");
        studentDAO.addStudent(s1);

        //READ
        System.out.println("Alle Studenten in der Datenbank:");
        for (Student s : studentDAO.getAllStudents()) {
            System.out.println(s);
        }

        //UPDATE
        s1.setName("Anna Müller");
        studentDAO.updateStudent(s1);

        //DELETE
        studentDAO.deleteStudent(s1.getId());
    }
}
