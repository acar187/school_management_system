package com.school_system.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    public void setId(int id) {
        this.id = id;
        
    }

    
    
    
}
