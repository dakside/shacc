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
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountEntry;
import dakside.hacc.core.models.comparators.AccountComparator;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import org.dakside.duck.plugins.AppCentral;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountEntryTable extends JTable {

    public AccountEntryTable() {
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (getMyModel() == null) {
                    return;
                }
                AccountEntry[] entries = getSelectedJournalEntries();
                if (entries != null && entries.length > 0) {
                    //show summary
                    double credit = 0;
                    double debit = 0;
                    for (AccountEntry entry : entries) {
                        if (entry.getAction() == Account.TYPE_CREDIT) {
                            credit += entry.getAmount();
                        } else if (entry.getAction() == Account.TYPE_DEBIT) {
                            debit += entry.getAmount();
                        }
                    }

                    String sum = MessageFormat.format("DR: {0} - CR: {1} - Balance: {2}", debit, credit, debit - credit);
                    AppCentral.getAPIDelegate().setStatusMessage(sum);
                }
            }
        });
    }

    public void load(AccountEntry[] entries) {
        AccountEntryTableModel model = this.getMyModel();
        model.reload(entries);
        this.getMyModel().format(this.getColumnModel());
        applySort();
        model.fireTableDataChanged();
    }

    private AccountEntryTableModel getMyModel() {
        return (AccountEntryTableModel) super.getModel();
    }

    public final void applySort() {
        if (getRowSorter() == null) {
            TableRowSorter<AccountEntryTableModel> sorter = new TableRowSorter<AccountEntryTableModel>();
            sorter.setModel(this.getMyModel());
            //add sorter
            sorter.setComparator(1, AccountComparator.getAscInstance());
            //set sorter to table
            this.setRowSorter(sorter);

            //default sorting
            ArrayList<SortKey> keys = new ArrayList<SortKey>();
            keys.add(new SortKey(0, SortOrder.ASCENDING)); //first column = transaction date
            sorter.setSortKeys(keys);
            sorter.sort();
        }
    }

    public AccountEntry getSelectedJournalEntry() {
        int[] indexes = getSelectedRows();
        if (indexes.length > 0) {
            return this.getMyModel().getRowObject(indexes[0]);
        }
        return null;
    }

    public AccountEntry[] getSelectedJournalEntries() {
        int[] indexes = getSelectedRows();
        if (indexes.length > 0) {
            AccountEntry[] entries = new AccountEntry[indexes.length];
            for (int i = 0; i < entries.length; i++) {
                entries[i] = this.getMyModel().getRowObject(indexes[i]);
            }
            return entries;
        }
        return null;
    }
}
