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

package helpers;

import java.util.Enumeration;
import java.util.Vector;
import models.TransactionType;

/**
 *
 * @author vanganh
 */
public class TransactionTypeHelper {
    public static TransactionType stringToType(String str){
        TransactionType type = new TransactionType(str);
        return type;
    }

    public static TransactionType toTransactionType(String str){
        TransactionType type = new TransactionType(str);
        return type;
    }

    /**
     * Convert a vector of TransactionType to TransactionType array
     * @param vector
     * @return array containing all transaction type objects
     */
    public static TransactionType[] toTransactionTypeArray(Vector vector) {
        TransactionType[] types = new TransactionType[vector.size()];
        int i = 0;
        Enumeration enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            types[i] = (TransactionType) enumeration.nextElement();
            i++;
        }
        return types;
    }

    public static byte[] stringToByteArr(String str){
        if(!str.equals(null) && !str.equals("")){
            byte[] byteArr = str.getBytes();
            return byteArr;
        } else {
            return new byte[0];
        }
    }
}
