package com.school_system;

public class EnrollmentView {

    private int id;
    private String studentName;
    private String courseName;

    public EnrollmentView(int id, String studentName, String courseName) {
        this.id = id;
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return "EnrollmentView{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
