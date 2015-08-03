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

package dakside.hacc.modules.databridge;

import dakside.hacc.modules.databridge.helpers.Exporter;
import dakside.hacc.modules.databridge.helpers.TypeDefinition;
import dakside.csv.CSVFile;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import dakside.hacc.modules.databridge.csvmodel.TransactionCSV;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ExporterTest {

    public ExporterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of export method, of class Exporter.
     */
    @Test
    public void testExport() {
        System.out.println("export");
        TransactionType type = new TransactionType("FOOD", "buy food", null);
        Transaction[] dataset = {
            new Transaction(10.90, type, "KFC", new Date(), "")
        };
        TypeDefinition definition = new TransactionCSV();
        CSVFile result = Exporter.export(dataset, definition);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertArrayEquals(definition.getHeader(), result.getLines()[0].getElements());
        assertEquals(definition.getHeader().length, result.getLines()[1].size());
    }

}