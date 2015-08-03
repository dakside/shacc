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
package org.dakside.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date time helper
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DateTimeHelper {

    /**
     * Get Date object from string
     * @param val
     * @return null if cannot parse
     */
    public static Date toDate(String val) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        try {
            return formatter.parse(val);
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Date toDate(String val, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        try {
            return formatter.parse(val);
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * author michael
     * return date with timestamp for 12 AM
     * @param val in dd/MM/yyyy format
     * @return null if cannot parse
     */
    public static Date toDateStart(String val) {
        val += " 00:00:00";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setLenient(false);
        try {
            return formatter.parse(val);
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * author michael
     * return date with timestamp for 11:59:59 PM
     * @param val in dd/MM/yyyy format
     * @return null if cannot parse
     */
    public static Date toDateEnd(String val) {
        val += " 23:59:59";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setLenient(false);
        try {
            return formatter.parse(val);
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * author michael
     * return date with timestamp for 12 AM
     * @param date
     * @return null if cannot parse
     */
    public static Date toDateStart(Date date) {
        if (date == null) {
            return null;
        }
        return toDateStart(toString(date));
    }

    /**
     * author michael
     * return date with timestamp for 11:59:59 PM
     * @param date
     * @return null if cannot parse
     */
    public static Date toDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        return toDateEnd(toString(date));
    }

    public static String getMonth(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM");
        return formatter.format(date);
    }

    /**
     * Format a date object with pattern dd/MM/yyyy
     * @param val
     * @return
     */
    public static String toString(Date val) {
        if (val == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        return formatter.format(val);
    }

    public static String toString(Date val, String format) {
        if (val == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        return formatter.format(val);
    }

    public static int thisYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Return date string only if time is 00:00:00:000
     * otherwise return date time string 
     * @param transactionDate
     * @return
     */
    public static String toStringSmart(Date transactionDate) {
        if (transactionDate == null) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(transactionDate);
        if (c.get(Calendar.HOUR_OF_DAY) == 0
                && c.get(Calendar.MINUTE) == 0
                && c.get(Calendar.SECOND) == 0
                && c.get(Calendar.MILLISECOND) == 0) {
            return toString(transactionDate, "dd/MMM/yyyy");
        } else {
            return toString(transactionDate, "dd/MMM/yyyy hh:mm");
        }
    }

    /**
     * Check if a date is between a range (Date only, not time)<br/>
     * Date From and Date To inclusive
     * @author LeTuanAnh <tuananh.ke@gmail.com>
     * @param compareDate
     * @param dateFrom
     * @param dateTo
     * @return always return true if both dateFrom and dateTo is null
     * always return false if compareDate is null
     * if dateFrom is null then consider dateTo only
     * if dateTo is null then consider dateFrom only
     */
    public static boolean isBetween(Date compareDate, Date dateFrom, Date dateTo) {
        if (compareDate == null) {
            return false;
        }
        //rip off time element
        Date from = toDateStart(dateFrom);
        Date to = toDateEnd(dateTo);

        return (dateFrom == null
                || ((dateFrom != null) && (from.before(compareDate) || from.equals(compareDate))))
                && (dateTo == null
                || ((dateTo != null) && (to.after(compareDate) || to.equals(compareDate))));
    }
}
