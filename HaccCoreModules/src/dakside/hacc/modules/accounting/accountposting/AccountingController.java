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
package dakside.hacc.modules.accounting.accountposting;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountEntry;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.JournalEntry;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Accounting controller
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountingController {

    private static final Logger logger = Logger.getLogger(AccountingController.class.getName());
    private AccountingView view = null;

    public AccountingController() {
    }

    public AccountingController(AccountingView view) {
        setView(view);
    }

    /**
     * @return the view
     */
    public AccountingView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(AccountingView view) {
        this.view = view;
    }

    public boolean unpost(JournalEntry je) {
        //only can unpost je of an opening period
        if (je.getAccountPeriod() != null
                && je.getAccountPeriod().getStatus() == AccountPeriod.OPENING) {
            //unpost
            try {
                AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
                dao.delete(je);
                return true;
            } catch (NullPointerException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (DAOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public JournalEntry post(Transaction transaction, AccountPeriod accountPeriod) {
        //validate transaction
        if (!isValidForPost(transaction) || accountPeriod == null) {
            return null;
        } else {
            JournalEntry je = new JournalEntry();
            Date postTime = new Date();
            //create account entries
            for (TransactionAction action : transaction.getType().getActions()) {
                //Transaction currency must be same as account base currency
                if (transaction.getCurrency() != action.getToAccount().getCurrency()) {
                    return null;
                }
                AccountEntry entry = new AccountEntry();
                //create account entry from action
                entry.setAction(action.getAction());
                entry.setAmount(transaction.getAmount());
                entry.setCurrency(transaction.getCurrency());
                entry.setToAccount(action.getToAccount());
                entry.setType(AccountEntry.TYPE_NORMAL);
                entry.setAccountPeriod(accountPeriod);
                entry.setPostDate(postTime);

                //add to journal entry
                je.getAccountEntries().add(entry);
            }

            je.setAccountPeriod(accountPeriod);
            //add reference to transactions
            je.setTransaction(transaction);
            //posting date is today
            //TODO should we let user to choose posting date?
            je.setPostingDate(postTime);

            //save journal entry to database
            try {
                AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
                dao.save(je);
            } catch (NullPointerException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            } catch (DAOException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }

            return je;
        }
    }

    private boolean isValidForPost(Transaction transaction) {
        return transaction != null && transaction.getType() != null;
    }

    /**
     * Get all account periods
     * @return
     */
    public AccountPeriod[] getAllOpeningPeriods() {
        try {
            AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
            return dao.getOpeningAccountPeriods();
        } catch (NullPointerException ex) {
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return new AccountPeriod[0];
    }

    AccountPeriod findMatchAccountPeriod(Transaction transaction) {
        try {
            AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
            return dao.getAccountPeriodAt(transaction.getTransactionDate());
        } catch (NullPointerException ex) {
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    Transaction[] getUnpostedTransactions() {
        try {
            TransactionDAO dao = Configuration.getInstance().getDAOFactory().getTransactionDAO();
            return dao.getTransactions(null, Boolean.FALSE, null, null);
        } catch (NullPointerException ex) {
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, "cannot get unposted transcations", ex);
        }
        return new Transaction[0];
    }

    JournalEntry[] getAllJournalEntries() {
        try {
            AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
            return dao.getAllJournalEntries();
        } catch (NullPointerException ex) {
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return new JournalEntry[0];
    }

    /**
     * Auto close all previous account periods
     */
    void autoClosePeriods() {

        try {
            AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();

            //1. get all past account periods
            AccountPeriod[] periods = dao.getPassedAccountPeriods();
            //2. get all available accounts
            Account[] accounts = dao.getAllAccounts();
            //3. for each account period, create close entries for all accounts
            ArrayList<AccountEntry> entries = new ArrayList<AccountEntry>();
            for (AccountPeriod period : periods) {
                for (Account account : accounts) {
                    //  a. get account balance within the account period
                    double balance = dao.getAccountBalance(account, period, false);
                    //  b. create an account entry (type=close) and amount = balance
                    AccountEntry closeEntry = new AccountEntry();
                    closeEntry.setAccountPeriod(period);
                    if (account.getType() == Account.TYPE_CREDIT) {
                        closeEntry.setAction(Account.TYPE_DEBIT);
                    } else {
                        closeEntry.setAction(Account.TYPE_CREDIT);
                    }
                    closeEntry.setAmount(balance);
                    closeEntry.setCurrency(account.getCurrency());
                    closeEntry.setToAccount(account);
                    closeEntry.setType(AccountEntry.TYPE_CLOSING);
                    closeEntry.setPostDate(period.getToDate());
                    //  c. create an account entry (type=open) in next account period
                    AccountEntry openEntry = new AccountEntry();
                    //find nextPeriod
                    AccountPeriod nextPeriod = getNextPeriod(dao, period);
                    if (nextPeriod == null) {
                        //      if there's no next account period, fire error.
                        throw new RuntimeException("Cannot find next period for " + period.toString());
                    }
                    openEntry.setAccountPeriod(nextPeriod);
                    openEntry.setPostDate(nextPeriod.getFromDate());
                    if (account.getType() == Account.TYPE_CREDIT) {
                        openEntry.setAction(Account.TYPE_CREDIT);
                    } else {
                        openEntry.setAction(Account.TYPE_DEBIT);
                    }
                    openEntry.setAmount(balance);
                    openEntry.setCurrency(account.getCurrency());
                    openEntry.setToAccount(account);
                    openEntry.setType(AccountEntry.TYPE_OPENING);
                    //everything ok, then add the pair entries to list
                    entries.add(openEntry);
                    entries.add(closeEntry);
                }
            }
            //4. If there's no error for closing, write all changes to database.
            for (AccountEntry entry : entries) {
                dao.save(entry);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AccountPeriod getNextPeriod(AccountDAO dao, AccountPeriod period) throws DAOException {
        Date nextPeriodFromDate = (Date) period.getToDate().clone();
        nextPeriodFromDate.setTime(nextPeriodFromDate.getTime() + 1); //plus 1 millisecond
        return dao.getAccountPeriodAt(nextPeriodFromDate);
    }

    /**
     * Get all accounts from database
     * @return
     */
    public Account[] getAllAccounts() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return dao.getAllAccounts();
            }
        } catch (DAOException ex) {
            Logger.getLogger(AccountingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Account[0];
    }

    public AccountEntry[] getAccountEntriesOf(Account acc, AccountPeriod period) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return dao.getAccountEntriesOf(acc, period);
            }
        } catch (DAOException ex) {
            Logger.getLogger(AccountingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new AccountEntry[0];
    }

    public JournalEntry[] getJournalEntriesOf(AccountPeriod period) {
        try {
            AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
            return dao.getJournalEntriesOf(period);
        } catch (NullPointerException ex) {
        } catch (DAOException ex) {
            Logger.getLogger(AccountingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JournalEntry[0];
    }
}
