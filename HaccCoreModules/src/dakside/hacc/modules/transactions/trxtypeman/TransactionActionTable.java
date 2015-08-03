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
import javax.swing.JTable;

/**
 * Transaction action table
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TransactionActionTable extends JTable {

    public TransactionAction getSelectedTransactionAction() {

        int[] indexes = getSelectedRows();
        if (indexes.length > 0) {
            TransactionActionTableModel model = (TransactionActionTableModel) getModel();
            return model.getRowObject(indexes[0]);
        }
        return null;
    }

    TransactionAction[] getAllActions() {
            TransactionActionTableModel model = (TransactionActionTableModel) getModel();
            return model.getAllActions();
    }

    void load(TransactionAction[] actions) {
        TransactionActionTableModel model = (TransactionActionTableModel) getModel();
        model.clear();
        System.out.println("Found actions: " + actions.length);
        for(TransactionAction action : actions){
            model.add(action);
        }
        model.fireTableDataChanged();
    }

    void removeSelected() {
        int idx = getSelectedRow();
        TransactionActionTableModel model = (TransactionActionTableModel) getModel();
        model.removeAt(idx);
    }

}
