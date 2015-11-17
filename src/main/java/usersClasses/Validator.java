package usersClasses;

import supporting.SingleMessage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator {

    private static final String NAME_REG = "^[А-ЯІЇЄ][а-яА-ЯІіЇїЄє-]{2,}$";
    private static final String TEACHER_PASSWORD_REG = "((?=.*\\d)(?=.*[a-zа-яіїє])(?=.*[A-ZА-ЯІЇЄ])(?=.*[@#$%&*-_]).{8,24})";
    private static final String STUDENT_PASSWORD_REG = "((?=.*\\d)(?=.*[a-zа-яіїє])(?=.*[A-ZА-ЯІЇЄ]).{8,24})";
    private static final String TEL_NUM_REG = "^[\\d]{10}$";
    private static final String MAIL_REG = "^[a-zA-Z0-9\\._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$";

    public boolean validateName(String surname, String name, String secondName) throws IOException {
        if (!validate(name, NAME_REG) || !validate(surname, NAME_REG) || !validate(secondName, NAME_REG)) {
            throw new IOException(SingleMessage.WRONG_NAME);
        }
        return true;
    }

    public boolean  validatePassword(char[] password, UserManager manager) throws IOException {
        if (password.length < 8) {
            throw new IOException(SingleMessage.SHORT_PASSWORD);
        }
        if (password.length > 24) {
            throw new IOException(SingleMessage.LONG_PASSWORD);
        }
        if (manager instanceof TeacherManager) {
            if (!validate(String.valueOf(password), TEACHER_PASSWORD_REG)) {
                throw new IOException(SingleMessage.WRONG_TEACHER_PASSWORD);
            }
        } else {
            if (!validate(String.valueOf(password), STUDENT_PASSWORD_REG)) {
                throw new IOException(SingleMessage.WRONG_STUDENT_PASSWORD);
            }
        }
        return true;
    }

    public boolean validateTelephone(String telephone) throws IOException {
        if (!validate(telephone, TEL_NUM_REG)) {
            throw new IOException(SingleMessage.WRONG_TEL);
        }
        return true;
    }

    public boolean validateMail(String mail) throws IOException {
        if (!validate(mail, MAIL_REG)) {
            throw new IOException(SingleMessage.WRONG_MAIL);
        }
        return true;
    }

    private boolean validate(String text, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
