package com.school_system;

public class App {
    public static void main(String[] args) {
        SchoolManager schoolManager = new SchoolManager();

        Student student1 = new Student("Alice", "alice@gmail.com","M12345");
        Student student2 = new Student("Bob", "bob@gmail.com", "M67890");

        schoolManager.addStudent(student1);
        schoolManager.addStudent(student2);

        Teacher teacher1 = new Teacher( "Dr. Smith", "smith@gmail.com", "T98765");
        schoolManager.addTeacher(teacher1);

        Course course1 = new Course("Mathematik");
        schoolManager.addCourse(course1);

        schoolManager.assignStudentToCourse(student1.getId(), course1.getId());
        schoolManager.assignStudentToCourse(student2.getId(), course1.getId());
        schoolManager.assignTeacherToCourse(teacher1.getId(), course1.getId());

        
        // Ausgabe
        System.out.println("\n📚 Kurse:");
        schoolManager.getCourses().forEach(c -> {
            System.out.println(c);
            c.getStudents().forEach(st -> System.out.println("  - " + st));
        });

        System.out.println("\nKursdetails:");
        for (Course course : schoolManager.getCourses()) {
            System.out.println(course);
            for (Student student : course.getStudents()) {
                System.out.println(" - " + student); 
                
            }
        }
    }
}
