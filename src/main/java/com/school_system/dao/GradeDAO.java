package com.school_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.school_system.model.Grade;
import com.school_system.util.DatabaseConnector;

public class GradeDAO {
    private final Connection conn;

    public GradeDAO() {
        this.conn = DatabaseConnector.connect();
    }

    public void addGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, course_id, grade, date_assigned) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setDouble(3, grade.getGradeValue());
            pstmt.setDate(4, java.sql.Date.valueOf(grade.getDateAssigned()));
            pstmt.executeUpdate();

            // automatisch vergebene ID holen
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                grade.setId(keys.getInt(1));
            }
            System.out.println("✅ Note hinzugefügt: " + grade);
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Hinzufügen der Note: " + e.getMessage());
        }
    }

    public List<Grade> getAllGrades() {
        // Implementation for retrieving all grades from the database
        List<Grade> gradesList = new java.util.ArrayList<>();
        String sql = "SELECT id, student_id, course_id, grade, date_assigned FROM grades";

        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grade grade = new Grade(
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getDouble("grade")
                );
                grade.setId(rs.getInt("id"));
                grade.setDateAssigned(rs.getDate("date_assigned").toLocalDate());
                gradesList.add(grade);
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Abrufen der Noten: " + e.getMessage());
        }
        return gradesList;
    }  

    public void updateGrade(Grade grade){
        String sql = "UPDATE grades SET grade = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, grade.getGradeValue());
            pstmt.setInt(2, grade.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Note aktualisiert: " + grade);
            } else {
                System.out.println("⚠️ Keine Note gefunden mit ID: " + grade.getId());
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Aktualisieren der Note: " + e.getMessage());
        }
    }

}


