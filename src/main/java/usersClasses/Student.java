package usersClasses;

public class Student extends User {

    private StudentsGroup group;

    public Student(String surname, String name, String secondName, StudentsGroup group) {
        super(surname, name, secondName, "");
        this.group = group;
        this.group.addStudent(this);
    }

    public String getGroupName() {
        return group.getName();
    }

    public void setGroup(StudentsGroup group) {
        this.group.deleteStudent(this);
        this.group = group;
        this.group.addStudent(this);
    }
}
