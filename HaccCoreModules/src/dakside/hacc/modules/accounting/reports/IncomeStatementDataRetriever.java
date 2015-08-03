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
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.comparators.AccountPeriodComparator;
import dakside.reports.DataRetriever;
import dakside.reports.ReportData;
import dakside.reports.SimpleReportData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Retrieve data for income statement
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class IncomeStatementDataRetriever implements DataRetriever {

    /**
     * Retrieve income statement data
     * @param parameters an array of account periods or empty array -> auto select this year
     * @return
     */
    public ReportData retrieveData(Object[] parameters) {
        //validate parameter
        if(parameters.length != 1 || !(parameters[0] instanceof Integer)){
            throw new RuntimeException("Report parameter is invalid (Report year is required)");
        }
        int thisYear = (Integer)parameters[0];
        SimpleReportData data = new SimpleReportData();

        ArrayList<IncomeStatementRow> revenue = new ArrayList<IncomeStatementRow>();
        ArrayList<IncomeStatementRow> expenses = new ArrayList<IncomeStatementRow>();

        AccountDAO dao = getAccountDAO();
        if (dao != null) {
            
            AccountPeriod[] periods = getPassedAndCurrentMonths(dao, thisYear);
            Arrays.sort(periods, AccountPeriodComparator.getAscInstance());

            //calculate revenues
            Account accRevenue = dao.getAccountByCode("321");
            calculateAccountBalance(accRevenue, periods, dao, revenue);

            //calculate expenses
            Account accExpenses = dao.getAccountByCode("322");
            calculateAccountBalance(accExpenses, periods, dao, expenses);

            //calculate summary
            IncomeStatementRow total_expenses = new IncomeStatementRow(accExpenses, periods.length);
            IncomeStatementRow gross_profit = new IncomeStatementRow(accRevenue, periods.length);
            IncomeStatementRow netIncome = new IncomeStatementRow("NET INCOME", periods.length);

            //calculate total expenses
            for (IncomeStatementRow acc : expenses) {
                for (int i = 0; i < total_expenses.balance.length; i++) {
                    total_expenses.balance[i] += acc.balance[i];
                }
                acc.swap().floorData();
            }
            total_expenses.swap();

            //calculate gross profit
            for (IncomeStatementRow acc : revenue) {
                for (int i = 0; i < gross_profit.balance.length; i++) {
                    gross_profit.balance[i] += acc.balance[i];
                }
                acc.floorData();
            }

            //calculate net income (after deduced expenses)
            for (int i = 0; i < gross_profit.balance.length; i++) {
                netIncome.balance[i] += gross_profit.balance[i]
                        + total_expenses.balance[i];
            }

            //save data to map
            data.setData("gross_profit", gross_profit.floorData());
            data.setData("total_expenses", total_expenses.floorData());
            data.setData("total_income", netIncome.floorData());
            data.setData("periods", periods);
        } else {
            //No data
            data.setData("gross_profit", 0);
            data.setData("total_expenses", 0);
            data.setData("total_income", 0);
            data.setData("periods", new Object[0]);
        }

        data.setData("title", "Income Statement " + parameters[0]);
        data.setData("revenue", revenue.toArray());
        data.setData("expenses", expenses.toArray());

        return data;
    }

    private AccountDAO getAccountDAO() {
        try {
            return Configuration.getInstance().getDAOFactory().getAccountDAO();
        } catch (Exception ex) {
            Logger.getLogger(IncomeStatementDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Recursive calculate account balance
     * @param acc
     * @param periods
     * @param dao
     * @param revenue
     */
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
        } catch (DAOException ex) {
            Logger.getLogger(IncomeStatementDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get all passed and current account periods
     * @param dao
     * @param thisYear
     * @return
     */
    private AccountPeriod[] getPassedAndCurrentMonths(AccountDAO dao, int thisYear) {
        AccountPeriod[] periods = dao.getAllAccountPeriods(thisYear);
        ArrayList results = new ArrayList();
        for (AccountPeriod period : periods) {
            if (!period.isFuture()) {
                results.add(period);
            }
        }
        return (AccountPeriod[]) results.toArray(new AccountPeriod[results.size()]);
    }
}
