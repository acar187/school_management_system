package com.school_system;

public class Student extends Person {

    private String matriculationNumber; // z.B. "s1234567"

    public Student(String name, String email, String matriculationNumber) {
        super(name, email);
        this.matriculationNumber = matriculationNumber;
    }
    
    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() + ", matriculationNumber=" + matriculationNumber + '}';
    }

    
}
