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
package dakside.hacc.core.dao.db4o;

import java.util.ArrayList;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.*;
import com.db4o.query.Predicate;
import org.dakside.dao.DAOException;
import org.dakside.dao.DAOHelper;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.JournalEntry;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionAction;
import dakside.hacc.core.models.TransactionType;
import org.dakside.utils.DateTimeHelper;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class DB4OTransactionDAO implements TransactionDAO {

    private static final Logger logger = Logger.getLogger(DB4OTransactionDAO.class.getName());
    private ObjectContainer db = null;

    public DB4OTransactionDAO(ObjectContainer db) {
        this.db = db;
    }

    public void save(Transaction transaction) throws DAOException {
        DAOHelper.daoArgumentNotNull(transaction);
        DAOHelper.daoArgumentNotNull(transaction);
        db.store(transaction);
    }

    public Transaction[] getAllTransactions() {
        ObjectSet<Transaction> transactions = db.query(Transaction.class);
        return transactions.toArray(new Transaction[0]);
    }

    public boolean delete(Transaction transaction) throws DAOException {
        DAOHelper.daoArgumentNotNull(transaction);
        //transaction not posted then can be delete
        final Transaction tobeDeleted = transaction;
        ObjectSet<JournalEntry> trx = db.query(new Predicate<JournalEntry>() {

            @Override
            public boolean match(JournalEntry et) {
                return et.getTransaction() == tobeDeleted;
            }
        });
        if (trx.size() >= 0) {
            logger.warning("Trying to delete a posted transaction!");
        } else {
            db.delete(transaction);
            db.commit();
            return true;
        }
        return false;
    }

    public boolean delete(Transaction[] transactions) throws DAOException {
        DAOHelper.daoArgumentNotNull(transactions);
        for (Transaction trx : transactions) {
//            if (isPosted(trx)) {
//                //cannot be deleted
            //return false;
//            } else {
            db.delete(trx);
            db.commit();
            logger.log(Level.INFO, "deleted: {0} - {1} - {2}", new Object[]{trx.getAmount(), trx.getTransactionDate(), trx.getNote()});
//            }
        }
        return true;
    }

//    public ArrayList<Transaction> getTransactionByDate(Date date) {
//        Query q = db.query();
//        q.constrain(Transaction.class);
//        q.descend("transactionDate").constrain(date);
//        ObjectSet<Transaction> results = q.execute();
//        return convert(results);
//    }

    /*public ArrayList<Transaction> retrieveByType(TransactionType type){
    Transaction trx = new Transaction();
    trx.setTrxType(type);
    ObjectSet<Transaction> result = db.queryByExample(trx);
    return convert(result);
    }*/
    public ArrayList<Transaction> getTransactionByAmountRange(double min, double max) {
        Query query = db.query();
        query.constrain(Transaction.class);
        Constraint constr = query.descend("amount").constrain(new Double(min)).greater();
        query.descend("amount").constrain(new Double(max)).smaller().and(constr);
        ObjectSet result = query.execute();
        return convert(result);
    }

    public Transaction[] getTransactionsByType(TransactionType type) {
        Query q = db.query();
        q.constrain(Transaction.class);
        q.descend("type").constrain(type);
        ObjectSet<Transaction> results = q.execute();
        return results.toArray(new Transaction[0]);
    }

//    public ArrayList<Transaction> getTransactionByUUID(String uuid) {
//        Query query = db.query();
//        query.constrain(Transaction.class);
//        query.descend("uuid").constrain(uuid);
//        ObjectSet result = query.execute();
//        return convert(result);
//    }

    private ArrayList<Transaction> convert(ObjectSet<Transaction> set) {
        ArrayList<Transaction> result = new ArrayList<Transaction>();
        while (set.hasNext()) {
            result.add(set.next());
        }
        return result;
    }

    public void save(TransactionType transactionType) throws DAOException {
        DAOHelper.daoArgumentNotNull(transactionType);
        db.store(transactionType);
    }

    public TransactionType[] getAllTransactionTypes() {
        ObjectSet<TransactionType> results = db.query(TransactionType.class);
        return results.toArray(new TransactionType[0]);
    }

    public boolean delete(TransactionType transactionType) throws DAOException {
        DAOHelper.daoArgumentNotNull(transactionType);
        //only can delete transaction type with no transaction
        if (getTransactionsByType(transactionType).length > 0) {
            return false;
        } else {
            //should delete in depth
            TransactionAction[] actions = transactionType.getActionArray();
            for (TransactionAction action : actions) {
                db.delete(action);
            }
            db.delete(transactionType);
            db.commit();
            return true;
        }
    }

    public Transaction[] getTransactions(final TransactionType type, final Boolean posted, final Date dateFrom, final Date dateTo) throws DAOException {
        final HashSet<Transaction> entries = new HashSet<Transaction>();
        //get all posted transactions if needed
        if (posted != null) {
            //get all journal entry
            ObjectSet<JournalEntry> results = db.query(JournalEntry.class);

            for (JournalEntry entry : results) {
                Transaction trx = entry.getTransaction();
                if (trx != null) {
                    entries.add(trx);
                }
            }
        }

        try {
            ObjectSet<Transaction> transactions = db.query(new Predicate<Transaction>() {

                @Override
                public boolean match(Transaction et) {
                    //check by type
                    if (type != null) {
                        if (!type.equals(et.getType())) {
                            return false;
                        }
                    }
                    //check date range
                    if(!DateTimeHelper.isBetween(et.getTransactionDate(), dateFrom, dateTo)){
                        return false;
                    }
                    //check posted
                    if (posted != null) {
                        if (!((posted && entries.contains(et))
                                || (!posted && !entries.contains(et)))) {
                            return false;
                        }
                    }
                    //nothing more to check
                    return true;
                }
            });

            return transactions.toArray(new Transaction[0]);
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public TransactionType getTransactionTypeByCode(String code) {
        Query query = db.query();
        query.constrain(TransactionType.class);
        query.descend("code").constrain(code);
        ObjectSet<TransactionType> result = query.execute();
        return (result.hasNext()) ? result.next() : null;
    }

    public int save(Transaction[] trx) throws DAOException {
        DAOHelper.daoArgumentNotNull(trx);
        try {
            int savedRecords = 0;
            for (Transaction transaction : trx) {
                DAOHelper.daoArgumentNotNull(transaction);
                db.store(transaction);
                savedRecords++;
            }
            //commit changes
            db.commit();
            return savedRecords;
        } catch (Exception ex) {
            //rollback changes
            try {
                db.rollback();
            } catch (Exception e) {
            }
            return -1;
        }

    }

    public boolean isPosted(Transaction trx) throws DAOException {
        DAOHelper.daoArgumentNotNull(trx);
        final Transaction arg = trx;
        ObjectSet<JournalEntry> results = db.query(new Predicate<JournalEntry>() {

            @Override
            public boolean match(JournalEntry et) {
                return et.getTransaction() == arg;
            }
        });
        return results.size() > 0;
    }
}
