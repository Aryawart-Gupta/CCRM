package edu.ccrm.domain;

public abstract class Person {
    private static int idCounter = 1;
    protected int id;
    protected String fullName;
    protected String email;

    public Person(String fullName, String email) {
        this.id = idCounter++;
        this.fullName = fullName;
        this.email = email;
    }

       public abstract String getProfileDetails();

   
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName;
    }
}