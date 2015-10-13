package usersClasses;

import java.io.Serializable;
import java.util.List;

public interface StudentDAO extends Serializable {

    List<Student> getAllStudents();

    Student getStudent(int index);

    void updateStudent(Student student);

    void deleteStudent(Student student);

    int addStudent(Student student);

    int getStudentIndex(Student student);
}
