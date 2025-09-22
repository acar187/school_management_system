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
        Student s = findStudentById(studentId);
        Course c = findCourseById(courseId);
        if (s != null && c != null) {
            c.addStudent(s);
            System.out.println("✅ " + s.getName() + " wurde in Kurs " + c.getName() + " eingeschrieben.");
        } else {
            System.out.println("❌ Student oder Kurs nicht gefunden.");
        }
    }

    public void assignTeacherToCourse(int teacherId, int courseId) {
        Teacher t = findTeacherById(teacherId);
        Course c = findCourseById(courseId);
        if (t != null && c != null) {
            c.setTeacher(t);
            System.out.println("✅ " + t.getName() + " wurde als Lehrkraft für " + c.getName() + " gesetzt.");
        } else {
            System.out.println("❌ Lehrer oder Kurs nicht gefunden.");
        }
    }

     public Student findStudentById(int id) {
        return studentsList.stream()
                           .filter(s -> s.getId() == id)
                           .findFirst()
                           .orElse(null);
    }

    public Course findCourseById(int id) {
        return coursesMap.get(id);
    }

    public Teacher findTeacherById(int id) {
        return teachersList.stream()
                           .filter(t -> t.getId() == id)
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
}
