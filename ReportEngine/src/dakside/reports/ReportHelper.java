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
package dakside.reports;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ReportHelper {

    /**
     * Validate arguments
     * @param args
     * @param types
     * @throws ReportException
     */
    public static void validateArguments(Object[] args, Class... types) throws ReportException {
        if (args == null) {
            throw new ReportException("Invalid argument(s): Null array passed in.");
        } else if (args.length != types.length) {
            throw new ReportException("Invalid argument(s) count.");
        } else {
            for (int i = 0; i < types.length; i++) {
                Class type = types[i];
                if (!type.isInstance(args[i])) {
                    throw new ReportException("Invalid argument type at index " + i);
                }
            }
        }
    }
}
