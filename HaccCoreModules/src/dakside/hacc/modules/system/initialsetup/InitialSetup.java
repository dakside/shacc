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
package dakside.hacc.modules.system.initialsetup;

import dakside.hacc.core.config.ConfigEntry;
import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.dao.WritableCurrencyDAO;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.TransactionAction;
import dakside.hacc.core.models.TransactionType;
import org.dakside.utils.DateTimeHelper;
import dakside.hacc.modules.accounting.accounttree.AccountManager;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Initial setup controller
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 * @author Michael
 */
public class InitialSetup {

    private InitialSetupView view = null;

    InitialSetup(InitialSetupView view) {
        setView(view);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/ Setters">
    /**
     * @return the view
     */
    public InitialSetupView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(InitialSetupView view) {
        this.view = view;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="DB Helpers">
    /**
     * Get Account DAO
     * @return
     */
    private AccountDAO getAccountDAO() {
        view.log("Creating Account management agent...");
        AccountDAO dao = null;
        try {
            dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
            return dao;
        } catch (NullPointerException ex) {
            view.log("No database connection found. Please load database.");
        } catch (DAOException ex) {
            view.log("Invalid database connection load.");
        }
        return null;
    }

    /**
     * Get Transaction DAO
     * @return
     */
    private TransactionDAO getTransactionDAO() {
        view.log("Creating Transaction management agent...");
        TransactionDAO dao = null;
        try {
            dao = Configuration.getInstance().getDAOFactory().getTransactionDAO();
            return dao;
        } catch (NullPointerException ex) {
            view.log("No database connection found. Please load database.");
        } catch (DAOException ex) {
            view.log("Invalid database connection load.");
        }
        return null;
    }

    private WritableCurrencyDAO getWritableCurrencyDAO() {
        view.log("Creating currency management agent...");
        WritableCurrencyDAO dao = null;
        try {
            dao = Configuration.getInstance().getDAOFactory().getWritableCurrencyDAO();
            return dao;
        } catch (NullPointerException ex) {
            view.log("No database connection found. Please load database.");
        } catch (DAOException ex) {
            view.log("Invalid database connection load.");
        }
        return null;
    }

    private CurrencyDAO getCurrencyDAO() {
        view.log("Creating currency management agent...");
        CurrencyDAO dao = null;
        try {
            dao = Configuration.getInstance().getDAOFactory().getCurrencyDAO();
            return dao;
        } catch (NullPointerException ex) {
            view.log("No database connection found. Please load database.");
        } catch (DAOException ex) {
            view.log("Invalid database connection load.");
        }
        return null;
    }

    /**
     * Shutdown the database
     */
    private void shutdownDB() {
        //try to shutdown db
        try {
            view.log("Shutting down DB");
            Configuration.getInstance().getDAOFactory().shutdown();
        } catch (DAOException ex) {
            //XXX cannot shutdown DB
            Logger.getLogger(InitialSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        view.log("Done!");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Data helpers">
    /**
     * Get account tree
     * @return root of the tree
     */
    Account getSampleAccountTree(Account root, Currency currency) {

        try {
            if (root == null) {
                root = new Account("R", "Accounts", Account.TYPE_FOLDER);
            }
            root.getChildren().clear();

            //create assets
            Account acc1 = root.newChild("1", "Assets", Account.TYPE_FOLDER);
            acc1.newChild("11", "Cash", Account.TYPE_DEBIT).setCurrency(currency);
            acc1.newChild("12", "Bank", Account.TYPE_DEBIT).setCurrency(currency);
            acc1.newChild("13", "AR Loan", Account.TYPE_DEBIT).setCurrency(currency);

            //create liability
            Account acc2 = root.newChild("2", "Liability", Account.TYPE_FOLDER);
            acc2.newChild("21", "AP Borrow", Account.TYPE_CREDIT).setCurrency(currency);

            //create equity
            Account acc3 = root.newChild("3", "Owner's equity", Account.TYPE_FOLDER);
            acc3.newChild("31", "Capital", Account.TYPE_CREDIT);
            Account acc32 = acc3.newChild("32", "Retained Earnings", Account.TYPE_FOLDER);
            Account acc321 = acc32.newChild("321", "Revenue", Account.TYPE_FOLDER);
            acc321.newChild("3211", "Salary", Account.TYPE_CREDIT).setCurrency(currency);
            acc321.newChild("3212", "Other Incomes", Account.TYPE_CREDIT).setCurrency(currency);
            Account acc322 = acc32.newChild("322", "Expenses", Account.TYPE_FOLDER);
            acc322.newChild("3221", "Rental", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3222", "PUB", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3223", "Food", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3224", "Transport", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3225", "Entertainment", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3226", "Household", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3227", "Shopping", Account.TYPE_DEBIT).setCurrency(currency);
            acc322.newChild("3228", "Expenses - Others", Account.TYPE_DEBIT).setCurrency(currency);

            //assign to root
        } catch (ArgumentException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return root;
    }
    //</editor-fold>

    /**
     * Create default transaction types
     * @author michael
     * @author LeTuanAnh <tuananh.ke@gmail.com>
     */
    public void createTransactionTypes() {
        try {
            AccountDAO accDAO = getAccountDAO();
            TransactionDAO trxDAO = getTransactionDAO();

            //check if DAOs are created
            if (accDAO == null || trxDAO == null) {
                view.log("Management agent(s) cannot be loaded.");
            } else {
                TransactionType[] types = trxDAO.getAllTransactionTypes();
                if (types == null || types.length == 0) {

                    Account accCash = accDAO.getAccountByCode("11");
                    Account accBank = accDAO.getAccountByCode("12");
                    Account accARLoan = accDAO.getAccountByCode("13");

                    Account accAPBorrow = accDAO.getAccountByCode("21");

                    Account accSalary = accDAO.getAccountByCode("3211");
                    Account accOtherIncomes = accDAO.getAccountByCode("3212");

                    Account accExpenseRental = accDAO.getAccountByCode("3221");
                    Account accExpensePUB = accDAO.getAccountByCode("3222");
                    Account accExpenseFood = accDAO.getAccountByCode("3223");
                    Account accExpenseTransport = accDAO.getAccountByCode("3224");
                    Account accExpenseEntertainment = accDAO.getAccountByCode("3225");
                    Account accExpenseHousehold = accDAO.getAccountByCode("3226");
                    Account accExpenseShopping = accDAO.getAccountByCode("3227");
                    Account accExpenseOther = accDAO.getAccountByCode("3228");

                    //Debit cash transaction types
                    TransactionAction[] action1 = {
                        new TransactionAction(TransactionAction.DEBIT, accCash),
                        new TransactionAction(TransactionAction.CREDIT, accBank)};
                    TransactionType withdrawCash = new TransactionType("CASH", "Withdraw Cash", action1);
                    trxDAO.save(withdrawCash);

                    TransactionAction[] action2 = {
                        new TransactionAction(TransactionAction.DEBIT, accCash),
                        new TransactionAction(TransactionAction.CREDIT, accARLoan)};
                    TransactionType receiveCashFromLoan = new TransactionType("GET_LOAN", "Receive Cash From Loan", action2);
                    trxDAO.save(receiveCashFromLoan);

                    TransactionAction[] action3 = {
                        new TransactionAction(TransactionAction.DEBIT, accCash),
                        new TransactionAction(TransactionAction.CREDIT, accAPBorrow)};
                    TransactionType borrowCash = new TransactionType("BORROW", "Borrow Cash", action3);
                    trxDAO.save(borrowCash);

                    TransactionAction[] action4 = {
                        new TransactionAction(TransactionAction.DEBIT, accCash),
                        new TransactionAction(TransactionAction.CREDIT, accSalary)};
                    TransactionType receiveCashFromSalary = new TransactionType("SALARY", "Receive Cash From Salary", action4);
                    trxDAO.save(receiveCashFromSalary);

                    TransactionAction[] action5 = {
                        new TransactionAction(TransactionAction.DEBIT, accCash),
                        new TransactionAction(TransactionAction.CREDIT, accOtherIncomes)};
                    TransactionType receiveCashFromOtherIncomes = new TransactionType("INCOME", "Receive Cash From Other Incomes", action5);
                    trxDAO.save(receiveCashFromOtherIncomes);

                    //Credit cash transaction type
                    TransactionAction[] action6 = {
                        new TransactionAction(TransactionAction.DEBIT, accBank),
                        new TransactionAction(TransactionAction.CREDIT, accCash)};
                    TransactionType depositCash = new TransactionType("DEPOSIT", "Deposit Cash", action6);
                    trxDAO.save(depositCash);

                    TransactionAction[] action7 = {
                        new TransactionAction(TransactionAction.DEBIT, accARLoan),
                        new TransactionAction(TransactionAction.CREDIT, accCash)};
                    TransactionType lendCash = new TransactionType("LEND", "Lend Cash", action7);
                    trxDAO.save(lendCash);

                    TransactionAction[] action8 = {
                        new TransactionAction(TransactionAction.DEBIT, accAPBorrow),
                        new TransactionAction(TransactionAction.CREDIT, accCash)};
                    TransactionType payBackCash = new TransactionType("PAY_LOAN", "Pay back Cash", action8);
                    trxDAO.save(payBackCash);

                    TransactionAction[] actionBuyFood = {
                        new TransactionAction(TransactionAction.DEBIT, accExpenseFood),
                        new TransactionAction(TransactionAction.CREDIT, accCash)};
                    TransactionType buyFood = new TransactionType("FOOD", "Buy food", actionBuyFood);
                    trxDAO.save(buyFood);

                    TransactionAction[] actionRental = {
                        new TransactionAction(TransactionAction.DEBIT, accExpenseRental),
                        new TransactionAction(TransactionAction.CREDIT, accCash)};
                    TransactionType payRental = new TransactionType("RENTAL", "Pay rental fee", actionRental);
                    trxDAO.save(payRental);

                    TransactionAction[] actions = {
                        new TransactionAction(TransactionAction.DEBIT, accExpensePUB),
                        new TransactionAction(TransactionAction.CREDIT, accCash)};
                    TransactionType trxType = new TransactionType("PUB", "Pay electricity", actions);
                    trxDAO.save(trxType);

                    actions = new TransactionAction[]{
                                new TransactionAction(TransactionAction.DEBIT, accExpenseTransport),
                                new TransactionAction(TransactionAction.CREDIT, accCash)};
                    trxType = new TransactionType("TRANSPORT", "Pay transportation fee (mrt, bus, etc)", actions);
                    trxDAO.save(trxType);

                    actions = new TransactionAction[]{
                                new TransactionAction(TransactionAction.DEBIT, accExpenseEntertainment),
                                new TransactionAction(TransactionAction.CREDIT, accCash)};
                    trxType = new TransactionType("FUN", "Pay for entertainment", actions);
                    trxDAO.save(trxType);

                    actions = new TransactionAction[]{
                                new TransactionAction(TransactionAction.DEBIT, accExpenseHousehold),
                                new TransactionAction(TransactionAction.CREDIT, accCash)};
                    trxType = new TransactionType("STUFF", "Buy household stuffs", actions);
                    trxDAO.save(trxType);

                    actions = new TransactionAction[]{
                                new TransactionAction(TransactionAction.DEBIT, accExpenseShopping),
                                new TransactionAction(TransactionAction.CREDIT, accCash)};
                    trxType = new TransactionType("SHOPPING", "Miscellaneous", actions);
                    trxDAO.save(trxType);
                    
                    actions = new TransactionAction[]{
                                new TransactionAction(TransactionAction.DEBIT, accExpenseOther),
                                new TransactionAction(TransactionAction.CREDIT, accCash)};
                    trxType = new TransactionType("EXPENSES", "Expenses - Others", actions);
                    trxDAO.save(trxType);

                    TransactionType[] typs = trxDAO.getAllTransactionTypes();
                    view.log("Transaction type added = " + typs.length);
                } else {
                    view.log("Transaction type count: " + types.length);
                }
            }
        } catch (DAOException ex) {
            Logger.getLogger(InitialSetup.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Create a default account tree
     */
    void createSampleAccountTree() {
        AccountDAO dao = getAccountDAO();
        CurrencyDAO curDAO = getCurrencyDAO();
        if (dao == null || curDAO == null) {
            view.log("... cannot open db connection");
            return;
        }

        //count account object
        view.log("Check if any account tree exist ...");
        Account[] acc = dao.getAllAccounts();
        view.log("Get default currency");
        Currency currency = Configuration.getCurrencyFactory().getDefaultCurrency();
        if (currency == null) {
            view.log("Cannot create account tree: no default currency is set");
            return;
        }

        //if no account -> create sample acc
        if (acc != null && acc.length == 0) {
            view.log("No account tree found, creating sample account tree ...");
            Account root = getSampleAccountTree(null, currency);
            try {
                dao.save(root);
                //XXX save ok
                view.log("Account tree has been created successfully.");
            } catch (ArgumentException ex) {
                //XXX cannot save account
                Logger.getLogger(InitialSetup.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            view.log("Found account tree, cannot create sample tree");
        }

        shutdownDB();
    }

    /**
     * Create account period for a specifieds year
     * @param year
     */
    void createAccountPeriod(int year) {
        AccountDAO dao = getAccountDAO();
        if (dao == null) {
            view.log("... cannot open db connection");
            return;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);

        //count account object
        view.log("Check if any account period exists for year " + year);
        AccountPeriod[] acc = dao.getAllAccountPeriods(year);

        //if no account -> create sample acc
        if (acc != null && acc.length == 0) {
            view.log("Creating account periods for year " + year);
            for (int i = 0; i < 12; i++) {
                //from date
                calendar.set(Calendar.MONTH, i);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                Date fromDate = calendar.getTime();
                //to date
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                Date toDate = calendar.getTime();
                //title
                String title = DateTimeHelper.getMonth(fromDate) + "-" + year;

                try {
                    AccountPeriod accountPeriod = new AccountPeriod(title, fromDate, toDate);
                    //save to database
                    dao.save(accountPeriod);
                    //notify
                    view.log("Created: [" + accountPeriod.getTitle()
                            + "] - From: " + DateTimeHelper.toString(accountPeriod.getFromDate())
                            + " - To: " + DateTimeHelper.toString(accountPeriod.getToDate()));
                } catch (ArgumentException ex) {
                    Logger.getLogger(InitialSetup.class.getName()).log(Level.SEVERE, null, ex);
                }


            }

        } else {
            view.log("There are existing account period for this year.");
        }
        shutdownDB();
    }

    void createCurrencies() {
        WritableCurrencyDAO curDAO = getWritableCurrencyDAO();
        //check if DAOs are created
        if (curDAO == null) {
            view.log("Management agent(s) cannot be loaded.");
        } else {
            Currency[] currencies = curDAO.getAllCurrencies();
            if (currencies.length > 0) {
                view.log("Found currencies: " + currencies.length);
            } else {
                view.log("Creating currencies ...");
                try {
                    curDAO.save(new Currency("VND"));
                    curDAO.save(new Currency("SGD"));
                    curDAO.save(new Currency("USD"));

                    String defaultCurrencyIso = Configuration.getInstance().getConfig(ConfigEntry.BASE_CURRENCY).getValueString();
                    if (defaultCurrencyIso.isEmpty()) {
                        Configuration.getInstance().saveConfig(ConfigEntry.BASE_CURRENCY, "SGD");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(InitialSetup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        shutdownDB();
    }
}
