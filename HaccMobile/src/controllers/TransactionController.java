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
package controllers;

import dao.TransactionDAO;
import helpers.TransactionHelper;
import models.Transaction;

/**
 * Transaction Controller
 * @author Vanganh
 */
public class TransactionController {

  /**
   * constructor
   */
  public TransactionController() {
  }

  /**
   * Add a new transaction to DB
   * @param trx
   */
  public void addTransaction(Transaction trx) {
    if (TransactionHelper.isTransaction(trx)) {
      TransactionDAO trxDAO = new TransactionDAO();
      trxDAO.addTransaction(trx);
    } else {
      //TODO: revise
      System.out.println("Not a valid transaction");
    }
  }

  /**
   * get all transactions in record store
   * @return array of all transactions
   */
  public Transaction[] getAllTransactions() {
    TransactionDAO trxDAO = new TransactionDAO();
    return trxDAO.getAllTransactions();
  }

  /**
   * delete all existing transactions
   */
  public void resetTransactions() {
    TransactionDAO trxDAO = new TransactionDAO();
    trxDAO.reset();
  }

  /**
   * retrieve transaction's info by id
   * @param id
   * @return
   */
  public String getTransactionById(int id) {
    String transInfo = null;
    TransactionDAO transDAO = new TransactionDAO();
    byte data[] = transDAO.getTransactionById(id);
    if (data != null) {
      transInfo = TransactionHelper.byteToString(data);
    }
    return transInfo;
  }

  
}