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
package dakside.hacc.modules.accounting.periodman;

import org.dakside.duck.flexui.tables.ObjectTableModel;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.modules.ui.tables.DefaultTableColumn;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountPeriodTableModel extends ObjectTableModel<AccountPeriod> {

    public AccountPeriodTableModel() {
        columns.add(new DefaultTableColumn<AccountPeriod>("Title") {

            public Object getValue(AccountPeriod object) {
                return object;
            }
        });
        columns.add(new DefaultTableColumn<AccountPeriod>("From Date") {

            public Object getValue(AccountPeriod object) {
                return object.getFromDate();
            }
        });
        columns.add(new DefaultTableColumn<AccountPeriod>("To Date") {

            public Object getValue(AccountPeriod object) {
                return object.getToDate();
            }
        });
        columns.add(new DefaultTableColumn<AccountPeriod>("Status") {

            public Object getValue(AccountPeriod object) {
                return object.getStatusString();
            }
        });
        columns.add(new DefaultTableColumn<AccountPeriod>("State") {

            public Object getValue(AccountPeriod object) {
                return object.getState();
            }
        });
    }
}
