package components;

//public class UsersTableModel implements TableModel {

//    private StudentManager studentManager;
//
//    public UsersTableModel(StudentManager studentManager) {
//        this.studentManager = studentManager;
//    }
//
//    private String[] items = {"Name", "Surname", "Username", "Account type"};
//
//    @Override
//    public int getRowCount() {
//        return studentManager.getUserSet().size();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return 4;
//    }
//
//    @Override
//    public String getColumnName(int columnIndex) {
//        return items[columnIndex];
//    }
//
//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        return String.class;
//    }
//
//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return false;
//    }
//
//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        switch (columnIndex) {
//            case 0:
//                return usersList.get(rowIndex).getName();
//            case 1:
//                return usersList.get(rowIndex).getSurname();
//            case 2:
//                return usersList.get(rowIndex).getUserName();
//            case 3:
//                return UsersRights.accountType(usersList.get(rowIndex).getRights());
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//
//    }
//
//    @Override
//    public void addTableModelListener(TableModelListener l) {
//
//    }
//
//    @Override
//    public void removeTableModelListener(TableModelListener l) {
//
//    }
//}
