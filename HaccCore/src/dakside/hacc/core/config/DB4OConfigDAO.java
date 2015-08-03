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

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import org.dakside.dao.DAOException;
import org.dakside.dao.DAOHelper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB4O config DAO
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
class DB4OConfigDAO implements ConfigDAO {

    private static final Logger logger = Logger.getLogger(DB4OConfigDAO.class.getName());
    ObjectContainer db = null;

    public DB4OConfigDAO(ObjectContainer db) {
        this.db = db;
    }

    public ConfigEntry[] getAllEntries() throws DAOException {
        ObjectSet<ConfigEntry> results = db.query(ConfigEntry.class);
        logger.log(Level.INFO, "Config entry count: {0}", results.size());
        return results.toArray(new ConfigEntry[0]);
    }

    public ConfigEntry getConfigEntry(String key) throws DAOException {
        ConfigEntry proto = new ConfigEntry();
        proto.setKey(key);
        ObjectSet<ConfigEntry> results =
                db.queryByExample(proto);
        if (results.size() > 0) {
            return results.next();
        } else {
            return new ConfigEntry("", "");
        }
    }

    /**
     * Save a config entry
     * @param entry
     * @throws DAOException
     */
    public void save(ConfigEntry entry) throws DAOException {
        DAOHelper.daoArgumentNotNull(db);

        //check if exist?
        ConfigEntry existingEntry = getConfigEntry(entry.getKey());
        if (ConfigEntry.isValid(existingEntry)) {
            //entry exists, only save value
            existingEntry.setValue(entry.getValue());
            db.store(existingEntry);
        } else {
            db.store(entry);
        }
        
        db.commit();
        logger.log(Level.INFO, "Saved entry: {0}[{1}]", new Object[]{entry.getKey(), entry});
    }
}
