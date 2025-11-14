package com.school_system.main;

import java.util.List;

import com.school_system.dao.StudentDAO;
import com.school_system.model.Student;
import com.school_system.service.StudentExportService;

public class MainStudentExportService {

    public static void main(String[] args) {
        System.out.println("This is the main class for Student Export Service.");
        StudentExportService exportService = new StudentExportService();
        StudentDAO studentDAO = new StudentDAO();
        
        List<Student> studentList = studentDAO.getAllStudents();;
         // Example usage (in a real scenario, you would fetch student data from your system)
         exportService.exportStudentDataToCSV(studentList, "students144.csv");   

        
    }
    
}
