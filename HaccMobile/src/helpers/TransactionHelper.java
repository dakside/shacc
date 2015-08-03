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

//import helpers.dclausen.microfloat.MicroDouble;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import models.TransactionType;
import models.Transaction;

/**
 *
 * @author Vanganh
 */
public class TransactionHelper {

    /**
     * get current date and time in format YYYMMDD_HHMMSS or YYY/MM/DD HH:MM:SS
     * @param format
     * @return a string containing current datetime in specific format
     */
    public static String getCurrentDateTime(String format) {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        String currentDateTime = null;
        if (format.equals("YYYMMDD_HHMMSS")) {
            currentDateTime =
                    c.get(Calendar.YEAR) + ""
                    + c.get(Calendar.MONTH) + ""
                    + c.get(Calendar.DATE) + "_"
                    + c.get(Calendar.HOUR_OF_DAY) + ""
                    + c.get(Calendar.MINUTE) + ""
                    + c.get(Calendar.SECOND);
        } else if (format.equals("YYY/MM/DD HH:MM:SS")) {
            currentDateTime =
                    c.get(Calendar.YEAR) + "/"
                    + c.get(Calendar.MONTH) + "/"
                    + c.get(Calendar.DATE) + " "
                    + c.get(Calendar.HOUR_OF_DAY) + ":"
                    + c.get(Calendar.MINUTE) + ":"
                    + c.get(Calendar.SECOND);
        }
        return currentDateTime;
    }

    /**
     * convert input date to String
     * @param inputDate
     * @return
     */
    public static String convertInputDateToString(Date inputDate) {
        String strDate = "";
        Calendar c = Calendar.getInstance();
        if (inputDate.equals(null)) {
            inputDate = new Date();
        }
        c.setTime(inputDate);
        strDate = c.get(Calendar.DATE) + "/"
                + (c.get(Calendar.MONTH) + 1) + "/"
                + c.get(Calendar.YEAR);
        return strDate;
    }

    /**
     * convert a string to Date
     * @param strDate
     * @return a Date obj
     */
    public static Date stringToDate(String strDate) {
        String[] dateElements = TransactionHelper.split(strDate, '/');
        int dd = 0;
        int mm = 0;
        int yyyy = 0;
        for (int i = 0; i < dateElements.length; i++) {
            dd = Integer.parseInt(dateElements[0]);
            mm = Integer.parseInt(dateElements[1]);
            yyyy = Integer.parseInt(dateElements[2]);
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, dd);
        c.set(Calendar.MONTH, mm);
        c.set(Calendar.YEAR, yyyy);
        return c.getTime();
    }

    /**
     * validation of a transaction
     * @param trans
     * @return true if a trans is valid, or false if not
     */
    public static boolean isTransaction(Transaction trx) {
        //TODO: validate a transaction
        //check valid transaction and return true
        //else return false
        if(doubleToString(trx.getAmount()) == null){
            return false;
        } else if(trx.getType().equals(null) || trx.getType().equals("")){
            return false;
        } else if(trx.getCurrency().equals(null) || trx.getCurrency().equals("")){
            return false;
        } else if(trx.getDate().equals(null)){
            return false;
        } else {
            return true;
        }        
    }

    /**
     * validate a double value
     * @param val
     * @return true if input value is double or false if not
     */
    public static boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * convert byte array into string
     * @param byteTransInfo
     * @return a string containing transaction's info from a byte array
     */
    public static String byteToString(byte[] byteArr) {
        if (byteArr != null) {
            return new String(byteArr);
        } else {
            return null;
        }
    }

    /**
     * convert a double to a string
     * @param val
     * @return string represents double value
     */
    public static String doubleToString(double val) {
        return Double.toString(val);
    }

    /**
     * convert string of transaction's info into byte array
     * @param strTransInfo
     * @return a byte array containing transaction's info
     */
    public static byte[] toByteArray(String strTransInfo) {
        if (!strTransInfo.equals(null) || !strTransInfo.equals("")) {
            byte byteTransInfo[] = strTransInfo.getBytes();
            return byteTransInfo;
        } else {
            return null;
        }
    }

    /**
     * Split a string into tokens separated by delimiter character
     * @param raw
     * @param delimiter
     * @return
     */
    public static String[] split(String raw, char delimiter) {
        return split(raw, delimiter, false);
    }

    /**
     * Split a string into tokens separated by delimiter character
     * @param raw
     * @param delimiter
     * @param ignoreEmptyToken if true then ignore all empty token (length = 0)
     * @return
     */
    public static String[] split(String raw, char delimiter, boolean ignoreEmptyToken) {
        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();

        for (int i = 0; i < raw.length(); i++) {
            if (delimiter == raw.charAt(i)) {
                //end of 1 token
                if (!ignoreEmptyToken || token.length() > 0) {
                    tokens.addElement(token.toString());
                }
                token.setLength(0);
            } else {
                token.append(raw.charAt(i));
            }
        }

        if (token.length() > 0 || (raw.charAt(raw.length() - 1) == delimiter && !ignoreEmptyToken)) {
            tokens.addElement(token.toString());
        }

        String[] results = new String[tokens.size()];
        tokens.copyInto(results);
        return results;
    }

    /**
     * Convert a vector of Transaction to Transaction array
     * @param vector
     * @return array containing all transaction objects
     */
    public static Transaction[] toTransactionArray(Vector vector) {
        Transaction[] transactions = new Transaction[vector.size()];
        int i = 0;
        Enumeration enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            transactions[i] = (Transaction) enumeration.nextElement();
            i++;
        }
        return transactions;
    }

    /**
     * Convert a string to Transaction object
     * @param rawRecordString
     * @return a transaction object
     */
    public static Transaction toTransaction(String rawRecordString) {
        String uuid = null;
        String desc = null;
        double amount = 0.00;
        String type = null;
        String currency = null;
        String strDate = null;

        String[] trxInfo = TransactionHelper.split(rawRecordString, '|');
        for (int i = 0; i < trxInfo.length; i++) {
            uuid = trxInfo[0];
            desc = trxInfo[1];
            amount = Double.parseDouble(trxInfo[2]);
            type = trxInfo[3];
            currency = trxInfo[4];
            strDate = trxInfo[5];
        }
        Date date = TransactionHelper.stringToDate(strDate);
        Transaction trx = new Transaction(uuid, desc, amount, TransactionTypeHelper.stringToType(type), currency, date);
        return trx;
    }

    /**
     * convert a transaction object into string array
     * @param transactions
     * @return a string array containing transaction's info
     */
    public static String[] toStringArray(Transaction transactions) {
        String trx = transactions.toString();
        String[] trxArr = TransactionHelper.split(trx, '|');
        return trxArr;
    }
}