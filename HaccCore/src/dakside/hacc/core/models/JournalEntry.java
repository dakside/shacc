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
package dakside.hacc.core.models;

import org.dakside.utils.Validator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Journal Entry model
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class JournalEntry {

    private Date postingDate;
    private Transaction transaction;
    private ArrayList<AccountEntry> accountEntries;
    private AccountPeriod accountPeriod;

    /**
     * @return the postingDate
     */
    public Date getPostingDate() {
        return postingDate;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the accountEntries
     */
    public ArrayList<AccountEntry> getAccountEntries() {
        if (accountEntries == null) {
            accountEntries = new ArrayList<AccountEntry>();
        }
        return accountEntries;
    }

    /**
     * @param postingDate the postingDate to set
     */
    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    /**
     * @return the accountPeriod
     */
    public AccountPeriod getAccountPeriod() {
        return accountPeriod;
    }

    /**
     * @param accountPeriod the accountPeriod to set
     */
    public void setAccountPeriod(AccountPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public static Comparator<JournalEntry> getComparator(){
        return new Comparator<JournalEntry>() {

            public int compare(JournalEntry o1, JournalEntry o2) {
                return -Validator.compareByDate(o1.getPostingDate(), o2.getPostingDate());
            }
        };
    }
}
