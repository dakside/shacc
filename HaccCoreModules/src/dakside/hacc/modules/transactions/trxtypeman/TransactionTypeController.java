/*
 *  Copyright (C) 2010 Le Tuan Anh <tuananh.ke@gmail.com>
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
package dakside.hacc.modules.transactions.trxtypeman;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import dakside.hacc.modules.transactions.trxman.TransactionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TransactionTypeController {

    private static TransactionTypeController controller = null;

    /**
     * Get controller instance
     * @return
     */
    public static TransactionTypeController getInstance() {
        if (controller == null) {
            controller = new TransactionTypeController();
        }
        return controller;
    }

    /**
     * Hide default constructor
     */
    protected TransactionTypeController() {
    }

    Account[] getAllAccounts() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return dao.getAllAccounts();
            }
        } catch (DAOException ex) {
            Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Account[0];
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
}
