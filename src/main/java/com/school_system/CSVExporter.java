package com.school_system;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVExporter {
    
    public static void exportStudentsToCSV(String filePath) {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> students = studentDAO.getAllStudents();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID,Name,Email,Matrikelnummer");
            for (Student s : students) {
                writer.printf("%d,%s,%s,%s%n", s.getId(), s.getName(), s.getEmail(), s.getMatriculationNumber());
            }
            System.out.println("✅ Studenten wurden erfolgreich nach " + filePath + " exportiert.");
        } catch (FileNotFoundException e) {
            System.out.println("❌ Datei nicht gefunden: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Fehler beim Exportieren der Studenten: " + e.getMessage());
        }
    }

    public static void exportCoursesToCSV(String filePath) {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = courseDAO.getAllCourses();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID,Kursname");
            for (Course c : courses) {
                writer.printf("%d,%s%n", c.getId(), c.getName());
            }
            System.out.println("✅ Kurse wurden erfolgreich nach " + filePath + " exportiert.");
        } catch (FileNotFoundException e) {
            System.out.println("❌ Datei nicht gefunden: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Fehler beim Exportieren der Kurse: " + e.getMessage());
        }
    }

    public static void exportEnrollmentsToCSV(String filePath) {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<EnrollmentView> enrollments = enrollmentDAO.getAllEnrollmentViews();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID,StudentID,KursID");
            for (EnrollmentView e : enrollments) {
                writer.printf("%d,%s,%s%n", e.getId(), e.getStudentName(), e.getCourseName());
            }
            System.out.println("✅ Einschreibungen wurden erfolgreich nach " + filePath + " exportiert.");
        } catch (FileNotFoundException e) {
            System.out.println("❌ Datei nicht gefunden: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Fehler beim Exportieren der Einschreibungen: " + e.getMessage());
        }
    }

    public static void exportEnrollments2(List<EnrollmentView> enrollments, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("student_name,course_name\n");
            for (EnrollmentView e : enrollments) {
                writer.write(e + "\n"); // Zeilenweise
            }
            System.out.println("✅ Einschreibungen exportiert nach " + filename);
        } catch (IOException e) {
            System.out.println("⚠️ Fehler beim Exportieren: " + e.getMessage());
        }
    }
}
