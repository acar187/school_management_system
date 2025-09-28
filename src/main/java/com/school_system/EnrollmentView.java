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


    @Override
    public String toString() {
        return "EnrollmentView{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
