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
import helpers.TransactionTypeHelper;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import models.TransactionType;

/**
 *
 * @author vanganh
 */
public class TransactionTypeDAO {

    private String typeStore = "hacc_type";

    public TransactionTypeDAO() {
    }

    /**
     * get default types
     * @return a string array of all default types
     */
    public String[] getDefaultTypes() {
        String[] defaultTypes = new String[]{"Purchase", "Borrow", "Loan", "Income"};
        return defaultTypes;
    }

    public void addDefautlTypes() {
        RecordStore rs = RMSHelper.openRecordStore(typeStore);
        String[] defaultTypesArr = this.getDefaultTypes();
        for (int i = 0; i < defaultTypesArr.length; i++) {
            String type = defaultTypesArr[i];
            try {
                rs.addRecord(TransactionHelper.toByteArray(type), 0, type.length());
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
        RMSHelper.closeRecordStore(rs);
    }

    /**
     * insert new type into record set
     * @param type
     */
    public void addType(final TransactionType type) {
        RecordStore rs = RMSHelper.openRecordStore(typeStore);
        try {
//            //check duplicated before insert new Type:
//            RecordEnumeration types = rs.enumerateRecords(new RecordFilter() {
//
//                public boolean matches(byte[] candidate) {
//                    String s = new String(candidate);
//                    return s.equals(type.getTypeName());
//                }
//            }, null, true);
//            if (!types.hasNextElement()) {
                //TODO: TrxTypeDAO>addType(): need to check TransactionType before insert if got changes
                byte[] typeData = TransactionHelper.toByteArray(type.toString());
                rs.addRecord(typeData, 0, typeData.length);
//            }
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        RMSHelper.closeRecordStore(rs);
    }

    /**
     * get all transaction types which is available inside record store
     * @return a string array containing all transaction types
     */
    public TransactionType[] getTypes() {
        RecordStore rs = RMSHelper.openRecordStore(typeStore);
        try {
            int recordCount = rs.getNumRecords();
            //a vector of types
            Vector types = new Vector(recordCount);
            //if record(s) exist
            if (recordCount > 0) {
                RecordEnumeration enumData = rs.enumerateRecords(null, null, true);
                while (enumData.hasNextElement()) {
                    byte[] rawRecordData;
                    try {
                        rawRecordData = enumData.nextRecord();
                        String rawRecordString = TransactionHelper.byteToString(rawRecordData);
                        TransactionType type = TransactionTypeHelper.toTransactionType(rawRecordString);
                        if (type != null) {
                            types.addElement(type);
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
            return TransactionTypeHelper.toTransactionTypeArray(types);
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }
        RMSHelper.closeRecordStore(rs);
        return new TransactionType[0];
    }

    /**
     * delete a transaction type
     * @param typeName
     */
    public void deleteType(final String typeName) {
        RecordStore rs = RMSHelper.openRecordStore(typeStore);
        try {
            RecordEnumeration types = rs.enumerateRecords(new RecordFilter() {

                public boolean matches(byte[] candidate) {
                    String s = new String(candidate);
                    return s.equals(typeName);
                }
            }, null, true);
            while (types.hasNextElement()) {
                int recordId = types.nextRecordId();
                rs.deleteRecord(recordId);
            }
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        RMSHelper.closeRecordStore(rs);
    }

    public boolean isDuplicatedTrxType(final String newType){
        RecordStore rs = RMSHelper.openRecordStore(typeStore);
        boolean isDuplicated = true;
        try{            
            RecordEnumeration types = rs.enumerateRecords(new RecordFilter() {

                public boolean matches(byte[] candidate) {
                    String s = new String(candidate);
                    return s.equals(newType);
                }
            }, null, true);
            
            if(!types.hasNextElement()) {
              isDuplicated = false;
            }
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        RMSHelper.closeRecordStore(rs);
        return isDuplicated;
    }
}
