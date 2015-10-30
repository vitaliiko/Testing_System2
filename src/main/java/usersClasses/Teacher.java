package usersClasses;

public class Teacher extends User {


    public Teacher(String surname, String name, String secondName, char[] password) {
        super(surname, name, secondName, password);
    }

    public Teacher(String surname, String name, String secondName, String password) {
        super(surname, name, secondName, password);
    }
}
