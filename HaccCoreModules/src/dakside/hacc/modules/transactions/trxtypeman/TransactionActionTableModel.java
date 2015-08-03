/*
 *  Copyright (C) 2010 Le Tuan Anh <tuananh.ke@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dakside.hacc.modules.transactions.trxtypeman;

import dakside.hacc.core.models.TransactionAction;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TransactionActionTableModel extends AbstractTableModel {

    public String[] columnNames = {"Action", "Account"};
    protected ArrayList<TransactionAction> actions = new ArrayList<TransactionAction>();

    public TransactionActionTableModel() {
    }

    public void add(TransactionAction action) {
        //dont accept null entry to be displayed on the model
        if (action != null) {
            actions.add(action);
        } else {
            System.out.println("Adding null action to table");
        }
    }

    public int getRowCount() {
        return actions.size();
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    public int getColumnCount() {
        //XXX hardcode?
        return 2;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //TODO edit action on the fly?
        return false;
//        return (columnIndex == 4) && (rowIndex >= 0 && rowIndex < getRowCount());
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        //TODO edit action on the fly?
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        //validate rowIndex & columnIndex
        if (rowIndex < 0 || rowIndex >= getRowCount() || columnIndex < 0 || columnIndex >= getColumnCount()) {
            return null;
        }
        TransactionAction action = actions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return action.getActionString();
            case 1:
                return action.getToAccount();

            default:
                return null;
        }
    }

    TransactionAction getRowObject(int index) {
        return actions.get(index);
    }

    TransactionAction[] getAllActions() {
        return actions.toArray(new TransactionAction[0]);
    }

    void clear() {
        actions.clear();
    }

    void removeAt(int idx) {
        if (idx >= 0 && idx < getRowCount()) {
            actions.remove(idx);
            fireTableRowsDeleted(idx, idx);
        }
    }
}
