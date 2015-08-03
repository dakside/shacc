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
import dakside.hacc.core.models.Transaction;
import dakside.hacc.modules.databridge.helpers.TypeDefinition;
import java.io.File;
import java.util.ArrayList;
import javax.swing.SwingWorker;

/**
 * Convert CSV data to transaction object[]
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ConversionWorker extends SwingWorker<Transaction[], Void> {

    File f = null;
    TypeDefinition typeDef = null;

    public ConversionWorker(File f, TypeDefinition typeDef) {
        this.f = f;
        this.typeDef = typeDef;
    }

    @Override
    protected Transaction[] doInBackground() throws Exception {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        CSVFileReader reader = new CSVFileReader(f);

        CSVLine line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            Transaction t = (Transaction) typeDef.fromCSV(line);
            transactions.add(t);
        }
        return transactions.toArray(new Transaction[0]);
    }
}
