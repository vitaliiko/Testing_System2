package usersClasses;

import java.io.Serializable;

public class Student implements Serializable {

    private String surname;
    private String name;
    private String secondName;
    private String userName;
    private int indexInGroup;
    private char[] password;
    private StudentsGroup group;

    public Student(String surname, String name, String secondName, StudentsGroup group) {
        this.surname = surname;
        this.name = name;
        this.secondName = secondName;
        this.group = group;
        indexInGroup = this.group.addStudent(this);
        userName = surname + " " + name + " " + secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getGroupName() {
        return group.getName();
    }

    public void setGroup(StudentsGroup group) {
        this.group = group;
    }

    public int getIndexInGroup() {
        return indexInGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return userName.equals(student.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
