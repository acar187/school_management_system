package com.school_system.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.school_system.model.Student;

public class StudentExportService {
    
    public void exportStudentDataToCSV(List<Student> studentList, String filePath) {
        
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID, Name, Email, Matriculation Number\n");

            for (Student student : studentList) {
                writer.append(student.getId() + "," + student.getName() + "," + student.getEmail() + "," + student.getMatriculationNumber());
                writer.append("\n");
            }

            writer.flush();
            System.out.println("Student data exported successfully to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
