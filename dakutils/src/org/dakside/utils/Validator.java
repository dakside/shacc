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

import org.dakside.exceptions.ArgumentException;
import java.text.Collator;
import java.util.Date;

/**
 * Arguments validator
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class Validator {

    public static boolean isNullOrEmpty(String aString) {
        return aString == null || aString.isEmpty();
    }

    /**
     * Validate a string (not null, not empty, doesn't contain only white space)
     * @param aString
     * @return return false is @aString is null or empty or contains only white space.<br/>
     * Otherwise return true
     */
    public static boolean isValid(String aString) {
        return !isNullOrEmpty(aString) && aString.trim().length() > 0;
    }

    /**
     * Check argument cannot be null
     *
     * @param arg
     */
    public static void argumentNotNull(Object arg) throws ArgumentException {
        if (arg == null) {
            throw new ArgumentException("Account arg cannot be null.");
        }
    }

    /**
     * Check argument cannot be null
     *
     * @param arg
     */
    public static void argumentNotNull(Object arg, String message) throws ArgumentException {
        if (arg == null) {
            throw new ArgumentException(message);
        }
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return str1.equals(str2);
        }
    }

    public static boolean equals(Date date1, Date date2) {
        if (date1 == null) {
            return date2 == null;
        } else {
            return date1.equals(date2);
        }
    }

    public static boolean equals(int num1, int num2) {
        return num1 == num2;
    }

    /**
     * Call equals() method of the object instead of compare reference num
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        } else {
            return obj1.equals(obj2);
        }
    }

    /**
     * If both not null return 0
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static int compareByNull(Object obj1, Object obj2) {
        //TODO test this
        if (obj1 == null) {
            return -1;
        } else if (obj2 == null) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int compareByDate(Date date1, Date date2) {
        //TODO test this
        if (date1 == null) {
            return -1;
        } else if (date2 == null) {
            return 1;
        } else {
            if (date1.after(date2)) {
                return 1;
            } else if (date1.before(date2)) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static int compareByString(String str1, String str2) {
        //TODO test this
        return Collator.getInstance().compare(str1, str2);
    }
}
