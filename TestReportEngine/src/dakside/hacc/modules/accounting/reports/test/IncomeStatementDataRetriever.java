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
package dakside.hacc.modules.accounting.reports.test;

import dakside.hacc.core.dao.AccountDAO;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountPeriod;
import dakside.reports.DataRetriever;
import dakside.reports.ReportData;
import dakside.reports.SimpleReportData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class IncomeStatementDataRetriever implements DataRetriever {

    public ReportData retrieveData(Object[] parameters) {
        SimpleReportData data = new SimpleReportData();

        ArrayList<IncomeStatementRow> revenue = new ArrayList<IncomeStatementRow>();
        ArrayList<IncomeStatementRow> expenses = new ArrayList<IncomeStatementRow>();

        AccountDAO dao = getAccountDAO();
        if (dao != null) {
            int thisYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
            AccountPeriod[] periods = dao.getAllAccountPeriods(thisYear);

            Account accRevenue = dao.getAccountByCode("321");
            calculateAccountBalance(accRevenue, periods, dao, revenue);
            Account accExpenses = dao.getAccountByCode("322");
            calculateAccountBalance(accExpenses, periods, dao, expenses);

            //calculate summary
            IncomeStatementRow total_expenses = new IncomeStatementRow(accExpenses, periods.length);
            IncomeStatementRow gross_profit = new IncomeStatementRow(accRevenue, periods.length);
            IncomeStatementRow netIncome = new IncomeStatementRow("NET INCOME", periods.length);

            for (IncomeStatementRow acc : expenses) {
                for (int i = 0; i < total_expenses.balance.length; i++) {
                    total_expenses.balance[i] += acc.balance[i];
                }
            }
            for (IncomeStatementRow acc : revenue) {
                for (int i = 0; i < gross_profit.balance.length; i++) {
                    gross_profit.balance[i] += acc.balance[i];
                }
            }
            for (int i = 0; i < gross_profit.balance.length; i++) {
                netIncome.balance[i] += gross_profit.balance[i]
                        - total_expenses.balance[i];
            }
            data.setData("gross_profit", gross_profit);
            data.setData("total_expenses", total_expenses);
            data.setData("total_income", netIncome);
            data.setData("periods", periods);
        } else {
            data.setData("gross_profit", 0);
            data.setData("total_expenses", 0);
            data.setData("total_income", 0);
            data.setData("periods", new Object[0]);
        }

        data.setData("title", "Income Statement");
        data.setData("revenue", revenue.toArray());
        data.setData("expenses", expenses.toArray());

        return data;
    }

    private AccountDAO getAccountDAO() {
        try {
            return MockDAO.getDAO().getAccountDAO();
        } catch (Exception ex) {
            Logger.getLogger(IncomeStatementDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void calculateAccountBalance(Account acc, AccountPeriod[] periods, AccountDAO dao, ArrayList revenue) {
        try {
            if (acc.getType() != Account.TYPE_FOLDER) {
                IncomeStatementRow row = new IncomeStatementRow(acc, periods.length);
                for (int i = 0; i < periods.length; i++) {
                    double balance = dao.getAccountBalance(acc, periods[i], true);
                    row.balance[i] = balance;
                }
                revenue.add(row);
            }
            for (Account child : acc.getChildren()) {
                calculateAccountBalance(child, periods, dao, revenue);
            }
        } catch (Exception ex) {
            Logger.getLogger(IncomeStatementDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
