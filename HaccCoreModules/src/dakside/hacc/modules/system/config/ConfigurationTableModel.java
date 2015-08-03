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
package dakside.hacc.modules.system.config;

import org.dakside.duck.flexui.tables.ObjectTableModel;
import dakside.hacc.core.config.ConfigEntry;
import dakside.hacc.modules.ui.tables.DefaultTableColumn;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ConfigurationTableModel extends ObjectTableModel<ConfigEntry> {

    public String[] columnNames = {"Key", "Value"};

    public ConfigurationTableModel() {
        //setup columns
        this.columns.add(new DefaultTableColumn<ConfigEntry>("Key") {

            public Object getValue(ConfigEntry object) {
                return object.getKey();
            }

            @Override
            public void setValue(ConfigEntry object, Object value) {
                object.setKey(value.toString());
            }
        });
        this.columns.add(new DefaultTableColumn<ConfigEntry>("Value") {

            public Object getValue(ConfigEntry object) {
                return object.getValue();
            }

            @Override
            public void setValue(ConfigEntry object, Object value) {
                object.setValue(value);
            }
        });
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount() || columnIndex < 0 || columnIndex >= getColumnCount()) {
            return false;
        } else {
            //only value column can be modified
            return (columnIndex == 1);
        }
    }
}
