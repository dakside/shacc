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
package dakside.hacc.modules.transactions.trxman;

import dakside.csv.CSVFile;
import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import org.dakside.duck.helpers.SwingHelper;
import dakside.hacc.modules.databridge.helpers.Exporter;
import dakside.hacc.modules.databridge.csvmodel.TransactionCSV;
import dakside.hacc.modules.databridge.helpers.CSVHelper;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class TransactionManager {

    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
    private static TransactionManager instance = null;

    protected TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    /**
     * Save a transaction
     * @param trx
     */
    public void save(Transaction trx) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                dao.save(trx);
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean delete(Transaction[] trx) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                if (!dao.delete(trx)) {
                    return false;
                }
            }
            return true;
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Transaction[] getAllByType(TransactionType type) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                return dao.getTransactionsByType(type);
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Transaction[0];
    }

    public TransactionType[] getAllTransactionType() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                return dao.getAllTransactionTypes();
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new TransactionType[0];
    }

    /**
     * Save a transaction type
     * @param trxType
     */
    void save(TransactionType trxType) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                dao.save(trxType);
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void delete(TransactionType trxType) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                dao.delete(trxType);
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Transaction[] getAllTransactions() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                return dao.getAllTransactions();
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Transaction[0];
    }

    Transaction[] getTransactions(TransactionType type, Boolean posted, Date fromDate, Date toDate) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                return dao.getTransactions(type, posted, fromDate, toDate);
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Transaction[0];
    }

    Currency[] getAllCurrencies() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                CurrencyDAO dao = factory.getCurrencyDAO();
                return dao.getAllCurrencies();
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Currency[0];
    }

    Currency getDefaultCurrency() {
        return Configuration.getCurrencyFactory().getDefaultCurrency();
    }

    int exportCSV(Transaction[] transactions) {
        if (transactions == null) {
            return -1;
        }
        //XXX debug info
        logger.info("Ask user where to save?");
        File file = SwingHelper.askSave("transactions.csv");
        logger.info("User chose: " + file);
        if (file != null) {
            CSVFile csv = Exporter.export(transactions, new TransactionCSV());
            if (csv != null) {
                boolean result = CSVHelper.save(csv, file);
                if (result) {
                    return transactions.length;
                }
            }
        }
        return -1;
    }

    boolean canDelete(Transaction[] transactions) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                TransactionDAO dao = factory.getTransactionDAO();
                for (Transaction transaction : transactions) {
                    if (dao.isPosted(transaction)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
