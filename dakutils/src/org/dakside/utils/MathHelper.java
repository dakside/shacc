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
package org.dakside.utils;

/**
 * Adavance Math helper
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class MathHelper {

    /**
     * Try parse string to integer
     * @param s
     * @param defaultValue
     * @return an integer or default value
     */
    public static int tryParse(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
        }
        return defaultValue;
    }
}
