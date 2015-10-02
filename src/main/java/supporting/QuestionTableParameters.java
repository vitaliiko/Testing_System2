package supporting;

import testingClasses.Question;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuestionTableParameters extends JTextArea implements TableModel, TableCellRenderer {

    private Set<TableModelListener> listeners = new HashSet<>();
    private ArrayList<Question> questionList;

    public QuestionTableParameters(ArrayList<Question> questionList) {
        super();
        setLineWrap(true);
        setWrapStyleWord(true);
        this.questionList = questionList;
    }

    @Override
    public int getRowCount() {
        return questionList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return (rowIndex + 1) + ". ";
        }
        return questionList.get(rowIndex).getTask();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        String data = value.toString();
        int lineWidth = this.getFontMetrics(this.getFont()).stringWidth(data);
        int lineHeight = this.getFontMetrics(this.getFont()).getHeight();
        int rowWidth = table.getCellRect(row, column, true).width;

        int newRowHeight = (lineWidth / rowWidth) * (lineHeight) + lineHeight * 2;
        if (table.getRowHeight(row) != newRowHeight) {
            table.setRowHeight(row, newRowHeight);
        }
        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
            this.setForeground(table.getSelectionForeground());
        } else {
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }
        this.setText(data);
        return this;
    }
}
