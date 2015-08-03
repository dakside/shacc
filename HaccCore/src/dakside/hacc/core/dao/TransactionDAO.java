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
package dakside.hacc.core.dao;

import org.dakside.dao.DAOException;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import java.util.Date;

/**
 * Transaction DAO (Manage all Transaction related data)
 * @author michael
 */
public interface TransactionDAO {

    public void save(Transaction transaction) throws DAOException;

    public void save(TransactionType transactionType) throws DAOException;

    public Transaction[] getAllTransactions() throws DAOException;

    public boolean delete(Transaction transaction) throws DAOException;

    /**
     * Delete a set of transactions, remember to check posted before call this method
     * @param transactions
     * @return
     * @throws DAOException
     */
    public boolean delete(Transaction[] transactions) throws DAOException;

    public boolean delete(TransactionType transactionType) throws DAOException;

    public Transaction[] getTransactionsByType(TransactionType type) throws DAOException;

    public TransactionType[] getAllTransactionTypes() throws DAOException;

    public Transaction[] getTransactions(TransactionType type, Boolean posted, Date dateFrom, Date dateTo) throws DAOException;

    public TransactionType getTransactionTypeByCode(String code);

    public int save(Transaction[] trx) throws DAOException;

    /**
     * Check if a transaction is posted
     * @param trx
     * @return
     * @throws DAOException
     */
    public boolean isPosted(Transaction trx) throws DAOException;
}
