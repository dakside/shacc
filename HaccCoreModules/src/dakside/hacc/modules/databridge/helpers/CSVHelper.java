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

import dakside.csv.CSVException;
import dakside.csv.CSVFile;
import dakside.csv.CSVFileWriter;
import dakside.csv.CSVLine;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * CSV helpers
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class CSVHelper {

    private static final Logger logger = Logger.getLogger(CSVHelper.class.getName());

    /**
     * export JTable's data to CSV
     * @param table
     * @return
     */
    public static CSVFile toCSV(JTable table) {
        CSVFile csv = new CSVFile();

        if (table != null || table.getModel() != null) {
            TableModel model = table.getModel();
            for (int row = 0; row < table.getRowCount(); row++) {
                CSVLine line = csv.newLine();
                for (int col = 0; col < table.getColumnCount(); col++) {
                    line.add(table.getValueAt(row, col));
                }
            }
        }

        return csv;
    }

    public static CSVLine newLine(CSVFile file, Object[] data) {
        CSVLine line = file.newLine();
        for (Object obj : data) {
            line.add(obj);
        }
        return line;
    }

    /**
     * Save csv data to a file
     * @param csv
     * @param file
     */
    public static boolean save(CSVFile csv, File file) {
        try {
            CSVFileWriter writer = new CSVFileWriter(file);
            writer.writeFile(csv);
            writer.close();
            logger.info("Exported csv data to " + file.getAbsolutePath());
            return true;
        } catch (CSVException ex) {
            logger.log(Level.WARNING, "Cannot save csv file to " + file.getAbsolutePath(), ex);
            return false;
        }
    }
}
