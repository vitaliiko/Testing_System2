package userGI;

import panelsAndFrames.BoxPanel;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class SelectionTableGI extends JDialog {

    private ArrayList<Object> namesList;
    private ArrayList<Object> markedNamesList;
    private ArrayList<Object> newMarkedNamesList = new ArrayList<>();
    private JTable selectionTable;
    private JButton completeButton;
    private JButton cancelButton;

    public SelectionTableGI(String title, ArrayList<Object> namesList, ArrayList<Object> markedNamesList) {
        super();
        this.namesList = namesList;
        this.markedNamesList = markedNamesList;
        newMarkedNamesList.addAll(this.markedNamesList);

        prepareTable();
        getContentPane().add(new JScrollPane(selectionTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        prepareCompleteButton();
        prepareCancelButton();
        getContentPane().add(new BoxPanel(completeButton, cancelButton), BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(335, 400));
        setIconImage(new ImageIcon("resources/checked.png").getImage());
        setTitle(title);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ArrayList<Object> getMarkedNamesList() {
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
        completeButton.addActionListener(e -> {
            markedNamesList.clear();
            markedNamesList.addAll(newMarkedNamesList);
            dispose();
        });
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> dispose());
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
                return newMarkedNamesList.contains(namesList.get(rowIndex).toString());
            }
            return namesList.get(rowIndex);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if ((Boolean) this.getValueAt(rowIndex, 0)) {
                newMarkedNamesList.remove(this.getValueAt(rowIndex, 1));
            } else {
                newMarkedNamesList.add(this.getValueAt(rowIndex, 1));
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
