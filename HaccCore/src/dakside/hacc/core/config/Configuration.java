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
package dakside.hacc.core.config;

import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.factories.CurrencyFactory;
import dakside.hacc.core.factories.DefaultCurrencyFactory;
import org.dakside.utils.PathHelper;
import org.dakside.exceptions.ArgumentException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dakside.utils.SystemHelper;

/**
 * Application configuration class
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public final class Configuration {

    private static final Logger logger = Logger.getLogger(Configuration.class.getName());
    private static final String SHACC_CUSTOM_CONFIG = "SHACC_CFG";
    /**
     * Default config file name
     */
    public static final String CONFIG_FILENAME = "shacc.cfg";
    /**
     * Singleton configuration object
     */
    private static Configuration singletonConfig = null;
    /**
     * Default SHACC database connection
     */
    private ConnectionInfo info = null;
    /**
     * SHACC DAO factory
     */
    private DAOFactory daoFactory = null;
    /**
     * Applicaton configuration info
     */
    private ConnectionInfo configInfo = null;
    /**
     * Applicaton config DAO
     */
    private ConfigDAOFactory configDAOFactory = null;
    /**
     * Path to config file (to open DAO)
     */
    private static String configFilePath = null;
    /**
     * Factory to create currency DAO
     */
    private static CurrencyFactory curFactory = null;

    /**
     * Hide default constructor
     */
    private Configuration() {
        getConfigDAO();
    }

    /**
     * @return database configuration
     */
    public static synchronized Configuration getInstance() {
        if (singletonConfig == null) {
            singletonConfig = new Configuration();
        }
        return singletonConfig;
    }

    //<editor-fold defaultstate="collapsed" desc="Database configuration">
    /**
     * @return the accounting database connection info
     */
    public ConnectionInfo getInfo() {
        return info;
    }

    /**
     * @param info the connection info to set
     */
    public void setInfo(ConnectionInfo info) {
        //when set info, need to clear DAO info
        this.info = info;
        this.daoFactory = null;
    }

    /**
     * Get DAO factory (auto create DAOFactory with current configurationInfo if found null)
     * @return
     * @throws DAOException
     */
    public DAOFactory getDAOFactory() throws DAOException {
        if (daoFactory == null) {
            daoFactory = DAOFactory.getDAOFactory(info);
        }
        return daoFactory;
    }

    /**
     * Get home configuration directory (auto created if not exsited)
     */
    public String getHomeDirectory() {
        String userHome = SystemHelper.getHomeFolder();
        String configFolderName = ".shacc";
        String configFolder = PathHelper.combine(userHome, configFolderName);

        try {
            //try to create folder
            File f = new File(configFolder);
            if (!f.exists()) {
                f.mkdir();
            }
            if (f.exists() && f.isDirectory()) {
                return f.getAbsolutePath();
            }
        } catch (Exception ex) {
            //any exception here
        }
        return null;
    }

    /**
     * Get last edit database file<br/>
     * If not found , return ${user.home}/.shacc/HomeAccounting.db<br/>
     * On Linux, we should get something similar to<br/>
     * /home/userid/.shacc/HomeAccounting.db
     * @return absolute path to home accounting database
     */
    public String getLastDatabaseFile() {
        ConfigEntry lastDB = getConfig(ConfigEntry.LAST_DB_FILE);
        if (ConfigEntry.isValid(lastDB)) {
            String lastPath = lastDB.getValueString();
            File f = new File(lastPath);
            if (f.exists() && f.canRead()) {
                return f.getAbsolutePath();
            }
        }
        logger.info("Cannot get last path, return default database");
        return PathHelper.combine(getHomeDirectory(), "HomeAccounting.db");
    }

    /**
     * Save last database file
     * @param path
     */
    public void saveLastDatabaseFile(String path) {
        saveConfig(new ConfigEntry(ConfigEntry.LAST_DB_FILE, path));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Configuration persistent">
    /**
     * Close config database
     */
    private void closeConfig() {
        try {
            if (configDAOFactory != null) {
                configDAOFactory.shutdown();
            }
        } catch (Exception ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            //nevermind :D
        }
    }

    /**
     * Get all config entry in database
     */
    public ConfigEntry[] getAllConfigEntries() {
        try {
            return getConfigDAO().getAllEntries();
        } catch (DAOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConfig();
        return new ConfigEntry[0];
    }

    public void saveConfig(String key, Object value) {
        saveConfig(new ConfigEntry(key, value));
    }

    public void saveConfig(ConfigEntry entry) {
        //no need to save invalid entry
        if (!ConfigEntry.isValid(entry)) {
            return;
        }
        try {
            getConfigDAO().save(entry);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            closeConfig();
        }
    }

    public ConfigEntry getConfig(String key) {
        if (key == null) {
            return null;
        }
        try {
            ConfigEntry entry = getConfigDAO().getConfigEntry(key);
            logger.log(Level.INFO, "Loaded entry: {0}[{1}]", new Object[]{entry.getKey(), entry});
            return entry;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConfig();
        }
    }

    /**
     * Get config entry (create a new one if not existed)
     * @param key
     * @return
     */
    public ConfigEntry getOrCreateConfig(String key) {
        ConfigEntry entry = getConfig(key);
        if (!ConfigEntry.isValid(entry)) {
            return new ConfigEntry(key, "");
        }
        return entry;
    }

    /**
     * Get config DAO
     * @return
     */
    private ConfigDAO getConfigDAO() {
        configInfo = getConfigConnectionInfo();
        if (configDAOFactory == null) {
            try {
                configDAOFactory = new ConfigDAOFactory(configInfo);
            } catch (DAOException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (configDAOFactory != null) {
            return configDAOFactory.getConfigDAO();
        } else {
            return null;
        }
    }

    /**
     * Build config connection info
     * @return
     */
    private ConnectionInfo getConfigConnectionInfo() {
        if (configInfo == null) {

            try {
                configInfo = new ConnectionInfo(getConfigFilePath(), DAOFactory.DB4O_DATABASE);
            } catch (ArgumentException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return configInfo;
    }

    /**
     * @return the configFilePath
     */
    public String getConfigFilePath() {
        configFilePath = System.getProperty(SHACC_CUSTOM_CONFIG);
        if (configFilePath == null || configFilePath.trim().length() == 0) {
            configFilePath = PathHelper.combine(getHomeDirectory(), CONFIG_FILENAME);
        }
        return configFilePath;
    }
    //</editor-fold>

    /**
     * @return the curFactory
     */
    public static CurrencyFactory getCurrencyFactory() {
        if (curFactory == null) {
            curFactory = new DefaultCurrencyFactory();
        }
        return curFactory;
    }
}
