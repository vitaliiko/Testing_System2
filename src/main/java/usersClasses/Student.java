package usersClasses;

public class Student {

    private String surname;
    private String name;
    private String secondName;
    private String userName;
    private char[] password;
    private String group;

    public Student(String surname, String name, String secondName, String group) {
        this.surname = surname;
        this.name = name;
        this.secondName = secondName;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
