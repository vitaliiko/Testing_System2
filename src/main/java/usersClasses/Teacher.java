package usersClasses;

import java.io.Serializable;
import java.util.Arrays;

public class Teacher extends User {

    private String telephoneNum;
    private String mailAddress;

    public Teacher(String surname, String name, String secondName, char[] password) {
        super(surname, name, secondName, password);
    }

    public Teacher(String surname, String name, String secondName, String password) {
        super(surname, name, secondName, password.toCharArray());
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
        return this.getUserName().equals(userName) && Arrays.equals(this.getPassword(), password);
    }
}
