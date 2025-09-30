package com.school_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class StudentDAO {
    // This class will handle database operations related to students
    // For example, methods to add, update, delete, and retrieve student records

    //CREATE
    public void addStudent(Student student) {
        // Implementation for adding a student to the database
        String sql = "INSERT INTO students (name, email, matriculation) VALUES (?, ?, ?)";
        // Use PreparedStatement to execute the SQL with student data
        Connection conn = DatabaseConnector.connect();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getMatriculationNumber());
            pstmt.executeUpdate();

            // automatisch vergebene ID holen
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                student.setId(keys.getInt(1));
            }
            System.out.println("✅ Student hinzugefügt: " + student);
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Hinzufügen des Studenten: " + e.getMessage());
        }

        
    }
    //READ
    public List<Student> getAllStudents() {
        
        // Implementation for retrieving all students from the database
        List<Student> studentsList = new java.util.ArrayList<>();
        String sql = "SELECT id, name, email, matriculation FROM students";
        
        try (Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                Student student = new Student(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("matriculation")
                );
                student.setId(rs.getInt("id"));
                studentsList.add(student);
            }
        }  catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Abrufen der Studenten: " + e.getMessage());
        }
        return studentsList;
    }

    //UPDATE
    public void updateStudent(Student student) {
        // Implementation for updating a student's information in the database
        String sql = "UPDATE students SET name = ?, email = ?, matriculation = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getMatriculationNumber());
            pstmt.setInt(4, student.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Student aktualisiert: " + student);
            } else {
                System.out.println("⚠️ Kein Student mit der ID " + student.getId() + " gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Aktualisieren des Studenten: " + e.getMessage());
        }  
    }

    public void deleteStudent(int studentId) {
        // Implementation for deleting a student from the database
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Student mit ID " + studentId + " gelöscht.");
            } else {
                System.out.println("⚠️ Kein Student mit der ID " + studentId + " gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Löschen des Studenten: " + e.getMessage());
        }
    }

    public boolean studentExists(int studentId) {
        String sql = "SELECT id FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Gibt true zurück, wenn ein Datensatz gefunden wurde
        } catch (SQLException e) {
            System.out.println("Fehler beim Überprüfen der Existenz des Studenten: " + e.getMessage());
            return false;
        }
    }
}
