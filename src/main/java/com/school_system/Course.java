package com.school_system;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private static int counter = 100;  
    private final int id;
    private String name;
    private List<Student> studentsList = new ArrayList<>();
    private Teacher teacher; // Ein Kurs kann einen Lehrer haben

    public Course(String name) {
        this.id = counter++;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public List<Student> getStudents() {
        return studentsList;
    }
    public void addStudent(Student student) {
        if (!studentsList.contains(student)) {
            studentsList.add(student);
        }
    }
    public void removeStudent(Student student) {
        studentsList.remove(student);
    }

    // Verstehe ich nicht
    // public List<Student> listStudents() {
    //     return new ArrayList<>(students); // defensive copy
    // }


    @Override
    public String toString() {
        String t = (teacher != null) ? "| Teacher: " + teacher.getName() : "";
        return "Course [id=" + id + ", name=" + name + ", students=" + studentsList.size() + "]" + t;
    }
    
}
