package com.school_system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course {
    //private static int counter = 100;  
    private int id;
    
    private String name;
    //private List<Student> studentsList = new ArrayList<>();
    private Set<Student> studentsSet = new HashSet<>();

    private Teacher teacher; // Ein Kurs kann einen Lehrer haben

    public Course(String name) {
        //this.id = counter++;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
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
    public Set<Student> getStudents() {
        return studentsSet;
    }
    public void addStudent(Student student) {
        // if (!studentsList.contains(student)) {
        //     studentsList.add(student);
        studentsSet.add(student);
    }
    
    public void removeStudent(Student student) {
        studentsSet.remove(student);
    }

    // Verstehe ich nicht
    // public List<Student> listStudents() {
    //     return new ArrayList<>(students); // defensive copy
    // }


    @Override
    public String toString() {
        String t = (teacher != null) ? "| Teacher: " + teacher.getName() : "";
        return "Course [id=" + id + ", name=" + name + ", students=" + studentsSet.size() + "]" + t;
    }

    
    
}
