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

import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DB4OTransactionDAOTest {

    public static DAOFactory factory = null;

    public DB4OTransactionDAOTest() {
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
        try {
            factory.getTransactionDAO().delete(factory.getTransactionDAO().getAllTransactions());
        } catch (DAOException ex) {
            Logger.getLogger(DB4OTransactionDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Cannot save null trx
     */
    @Test(expected = DAOException.class)
    public void testSaveTransaction() throws DAOException {
        System.out.println("Cannot save null trx");
        Transaction transaction = null;
        TransactionDAO dao = factory.getTransactionDAO();
        dao.save(transaction);
    }

    /**
     * Test of getAllTransactions method, of class DB4OTransactionDAO.
     */
    @Test
    public void testGetAllTransactions() throws DAOException {
        System.out.println("Test select all trx");
        TransactionDAO dao = factory.getTransactionDAO();
        Transaction[] transactions = dao.getAllTransactions();
        assertNotNull(transactions);
        System.out.println("Count: " + transactions.length);
    }

    /**
     * Cannot delete null trx
     */
    @Test(expected = DAOException.class)
    public void testDeleteTransaction() throws DAOException {
        System.out.println("Cannot delete null trx");
        Transaction transaction = null;
        TransactionDAO dao = factory.getTransactionDAO();
        dao.delete(transaction);
    }

    /**
     * Test of save method, of class DB4OTransactionDAO.
     */
    @Test(expected = DAOException.class)
    public void testSave_Transaction() throws Exception {
        System.out.println("save");
        TransactionDAO dao = factory.getTransactionDAO();
        Transaction trx = null;
        dao.save(trx);
    }

    /**
     * Test of delete method, of class DB4OTransactionDAO.
     */
    @Test(expected = DAOException.class)
    public void testDelete_Transaction() throws Exception {
        System.out.println("delete");
        Transaction transaction = null;
        TransactionDAO instance = factory.getTransactionDAO();
        boolean result = instance.delete(transaction);
    }

    /**
     * Test of delete method, of class DB4OTransactionDAO.
     */
    @Test(expected=DAOException.class)
    public void testDelete_TransactionArr() throws Exception {
        System.out.println("delete");
        Transaction[] transactions = null;
        TransactionDAO instance = factory.getTransactionDAO();
        boolean expResult = false;
        boolean result = instance.delete(transactions);
    }

    /**
     * Test of save method, of class DB4OTransactionDAO.
     */
    @Test(expected=DAOException.class)
    public void testSave_TransactionType() throws Exception {
        System.out.println("save");
        TransactionType transactionType = null;
        TransactionDAO instance = factory.getTransactionDAO();
        instance.save(transactionType);
    }

    /**
     * Test of delete method, of class DB4OTransactionDAO.
     */
    @Test(expected=DAOException.class)
    public void testDelete_TransactionType() throws Exception {
        System.out.println("delete");
        TransactionType transactionType = null;
        TransactionDAO instance = factory.getTransactionDAO();
        boolean expResult = false;
        boolean result = instance.delete(transactionType);
    }

    /**
     * Test of getTransactions method, of class DB4OTransactionDAO.
     */
    @Test
    public void testGetTransactions() throws Exception {
        System.out.println("Test getTransactions with type, posted, dateFrom, dateTo");
        TransactionDAO dao = factory.getTransactionDAO();
        //prepare test data
        TransactionType type = dao.getTransactionTypeByCode("CASH");
        Transaction trx = new Transaction(10, type, "Test", new Date(), null);
        dao.save(trx);

        type = null;
        Boolean posted = null;
        Date dateFrom = null;
        Date dateTo = null;
        Transaction[] result = dao.getTransactions(type, posted, dateFrom, dateTo);
        assertNotNull(result);
        assertEquals("All NULL - Should have 1 record", 1, result.length);

        //test date From
        Calendar d = Calendar.getInstance();
        d.add(Calendar.DAY_OF_MONTH, -1);
        dateFrom = d.getTime();

        result = dao.getTransactions(type, posted, dateFrom, dateTo);
        assertNotNull(result);
        assertEquals("(DateFrom=now-1hr) - Should have 1 record", 1, result.length);

        //test date To
        d = Calendar.getInstance();
        d.add(Calendar.DAY_OF_MONTH, 1);
        dateTo = d.getTime();
        dateFrom = null;
        result = dao.getTransactions(type, posted, dateFrom, dateTo);
        assertNotNull(result);
        assertEquals("(DateTo=now+1hr) - Should have 1 record", 1, result.length);

        //test date To
        d = Calendar.getInstance();
        d.add(Calendar.DAY_OF_MONTH, -1);
        dateTo = d.getTime();
        dateFrom = null;
        result = dao.getTransactions(type, posted, dateFrom, dateTo);
        assertNotNull(result);
        assertEquals("(DateTo=now-1hr) - Results should be empty", 0, result.length);
    }

    /**
     * Test of getTransactionTypeByCode method, of class DB4OTransactionDAO.
     */
    public void testGetTransactionTypeByCode() {
        System.out.println("getTransactionTypeByCode");
        String code = "";
        TransactionDAO instance = factory.getTransactionDAO();
        TransactionType expResult = null;
        TransactionType result = instance.getTransactionTypeByCode(code);
        assertEquals(expResult, result);
    }

    /**
     * Test of save method, of class DB4OTransactionDAO.
     */
    @Test(expected=DAOException.class)
    public void testSave_TransactionArr() throws Exception {
        System.out.println("save");
        Transaction[] trx = null;
        TransactionDAO instance = factory.getTransactionDAO();
        int expResult = -1;
        int result = instance.save(trx);
        assertEquals(expResult, result);
    }

    /**
     * Test of isPosted method, of class DB4OTransactionDAO.
     */
    @Test(expected=DAOException.class)
    public void testIsPosted() throws Exception {
        System.out.println("isPosted");
        Transaction trx = null;
        TransactionDAO instance = factory.getTransactionDAO();
        boolean expResult = false;
        boolean result = instance.isPosted(trx);
    }
}
