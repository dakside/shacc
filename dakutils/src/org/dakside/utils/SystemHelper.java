/*
 * Copyright (C) 2014 Le Tuan Anh <tuananh.ke@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dakside.utils;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class SystemHelper {

    private static final Logger logger = Logger.getLogger(SystemHelper.class.getName());

    /**
     * Get a custom config from command line</br>
     * E.g.:<br/>
     * java -DMY_VALUE=LoveIsLove -jar myapp.jar<br/>
     * The key will be "MY_VALUE" and value is "LoveIsLove"<br/>
     * Be aware of the part "-D" before the key. D is NOT a part of the key's
     * name</br>
     * Plus, any -D option must appear BEFORE the -jar option since they are VM
     * options, not application arguments.
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getTextFromVMOption(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (Validator.isValid(value)) {
            return value;
        } else {
            return defaultValue;
        }
    }

    /**
     * Read a custom text from a file
     *
     * @param key
     * @return null if the file couldn't be found
     */
    public static String getTextFromCustomFile(String key) {
        String filename = getTextFromVMOption(key, "");
        try {
            return FileUtil.read(filename);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a properties file by key<br/>
     *
     * @param key the name of the config<br/>
     * E.g.: java -DMYFILE=conf.properties -jar myapp.jar<br/>
     * Then key will be MYFILE
     * @return a properties object which contains every properties item or null
     * if the file content couldn't be read.
     */
    public static Properties getPropertiesFromCustomFile(String key) {
        try {
            Properties props = new Properties();
            String configValue = getTextFromVMOption(key, "");
            String absPath = Paths.get(configValue).toAbsolutePath().toString();
            logger.log(Level.INFO, "I''m trying to load a file here: {0}", absPath);
            props.load(new FileReader(absPath));
            return props;
        } catch (Exception ex) {
            Logger.getLogger(SystemHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Get home folder of current user
     *
     * @return
     */
    public static String getHomeFolder() {
        // TODO: this should be tested on Linux, Windows and Mac
        return System.getProperty("user.home");
    }

    /**
     * Get line separator (E.g. \r\n)
     * @return 
     */
    public static String getLineSeparator() {
        return SystemHelper.getLineSeparator();
    }

    /**
     * Get temporary directory
     * @return 
     */
    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Get path separator character (should be \ in Windows and / in Linux)
     * @return 
     */
    public static String getPathSeparator() {
        return System.getProperty("file.separator");
    }
}
