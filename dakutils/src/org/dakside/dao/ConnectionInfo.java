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
package org.dakside.dao;

import org.dakside.utils.Validator;
import java.text.MessageFormat;
import org.dakside.exceptions.ArgumentException;

/**
 * Default database connection information
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ConnectionInfo {

    private int dbType = AbstractDAOFactory.DB4O_DATABASE;
    private String connectionString;

    public ConnectionInfo(String connectionString, int dbType) throws ArgumentException {
        setConnectionString(connectionString);
        setDbType(dbType);

    }

    public ConnectionInfo(String connectionString) throws ArgumentException {
        setConnectionString(connectionString);
    }

    /**
     * @return the connectionString
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * @param connectionString the connectionString to set
     */
    public void setConnectionString(String connectionString) throws ArgumentException {
        Validator.argumentNotNull(connectionString, "Connection string cannot be null");
        this.connectionString = connectionString;
    }

    public boolean isValid() {
        return connectionString != null;
    }

    /**
     * @return the dbType
     */
    public int getDbType() {
        return dbType;
    }

    /**
     * @param dbType the dbType to set
     */
    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[DBType={0}] - [ConnString={1}]",
                new Object[]{dbType, connectionString});
    }
}
