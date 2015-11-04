package usersClasses;

public class Student extends User {

    private String groupName;

    public Student(String surname, String name, String secondName, StudentsGroup group) {
        super(surname, name, secondName, "");
        this.groupName = group.getName();
        group.addUser(this);
    }

    public String getGroupName() {
        return groupName;
    }
}
