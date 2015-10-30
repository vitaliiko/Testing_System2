package usersClasses;

import java.util.Set;

public interface UserDAO {

    <T extends User> Set<T> getAllUsers();

    User getUser(int index);

    void updateUser(User user);

    void deleteUser(User user);

    int addUser(User user);

    int getUserIndex(User user);
}
