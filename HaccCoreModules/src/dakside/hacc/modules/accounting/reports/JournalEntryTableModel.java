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

import org.dakside.duck.flexui.tables.ObjectTableModel;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.JournalEntry;
import dakside.hacc.core.models.TransactionType;
import dakside.hacc.modules.ui.tables.DefaultTableColumn;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class JournalEntryTableModel extends ObjectTableModel<JournalEntry> {

    public JournalEntryTableModel() {
        columns.add(new DefaultTableColumn<JournalEntry>("Period") {

            public Object getValue(JournalEntry object) {
                return object.getAccountPeriod();
            }
        });
        columns.add(new DefaultTableColumn<JournalEntry>("Posted Date") {

            public Object getValue(JournalEntry object) {
                return object.getPostingDate();
            }
        });
        columns.add(new DefaultTableColumn<JournalEntry>("Transaction Date") {

            public Object getValue(JournalEntry entry) {
                if (entry.getTransaction() == null) {
                    return "#ERR#";
                }
                return entry.getTransaction().getTransactionDate();
            }
        });
        columns.add(new DefaultTableColumn<JournalEntry>("Code") {

            public Object getValue(JournalEntry entry) {
                if (entry.getTransaction() == null) {
                    return "#ERR#";
                }
                return entry.getTransaction().getType();
            }
        });
        columns.add(new DefaultTableColumn<JournalEntry>("Amount") {

            public Object getValue(JournalEntry entry) {
                if (entry.getTransaction() == null) {
                    return "#ERR#";
                }
                return entry.getTransaction().getAmount();
            }
        });
        columns.add(new DefaultTableColumn<JournalEntry>("Currency") {

            public Object getValue(JournalEntry entry) {
                if (entry.getTransaction() == null) {
                    return "#ERR#";
                }
                return entry.getTransaction().getCurrency();
            }
        });
        columns.add(new DefaultTableColumn<JournalEntry>("Note") {

            public Object getValue(JournalEntry entry) {
                if (entry.getTransaction() == null) {
                    return "#ERR#";
                }
                return entry.getTransaction().getNote();
            }

            @Override
            public void setValue(JournalEntry object, Object value) {
                object.getTransaction().setNote((String) value);
            }
        });
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 4) && (rowIndex >= 0 && rowIndex < getRowCount());
    }
}
