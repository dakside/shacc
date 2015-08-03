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
package dakside.hacc.core.dao;

import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.db4o.DB4ODAOFactory;
import org.dakside.dao.AbstractDAOFactory;
import org.dakside.exceptions.ArgumentException;

/**
 * Abstract DAO Factory
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public abstract class DAOFactory extends AbstractDAOFactory {

    //abstract functions
    public abstract AccountDAO getAccountDAO();

    public abstract TransactionDAO getTransactionDAO();

    public abstract CurrencyDAO getCurrencyDAO();

    /**
     * WritableCurrencyDAO allows client class to save and delete currencies
     *
     * @return
     */
    public abstract WritableCurrencyDAO getWritableCurrencyDAO();

    public abstract ExchangeRateDAO getExchangeRateDAO();

    /**
     * Get concrete DAOFactory instance
     *
     * @param connectionString
     * @param dbType
     * @return
     * @throws DAOException
     */
    public static DAOFactory getDAOFactory(String connectionString, int dbType) throws DAOException {
        try {
            return getDAOFactory(new ConnectionInfo(connectionString, dbType));
        } catch (ArgumentException ex) {
            throw new DAOException("Invalid connection information", ex);
        }
    }

    /**
     * Get concrete DAOFactory instance
     *
     * @param connectionInfo
     * @return
     * @throws DAOException
     */
    public static DAOFactory getDAOFactory(ConnectionInfo connectionInfo) throws DAOException {
        //validate parameters
        if (connectionInfo == null || !connectionInfo.isValid()) {
            throw new DAOException("Invalid connection infomation");
        }

        switch (connectionInfo.getDbType()) {
            case DB4O_DATABASE:
                return new DB4ODAOFactory(connectionInfo);
            case MYSQL_DATABASE:
            case DERBY_DATABASE:
            case PGSQL_DATABASE:
            default:
                return null;
        }
    }
}
