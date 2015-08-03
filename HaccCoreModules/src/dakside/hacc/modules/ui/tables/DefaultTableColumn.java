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

package dakside.hacc.modules.ui.tables;

import org.dakside.duck.flexui.tables.ColumnDefinition;
import dakside.hacc.core.models.AccountEntry;
import javax.swing.table.TableCellRenderer;

/**
 * A default read-only column<br/>
 * Can override setValue method to make it editable
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public abstract class DefaultTableColumn<T> implements ColumnDefinition<T> {

        private String name;
        private TableCellRenderer renderer = new EasyCellRenderer();

        public DefaultTableColumn(String name) {
            this.name = name;
        }

        public DefaultTableColumn(String name, TableCellRenderer renderer) {
            this.name = name;
            this.renderer = renderer;
        }

        public String getName() {
            return name;
        }

        public void setValue(T object, Object value) {
            //do nothing
        }

        public TableCellRenderer getRenderer() {
            return renderer;
        }
    }
