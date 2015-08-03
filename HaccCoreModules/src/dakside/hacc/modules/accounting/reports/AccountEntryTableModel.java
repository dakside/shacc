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

import dakside.hacc.modules.ui.tables.DefaultTableColumn;
import org.dakside.duck.flexui.tables.ObjectTableModel;
import org.dakside.duck.flexui.tables.ColumnDefinition;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountEntry;
import dakside.hacc.core.models.Currency;
import dakside.hacc.modules.ui.tables.EasyCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Account Entry table model
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountEntryTableModel extends ObjectTableModel<AccountEntry> {

    public AccountEntryTableModel() {
        this.columns.add(new DefaultTableColumn<AccountEntry>("Posted Date") {

            public Object getValue(AccountEntry object) {
                return object.getPostDate();
            }
        });
        this.columns.add(new DefaultTableColumn<AccountEntry>("Account") {

            public Object getValue(AccountEntry object) {
                return object.getToAccount();
            }
        });
        this.columns.add(new DefaultTableColumn<AccountEntry>("DR", new DrCrRenderer()) {

            public Object getValue(AccountEntry entry) {
                //if is debit record -> dr amount = record amount
                return (entry.getAction() == Account.TYPE_DEBIT) ? entry.getAmount() : 0;
            }
        });
        this.columns.add(new DefaultTableColumn<AccountEntry>("CR", new DrCrRenderer()) {

            public Object getValue(AccountEntry entry) {
                //if is credit record -> cr amount = record amount
                return (entry.getAction() == Account.TYPE_CREDIT) ? entry.getAmount() : 0;
            }
        });
        this.columns.add(new DefaultTableColumn<AccountEntry>("Currency") {

            public Object getValue(AccountEntry entry) {
                Currency c = entry.getCurrency();
                return (c != null) ? entry.getCurrency().getIsoCode() : "";
            }
        });
        this.columns.add(new DefaultTableColumn<AccountEntry>("Type") {
            public Object getValue(AccountEntry entry) {
                return entry.getTypeString();
            }
        });
    }
}
