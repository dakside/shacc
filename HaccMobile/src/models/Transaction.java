/*
 *  Copyright (C) 2010 VangAnh
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
package models;

import helpers.TransactionHelper;
import java.util.Date;

/**
 * Transaction object
 * @author Vanganh
 */
public class Transaction {

  private String uuid;
  private TransactionType type;
  private double amount;
  private String description;
  private String currency; //added
  private Date date; //added

  /**
   * constructor
   */
  public Transaction() {
  }

  public Transaction(TransactionType type, double amount, String description, String currency, Date date) {
    this.uuid = this.getUuid(type.toString(), amount);
    this.type = type;
    this.amount = amount;
    this.description = description;
    this.currency = currency;
    this.date = date;
  }

  public Transaction(String uuid, String description, double amount, TransactionType type, String currency, Date date) {
    this.uuid = uuid;
    this.type = type;
    this.amount = amount;
    this.description = description;
    this.currency = currency;
    this.date = date;
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
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  public String getUuid(String type, double amount) {
    return TransactionHelper.getCurrentDateTime("YYYMMDD_HHMMSS") + "_" +
            type + "_" +
            TransactionHelper.doubleToString(amount);
  }

  public String toString() {
    String transInfo = this.getUuid(this.type.toString(), this.amount) + "|" +
            this.description + "|" +
            TransactionHelper.doubleToString(this.amount) + "|" +
            this.type  + "|" +
            this.currency + "|" +
            TransactionHelper.convertInputDateToString(this.date);
            //this.date;
    return transInfo;
  }

  /**
   * Validate data of a transaction
   * @return
   */
  public boolean validate() {
    if (TransactionHelper.doubleToString(this.amount).equals("")) {
      return false;
    } else if (this.type.equals("")) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @param currency the currency to set
   */
  public void setCurrency(String currency) {
    if(currency.equals(null) || currency.equals("")){
      this.currency = "SGD";
    } else {
      this.currency = currency;
    }
  }

  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    if(date.equals(null)){
      this.date = new Date();
    }else {
      this.date = date;
    }
  }
}