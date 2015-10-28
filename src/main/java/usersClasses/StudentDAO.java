package usersClasses;

import java.util.List;

public interface StudentDAO {

    List<Student> getAllStudents();

    Student getStudent(int index);

    void updateStudent(Student student);

    void deleteStudent(Student student);

    int addStudent(Student student);

    int getStudentIndex(Student student);
}
