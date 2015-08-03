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
package dakside.hacc.core.dao;

import org.dakside.dao.DAOException;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountEntry;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.JournalEntry;
import java.util.Date;

/**
 * Account DAO
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public interface AccountDAO {
    //manage accounts

    /**
     * Save an account to database
     * @param account
     * @throws ArgumentException
     */
    public void save(Account account) throws ArgumentException;

    /**
     * Get an account by code
     * @param code
     * @return
     */
    public Account getAccountByCode(String code);

    /**
     * Get all accounts in database
     * @return
     */
    public Account[] getAllAccounts();

    /**
     * Delete an account object
     * @param account
     * @throws ArgumentException
     */
    public void delete(Account account) throws ArgumentException;
    //manage account period

    /**
     * Save account period
     * @param period
     */
    public void save(AccountPeriod period) throws ArgumentException;

    /**
     * Get all account period instances in database
     * @return
     */
    public AccountPeriod[] getAllAccountPeriods();

    /**
     * Delete an account period
     * @param period
     */
    public void delete(AccountPeriod period) throws ArgumentException;

    /**
     * Get all available account periods
     * @param year
     * @return
     */
    public AccountPeriod[] getAllAccountPeriods(int year);

    /**
     * Get active (current) account period
     * @return null if no active account period found
     */
    public AccountPeriod getActiveAccountPeriod();

    /**
     * Save a journal entry to database
     * @param je
     */
    public void save(JournalEntry je) throws DAOException;

    /**
     * Delete a journal entry (and all related account record)
     * @param je
     */
    public void delete(JournalEntry je) throws DAOException;

    /**
     * Get the account period which is the specified date belong to
     * @param transactionDate
     * @return
     */
    public AccountPeriod getAccountPeriodAt(Date transactionDate) throws DAOException;

    public JournalEntry[] getAllJournalEntries() throws DAOException;

    /**
     * Get account balance (store in temporary CR/DR value of the specified account object)
     * @param account
     * @return
     * @throws DAOException
     */
    public double getAccountBalance(Account account) throws DAOException;

    /**
     * Get all passed account periods
     * @return
     */
    public AccountPeriod[] getPassedAccountPeriods();

    /**
     * Get account balance within a period (Balance of a closed period or a folder is always 0)
     * @param account
     * @param period
     * @param ignorePeriodState if is true, will calculate account balance even the accountPeriod state is closed
     * @return
     * @throws DAOException
     */
    public double getAccountBalance(Account account, AccountPeriod period, boolean ignorePeriodState) throws DAOException;

    /**
     * Save an account entry
     * @param entry
     */
    public void save(AccountEntry entry) throws DAOException;

    /**
     * Get all account entry of an account
     * @param acc null if we want to get entries of all account
     * @param period null if we want to get entries of all account periods
     * @return
     * @throws DAOException
     */
    public AccountEntry[] getAccountEntriesOf(Account acc, AccountPeriod period)throws DAOException;

    /**
     * Get journal entries of an account period
     * @param period null if you want to get journal entries
     * @return
     */
    public JournalEntry[] getJournalEntriesOf(AccountPeriod period);

    public AccountPeriod[] getOpeningAccountPeriods() throws DAOException;
}
