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

/**
 * Configuration entry
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ConfigEntry {

    public static final String LAST_DB_FILE = "LASTDBFILE";
    public static final String BASE_CURRENCY = "BASECURRENCY";
    public static final String LANGUAGE = "Language";

    private String key;
    private Object value;

    public ConfigEntry() {
    }

    public ConfigEntry(String key, Object value) {
        setKey(key);
        setValue(value);
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Get value as string
     * @return an empty string if value is null
     */
    public String getValueString() {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    /**
     * Return value as string
     * @return
     */
    @Override
    public String toString() {
        return getValueString();
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Condition of a valid config entry
     * Not null<br/>
     * key not empty<br/>
     * key cannot only contains blank (space characters)<br/>
     * @param entry
     * @return
     */
    public static boolean isValid(ConfigEntry entry) {
        return entry != null
                && entry.getKey() != null
                && entry.getKey().length() > 0
                && entry.getKey().trim().length() > 0;
    }
}
