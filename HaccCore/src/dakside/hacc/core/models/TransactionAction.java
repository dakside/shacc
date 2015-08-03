/*
 *  Copyright (C) 2009 michael
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

/**
 * Transaction action
 * @author michael
 */
public class TransactionAction {

    public static final int DEBIT = 1;
    public static final int CREDIT = 2;
    private int action;
    private Account toAccount;

    /*added by michael
     * need a constructor to quickly create a transaction action
     */
    public TransactionAction(int action, Account toAccount) {
        this.action = action;
        this.toAccount = toAccount;
    }

    public TransactionAction() {
    }

    /**
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        this.action = action;
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
     * Get action string
     * @return
     */
    public String getActionString() {
        //XXX localization
        switch (getAction()) {
            case Account.TYPE_CREDIT:
                return "Credit";
            case Account.TYPE_DEBIT:
                return "Debit";
            default:
                return "";
        }
    }
}
