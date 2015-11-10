package supporting;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SingleMessage extends JLabel {

    private static final ImageIcon WARNING_IMAGE = new ImageIcon("resources/warning.png");

    public static final String WRONG_USER = "Непрвильний логін або пароль";
    public static final String WRONG_GROUP = "Такої групи не існує";
    public static final String EXIST_USER = "Користувач з таким ім'ям вжу існує";
    public static final String ADD_USER_SUC = "Обліковий запис успішно створено!";
    public static final String PASSWORDS_DOES_NOT_MATCH = "Паролі не співпадають";
    public static final String WRONG_USERNAME = "Неправильне ім'я користувача";
    public static final String SHORT_PASSWORD = "Пароль занадто короткий";
    public static final String LONG_PASSWORD = "Пароль занадто довгий";
    public static final String WRONG_PASSWORD = "<html>Пароль повинен містити великі та<br>малі літери, " +
            "числа та спеціальні символи</html>";
    public static final String INCORRECT_PASSWORD = "Неправильний пароль";
    public static final String WRONG_NAME = "Прізвище, ім'я або по-батькові містять заборонені символи";
    public static final String LOGIN = "Здійсніть вхід у свій обліковий запис";
    public static final String CREATE = "Створення нового облікового запису";

    public static final String SETTINGS = "Налаштування облікового запису";
    public static final String WRONG_TEL = "Неправильний номер телефону";
    public static final String WRONG_MAIL = "Неправильна адреса ел. пошти";
    public static final String SAVED = "Зміни збережено";

    private static SingleMessage instance = new SingleMessage();

    private SingleMessage() {}

    public static SingleMessage getInstance(String message) {
        instance.setText(message);
        instance.setHorizontalAlignment(JLabel.CENTER);
        instance.setBorder(new EmptyBorder(8, 0, 8, 0));
        instance.setFont(new Font("times new roman", Font.PLAIN, 16));
        return instance;
    }

    public static void setDefaultMessage(String message) {
        instance.setText(message);
        instance.setIcon(null);
    }

    public static void setWarningMessage(String message) {
        instance.setText(message);
        instance.setIcon(WARNING_IMAGE);
    }
}
