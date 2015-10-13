package userGI;

import panelsAndFrames.BoxPanel;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

import java.util.ArrayList;

public class SelectionTableGI extends JDialog {

    private ArrayList<String> namesList;
    private ArrayList<String> markedNamesList;
    private JTable selectionTable;
    private JButton completeButton;

    public SelectionTableGI(String title, ArrayList<String> namesList, ArrayList<String> markedNamesList) {
        super();
        this.namesList = namesList;
        this.markedNamesList = markedNamesList;

        prepareTable();
        getContentPane().add(new JScrollPane(selectionTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        prepareCompleteButton();
        getContentPane().add(new BoxPanel(completeButton), BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(335, 400));
        setIconImage(new ImageIcon("resources/checked.png").getImage());
        setTitle(title);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ArrayList<String> getMarkedNamesList() {
        return markedNamesList;
    }

    public void prepareTable() {
        TableModel tableModel = new SelectionTableModel();
        selectionTable = new JTable(tableModel);
        selectionTable.setShowHorizontalLines(false);
        selectionTable.setShowVerticalLines(false);
        selectionTable.setTableHeader(null);
        selectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionTable.getColumnModel().getColumn(0).setPreferredWidth(12);
        selectionTable.getColumnModel().getColumn(0).setMinWidth(25);
        selectionTable.getColumnModel().getColumn(0).setMaxWidth(50);
    }

    public void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.addActionListener(e -> dispose());
    }

    public class SelectionTableModel implements TableModel {

        @Override
        public int getRowCount() {
            return namesList.size();
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
            return columnIndex == 0 ? Boolean.class : String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return markedNamesList.contains(namesList.get(rowIndex));
            }
            return namesList.get(rowIndex);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if ((Boolean) this.getValueAt(rowIndex, 0)) {
                markedNamesList.remove((String) this.getValueAt(rowIndex, 1));
            } else {
                markedNamesList.add((String) this.getValueAt(rowIndex, 1));
            }
        }

        @Override
        public void addTableModelListener(TableModelListener l) {

        }

        @Override
        public void removeTableModelListener(TableModelListener l) {

        }
    }
}
