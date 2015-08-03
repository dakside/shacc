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
package dakside.hacc.core.dao.db4o;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.ext.Db4oIOException;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.ConnectionInfo;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.ExchangeRateDAO;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.dao.WritableCurrencyDAO;
import dakside.hacc.core.models.Account;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAOFactory for DB4O database
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DB4ODAOFactory extends DAOFactory {

    private static final Logger logger = Logger.getLogger(DB4ODAOFactory.class.getName());
    private ConnectionInfo info = null;
    private ObjectContainer db = null;

    public DB4ODAOFactory(ConnectionInfo info) throws DAOException {
        if (info == null || info.getConnectionString() == null) {
            throw new RuntimeException("Invalid connection object");
        }
        try {
            setInfo(info);
            getDb(); //get for the first time
        } catch (Db4oIOException exception) {
            throw new DAOException("Cannot open database with specified connection info", exception);
        }
    }

    @Override
    public AccountDAO getAccountDAO() {
        try {
            return new DB4OAccountDAO(getDb());
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public CurrencyDAO getCurrencyDAO() {
        try {
            return new DB4OCurrencyDAO(getDb());
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public WritableCurrencyDAO getWritableCurrencyDAO() {
        try {
            return new DB4OWritableCurrencyDAO(getDb());
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ExchangeRateDAO getExchangeRateDAO() {
        try {
            return new DB4OExchangeRateDAO(getDb());
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public TransactionDAO getTransactionDAO() {
        try {
            return new DB4OTransactionDAO(getDb());
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void shutdown() {
        if (db != null) {
            try {
                db.close();
            } catch (Exception ex) {
                //XXX should we handle anything when DB cannot close?
                logger.log(Level.SEVERE, null, ex);
            }
        }
        setDb(null);
    }

    /**
     * @return the info
     */
    protected ConnectionInfo getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    protected final void setInfo(ConnectionInfo info) {
        this.info = info;
    }

    /**
     * @return the db
     */
    protected final ObjectContainer getDb() throws DAOException {
        if (db == null) {
            if (info == null || info.getConnectionString() == null) {
                throw new DAOException("Invalid connection object");
            }
            try {
                Configuration config = Db4o.newConfiguration();
                config.objectClass(Account.class.getName()).cascadeOnUpdate(true);
                db = Db4o.openFile(config, info.getConnectionString());
                logger.log(Level.INFO, "Loaded db: {0}", info);
            } catch (Exception exception) {
                throw new DAOException("Cannot open database with specified connection info", exception);
            }
        }
        return db;
    }

    /**
     * @param db the db to set
     */
    protected void setDb(ObjectContainer db) {
        this.db = db;
    }
}
