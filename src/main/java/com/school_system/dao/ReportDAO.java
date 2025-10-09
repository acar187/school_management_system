package com.school_system.dao;

import java.sql.Connection;

public class ReportDAO {
    private final Connection conn;

    public ReportDAO(Connection conn) {
        this.conn = conn;
    }   

    public void printStudentsPerCourse(){
        String sql = """
            SELECT c.name AS course_name, COUNT(e.student_id) AS student_count
            FROM courses c
            LEFT JOIN enrollments e ON c.id = e.course_id
            GROUP BY c.id
            ORDER BY student_count DESC
        """;
        try (var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            System.out.println("📊 Anzahl der Studenten pro Kurs:");
            while (rs.next()) {
                String courseName = rs.getString("course_name");
                int studentCount = rs.getInt("student_count");
                System.out.printf("Kurs: %s | Anzahl der Studenten: %d%n", courseName, studentCount);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Fehler beim Generieren des Berichts: " + e.getMessage());
        }
    }
    
    
}
