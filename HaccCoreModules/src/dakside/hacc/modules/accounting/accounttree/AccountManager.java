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
package dakside.hacc.modules.accounting.accounttree;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Account management controller
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountManager {

    private static final Logger logger = Logger.getLogger(AccountManager.class.getName());
    private static AccountManager instance = null;

    static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    protected AccountManager() {
    }

    /**
     * Save an account to database
     * @param root
     */
    void save(Account root) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                dao.save(root);
            }
        } catch (ArgumentException ex) {
            logger.log(Level.SEVERE, "Cannot save account tree", ex);
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, "Cannot save account tree", ex);
        }
    }

    /**
     * Shutdown db
     */
    void shutdownDb() {
        try {
            Configuration.getInstance().getDAOFactory().shutdown();
        } catch (NullPointerException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get all accounts from database
     * @return
     */
    Account[] getAllAccounts() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return dao.getAllAccounts();
            }
        } catch (DAOException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Account[0];
    }

    /**
     * Load account tree from database
     * @return
     */
    Account getAccountTree() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return dao.getAccountByCode("R");
            }
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Delete an array of accounts in database
     * @param accounts
     */
    void delete(Account[] accounts) {
        for (Account account : accounts) {
            try {
                DAOFactory factory = Configuration.getInstance().getDAOFactory();
                if (factory != null) {
                    AccountDAO dao = factory.getAccountDAO();
                    try {
                        dao.delete(account);
                    } catch (ArgumentException ex) {
                        Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (DAOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    double getAccountBalance(Account acc) throws RuntimeException {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return dao.getAccountBalance(acc);
            } else {
                throw new RuntimeException("Cannot connect to database");
            }
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new RuntimeException("Cannot retrieve account balance", ex);
        }
    }

    boolean canDelete(Account acc) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                return acc != null
                        && acc.getChildren() != null
                        && acc.getChildren().size() == 0
                        && dao.getAccountEntriesOf(acc, null).length == 0;
            } else {
                throw new RuntimeException("Cannot connect to database");
            }
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //get all currencies from database
    Currency[] getCurrencies() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                CurrencyDAO dao = factory.getCurrencyDAO();
                return dao.getAllCurrencies();
            }
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return new Currency[0];
    }

    Currency getDefaultCurrency() {
        return Configuration.getCurrencyFactory().getDefaultCurrency();
    }
}
