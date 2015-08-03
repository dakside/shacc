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
package dakside.hacc.modules.databridge.dataagent;

import dakside.csv.CSVFileReader;
import dakside.csv.CSVLine;
import dakside.hacc.modules.databridge.helpers.DataError;
import dakside.hacc.modules.databridge.helpers.TypeDefinition;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ValidatorWorker extends SwingWorker<DataError[], Void> {

    File f = null;
    TypeDefinition typeDef = null;

    private static final Logger logger = Logger.getLogger(ValidatorWorker.class.getName());

    public ValidatorWorker(File f, TypeDefinition typeDef) {
        this.f = f;
        this.typeDef = typeDef;
    }

    @Override
    protected DataError[] doInBackground() throws Exception {
        ArrayList<DataError> errors = new ArrayList<DataError>();
        try {
            CSVFileReader reader = new CSVFileReader(f);
            CSVLine line = reader.readLine(); //ignore first row
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                Collections.addAll(errors, typeDef.validate(line, lineNum));
            }
            logger.info("Readed: " + lineNum);
            logger.info("Error : " + errors.size());
            //log all errors
            for(DataError err : errors){
                logger.warning(err.toString());
            }
        } catch (Exception ex) {
            errors.add(new DataError("File cannot be read: " + ex.getLocalizedMessage()));
//            ex.printStackTrace();
        }
        return errors.toArray(new DataError[0]);
    }
}
