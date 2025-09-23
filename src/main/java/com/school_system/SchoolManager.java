package com.school_system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
}
