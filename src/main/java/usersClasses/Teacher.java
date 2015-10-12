package usersClasses;

import java.io.Serializable;
import java.util.Arrays;

public class Teacher implements Serializable {

    private String name;
    private String surname;
    private String secondName;
    private String userName;
    private char[] password;
    private String telephoneNum;
    private String mailAddress;

    public Teacher(String surname, String name, String secondName, String password) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.password = password.toCharArray();
        userName = surname + " " + name + " " + secondName;
    }

    public Teacher(String surname, String name, String secondName, char[] password) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.password = password;
        userName = surname + " " + name + " " + secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public boolean isMatches(String userName, char[] password) {
        return this.userName.equals(userName) && Arrays.equals(this.password, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        return userName.equals(teacher.userName);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + secondName.hashCode();
        return result;
    }
}
