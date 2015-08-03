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
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.core.models.TransactionType;
import org.dakside.utils.DateTimeHelper;
import java.util.Date;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DrCrRenderer extends DefaultTableCellRenderer {

    public DrCrRenderer() {
        this.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public void setValue(Object value) {

        if (value instanceof Date) {
            String s = DateTimeHelper.toStringSmart((Date) value);
            setText(s);
        } else if (value instanceof TransactionType) {
            TransactionType trx = (TransactionType) value;
            setText(getString(trx.getCode()));
        } else if (value instanceof Double || value instanceof Float) {
            setText(getString(value));
        }
    }

    private static String getString(Object obj) {
        return (obj == null || new Double(0).equals(obj)) ? "" : obj.toString();
    }
}
