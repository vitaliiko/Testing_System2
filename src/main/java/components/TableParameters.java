package components;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class TableParameters<T> extends JTextArea implements TableModel, TableCellRenderer {

    private ArrayList<T> objectsList;

    public TableParameters(ArrayList<T> objectsList) {
        super();
        setLineWrap(true);
        setWrapStyleWord(true);
        this.objectsList = objectsList;
    }

    @Override
    public int getRowCount() {
        return objectsList.size();
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
        return objectsList.get(rowIndex).toString();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public void addTableModelListener(TableModelListener l) {}

    @Override
    public void removeTableModelListener(TableModelListener l) {}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setFont(new Font("Arial", Font.PLAIN, 12));

        String data = value.toString();
        int lineWidth = getFontMetrics(getFont()).stringWidth(data);
        int lineHeight = getFontMetrics(getFont()).getHeight();
        int rowWidth = table.getCellRect(row, column, true).width;

        int newRowHeight = (lineWidth / rowWidth) * (lineHeight) + lineHeight * 2;
        if (table.getRowHeight(row) != newRowHeight) {
            table.setRowHeight(row, newRowHeight);
        }
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        setText(data);
        return this;
    }
}
