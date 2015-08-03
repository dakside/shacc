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
package dakside.hacc.test.helpers;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dump model information<br/>
 * Depp dump only dump types in package dakside.**
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class Dumper {

    /**
     * Force to dump an object (all fields) (not deep dump)<br/>
     * All fields with details will be written to console
     * @param obj
     */
    public static void dump(Object obj) {
        dump(obj, true, false, 0);
    }

    public static void dump(Object obj, boolean forceDump, boolean deepDump, int level) {
        if (obj == null) {
            println("obj = null", level);
        } else {
            println("obj instance of " + obj.getClass().getName(), level);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                dumpField(obj, field, forceDump, deepDump, level);
            }
        }
    }

    private static void fillTab(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("\t");
        }
    }

    private static void println(String msg, int level) {
        fillTab(level);
        System.out.println(msg);
    }

    /**
     * Dump a field of an object
     * @param obj
     * @param field
     * @param forceDump
     * @param deepDump
     * @return
     */
    private static void dumpField(Object obj, Field field, boolean forceDump, boolean deepDump, int level) {
        if (forceDump && !field.isAccessible()) {
            try {
                field.setAccessible(true);
            } catch (SecurityException ex) {
                return;
            }
        }
        try {
            if (field.isAccessible()) {
                Object fieldData = field.get(obj);
                println(field.getName() + ": " + fieldData, level);
                if (deepDump && fieldData instanceof Object
                        && fieldData.getClass().getPackage().getName().startsWith("dakside")) {
                    dump(fieldData, true, false, level + 1);
                }
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Dumper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Dumper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
