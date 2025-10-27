package com.school_system.main;

import com.school_system.dao.GradeDAO;
import com.school_system.model.Grade;

public class MainGrades {

    public static void main(String[] args) {
        // Grade g = new Grade(30, 7, 1.8);  // Student: Max Mustermann, Course: Mathematik
        Grade g2 = new Grade(15, 7, 3.3); // Student: Erkan, Course: Mathematik

        GradeDAO gradeDAO = new GradeDAO();
        //gradeDAO.addGrade(g);
        gradeDAO.addGrade(g2); 

        //gradeDAO.getAllGrades().forEach(System.out::println);
        
        for (Grade grade : gradeDAO.getAllGrades()) {
            System.out.println(grade);
        }

        g2.setGradeValue(2.0);

        gradeDAO.updateGrade(g2); // Update grade in Mathematics to 2.0

    }
   
}
