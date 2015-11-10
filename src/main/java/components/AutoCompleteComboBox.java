package components;


import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AutoCompleteComboBox<E> extends JComboBox<E> {

    private int caretPos;
    private JTextField inputField;

    public AutoCompleteComboBox(final E[] elements) {
        super(elements);
        setEditor(new BasicComboBoxEditor());
        setEditable(true);
    }

    @Override
    public void setSelectedIndex(int index) {
        try {
            super.setSelectedIndex(index);

            if (getItemAt(index) != null) {
                inputField.setText(getItemAt(index).toString());
                inputField.setSelectionEnd(caretPos + inputField.getText().length());
                inputField.moveCaretPosition(caretPos);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setEditor(ComboBoxEditor editor) {
        super.setEditor(editor);

        if (editor.getEditorComponent() instanceof JTextField) {
            inputField = (JTextField) editor.getEditorComponent();
            inputField.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent ev) {
                    char key = ev.getKeyChar();

                    if (Character.isLetterOrDigit(key) || Character.isSpaceChar(key)) {
                        caretPos = inputField.getCaretPosition();
                        String text = "";
                        try {
                            text = inputField.getText(0, caretPos);
                        } catch (javax.swing.text.BadLocationException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < getItemCount(); i++) {
                            String element = (String) getItemAt(i);
                            if (element.toLowerCase().startsWith(text.toLowerCase())) {
                                setSelectedIndex(i);
                                return;
                            }
                        }
                    }
                }
            });
        }
    }
}
