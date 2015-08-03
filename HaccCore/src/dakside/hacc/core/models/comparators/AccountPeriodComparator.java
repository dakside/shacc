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
package dakside.hacc.core.models.comparators;

import org.dakside.utils.Validator;
import dakside.hacc.core.models.AccountPeriod;
import java.util.Comparator;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public abstract class AccountPeriodComparator implements Comparator<AccountPeriod> {

    private static AccountPeriodComparator ascInstance = null;
    private static AccountPeriodComparator descInstance = null;

    public synchronized static AccountPeriodComparator getAscInstance() {
        if (ascInstance == null) {
            ascInstance = new Asc();
        }
        return ascInstance;
    }

    public synchronized static AccountPeriodComparator getDescInstance() {
        if (descInstance == null) {
            descInstance = new Desc();
        }
        return descInstance;
    }

    private static final class Asc extends AccountPeriodComparator {

        public final int compare(AccountPeriod o1, AccountPeriod o2) {
            int result = Validator.compareByNull(o1, o2);
            if (result == 0) {
                result = Validator.compareByDate(o1.getFromDate(), o2.getFromDate());
            }
            if (result == 0) {
                result = Validator.compareByDate(o1.getToDate(), o2.getToDate());
            }
            if (result == 0) {
                result = Validator.compareByString(o1.getTitle(), o2.getTitle());
            } else if (result == 0) {
                result = o1.getStatus() - o2.getStatus();
            }
            return result;
        }
    }

    private static final class Desc extends AccountPeriodComparator {

        public final int compare(AccountPeriod o1, AccountPeriod o2) {
            int result = Validator.compareByNull(o1, o2);
                if (result == 0) {
                    result = Validator.compareByDate(o1.getFromDate(), o2.getFromDate());
                }
                if (result == 0) {
                    result = Validator.compareByDate(o1.getToDate(), o2.getToDate());
                }
                if (result == 0) {
                    result = Validator.compareByString(o1.getTitle(), o2.getTitle());
                } else if (result == 0) {
                    result = o1.getStatus() - o2.getStatus();
                }
                return -result;
        }
    }

    public abstract int compare(AccountPeriod o1, AccountPeriod o2);
}
