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
import dakside.hacc.core.models.Account;
import java.util.Comparator;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountComparator implements Comparator<Account> {

    private static AccountComparator instance = null;

    public synchronized static AccountComparator getAscInstance() {
        if (instance == null) {
            instance = new AccountComparator();
        }
        return instance;
    }

    private AccountComparator() {
    }

    public int compare(Account o1, Account o2) {
        int result = Validator.compareByNull(o1, o2);
        if (result == 0) {
            String code1 = o1.getCode();
            String code2 = o2.getCode();
            return Validator.compareByString(code1, code2);
        } else {
            return result;
        }
    }
}
