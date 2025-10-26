package com.school_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.school_system.model.Enrollment;
import com.school_system.model.EnrollmentView;
import com.school_system.util.DatabaseConnector;

public class EnrollmentDAO {
    //CREATE
    public void addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, enrollment.getStudentId());
            pstmt.setInt(2, enrollment.getCourseId());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    enrollment.setId(keys.getInt(1));
                }

            System.out.println("Einschreibung hinzugefügt: " + enrollment);
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen der Einschreibung: " + e.getMessage());
        }
    }

    //READ
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollmentsList = new java.util.ArrayList<>();
        String sql = "SELECT id, student_id, course_id FROM enrollments";

        try (Connection conn = DatabaseConnector.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Enrollment enrollment = new Enrollment(rs.getInt("student_id"), rs.getInt("course_id"));
                enrollment.setId(rs.getInt("id"));
                enrollmentsList.add(enrollment);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Einschreibungen: " + e.getMessage());
        }
        return enrollmentsList;
    }
    //READ EnrollmentsByName
    public List<EnrollmentView> getAllEnrollmentViews(){
        List<EnrollmentView> enrollmentViewsList = new java.util.ArrayList<>();
        String sql = "SELECT e.id, s.name AS student_name, c.name AS course_name " +
                     "FROM enrollments e " +
                     "JOIN students s ON e.student_id = s.id " +
                     "JOIN courses c ON e.course_id = c.id";

        try (Connection conn = DatabaseConnector.connect();
             java.sql.Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EnrollmentView enrollmentView = new EnrollmentView(rs.getInt("id"),
                                                                   rs.getString("student_name"),
                                                                   rs.getString("course_name"));
                enrollmentViewsList.add(enrollmentView);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Einschreibungsansichten: " + e.getMessage());
        }
        return enrollmentViewsList;
    }

    //alle Kurse eines Studenten
    public List<String> getCoursesByStudentId(int studentId) {
        List<String> courseNames = new java.util.ArrayList<>();
        String sql = "SELECT c.name " +
                     "FROM enrollments e " +
                     "JOIN courses c ON e.course_id = c.id " +
                     "WHERE e.student_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                courseNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Kurse für Student mit ID " + studentId + ": " + e.getMessage());
        }
        return courseNames;
    }

    //alle Studenten eines Kurses
    public List<String> getStudentsByCourseId(int courseId) {
        List<String> studentNames = new java.util.ArrayList<>();
        // String sql = "SELECT s.name " +
        //              "FROM enrollments e " +
        //              "JOIN students s ON e.student_id = s.id " +
        //              "WHERE e.course_id = ?";

        String sql = "SELECT students.name "+
                     "FROM students " +
                     "JOIN enrollments ON enrollments.student_id = students.id " +
                     "where enrollments.course_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                studentNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Studenten für Kurs mit ID " + courseId + ": " + e.getMessage());
        }
        return studentNames;
    }

    //DELETE
    public void deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, enrollmentId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Einschreibung mit ID " + enrollmentId + " gelöscht.");
            } else {
                System.out.println("Keine Einschreibung mit ID " + enrollmentId + " gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen der Einschreibung: " + e.getMessage());
        }
    }

    // Entfernt einen Kurs von einem Schüler
    public void removeEnrollment(int studentId, int courseId) {
        String sql = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
