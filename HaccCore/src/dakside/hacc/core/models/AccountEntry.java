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

import java.util.Date;

/**
 * Account entry
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountEntry {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_OPENING = 1;
    public static final int TYPE_CLOSING = 2;
    private double amount;
    private int type;
    private Account toAccount;
    private int action;
    private Currency currency;
    private AccountPeriod accountPeriod;
    private Date postDate;

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the toAccount
     */
    public Account getToAccount() {
        return toAccount;
    }

    /**
     * @param toAccount the toAccount to set
     */
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * Should return Account.TYPE_DEBIT or Account.TYPE_CREDIT
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        if (action != Account.TYPE_DEBIT && action != Account.TYPE_CREDIT) {
            throw new RuntimeException("Invalid action (should be Debit or Credit)");
        }
        this.action = action;
    }

    /**
     * Get account entry type string
     * @return
     */
    public String getTypeString() {
        switch (getType()) {
            case TYPE_NORMAL:
                return "Normal";
            case TYPE_OPENING:
                return "Opening";
            case TYPE_CLOSING:
                return "Closing";
            default:
                return "Normal";
        }
    }

    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
