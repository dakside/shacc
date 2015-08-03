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
package dakside.hacc.core.plugins;

import java.awt.Component;
import javax.swing.Icon;
import org.dakside.duck.plugins.AppTab;

/**
 * Dummy Module class for testing purpose
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
class DummyModule implements AppTab {

    public Icon getIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Component getView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unload() {
    }
}
