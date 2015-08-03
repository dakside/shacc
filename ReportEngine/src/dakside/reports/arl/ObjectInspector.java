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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ObjectInspector {

    private static final Logger logger = Logger.getLogger(ObjectInspector.class.getName());

    public static Object get(Object var, Object obj) {
        if (obj instanceof ARRuntime && var instanceof String) {
            return ((ARRuntime) obj).getRuntimeVariable((String) var);
        } else if (obj != null && var instanceof String) {
            return invoke(obj, (String) var);
        } else {
            return null;
        }
    }

    /**
     * get from array
     * @param arr
     * @param index
     * @return
     */
    private static Object get(Object[] arr, Object index) {
        //try to get as index
        if (index instanceof Integer) {
            try {
                return arr[(Integer) index];
            } catch (IndexOutOfBoundsException ex) {
                return null;
            }
        } else if (index instanceof String) {
            try {
                int idx = Integer.parseInt((String) index);
                return arr[idx];
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Invoke method or read property
     * @param obj
     * @param name
     * @return
     */
    public static Object invoke(Object obj, String name) {
        //XXX (hard code for this version)
        try {
            if (name.endsWith("()")) {
                //this is a method
                //get field
                Method m = obj.getClass().getDeclaredMethod(name.substring(0, name.length() - 2));
                m.setAccessible(true);
                return m.invoke(obj);
            } else {
                //get field
                Field f = obj.getClass().getDeclaredField(name);
                f.setAccessible(true);
                return f.get(obj);
            }
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (InvocationTargetException ex) {
            return null;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (SecurityException ex) {
            return null;
        }
    }

    public static void set(Object variableName, Object obj, Object value) {
        //        logger.info("getting " + var);
        if (obj instanceof Map) {
            ((Map) obj).put(variableName, value);
        } else if (obj instanceof Object[]) {
            //try to get as index
            Object[] arr = (Object[]) obj;
            if (variableName instanceof Integer) {
                try {
                    arr[(Integer) variableName] = value;
                } catch (IndexOutOfBoundsException ex) {
                }
            } else if (variableName instanceof String) {
                try {
                    int idx = Integer.parseInt((String) variableName);
                    arr[idx] = value;
                } catch (Exception ex) {
                }
            }
        } else if (obj != null && variableName instanceof String) {
            try {
                //get field
                Field f = obj.getClass().getDeclaredField((String) variableName);
                f.setAccessible(true);
                f.set(obj, value);
            } catch (IllegalArgumentException ex) {
            } catch (IllegalAccessException ex) {
            } catch (NoSuchFieldException ex) {
            } catch (SecurityException ex) {
            }
        }
    }
}
