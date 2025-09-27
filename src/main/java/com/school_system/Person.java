package com.school_system;

public abstract class Person {
    private static int counter = 1;
    protected int id;
    private String name;
    private String email;

    public Person(String name, String email) {
        this.id = counter++;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", email=" + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person p = (Person) o;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
