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
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountPeriod;
import dakside.reports.DataRetriever;
import dakside.reports.ReportData;
import dakside.reports.ReportException;
import dakside.reports.ReportHelper;
import dakside.reports.SimpleReportData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class BalanceSheetDataRetriever implements DataRetriever {

    private static final Logger logger = Logger.getLogger(BalanceSheetDataRetriever.class.getName());

    /**
     * Get balance of an account &amp; all its children
     * @param dao
     * @param acc
     * @param periods
     */
    private void getAccountBalance(AccountDAO dao, Account acc, AccountPeriod[] periods) {
        try {
            //get this account balance
            double dr = 0;
            double cr = 0;
            for (AccountPeriod period : periods) {
                dao.getAccountBalance(acc, period, true);
                dr += acc.getDrAmount();
                cr += acc.getCrAmount();
            }
            acc.setDrAmount(dr);
            acc.setCrAmount(cr);
            //get all child balance
            for (Account child : acc.getChildren()) {
                getAccountBalance(dao, child, periods);
                acc.debit(child.getDrAmount());
                acc.credit(child.getCrAmount());
            }
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get account with balance calculated from database
     * @param dao
     * @param code
     * @param periods
     * @return
     */
    private Account getAccount(AccountDAO dao, String code, AccountPeriod[] periods) {
        Account acc = dao.getAccountByCode(code).clone();
//        logger.info(Integer.toHexString(acc.hashCode()));
        getAccountBalance(dao, acc, periods);
        return acc;
    }

    /**
     * Query report data
     * @param parameters
     * @return null if cannot retrieve data
     * @throws ReportException
     */
    public ReportData retrieveData(Object[] parameters) throws ReportException {
        try {
            //validate & retrieve parameters
            ReportHelper.validateArguments(parameters, Integer.class, Integer.class);
            int lastYear = (Integer) parameters[0];
            int thisYear = (Integer) parameters[1];

            //connect to DB
            DAOFactory factory = MockDAO.getDAO();
            final AccountDAO dao = factory.getAccountDAO();

            //retrieve last year data
            final AccountPeriod[] lastYearPeriods = dao.getAllAccountPeriods(lastYear);
            final BalanceSheetData lastYearData = new BalanceSheetData(
                    getAccount(dao, "1", lastYearPeriods),
                    getAccount(dao, "2", lastYearPeriods),
                    getAccount(dao, "3", lastYearPeriods));
//            logger.log(Level.INFO, "Last year: {0}", lastYearData);
            //retrieve this year data
            final AccountPeriod[] thisYearPeriods = dao.getAllAccountPeriods(thisYear);
            final BalanceSheetData thisYearData = new BalanceSheetData(
                    getAccount(dao, "1", thisYearPeriods),
                    getAccount(dao, "2", thisYearPeriods),
                    getAccount(dao, "3", thisYearPeriods));
//            logger.log(Level.INFO, "This year: {0}", thisYearData);
            factory.shutdown();

            //save report data to transfer object & send
            SimpleReportData data = new SimpleReportData();
            data.setData("title", "Balance Sheet");
            //balance sheet data
            data.setData("lastyear", lastYearData);
            data.setData("thisyear", thisYearData);
            //table data
            Object[][] assets = buildTable(
                    new Account[]{thisYearData.getAssets(), lastYearData.getAssets()});
            Object[][] liabilities = buildTable(
                    new Account[]{thisYearData.getLiabilities(), lastYearData.getLiabilities()});
            Object[][] equity = buildTable(
                    new Account[]{thisYearData.getEquity(), lastYearData.getEquity()});
            data.setData("assets", assets);
            data.setData("liabilities", liabilities);
            data.setData("equity", equity);

            logger.log(Level.INFO, "Data: {0}", data);
            logger.log(Level.INFO, "Last year: {0}", data.getData("lastyear"));
            logger.log(Level.INFO, "This year: {0}", data.getData("thisyear"));

            return data;
        } catch (ArgumentException ex) {
            logger.log(Level.SEVERE, "Invalid report parameters", ex);
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, "Database related exception was raised", ex);
        }
        return null;
    }

    private static Object[][] buildTable(Account[] dataset) {
        HashMap<String, Double[]> datamap = new HashMap<String, Double[]>();
        for (int yearIndex = 0; yearIndex < dataset.length; yearIndex++) {
            Account[] accounts = getAllLeaves(dataset[yearIndex]);
            for (Account acc : accounts) {
                if (!datamap.containsKey(acc.toString())) {
                    datamap.put(acc.toString(), new Double[dataset.length]);
                }
//                if (acc.getType() == Account.TYPE_CREDIT) {
//                    datamap.get(acc.toString())[yearIndex] = -acc.getBalance();
//                } else {
//                    datamap.get(acc.toString())[yearIndex] = acc.getBalance();
//                }
                datamap.get(acc.toString())[yearIndex] = acc.getBalance();
            }
        }

        //build a table from map
        String[] accounts = datamap.keySet().toArray(new String[0]);
        Arrays.sort(accounts);
        Object[][] table = new Object[accounts.length][dataset.length + 1];
        for (int i = 0; i < table.length; i++) {
            table[i][0] = accounts[i];
            Double[] yearData = datamap.get(accounts[i]);
            System.arraycopy(yearData, 0, table[i], 1, yearData.length);
        }

        return table;
    }

    /**
     * get all leaves of an account tree branch (rip off all folder nodes)<br/>
     * if account object passed in is a leaf, it'll be added to the return array
     * @param account
     * @return
     */
    private static Account[] getAllLeaves(Account account) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        Queue<Account> traces = new LinkedList<Account>();
        traces.add(account);
        Account current = null;
        while ((current = traces.poll()) != null) {
            if (current.getType() == Account.TYPE_FOLDER) {
                traces.addAll(current.getChildren());
            } else {
                accounts.add(current);
            }
        }
        return (Account[]) accounts.toArray(new Account[0]);
    }

    /**
     * Test report using a MockDAO to a test database
     * @see MockDAO
     * @param args
     */
    public static void main(String[] args) {
        DataRetriever retriever = new BalanceSheetDataRetriever();
        try {
            ReportData result = retriever.retrieveData(new Object[]{2009, 2010});
            //retrieve report information
            BalanceSheetData lastYear = (BalanceSheetData) result.getData("lastyear");
            BalanceSheetData thisYear = (BalanceSheetData) result.getData("thisyear");
            Object[][] assets = (Object[][]) result.getData("assets");
            Object[][] liabilities = (Object[][]) result.getData("liabilities");
            Object[][] equity = (Object[][]) result.getData("equity");
            //display table
            displayTable("ASSETS", assets);
            displayRow("Total:", thisYear.getAssetBalanceString(), lastYear.getAssetBalanceString());
            displayTable("LIABILITIES", liabilities);
            displayRow("Total:", thisYear.getLiabilities().getBalance(), lastYear.getLiabilities().getBalance());
            displayTable("EQUITY", equity);
            displayRow("Total:", thisYear.getEquity().getBalance(), lastYear.getEquity().getBalance());

        } catch (ReportException ex) {
            Logger.getLogger(BalanceSheetDataRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * A row format for display row to console
     */
//    public static final String rowFormat = "%1s\t\t%s\t%s\r\n";
    /**
     * display a report row to console
     * @param args
     */
    public static void displayRow(Object... args) {
//        System.out.printf(rowFormat, args);
        for (Object obj : args) {
            System.out.print(obj);
            System.out.print("\t");
        }
        System.out.println("");
    }

    /**
     * display a table to consoles
     * @param tableName
     * @param assets
     */
    private static void displayTable(String tableName, Object[][] assets) {
        //display table
        System.out.println(tableName);
        displayRow("Name", 2010, 2009);
        for (Object[] row : assets) {
            displayRow(row);
        }
    }
}
