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
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.db4o.DB4ODAOFactory;
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
public class DAOFactoryTest {

    public DAOFactoryTest() {
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
     * Cannot pass null connectionInfo through getDAOFactory method
     */
    @Test(expected = DAOException.class)
    public void testGetDAOFactory() throws DAOException {
        System.out.println("Test null connection info");
        //test db4o
        int dbType = DAOFactory.DB4O_DATABASE;
        ConnectionInfo connectionInfo = null;
        DAOFactory result = DAOFactory.getDAOFactory(connectionInfo);
    }

    @Test(expected = DAOException.class)
    public void testDB4ODBType() throws DAOException, ArgumentException {
        System.out.println("Test DB4O invalid connection string");
        int dbType = DAOFactory.DB4O_DATABASE;
        ConnectionInfo info = new ConnectionInfo("D:/not/existed/path/mydb.db", dbType);
        DAOFactory result = DAOFactory.getDAOFactory(info);
        assertNotNull(result);
        assertTrue(result instanceof DB4ODAOFactory);
    }

    @Test
    public void testDifferentDBType() throws DAOException, ArgumentException {
        System.out.println("Test different DB type");
        System.out.println("DB4O");
        int dbType = DAOFactory.DB4O_DATABASE;
        ConnectionInfo info = new ConnectionInfo("C:/tmp/mydb.db", dbType);
        DAOFactory result = DAOFactory.getDAOFactory(info);
        assertNotNull(result);
        assertTrue(result instanceof DB4ODAOFactory);

        //XXX other dbms add here later
        System.out.println("Derby");
        dbType = DAOFactory.DERBY_DATABASE;
        info = new ConnectionInfo("D:/connection/toDatabase", dbType);
        result = DAOFactory.getDAOFactory(info);
        assertNull(result);

        System.out.println("My SQL");
        dbType = DAOFactory.MYSQL_DATABASE;
        info = new ConnectionInfo("D:/connection/toDatabase", dbType);
        result = DAOFactory.getDAOFactory(info);
        assertNull(result);

        System.out.println("PGSQL");
        dbType = DAOFactory.PGSQL_DATABASE;
        info = new ConnectionInfo("D:/connection/toDatabase", dbType);
        result = DAOFactory.getDAOFactory(info);
        assertNull(result);

        //invalid id
        System.out.println("Invalid DB id");
        dbType = Integer.MAX_VALUE;
        info = new ConnectionInfo("D:/connection/toDatabase", dbType);
        result = DAOFactory.getDAOFactory(info);
        assertNull(result);
    }
}
