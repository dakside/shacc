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
package dakside.hacc.modules.databridge.csvmodel;

import dakside.hacc.modules.databridge.helpers.TypeDefinition;
import dakside.csv.CSVLine;
import dakside.hacc.core.config.Configuration;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import dakside.hacc.modules.databridge.helpers.CSVNavigator;
import dakside.hacc.modules.databridge.helpers.DataError;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Convert transaction from/to csvline
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TransactionCSV implements TypeDefinition<Transaction> {

    private static final String[] HEADER = {
        "Amount",
        "Currency",
        "Note",
        "TransactionDate",
        "Type",
        "Uuid"
    };
    private final CSVNavigator navigator = new CSVNavigator();
    private static final Class<?>[] TYPES = {
        Double.class,
        String.class,
        String.class,
        java.util.Date.class,
        String.class,
        String.class
    };

    public String[] getHeader() {
        return HEADER;
    }

    /**
     * Convert a transaction model to object[]<br/>
     * This can be used to construct a CSVLine
     * @param obj
     * @return
     */
    public Object[] toCSV(Transaction trx) {
        return new Object[]{
                    trx.getAmount(),
                    (trx.getCurrency() == null) ? "" : trx.getCurrency().getIsoCode(),
                    trx.getNote(),
                    navigator.getDateFormatter().format(trx.getTransactionDate()),
                    (trx.getType() == null) ? "" : trx.getType().getCode(),
                    trx.getUuid()
                };

    }

    /**
     * Create a transaction object from CSV
     * @param line
     * @return
     */
    public Transaction fromCSV(CSVLine line) {
        if (line != null && line.size() == TYPES.length) {
            Transaction trx = new Transaction();
            navigator.setLine(line);
            //        "Amount",
            trx.setAmount(navigator.nextDouble());
            //        "Currency",
            String isoCode = navigator.nextString();
            Currency c = Configuration.getCurrencyFactory().getCurreny(isoCode);
            trx.setCurrency(c);
            //        "Note",
            trx.setNote(navigator.nextString());
            //        "TransactionDate",
            trx.setTransactionDate(navigator.nextDate());
            //        "Type",
            String typeCode = navigator.nextString();
            trx.setType(lookupTransactionType(typeCode));
            //        "Uuid"
            trx.setUuid(navigator.nextString());

            return trx;
        }
        return null;
    }

    private TransactionType lookupTransactionType(String code) {
        try {
            TransactionDAO dao = Configuration.getInstance().getDAOFactory().getTransactionDAO();
            TransactionType type = dao.getTransactionTypeByCode(code);
            return type;
        } catch (DAOException ex) {
            Logger.getLogger(TransactionCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "[CSV] Transactions";
    }

    public DataError[] validate(CSVLine line, int row) {
        ArrayList<DataError> errors = new ArrayList<DataError>();

        if (line == null) {
            //line not null
            errors.add(new DataError(row, 0, "Invalid line (empty)"));
        } else if (line.size() != TYPES.length) {
            //invalid columns length
            errors.add(new DataError(row, 0,
                    "Invalid columns count. Expected {0} but found {1}",
                    new Object[]{TYPES.length, line.size()}));
        } else {
            navigator.setLine(line);
            errors.addAll(checkColumns(navigator, TYPES, row));
        }

        return errors.toArray(new DataError[0]);
    }

    /**
     * Validate columns type
     * @param navigator
     * @param types
     * @param row
     * @return
     */
    private static ArrayList<DataError> checkColumns(CSVNavigator navigator, Class<?>[] types, int row) {
        ArrayList<DataError> errors = new ArrayList<DataError>();

        int i = 0;
        while (i < types.length) {
            //type of this cell
            Class cellType = types[i];
            System.out.println("cellType=" + cellType);
            if (cellType == Byte.class) {
                if (navigator.nextByte() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Short.class) {
                if (navigator.nextShort() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Integer.class) {
                if (navigator.nextInteger() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Long.class) {
                if (navigator.nextLong() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Float.class) {
                if (navigator.nextFloat() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Double.class) {
                System.out.println("Found double");
                if (navigator.nextDouble() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == String.class) {
                System.out.println("Found string");
                if (navigator.nextString() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Boolean.class) {
                if (navigator.nextBoolean() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            } else if (cellType == Date.class) {
                if (navigator.nextDate() == null) {
                    errors.add(new DataError(row, i, "Data format error. Column {0} requires type {1}", new Object[]{HEADER[i], cellType.getSimpleName()}));
                }
            }
            i++;
        }

        return errors;
    }

    public boolean exist(Transaction obj) {
        //TODO implement this
        return false;
    }
}

