package com.school_system.model;

import java.time.LocalDate;

public class Grade {
    private int id;
    private int studentId;
    private int courseId;
    private double gradeValue;
    private LocalDate dateAssigned;

    public Grade(int studentId, int courseId, double gradeValue) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeValue = gradeValue;
        this.dateAssigned = LocalDate.now();
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public double getGradeValue() {
        return gradeValue;
    }
    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }
    public LocalDate getDateAssigned() {
        return dateAssigned;
    }
    public void setDateAssigned(LocalDate dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    @Override
    public String toString() {
        return "Grade [id=" + id + ", studentId=" + studentId + ", courseId=" + courseId + ", gradeValue=" + gradeValue
                + ", dateAssigned=" + dateAssigned + "]";
    }
}
