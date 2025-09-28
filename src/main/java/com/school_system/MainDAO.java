package com.school_system;

import java.util.Scanner;

public class MainDAO {  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        boolean running = true;
        while (running) {
            System.out.println("\n📚 Schulverwaltung");
            System.out.println("1. Student hinzufügen");
            System.out.println("2. Alle Studenten anzeigen");
            System.out.println("3. Kurs hinzufügen");
            System.out.println("4. Alle Kurse anzeigen");
            System.out.println("5. Student in Kurs einschreiben");
            System.out.println("6. Alle Einschreibungen anzeigen");
            System.out.println("7. Alle Kurse eines Studenten anzeigen");
            System.out.println("8. Alle Studenten eines Kurses anzeigen");
            System.out.println("9. Student aktualisieren");
            System.out.println("10. Student löschen");
            System.out.println("11. Kurs aktualisieren");
            System.out.println("12. Kurs löschen");
            System.out.println("0. Beenden");
            System.out.print("👉 Auswahl: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Zeilenumbruch einlesen

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Matrikelnummer: ");
                    String matriculation = scanner.nextLine();
                    Student student = new Student(name, email, matriculation);
                    studentDAO.addStudent(student);
                    System.out.println("✅ Student hinzugefügt!");
                }
                case 2 -> {
                    System.out.println("📋 Alle Studenten:");
                    for (Student s : studentDAO.getAllStudents()) {
                        System.out.println(s);
                        }
                    }
                case 3 -> {
                    System.out.print("Kursname: ");
                    String courseName = scanner.nextLine();
                    Course course = new Course(courseName);
                    courseDAO.addCourse(course);
                    System.out.println("✅ Kurs hinzugefügt!");
                }
                case 4 -> {
                    System.out.println("📋 Alle Kurse:");
                    for (Course c : courseDAO.getAllCourses()) {
                        System.out.println(c);
                    }
                }
                case 5 -> {
                    System.out.print("Studenten-ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Kurs-ID: ");
                    int courseId = scanner.nextInt();
                    Enrollment enrollment = new Enrollment(studentId, courseId);
                    enrollmentDAO.addEnrollment(enrollment);
                    System.out.println("✅ Student in Kurs eingeschrieben!");
                }
                case 6 -> {
                    System.out.println("📋 Alle Einschreibungen:");
                    for (Enrollment e : enrollmentDAO.getAllEnrollments()) {
                        System.out.println(e);
                    }
                }
                case 7 -> {
                    System.out.print("Studenten-ID: ");
                    int studentId = scanner.nextInt();
                    System.out.println("📋 Kurse des Studenten:");
                    for (String courseName : enrollmentDAO.getCoursesByStudentId(studentId)) {
                        System.out.println(courseName);
                    }
                }
                case 8 -> {
                    System.out.print("Kurs-ID: ");
                    int courseId = scanner.nextInt();
                    System.out.println("📋 Studenten im Kurs:");
                    for (String studentName : enrollmentDAO.getStudentsByCourseId(courseId)) {
                        System.out.println(studentName);
                    }
                }
                case 9 -> {
                    System.out.print("Studenten-ID zum Aktualisieren: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Zeilenumbruch einlesen
                    System.out.print("Neuer Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Neue Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Neue Matrikelnummer: ");
                    String newMatriculation = scanner.nextLine();
                    Student updatedStudent = new Student(newName, newEmail, newMatriculation);
                    updatedStudent.setId(studentId);
                    studentDAO.updateStudent(updatedStudent);
                }
                case 10 -> {
                    System.out.print("Studenten-ID zum löschen: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Zeilenumbruch einlesen
                    studentDAO.deleteStudent(studentId);
                }
                case 11 -> {
                    System.out.print("Kurs-ID zum Aktualisieren: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine(); // Zeilenumbruch einlesen
                    System.out.print("Neuer Kursname: ");
                    String newCourseName = scanner.nextLine();
                    Course updatedCourse = new Course(newCourseName);
                    updatedCourse.setId(courseId);
                    courseDAO.updateCourse(updatedCourse);
                }
                case 12 -> {
                    System.out.print("Kurs-ID zum löschen: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine(); // Zeilenumbruch einlesen
                    courseDAO.deleteCourse(courseId);
                }
                case 0 -> {
                    running = false;
                    System.out.println("👋 Programm beendet.");
                }
                default -> {
                    System.out.println("❌ Ungültige Auswahl. Bitte erneut versuchen.");
                }
            }
            
        }
        scanner.close();
    }
        
}
