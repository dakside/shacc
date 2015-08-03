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
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.core.models.JournalEntry;
import dakside.hacc.core.models.comparators.AccountPeriodComparator;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class JournalEntryTable extends JTable {

    public void loadJournalEntries(JournalEntry[] entries) {
        JournalEntryTableModel model = (JournalEntryTableModel) getModel();
        model.reload(entries);
        applySort(model);
        model.format(this.getColumnModel());
        model.fireTableDataChanged();

    }

    private void applySort(JournalEntryTableModel model) {
        if (getRowSorter() == null) {
            TableRowSorter<JournalEntryTableModel> sorter = new TableRowSorter<JournalEntryTableModel>();
            sorter.setModel(model);
            sorter.setComparator(0, AccountPeriodComparator.getAscInstance());
            //previous sortkey or current sortkey
            ArrayList<SortKey> keys = new ArrayList<SortKey>();
            keys.add(new SortKey(0, SortOrder.ASCENDING)); //first column = transaction date
            sorter.setSortKeys(keys);
            sorter.sort();
            //set sorter to table
            this.setRowSorter(sorter);
        }
    }

    public JournalEntry[] getSelectedJournalEntry() {
        int[] indexes = getSelectedRows();
        JournalEntry[] entries = new JournalEntry[indexes.length];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = getJournalEntry(indexes[i]);
        }
        return entries;
    }

    public JournalEntry getJournalEntry(int rowIndex) {
        JournalEntryTableModel model = (JournalEntryTableModel) getModel();
        int modelIndex = rowIndex;
        if (getRowSorter() != null) {
            modelIndex = getRowSorter().convertRowIndexToModel(rowIndex);
        }
        return model.getRowObject(modelIndex);
    }
}
