/*
 *  Copyright (C) 2009 Le Tuan Anh
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

package dakside.hacc.core.dao;

import org.dakside.dao.ConnectionInfo;
import org.dakside.exceptions.ArgumentException;
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
public class ConnectionInfoTest {

    public ConnectionInfoTest() {
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
     * Test of getConnectionString method, of class ConnectionInfo.
     */
    @Test(expected=ArgumentException.class)
    public void testConstructor() throws ArgumentException {
        /* connectionInfo should not accept null or empty connection string as
         * constructor's parameter
         */
        System.out.println("test null connection string");
        String connectionString = null;
        new ConnectionInfo(connectionString);
    }

    @Test
    public void testConstructorCorrect() throws ArgumentException{
        System.out.println("test valid connection string");
        String connectionString = "d:/mydb/database.db";
        
        ConnectionInfo instance = new ConnectionInfo(connectionString);
        String actual = instance.getConnectionString();
        String expected = "d:/mydb/database.db";


        assertEquals(expected, actual);
    }
}