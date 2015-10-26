package usersClasses;

public class Teacher extends User {

    private String telephoneNum;
    private String mailAddress;

    public Teacher(String surname, String name, String secondName, char[] password) {
        super(surname, name, secondName, password);
    }

    public Teacher(String surname, String name, String secondName, String password) {
        super(surname, name, secondName, password);
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
}
