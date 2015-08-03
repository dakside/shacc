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

import org.dakside.utils.MathHelper;
import org.dakside.exceptions.ArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Account model
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class Account implements Comparable<Account>, Cloneable {

    public static final int TYPE_FOLDER = 0;
    public static final int TYPE_DEBIT = 1;
    public static final int TYPE_CREDIT = 2;
    public static final String TYPE_FOLDER_STRING = "Folder";
    public static final String TYPE_DEBIT_STRING = "Debit";
    public static final String TYPE_CREDIT_STRING = "Credit";
    private String code;
    private String name;
    private int type;
    private ArrayList<Account> children;
    private Currency currency;
    //transient fields used to store temporary account balance
    /**
     * This field is not stored in database, required manual calculation before
     * used
     */
    private transient double crAmount;
    /**
     * This field is not stored in database, required manual calculation before
     * used
     */
    private transient double drAmount;

    public Account() {
        code = "";
        name = "";
        type = TYPE_FOLDER;
    }

    public Account(String code, String name, int type) throws ArgumentException {
        this.code =code;
        this.name = name;
        this.setType(type);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type of account (debit, credit, folder, subaccount, etc)
     */
    public int getType() {
        return type;
    }

    public String getTypeString() {
        switch (type) {
            case Account.TYPE_DEBIT:
                return TYPE_DEBIT_STRING;
            case Account.TYPE_CREDIT:
                return TYPE_CREDIT_STRING;
            case Account.TYPE_FOLDER:
                return TYPE_FOLDER_STRING;
            default:
                return "Unknown";
        }
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) throws ArgumentException {
        if (type != TYPE_CREDIT && type != TYPE_DEBIT && type != TYPE_FOLDER) {
            throw new ArgumentException("Unknown account type");
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", getCode(), getName());
    }

    public Account newChild(String code, String name, int type) throws ArgumentException {
        Account child = new Account(code, name, type);
        this.getChildren().add(child);
        return child;
    }

    /**
     * @return the children
     */
    public ArrayList<Account> getChildren() {
        if (children == null) {
            children = new ArrayList<Account>();
        }
        return children;
    }

    /**
     * Get children list as a sorted array
     * @return
     */
    public Account[] getChildrenArray() {
        Account[] temp = (Account[]) getChildren().toArray(new Account[0]);
        Arrays.sort(temp);
        return temp;
    }

    /**
     * Compare to another account (by code)
     * @param o
     * @return if o == null return 1 <br/>
     * account code is null or not a number then account code = 0
     */
    public int compareTo(Account o) {
        if (o == null) {
            return 1;
        }
        int oCode = MathHelper.tryParse(o.getCode(), 0);
        int myCode = MathHelper.tryParse(getCode(), 0);
        return myCode - oCode;
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
     * @return the crAmount
     */
    public double getCrAmount() {
        return crAmount;
    }

    /**
     * @param crAmount the crAmount to set
     */
    public void setCrAmount(double crAmount) {
        this.crAmount = crAmount;
    }

    /**
     * @return the drAmount
     */
    public double getDrAmount() {
        return drAmount;
    }

    /**
     * @param drAmount the drAmount to set
     */
    public void setDrAmount(double drAmount) {
        this.drAmount = drAmount;
    }

    /**
     * Debit the given amount to this account
     * @param amount
     */
    public void debit(double amount) {
        this.drAmount += amount;
    }

    /**
     * Credit the given amount to this account
     * @param amount
     */
    public void credit(double amount) {
        this.crAmount += amount;
    }

    public double getBalance() {
        if (getType() == Account.TYPE_CREDIT) {
            return crAmount - drAmount;
        } else {
            return drAmount - crAmount;
        }
    }

    @Override
    public Account clone() {
        try {
            Account acc = (Account) super.clone();
            if (this.children != null) {
                acc.children = new ArrayList<Account>();
                for (int i = 0; i < this.children.size(); i++) {
                    acc.children.add(this.children.get(i).clone());
                }
            }
            return acc;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
