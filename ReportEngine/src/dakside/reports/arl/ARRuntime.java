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
package dakside.reports.arl;

import java.io.OutputStream;

/**
 * Active report runtime environment
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ARRuntime extends ReportRuntime {

    ARRuntime(OutputStream output) {
        super(output);
    }

    /**
     * Set variable value by path<br/>
     * E.g.: parent.child.field = value
     * @param variableName
     * @param value
     */
    public void set(String variableName, Object value) {
        //last index = name
        Object obj = getRuntime();
        int idx = variableName.lastIndexOf('.');
        if (idx > 0) {
            obj = get(variableName.substring(0, idx));
            variableName = variableName.substring(idx + 1, variableName.length());
        }
        //get object to set
        ObjectInspector.set(variableName, obj, value);
    }

    /**
     * Get variable value by path<br/>
     * E.g.: parent.child.field
     * @param variableName
     * @return
     */
    public Object get(String variableName) {
        System.out.println("Getting: " + variableName);
        String[] path = variableName.split("\\.");
        Object value = this;
        for (int i = 0; i < path.length; i++) {
            value = ObjectInspector.get(path[i], value);
        }
        return value;
    }
}
