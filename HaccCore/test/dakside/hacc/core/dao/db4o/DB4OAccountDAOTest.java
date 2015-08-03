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
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountPeriod;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DB4O Account DAO test
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DB4OAccountDAOTest {

    public static DAOFactory factory = null;

    public DB4OAccountDAOTest() {
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
     * Cannot save null account
     */
    @Test(expected = ArgumentException.class)
    public void testSaveAccount() throws ArgumentException {
        //factory object is created @beforeClass of Db4oSuite
        System.out.println("Cannot save null account");
        Account account = null;
        AccountDAO dao = factory.getAccountDAO();
        dao.save(account);

        //test save account
        System.out.println("Test save account");
        Account acc = dao.getAccountByCode("TESTCODE");
        if (acc == null) {
            acc = new Account("TESTCODE", "Test account", Account.TYPE_FOLDER);
        }
        dao.save(acc);

        System.out.println("test get account by code");
        Account acc1 = dao.getAccountByCode("TESTCODE");
        assertNotNull(acc1);

        System.out.println("Test delete account");
        dao.delete(acc1);

        System.out.println("Now the account should disappear");
        acc1 = dao.getAccountByCode("TESTCODE");
        assertNull(acc1);

        factory.shutdown();
    }

    /**
     * Test of getAllAccounts method, of class DB4OAccountDAO.
     */
    @Test
    public void testGetAllAccounts() {
        //factory object is created @beforeClass of Db4oSuite
        System.out.println("Test get all accounts");
        AccountDAO dao = factory.getAccountDAO();
        Account[] accounts = dao.getAllAccounts();
        assertNotNull(accounts);
        System.out.println("Count items: " + accounts.length);

        factory.shutdown();
    }

    @Test
    public void testGetAccountByCode() {
        //factory object is created @beforeClass of Db4oSuite
        System.out.println("Test get null code");
        AccountDAO dao = factory.getAccountDAO();
        Account account = dao.getAccountByCode(null);
        assertNull(account);

        System.out.println("Test get not existed code");
        dao = factory.getAccountDAO();
        account = dao.getAccountByCode("not existed code");
        assertNull(account);

        factory.shutdown();
    }

    /**
     * Test of deleteAccount method, of class DB4OAccountDAO.
     */
    @Test(expected = ArgumentException.class)
    public void testDeleteAccount() throws ArgumentException {
        System.out.println("cannot delete null account");
        Account account = null;
        AccountDAO instance = factory.getAccountDAO();
        instance.delete(account);

        factory.shutdown();
    }

    @Test(expected = ArgumentException.class)
    public void testSavePeriod() throws ArgumentException {
        System.out.println("Test save account period");
        AccountPeriod ap = null;
        AccountDAO instance = factory.getAccountDAO();
        instance.save(ap);
    }

    @Test
    public void testGetAllAccountPeriods() throws ArgumentException {
        System.out.println("Test get all account periods");

        //no ap
        AccountDAO instance = factory.getAccountDAO();
        AccountPeriod[] aplist = instance.getAllAccountPeriods();
        assertNotNull(aplist);
        assertEquals(0, aplist.length);

        //now we create a new ap to test select all
        AccountPeriod ap = new AccountPeriod("UN");
        instance.save(ap);
        factory.shutdown();

        //select again
        instance = factory.getAccountDAO();
        aplist = instance.getAllAccountPeriods();
        assertNotNull(aplist);
        assertEquals(1, aplist.length);
        assertTrue(ap.equals(aplist[0]));

        factory.shutdown();
    }

    @Test
    public void testDeleteAccountPeriod() throws ArgumentException {
        AccountDAO instance = factory.getAccountDAO();
        AccountPeriod[] aplist = instance.getAllAccountPeriods();
        assertNotNull(aplist);

        //for each account period -> delete it
        for(AccountPeriod ap : aplist){
            instance.delete(ap);
        }

        factory.shutdown();

        instance = factory.getAccountDAO();
        aplist = instance.getAllAccountPeriods();
        assertNotNull(aplist);
        assertEquals(0, aplist.length);

        factory.shutdown();
    }
}
