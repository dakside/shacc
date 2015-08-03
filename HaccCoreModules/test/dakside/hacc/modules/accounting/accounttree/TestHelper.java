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

package dakside.hacc.modules.accounting.accounttree;

import dakside.hacc.modules.accounting.accounttree.AccountManager;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TestHelper {
    /**
     * Get account tree
     * @return root of the tree
     */
   static Account getSampleAccountTree(Account root) {
        //TODO remove this function
        try {
            if (root == null) {
                root = new Account("R", "Accounts", Account.TYPE_FOLDER);
            }
            root.getChildren().clear();

            //create assets
            Account acc1 = root.newChild("1", "Assets", Account.TYPE_FOLDER);
            acc1.newChild("11", "Cash", Account.TYPE_DEBIT);
            acc1.newChild("12", "Bank", Account.TYPE_DEBIT);
            acc1.newChild("13", "AR Loan", Account.TYPE_DEBIT);

            //create liability
            Account acc2 = root.newChild("2", "Liability", Account.TYPE_FOLDER);
            acc2.newChild("21", "AP Borrow", Account.TYPE_CREDIT);

            //create equity
            Account acc3 = root.newChild("3", "Owner's equity", Account.TYPE_FOLDER);
            acc3.newChild("31", "Capital", Account.TYPE_CREDIT);
            Account acc32 = acc3.newChild("32", "Retained Earnings", Account.TYPE_FOLDER);
            Account acc321 = acc32.newChild("321", "Revenue", Account.TYPE_FOLDER);
            acc321.newChild("3211", "Salary", Account.TYPE_CREDIT);
            acc321.newChild("3212", "Other Incomes", Account.TYPE_CREDIT);
            Account acc322 = acc32.newChild("322", "Expenses", Account.TYPE_FOLDER);
            acc322.newChild("3221", "Rental", Account.TYPE_DEBIT);
            acc322.newChild("3222", "PUB", Account.TYPE_DEBIT);
            acc322.newChild("3223", "Food", Account.TYPE_DEBIT);
            acc322.newChild("3224", "Transport", Account.TYPE_DEBIT);
            acc322.newChild("3225", "Entertainment", Account.TYPE_DEBIT);
            acc322.newChild("3226", "Do dung (xa phong, nuoc xa, etc)", Account.TYPE_DEBIT);
            acc322.newChild("3227", "Stuffs", Account.TYPE_DEBIT);

            //assign to root
        } catch (ArgumentException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return root;
    }
}
