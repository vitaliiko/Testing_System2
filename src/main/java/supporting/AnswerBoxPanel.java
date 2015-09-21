package supporting;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;
import java.awt.Cursor;

public class AnswerBoxPanel extends JPanel {

    private JCheckBox checkBox;
    private JTextArea textArea;

    public AnswerBoxPanel() {
        checkBox = new JCheckBox("");
        checkBox.setEnabled(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(checkBox);

        textArea = new JTextArea(4, 30);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    public void setEnabledCheckBox(boolean b) {
        checkBox.setEnabled(b);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public String getText() {
        return textArea.getText();
    }

    public void setEnabledTextArea(boolean b) {
        textArea.setEnabled(b);
    }

    public void addDocumentListener(DocumentListener listener) {
        textArea.getDocument().addDocumentListener(listener);
    }

    public void setSelected(boolean b) {
        checkBox.setSelected(b);
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }
}
