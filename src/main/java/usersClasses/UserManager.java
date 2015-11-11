package usersClasses;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserManager<T extends User> {

    Set<T> getUserSet();

    List<String> getUsersNameList();

    T getCurrentUser();

    void saveUserSet();

    void createUser(String surname, String name, String secondName, char[] password) throws IOException;

    void updateCurrentUserInfo(String surname, String name, String secondName, String telephone, String mail)
            throws IOException;

    boolean checkUsername(String username) throws IOException;

    void deleteUser(T user);

    void deleteCurrentUser();

    boolean authorizeUser(String userName, char[] password);
}
