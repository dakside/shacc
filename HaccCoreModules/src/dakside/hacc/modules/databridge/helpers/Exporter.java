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

import dakside.csv.CSVFile;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class Exporter {

    private static final Logger logger = Logger.getLogger(Exporter.class.getName());

    public static CSVFile export(Object[] dataset, TypeDefinition definition) {
        CSVFile file = new CSVFile();

        //add header
        CSVHelper.newLine(file, definition.getHeader());
        //convert each item inside data set
        logger.info("Exporting " + dataset + " transactions ...");
        for (Object obj : dataset) {
            CSVHelper.newLine(file, definition.toCSV(obj));
        }

        return file;
    }
}
