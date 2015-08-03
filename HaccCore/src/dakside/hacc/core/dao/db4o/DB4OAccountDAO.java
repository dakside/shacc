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
package dakside.hacc.core.dao.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.DAOException;
import org.dakside.exceptions.ArgumentException;
import org.dakside.dao.DAOHelper;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountEntry;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.JournalEntry;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Account DAO implementation for DB4O
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DB4OAccountDAO implements AccountDAO {

    ObjectContainer db = null;

    public DB4OAccountDAO(ObjectContainer db) {
        this.db = db;
    }

    public void save(Account account) throws ArgumentException {
        //validate parameters
        DAOHelper.argumentNotNull(account);
        db.store(account);
        db.commit();
    }

    public Account getAccountByCode(String code) {
        final String argCode = code;
        ObjectSet<Account> results = db.query(new Predicate<Account>() {

            @Override
            public boolean match(Account et) {
                if (argCode == null) {
                    return et.getCode() == null;
                } else {
                    return (argCode.equals(et.getCode()));
                }
            }
        });

        if (results.size() == 1) {
            return results.next();
        }

        return null;
    }

    public Account[] getAllAccounts() {
        ObjectSet<Account> accounts = db.query(Account.class);
        return accounts.toArray(new Account[0]);
    }

    public void delete(Account account) throws ArgumentException {
        try {
            DAOHelper.argumentNotNull(account);
            //cannot delete account with account entries
            if (getAccountEntriesOf(account, null).length > 0) {
                throw new ArgumentException("Cannot delete a non-empty account");
            }
            db.delete(account);
        } catch (DAOException ex) {
            Logger.getLogger(DB4OAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(AccountPeriod period) throws ArgumentException {
        //validate parameters
        DAOHelper.argumentNotNull(period);
        db.store(period);
    }

    public AccountPeriod[] getAllAccountPeriods() {
        ObjectSet<AccountPeriod> accounts = db.query(AccountPeriod.class);
        return accounts.toArray(new AccountPeriod[0]);
    }

    public AccountPeriod[] getOpeningAccountPeriods() throws DAOException {
        final Calendar c = Calendar.getInstance();
        ObjectSet<AccountPeriod> accounts = db.query(new Predicate<AccountPeriod>() {

            @Override
            public boolean match(AccountPeriod et) {
                return et.getStatus() == AccountPeriod.OPENING;
            }
        });
        return accounts.toArray(new AccountPeriod[0]);
    }

    public void delete(AccountPeriod period) throws ArgumentException {
        DAOHelper.argumentNotNull(period);

        //cannot delete closed or non-empty periods
        if (period.getStatus() != AccountPeriod.OPENING) {
            throw new ArgumentException("Cannot delete a closed account period.");
        } else if (getJournalEntriesOf(period).length > 0) {
            throw new ArgumentException("Cannot delete a non-empty account period.");
        }
        db.delete(period);
    }

    public AccountPeriod[] getAllAccountPeriods(int year) {
        final int searchYear = year;
        final Calendar c = Calendar.getInstance();
        ObjectSet<AccountPeriod> accounts = db.query(new Predicate<AccountPeriod>() {

            @Override
            public boolean match(AccountPeriod et) {
                if (et.getFromDate() != null) {
                    c.setTime(et.getFromDate());
                    return c.get(Calendar.YEAR) == searchYear;
                }
                return false;
            }
        });
        return accounts.toArray(new AccountPeriod[0]);
    }

    public AccountPeriod getActiveAccountPeriod() {
        //@todo test this
        ObjectSet<AccountPeriod> results = db.query(new Predicate<AccountPeriod>() {

            @Override
            public boolean match(AccountPeriod ap) {
                return AccountPeriod.CURRENT.equals(ap.getState());
            }
        });

        if (results.size() == 1) {
            return results.next();
        }

        return null;
    }

    public void save(JournalEntry je) throws DAOException {
        //@todo test this
        //validate parameters
        DAOHelper.daoArgumentNotNull(je);
        db.store(je);
    }

    public void delete(JournalEntry je) throws DAOException {
        //@todo test this
        DAOHelper.daoArgumentNotNull(je);
        try {
            //1. delete acct entries
            for (AccountEntry entry : je.getAccountEntries()) {
                db.delete(entry);
            }
            //2. delete journal entry
            db.delete(je);
            db.commit();
        } catch (Exception ex) {
            db.rollback();
        }
    }

    public AccountPeriod getAccountPeriodAt(Date transactionDate) throws DAOException {
        //TODO test this
        DAOHelper.daoArgumentNotNull(transactionDate);
        final Date trxDate = (Date) transactionDate.clone();
        //@todo test this
        ObjectSet<AccountPeriod> results = db.query(new Predicate<AccountPeriod>() {

            @Override
            public boolean match(AccountPeriod ap) {
                return (trxDate.after(ap.getFromDate()) || trxDate.equals(ap.getFromDate()))
                        && (trxDate.before(ap.getToDate()) || trxDate.equals(ap.getToDate()));
            }
        });

        if (results.size() == 1) {
            return results.next();
        }

        return null;
    }

    public JournalEntry[] getAllJournalEntries() throws DAOException {
        ObjectSet<JournalEntry> accounts = db.query(JournalEntry.class);
        return accounts.toArray(new JournalEntry[0]);
    }

    public double getAccountBalance(Account account) throws DAOException {
        DAOHelper.daoArgumentNotNull(account);
        if (account.getType() == Account.TYPE_FOLDER) {
            return 0;
        }

        Query query = db.query();
        query.constrain(AccountEntry.class);
        query.descend("toAccount").constrain(account);
        ObjectSet<AccountEntry> results = query.execute();

        account.setCrAmount(0);
        account.setDrAmount(0);
        for (AccountEntry entry : results) {
            if (entry.getAction() == Account.TYPE_DEBIT) {
                account.debit(entry.getAmount());
            } else if (entry.getAction() == Account.TYPE_CREDIT) {
                account.credit(entry.getAmount());
            }
        }

        return account.getBalance();
    }

    public AccountPeriod[] getPassedAccountPeriods() {
        ObjectSet<AccountPeriod> results = db.query(new Predicate<AccountPeriod>() {

            @Override
            public boolean match(AccountPeriod ap) {
                return AccountPeriod.PASSED.equals(ap.getState());
            }
        });
        return results.toArray(new AccountPeriod[0]);
    }

    public double getAccountBalance(Account account, AccountPeriod period, boolean ignorePeriodState) throws DAOException {
        DAOHelper.daoArgumentNotNull(account);
        DAOHelper.daoArgumentNotNull(period);

        /**
         * balance of an account during closed period always = 0
         * Folder doesn't have balance
         */
        if ((!ignorePeriodState && period.getStatus() == AccountPeriod.CLOSED) || account.getType() == Account.TYPE_FOLDER) {
            return 0;
        }

        //get all account entry of a period
        Query query = db.query();
        query.constrain(AccountEntry.class);
        query.descend("accountPeriod").constrain(period).and(
                query.descend("toAccount").constrain(account));
        ObjectSet<AccountEntry> results = query.execute();
        
        account.setCrAmount(0);
        account.setDrAmount(0);
        for (AccountEntry entry : results) {
            if (entry.getAction() == Account.TYPE_DEBIT) {
                account.debit(entry.getAmount());
            } else if (entry.getAction() == Account.TYPE_CREDIT) {
                account.credit(entry.getAmount());
            }
        }

        //TODO need to check this business logic
        return account.getBalance();
    }

    /**
     * Save an account entry
     * @param entry
     */
    public void save(AccountEntry entry) throws DAOException {
        //validate parameters
        DAOHelper.daoArgumentNotNull(entry);
        db.store(entry);
        db.commit();
    }

    public AccountEntry[] getAccountEntriesOf(Account account, AccountPeriod period) throws DAOException {
        //DAOHelper.daoArgumentNotNull(account);

        Query query = db.query();
        query.constrain(AccountEntry.class);
        if (account != null) {
            query.descend("toAccount").constrain(account);
        }
        if (period != null) {
            query.descend("accountPeriod").constrain(period);
        }
        ObjectSet<AccountEntry> results = query.execute();

        return results.toArray(new AccountEntry[0]);
    }

    public JournalEntry[] getJournalEntriesOf(AccountPeriod period) {
        Query q = db.query();
        q.constrain(JournalEntry.class);
        if (period != null) {
            q.descend("accountPeriod").constrain(period);
        }
        ObjectSet<JournalEntry> accounts = q.execute();
        return accounts.toArray(new JournalEntry[0]);
    }
}
