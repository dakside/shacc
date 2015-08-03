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
package dakside.hacc.modules.databridge.csvmodel;

import dakside.csv.CSVLine;
import dakside.hacc.core.config.Configuration;
import org.dakside.dao.ConnectionInfo;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.test.helpers.Dumper;
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
public class TransactionCSVTest {

    TransactionCSV instance = null;

    public TransactionCSVTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //connect to database for lookup transaction types
        String conn =
                Configuration.getInstance().getLastDatabaseFile();
        Configuration.getInstance().setInfo(new ConnectionInfo(conn));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new TransactionCSV();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getHeader method, of class TransactionCSV.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        String[] result = instance.getHeader();
        assertNotNull(result);
    }

    /**
     * Test of toCSV method, of class TransactionCSV.
     */
    @Test
    public void testToCSV() {
        System.out.println("toCSV");
        Transaction obj = new Transaction();
        Object[] result = instance.toCSV(obj);
        assertNotNull(obj);
    }

    /**
     * Test of fromCSV method, of class TransactionCSV.
     */
    @Test
    public void testFromCSV() {
        System.out.println("fromCSV");
        CSVLine line = null;
        Object result = instance.fromCSV(line);
        assertNull(result);

        line = new CSVLine().add(10.90).add("SGD").add("KFC").
                add("10/01/2010").
                add("FOOD").
                add("");
        result = instance.fromCSV(line);

        System.out.println("[Start dump result object ]=========");
        Dumper.dump(result, true, true, 0);
        System.out.println("[End of dump result object]========");
        assertNotNull(result);
        assertTrue(result instanceof Transaction);

    }
}
