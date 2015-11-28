package usersClasses;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public abstract class UserManager<T extends User> extends Validator {

    protected T currentUser;

    public abstract Set<T> getUserSet();

    public abstract List<String> getUsersNameList();

    public abstract T getCurrentUser();

    public abstract void saveUserSet();

    public abstract boolean checkUsername(String username) throws IOException;

    public abstract void deleteUser(T user);

    public abstract void deleteCurrentUser();

    public abstract boolean authorizeUser(String userName, char[] password) throws IOException;

    public  void updateCurrentUserInfo(String surname, String name, String secondName, String telephone, String mail)
            throws IOException {
        validateName(surname, name, secondName);
        updateUserName(surname, name, secondName);

        if (!telephone.isEmpty()) {
            validateTelephone(telephone);
        }
        currentUser.setTelephoneNum(telephone);

        if (!mail.isEmpty()) {
            validateMail(mail);
        }
        currentUser.setMailAddress(mail);
    }

    public void updateCurrentUserInfo(String surname, String name, String secondName) throws IOException {
        validateName(surname, name, secondName);
        updateUserName(surname, name, secondName);
        saveUserSet();
    }

    private void updateUserName(String surname, String name, String secondName) throws IOException {
        String userName = surname + " " + name + " " + secondName;
        if (!currentUser.getUserName().equals(userName)) {
            checkUsername(userName);
        }

        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setSecondName(secondName);
        currentUser.setUserName(userName);
    }
}
