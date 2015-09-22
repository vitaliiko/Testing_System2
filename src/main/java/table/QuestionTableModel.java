package table;

import testingClasses.Question;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {

    List questions;

    public QuestionTableModel(List questions) {
        this.questions = questions;
    }

    @Override
    public int getRowCount() {
        return questions.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return questions.get(rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Question.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
