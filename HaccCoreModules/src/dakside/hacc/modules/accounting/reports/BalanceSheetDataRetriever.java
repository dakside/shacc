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

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.models.Account;
import dakside.reports.DataRetriever;
import dakside.reports.ReportData;
import dakside.reports.SimpleReportData;
import org.dakside.utils.FinancialHelper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class BalanceSheetDataRetriever implements DataRetriever {

    private void getAccountBalance(AccountDAO dao, Account acc) {
        try {
            dao.getAccountBalance(acc);
            for (Account child : acc.getChildren()) {
                getAccountBalance(dao, child);
                acc.debit(child.getDrAmount());
                acc.credit(child.getCrAmount());
            }
        } catch (DAOException ex) {
            Logger.getLogger(BalanceSheetDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Account getAccount(AccountDAO dao, String code) {
        Account acc = dao.getAccountByCode(code);
        getAccountBalance(dao, acc);
        return acc;
    }

    /**
     * Query report data
     * @param parameters
     * @return
     */
    public ReportData retrieveData(Object[] parameters) {
        try {
            SimpleReportData data = new SimpleReportData();
            //report header
            data.setData("title", "Balance Sheet");
            DAOFactory factory = Configuration.getInstance().getDAOFactory();

            final AccountDAO dao = factory.getAccountDAO();

            final Account assets = getAccount(dao, "1");
            final Account liabilities = getAccount(dao, "2");
            final Account equity = getAccount(dao, "3");

            //report body
            data.setData("assets", assets);
            data.setData("liabilities", liabilities);
            data.setData("equity", equity);


            double total_liabilities_equity = (liabilities.getDrAmount() - liabilities.getCrAmount()
                    + equity.getDrAmount() - equity.getCrAmount());
            data.setData("total_assets", assets.getBalance());
            data.setData("total_liabilities_equity", FinancialHelper.formatCurrency(total_liabilities_equity));

//            factory.shutdown();
            return data;
        } catch (DAOException ex) {
            Logger.getLogger(BalanceSheetDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
