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
package dakside.hacc.modules.transactions;

import dakside.hacc.modules.transactions.trxman.TransactionManageView;
import dakside.hacc.modules.transactions.trxtypeman.TransactionTypeManagerView;
import java.awt.Component;
import org.dakside.duck.plugins.Function;
import org.dakside.duck.plugins.Unloadable;

/**
 * Transaction module definition
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TransactionModule implements Unloadable {

    private static Component viewTrxMan = null;
    private static TransactionTypeManagerView viewTrxTypeMan = null;

    public synchronized void unload() {
        viewTrxMan = null;
    }

    @Function(Text = "TransactionManagement", Description = "Create/edittransactions",
    IconPath = "TrxManIcon", Category = "Transactions", Location = Function.STARTPAGE)
    public Component getTrxManView() {
        if (viewTrxMan == null) {
            viewTrxMan = new TransactionManageView();
        }
        return viewTrxMan;
    }

    @Function(Text = "TransactionTypeManagement", Description = "Create/edittransactiontypes",
    IconPath = "icon_trxman.png", Category = "Transactions", Location = Function.STARTPAGE)
    public Component getTrxTypeManView() {
        if (viewTrxTypeMan == null) {
            viewTrxTypeMan = new TransactionTypeManagerView();
        }
        return viewTrxTypeMan;
    }
}

