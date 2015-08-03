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
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.core.models.Account;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A list of balance of an account (monthly)
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class IncomeStatementRow {

    public String name;
    public double[] balance;

    public IncomeStatementRow(String name, int length) {
        this.name = name;
        balance = new double[length];
    }

    public IncomeStatementRow(Account acc, int length) {
        this.name = acc.toString();
        balance = new double[length];
    }

    /**
     * Set scale of all balances to 2
     * @return
     */
    IncomeStatementRow floorData() {
        for (int i = 0; i < balance.length; i++) {
            balance[i] = new BigDecimal(balance[i]).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        return this;
    }

    /**
     * Swap balance from negative to positive and vice versa
     * @return
     */
    IncomeStatementRow swap() {
        for (int i = 0; i < balance.length; i++) {
            balance[i] = -balance[i];
        }
        return this;
    }
}
