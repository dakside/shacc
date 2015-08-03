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

import org.dakside.utils.DateTimeHelper;
import java.util.Date;

/**
 * Transaction model
 * @author michael
 */
public class Transaction {

    private double amount;
    private TransactionType type;
    private String note;
    private Date transactionDate;
    private String uuid;
    private Currency currency;

    /**
     *
     * @param amount
     * @param type
     * @param note
     * @param transactionDate
     * @param uuid
     * @author michael
     */
    public Transaction(double amount, TransactionType type, String note, Date transactionDate, String uuid) {
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.transactionDate = transactionDate;
        this.uuid = uuid;
    }

    public Transaction() {
    }

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
    public TransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return DateTimeHelper.toString(getTransactionDate(), "dd/MMM/yyyy") +
                " - " + getAmount() + " - " +
                ((getType() != null) ? getType().getCode() : "");
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
}
