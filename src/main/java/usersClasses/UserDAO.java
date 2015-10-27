package usersClasses;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    User getUser(int index);

    void updateUser(User user);

    void deleteUser(User user);

    int addUser(User user);

    int getUserIndex(User user);
}
