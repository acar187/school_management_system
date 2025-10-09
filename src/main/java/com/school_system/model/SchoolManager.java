package com.school_system.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.school_system.exception.CourseNotFoundException;
import com.school_system.exception.StudentNotFoundException;

public class SchoolManager {
    //private List<Course> coursesList = new ArrayList<>();
    private Map<Integer, Course> coursesMap = new HashMap<>();

    private List<Student> studentsList = new ArrayList<>();
    private List<Teacher> teachersList = new ArrayList<>();

    // Counter für IDs
    private int nextStudentId = 1;
    private int nextTeacherId = 1;
    private int nextCourseId = 1;

    public void addCourse(Course course) {
        course.setId(nextCourseId++);
        coursesMap.put(course.getId(), course);
    }

    public void addStudent(Student student) {
        studentsList.add(student);
    }
    public void addTeacher(Teacher teacher) {
        teachersList.add(teacher);
    }

    // public void assignStudentToCourse(Student student, Course course) {
    //     if(studentsList.contains(student) && coursesList.contains(course)) {
    //         course.addStudent(student);
    //     }
    // }

    // Zuweisung
    public void assignStudentToCourse(int studentId, int courseId) {
        try {
            Student s = findStudentById(studentId);
            Course c = findCourseById(courseId);
            c.addStudent(s);
            System.out.println("✅ " + s.getName() + " wurde in Kurs " + c.getName() + " eingeschrieben.");
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    public void assignTeacherToCourse(int teacherId, int courseId) {
        try {
            Teacher t = findTeacherById(teacherId);
            Course c = findCourseById(courseId);
            c.setTeacher(t);
            System.out.println("✅ " + t.getName() + " wurde als Lehrkraft für " + c.getName() + " gesetzt.");
        } catch (CourseNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

     public Student findStudentById(int id) {
        Student s = studentsList.stream()
                           .filter(st -> st.getId() == id)
                           .findFirst()
                           .orElse(null);
        if (s == null) {
            throw new StudentNotFoundException(id);
        }
        return s;
    }

    public Course findCourseById(int id) {
        Course c = coursesMap.get(id);
        if (c == null) {
            throw new CourseNotFoundException(id);
        }
        return c;   
    }

    public Teacher findTeacherById(int id) {
        return teachersList.stream()
                           .filter(t -> t.getId() == id)
                           .findFirst()
                           .orElse(null);
    }

    public Student findStudentByName(String name) {
        return studentsList.stream()
                           .filter(s -> s.getName().equalsIgnoreCase(name))
                           .findFirst()
                           .orElse(null);
    }

    public Course findCourseByName(String name) {
        return coursesMap.values().stream()
                         .filter(c -> c.getName().equalsIgnoreCase(name))
                         .findFirst()
                         .orElse(null);
    }
    
    public Collection<Course> getCourses() {
        return coursesMap.values();
    }

    public List<Student> getStudents() {
        return studentsList;
    }

    public List<Teacher> getTeachersList() {
        return teachersList;
    }

    public List<Student> getStudentsSortedByName() {
        return studentsList.stream()
                           .sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()))
                           .toList();
    }

    public List<Course> getCoursesSortedByName() {
        return coursesMap.values().stream()
                         .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                         .toList();
    }

    public List<Student> getStudentsInCourse(int courseId) {
        Course c = findCourseById(courseId);
        return new ArrayList<>(c.getStudents()) ;   
    }
    
    public List<Course> getCoursesForStudent(int studentId) {
        Student s = findStudentById(studentId);
        List<Course> enrolledCourses = new ArrayList<>();
        for (Course c : coursesMap.values()) {
            if (c.getStudents().contains(s)) {
                enrolledCourses.add(c);
            }
        }
        return enrolledCourses;
    }

    public List<Course> getCoursesForTeacher(int teacherId) {
        Teacher t = findTeacherById(teacherId);
        List<Course> teachingCourses = new ArrayList<>();
        for (Course c : coursesMap.values()) {
            if (t.equals(c.getTeacher())) {
                teachingCourses.add(c);
            }
        }
        return teachingCourses;
    }

    public int amountOfStudents() {
        return studentsList.size();
    }
    public int amountOfCourses() {
        return coursesMap.size();
    }
    public int amountOfTeachers() {
        return teachersList.size();
    }

    public int countStudentsInCourse(int courseId) {
        // Course c = findCourseById(courseId);
        // return c.getStudents().size();
        // or
        return getStudentsInCourse(courseId).size();
    }

    public int countCoursesOfTeacher(int teacherId) {
        // Teacher t = findTeacherById(teacherId);
        // int count = 0;
        // for (Course c : coursesMap.values()) {
        //     if (t.equals(c.getTeacher())) {
        //         count++;
        //     }
        // }
        // return count;
        // or
        return getCoursesForTeacher(teacherId).size();
    }

    public Course getCourseWithMostStudents() {
        return coursesMap.values().stream()
                  .max(Comparator.comparingInt(c -> c.getStudents().size()))
                  .orElse(null);
    }

    public void exportStudentsToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) 
        { writer.write("id,name,email,matriculationNumber\n");
            for (Student s : studentsList) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getEmail() + "," + s.getMatriculationNumber() + "\n");
            }
            System.out.println("✅ Studenten wurden nach " + filename + " exportiert.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("❌ Fehler beim Exportieren der Studenten: " + e.getMessage());
        }
    }

    public void importStudentsFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String matriculationNumber = parts[3];
                    Student s = new Student(name, email, matriculationNumber);
                    studentsList.add(s); 
                }
            }
            System.out.println("✅ Studenten wurden aus " + filename + " importiert.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("❌ Fehler beim Importieren der Studenten: " + e.getMessage());
        }
    }

    public void exportSchoolReport(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("============ Schulbericht ============");

            for (Course c : coursesMap.values()) {
                writer.println("Kurs: " + c.getName() + " (ID: " + c.getId() + ")");
                Teacher t = c.getTeacher();
                String teacherInfo = (t != null) ? t.getName() + " (ID: " + t.getId() + ")" : "Kein Lehrer zugewiesen";
                writer.println("Lehrer: " + teacherInfo);
                writer.println("Anzahl der Studenten: " + c.getStudents().size());
                writer.println("Studenten:");
                for (Student s : c.getStudents()) {
                    writer.println(" - " + s.getName() + " (ID: " + s.getId() + ", Email: " + s.getEmail() + ", Matrikelnummer: " + s.getMatriculationNumber() + ")");
                }
                writer.println("-------------------------------------");
            }
            System.out.println("✅ Schulbericht wurde nach " + filename + " exportiert.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("❌ Fehler beim Exportieren des Schulberichts: " + e.getMessage());
        }
        
    }
}
        