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
package dakside.hacc.core.dao.db4o;

import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.TransactionDAO;
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
public class DB4ODAOFactoryTest {

    public static DAOFactory factory = null;

    public DB4ODAOFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = Db4oSuite.buildFactory();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        factory.shutdown();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAccountDAO method, of class DB4ODAOFactory.
     */
    @Test
    public void testGetAccountDAO() throws DAOException {
        //factory object is created @beforeClass of Db4oSuite
        AccountDAO dao = factory.getAccountDAO();
        assertNotNull(dao);
        assertTrue(dao instanceof DB4OAccountDAO);

    }

    /**
     * Test of getTransactionDAO method, of class DB4ODAOFactory.
     */
    @Test
    public void testGetTransactionDAO() throws DAOException {
        //factory object is created @beforeClass
        TransactionDAO dao = factory.getTransactionDAO();
        assertNotNull(dao);
        assertTrue(dao instanceof DB4OTransactionDAO);
    }
}
