package com.school_system;

import java.util.Scanner;

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
