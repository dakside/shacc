/*
 *  Copyright (C) 2009 michael
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
package dakside.hacc.modules.transactions.trxman;

import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.*;

/**
 * Transaction table model
 * @author michael
 */
public class TransactionTableModel extends AbstractTableModel {

    private static String[] columnNames = {"Date", "Amount", "Currency", "Type", "Note"};
    private static Class<?>[] columnTypes = {Date.class, Double.class, Currency.class, TransactionType.class, String.class};
    private ArrayList<Transaction> transactions;

    public TransactionTableModel() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnTypes.length) {
            return columnTypes[columnIndex];
        } else {
            return null;
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Get number of transactions
     * @return
     */
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Transaction transaction = transactions.get(row);
        switch (col) {
            case 0:
                //should not render data in the model object
                return transaction.getTransactionDate();
            case 1:
                return transaction.getAmount();
            case 2:
                return transaction.getCurrency() != null
                        ? transaction.getCurrency().toString() : "";
            case 3:
                return transaction.getType();
            case 4:
                return transaction.getNote();
            default:
                return null;
        }
    }

    Transaction getRowObject(int index) {
        return transactions.get(index);
    }

    Transaction[] getAllTransactions() {
        return transactions.toArray(new Transaction[0]);
    }

    void clear() {
        transactions.clear();
    }

    void add(Transaction action) {
        transactions.add(action);
    }

    void removeAt(int idx) {
        transactions.remove(idx);
    }
}
