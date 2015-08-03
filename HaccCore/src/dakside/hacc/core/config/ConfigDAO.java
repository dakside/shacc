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

import org.dakside.dao.DAOException;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
interface ConfigDAO {

    public ConfigEntry[] getAllEntries() throws DAOException;

    /**
     * Get a configEntry by its key
     * @param key
     * @return a blank config entry if cannot find
     * @throws DAOException
     */
    public ConfigEntry getConfigEntry(String key) throws DAOException;

    /**
     * Save a config entry to configuration
     * @param entry
     * @throws DAOException
     */
    public void save(ConfigEntry entry) throws DAOException;
}
