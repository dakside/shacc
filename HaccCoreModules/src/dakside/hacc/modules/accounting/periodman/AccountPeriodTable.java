/*
 *  Copyright (C) 2009 Le Tuan Anh
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
package dakside.hacc.modules.accounting.periodman;

import dakside.hacc.core.models.AccountPeriod;
import javax.swing.JTable;

/**
 * Account Period table
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountPeriodTable extends JTable {

    public AccountPeriod getSelectedAccountPeriod() {
        int r = getSelectedRow();
        return getAccountPeriodAt(r);
    }

    /**
     * Always return not-null array
     * @return
     */
    public AccountPeriod[] getSelectedAccountPeriods() {
        int[] rows = getSelectedRows();
        AccountPeriod[] arrAP = new AccountPeriod[rows.length];
        for (int i = 0; i < rows.length; i++) {
            arrAP[i] = getAccountPeriodAt(rows[i]);
        }
        return arrAP;
    }

    public AccountPeriod getAccountPeriodAt(int rowIndex) {
        try {
            return (AccountPeriod) getValueAt(rowIndex, 0);
        } catch (Exception ex) {
        }
        return null;
    }

    public AccountPeriodTableModel getMyModel() {
        try {
            if(!(getModel() instanceof AccountPeriodTableModel)){
                setModel(new AccountPeriodTableModel());
            }

            return (AccountPeriodTableModel) getModel();
        } catch (Exception ex) {
            return null;
        }
    }

    public void add(AccountPeriod ap) {
        getMyModel().add(ap);
        getMyModel().fireTableDataChanged();
    }

    /**
     * Delete all selected row
     */
    public void deleteSelectedRow() {
        AccountPeriodTableModel model = getMyModel();
        int idx[] = getSelectedRows();
        if (idx.length > 0) {
            for (int i = idx.length - 1; i >= 0; i--) {
                model.remove(idx[i]);
            }
        }
        model.fireTableDataChanged();
    }

    void reload(AccountPeriod[] aplist) {
        getMyModel().reload(aplist);
    }
}
