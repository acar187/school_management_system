package com.school_system.main;

import java.util.List;
import java.util.Scanner;

import com.school_system.model.Course;
import com.school_system.model.SchoolManager;
import com.school_system.model.Student;
import com.school_system.model.Teacher;

public class Main {
    public static void main(String[] args) {
        SchoolManager manager = new SchoolManager();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Schulverwaltung ===");
            System.out.println("1: Student hinzufügen");
            System.out.println("2: Lehrer hinzufügen");
            System.out.println("3: Kurs hinzufügen");
            System.out.println("4: Student zu Kurs zuordnen");
            System.out.println("5: Lehrer zu Kurs zuordnen");
            System.out.println("6: Alle Kurse anzeigen");
            System.out.println("7: Studenten eines Kurses anzeigen");
            System.out.println("8: Student nach Name suchen");
            System.out.println("9: Kurs nach Name suchen");
            System.out.println("10: Studenten alphabetisch anzeigen");
            System.out.println("11: Kurse alphabetisch anzeigen");
            System.out.println("12: Studenten eines Kurses anzeigen");
            System.out.println("13: Kurse eines Studenten anzeigen");
            System.out.println("14: Kurse eines Lehrers anzeigen");
            System.out.println("15: Anzahl Studenten in einem Kurs");
            System.out.println("16: Anzahl Kurse eines Lehrers");
            System.out.println("17: Kurs mit den meisten Studenten");
            System.out.println("18: Studenten nach CSV exportieren");
            System.out.println("19: Studenten aus CSV importieren");
            System.out.println("20: Schulreport exportieren");

            System.out.println("0: Beenden");
            System.out.print("Eingabe: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Zeilenumbruch einlesen

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Matrikelnummer: ");
                    String matriculation = sc.nextLine();
                    manager.addStudent(new Student(name, email, matriculation));
                    System.out.println("✅ Student hinzugefügt!");
                }
                case 2 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Fach: ");
                    String subject = sc.nextLine();
                    manager.addTeacher(new Teacher(name, email, subject));
                    System.out.println("✅ Lehrer hinzugefügt!");
                }
                case 3 -> {
                    System.out.print("Kursname: ");
                    String name = sc.nextLine();
                    manager.addCourse(new Course(name));
                    System.out.println("✅ Kurs hinzugefügt!");
                }
                case 4 -> {
                    System.out.print("Student-ID: ");
                    int sId = sc.nextInt();
                    System.out.print("Kurs-ID: ");
                    int cId = sc.nextInt();
                    sc.nextLine();
                    manager.assignStudentToCourse(sId, cId);
                }
                case 5 -> {
                    System.out.print("Lehrer-ID: ");
                    int tId = sc.nextInt();
                    System.out.print("Kurs-ID: ");
                    int cId = sc.nextInt();
                    sc.nextLine();
                    manager.assignTeacherToCourse(tId, cId);
                }
                case 6 -> {
                    System.out.println("📚 Kurse:");
                    manager.getCourses().forEach(System.out::println);
                }
                case 7 -> {
                    System.out.print("Kurs-ID: ");
                    int cId = sc.nextInt();
                    sc.nextLine();
                    Course c = manager.findCourseById(cId);
                    if (c != null) {
                        System.out.println("👥 Studenten im Kurs " + c.getName() + ":");
                        c.getStudents().forEach(st -> System.out.println("  - " + st));
                    if (c.getTeacher() != null)
                        System.out.println("Lehrer: " + c.getTeacher());
                    } else {
                        System.out.println("⚠️ Kurs nicht gefunden!");
                    }
                }
                case 8 -> {
                    System.out.print("Studentenname: ");
                    String name = sc.nextLine();
                    Student s = manager.findStudentByName(name);
                    if (s != null) {
                        System.out.println("🔍 Gefundener Student: " + s);
                    } else {
                        System.out.println("⚠️ Student nicht gefunden!");
                    }
                }
                case 9 -> {
                    System.out.print("Kursname: ");
                    String name = sc.nextLine();
                    Course c = manager.findCourseByName(name);
                    if (c != null) {
                        System.out.println("🔍 Gefundener Kurs: " + c);
                    } else {
                        System.out.println("⚠️ Kurs nicht gefunden!");
                    }
                }
                case 10 -> {
                    System.out.println("👥 Studenten alphabetisch:");
                    manager.getStudentsSortedByName().forEach(System.out::println);
                }
                case 11 -> {
                    System.out.println("📚 Kurse alphabetisch:");
                    manager.getCoursesSortedByName().forEach(System.out::println);
                }
                case 12 -> {
                    System.out.print("Kurs-ID: ");
                    int cId = Integer.parseInt(sc.nextLine());
                    List<Student> list = manager.getStudentsInCourse(cId);
                    System.out.println("📋 Studenten im Kurs:");
                    list.forEach(System.out::println);
                }
                case 13 -> {
                    System.out.print("Studenten-ID: ");
                    int sId = Integer.parseInt(sc.nextLine());
                    List<Course> list = manager.getCoursesForStudent(sId);
                    System.out.println("📋 Kurse des Studenten:");
                    list.forEach(System.out::println);
                }
                case 14 -> {
                    System.out.print("Lehrer-ID: ");
                    int tId = Integer.parseInt(sc.nextLine());
                    List<Course> list = manager.getCoursesForTeacher(tId);
                    System.out.println("📋 Kurse des Lehrers:");
                    list.forEach(System.out::println);
                }
                case 15 -> {
                    System.out.print("Kurs-ID: ");
                    int cId = Integer.parseInt(sc.nextLine());
                    int count = manager.countStudentsInCourse(cId);
                    System.out.println("📊 Anzahl der Studenten im Kurs: " + count);
                }
                case 16 -> {
                    System.out.print("Lehrer-ID: ");
                    int tId = Integer.parseInt(sc.nextLine());
                    int count = manager.countCoursesOfTeacher(tId);
                    System.out.println("📊 Anzahl der Kurse des Lehrers: " + count);
                }
                case 17 -> { 
                    Course c = manager.getCourseWithMostStudents();
                    if (c != null) {
                        System.out.println("🏆 Kurs mit den meisten Studenten: " + c + " (Anzahl: " + c.getStudents().size() + ")");
                    } else {
                        System.out.println("⚠️ Keine Kurse verfügbar!");
                    }
                }
                case 18 -> {
                    System.out.print("Dateiname: ");
                    String filename = sc.nextLine();
                    manager.exportStudentsToCSV(filename);
                }
                case 19 -> {
                    System.out.print("Dateiname: ");
                    String filename = sc.nextLine();
                    manager.importStudentsFromCSV(filename);
                }
                case 20 -> {
                    System.out.print("Dateiname: ");
                    String filename = sc.nextLine();
                    manager.exportSchoolReport(filename);
                }


                case 0 -> {
                    running = false;
                    System.out.println("Programm beendet.");
                }
                default -> System.out.println("⚠️ Ungültige Eingabe!");
            }
        }
        sc.close();
    }
}
