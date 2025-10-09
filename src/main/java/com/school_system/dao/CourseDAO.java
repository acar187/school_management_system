package com.school_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.school_system.model.Course;
import com.school_system.util.DatabaseConnector;

public class CourseDAO {
    // CRUD Methoden für Kurse
    ///CREATE
    public void addCourse(Course course) {
        // Implementierung zum Hinzufügen eines Kurses zur Datenbank
        String sql = "INSERT INTO courses (name) VALUES (?)";

        // Verwende PreparedStatement, um das SQL mit Kursdaten auszuführen
        try (Connection conn = DatabaseConnector.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, course.getName());
                pstmt.executeUpdate();
    
                // automatisch vergebene ID holen
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    course.setId(keys.getInt(1));
                }
                System.out.println("Kurs hinzugefügt: " + course);
            } catch (SQLException e) {
                System.out.println("Fehler beim Hinzufügen des Kurses: " + e.getMessage());
            }
    }

    //READ
    public List<Course> getAllCourses() {
        // Implementierung zum Abrufen aller Kurse aus der Datenbank
        List<Course> coursesList = new java.util.ArrayList<>();
        String sql = "SELECT id, name FROM courses";
        
        try (Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                Course course = new Course(rs.getString("name"));
                course.setId(rs.getInt("id"));
                coursesList.add(course);
            }
        }  catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Kurse: " + e.getMessage());
        }
        return coursesList;
    }
    //UPDATE
    public void updateCourse(Course course) {
        // Implementierung zum Aktualisieren der Kursinformationen in der Datenbank
        String sql = "UPDATE courses SET name = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getId());
            pstmt.executeUpdate();
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
            System.out.println("Kurs aktualisiert: " + course);
            } else {
                System.out.println("Kein Kurs gefunden mit ID: " + course.getId());
            }
        }
        catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren des Kurses: " + e.getMessage());
            }
        
    }
        //DELETE
    public void deleteCourse(int courseId) {
        // Implementierung zum Löschen eines Kurses aus der Datenbank
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Kurs gelöscht mit ID: " + courseId);
            } else {
                System.out.println("Kein Kurs gefunden mit ID: " + courseId);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen des Kurses: " + e.getMessage());
        }
    }

    public boolean courseExists(int courseId) {
        String sql = "SELECT id FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // true, wenn ein Datensatz gefunden wurde
        } catch (SQLException e) {
            System.out.println("Fehler beim Überprüfen des Kurses: " + e.getMessage());
            return false;
        }
    }
}   
