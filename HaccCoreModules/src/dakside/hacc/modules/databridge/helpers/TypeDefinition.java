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

package dakside.hacc.modules.databridge.helpers;

import dakside.csv.CSVLine;

/**
 * The interface to convert model from/to CSV
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public interface TypeDefinition<T> {

    /**
     * Convert an object from CSV line
     * @param line
     * @return
     */
    T fromCSV(CSVLine line);

    /**
     * Validate a line
     * @param line
     * @return an empty array if no error was raised
     */
    DataError[] validate(CSVLine line, int row);

    /**
     * Get CSV header column list
     * @return
     */
    String[] getHeader();

    /**
     * Get CSV columns to represent object data
     * @param obj
     * @return
     */
    Object[] toCSV(T obj);

    /**
     * this object is existing in database
     * @param obj
     * @return
     */
    boolean exist(T obj);
}
