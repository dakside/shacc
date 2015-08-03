/*
 *  Copyright (C) 2010 VangAnh
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
package dao;

import helpers.RMSHelper;
import helpers.TransactionHelper;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import models.Transaction;

/**
 * Transaction DAO
 * @author Vanganh
 */
public class TransactionDAO {

    private String recordStore = "hacc_transaction";

    /**
     * constructor
     */
    public TransactionDAO() {
    }

    /**
     * insert a transaction into record store
     * @param trans
     */
    public void addTransaction(Transaction trans) {
        RecordStore rs = RMSHelper.openRecordStore(recordStore);
        try {
            byte[] data = TransactionHelper.toByteArray(trans.toString());
            rs.addRecord(data, 0, data.length);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        RMSHelper.closeRecordStore(rs);
    }

    /**
     * get all transactions inside record store
     * @return array containing all transactions
     */
    public Transaction[] getAllTransactions() {
        RecordStore rs = RMSHelper.openRecordStore(recordStore);
        try {
            int recordCount = rs.getNumRecords();
            //a vector of transactions
            Vector transactions = new Vector(recordCount);
            //if record(s) exist
            if (recordCount > 0) {
                RecordEnumeration enumData = rs.enumerateRecords(null,null, true);
                while (enumData.hasNextElement()) {
                    byte[] rawRecordData;
                    try {
                        rawRecordData = enumData.nextRecord();
                        String rawRecordString = TransactionHelper.byteToString(rawRecordData);
                        Transaction trx = TransactionHelper.toTransaction(rawRecordString);
                        if (trx != null) {
                            transactions.addElement(trx);
                        }
                    } catch (InvalidRecordIDException ex) {
                        ex.printStackTrace();
                    } catch (RecordStoreNotOpenException ex) {
                        ex.printStackTrace();
                    } catch (RecordStoreException ex) {
                        ex.printStackTrace();
                    }
                    
                }
            }
            return TransactionHelper.toTransactionArray(transactions);
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }
        RMSHelper.closeRecordStore(rs);
        return new Transaction[0];
    }

    //TODO: update later
    public byte[] getTransactionById(int id) {
        byte data[] = null;
        try {
            RecordStore rs = RMSHelper.openRecordStore(recordStore);
            //data = rs.getRecord(id);
            data = rs.getRecord(1);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    /**
     * delete all transactions
     */
    public void reset() {
        try {
            RecordStore rs = RMSHelper.openRecordStore(recordStore);
            RMSHelper.closeRecordStore(rs);
            RecordStore.deleteRecordStore(recordStore);
            //System.out.println(rs.getNumRecords());
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }
}
