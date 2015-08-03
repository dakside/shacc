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

import java.text.MessageFormat;

/**
 * Data error information
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DataError {

    private int row;
    private int column;
    private String description;

    public DataError(String description) {
        setDescription(description);
    }

    public DataError(int row, int column, String description) {
        setRow(row);
        setColumn(column);
        setDescription(description);
    }

    public DataError(int row, int column, String description, Object[] messageArgs) {
        setRow(row);
        setColumn(column);
        //XXX localization
        setDescription(MessageFormat.format(description, messageArgs));
    }

    @Override
    public String toString() {
        return MessageFormat.format("[row:{0} col:{1} - {2}", row, column, description);
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
