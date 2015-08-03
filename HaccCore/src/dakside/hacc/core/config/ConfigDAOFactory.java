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
package dakside.hacc.core.config;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oIOException;
import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
class ConfigDAOFactory {

    private static final Logger logger = Logger.getLogger(ConfigDAOFactory.class.getName());
    private ConnectionInfo info = null;
    private ObjectContainer db = null;

    public ConfigDAOFactory(ConnectionInfo info) throws DAOException {
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

    public ConfigDAO getConfigDAO() {
        try {
            return new DB4OConfigDAO(getDb());
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void shutdown() {
        if (db != null) {
            try {
                db.close();
            } catch (Exception ex) {
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
    protected void setInfo(ConnectionInfo info) {
        this.info = info;
    }

    /**
     * @return the db
     */
    protected ObjectContainer getDb() throws DAOException {
        if (db == null) {
            if (info == null || info.getConnectionString() == null) {
                throw new DAOException("Invalid connection object");
            }
            try {
                com.db4o.config.Configuration config = Db4o.newConfiguration();
                db = Db4o.openFile(config, info.getConnectionString());
                logger.info("Loaded config: " + info);
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
