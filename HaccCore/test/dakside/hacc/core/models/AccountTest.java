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
package dakside.hacc.core.models;

import org.dakside.exceptions.ArgumentException;
import java.util.ArrayList;
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
public class AccountTest {

    public AccountTest() {
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
     * Test of getTypeString method, of class Account.
     */
    @Test
    public void testGetTypeString() throws ArgumentException {
        System.out.println("folder type");
        Account instance = new Account("TESTCODE", "Test Account", Account.TYPE_FOLDER);

        String expResult = "Folder";
        String result = instance.getTypeString();
        assertEquals(expResult, result);

        instance.setType(Account.TYPE_CREDIT);
        result = instance.getTypeString();
        expResult = "Credit";
        assertEquals(expResult, result);

        instance.setType(Account.TYPE_DEBIT);
        result = instance.getTypeString();
        expResult = "Debit";
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class Account.
     */
    @Test(expected = ArgumentException.class)
    public void testSetType() throws ArgumentException {
        System.out.println("setType");
        Account instance = new Account();
        instance.setType(999);
    }

    /**
     * Test of toString method, of class Account.
     */
    @Test
    public void testToString() throws ArgumentException {
        System.out.println("toString");
        Account instance = new Account("CODE", "NAME", Account.TYPE_FOLDER);
        String expResult = "CODE - NAME";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChildren method, of class Account.
     */
    @Test
    public void testGetChildren() {
        System.out.println("by default should get an instance of HashSet<Account>");
        Account instance = new Account();
        ArrayList<Account> result = instance.getChildren();
        assertNotNull(result);
    }

    /**
     * Test of getChildrenArray method, of class Account.
     */
    @Test
    public void testGetChildrenArray() throws ArgumentException {
        System.out.println("get sorted children");
        Account instance = new Account();
        Account[] expResult = new Account[0];
        Account[] result = instance.getChildrenArray();
        assertArrayEquals(expResult, result);

        System.out.println("Test sorting function");
        Account[] accounts = new Account[]{
            new Account("2", "def", Account.TYPE_FOLDER),
            new Account("3", "xyz", Account.TYPE_FOLDER),
            new Account("1", "abc", Account.TYPE_FOLDER)
        };

        instance.getChildren().add(accounts[0]);
        instance.getChildren().add(accounts[1]);
        instance.getChildren().add(accounts[2]);

        Account[] results = instance.getChildrenArray();
        assertEquals(results[0], accounts[2]);
        assertEquals(results[1], accounts[0]);
        assertEquals(results[2], accounts[1]);
    }

    /**
     * Test of compareTo method, of class Account.
     */
    @Test
    public void testCompareTo() throws ArgumentException {
        System.out.println("compareTo a null account");
        Account o = null;
        Account instance = new Account();
        int expResult = 1;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);

        System.out.println("Compare to an ABC code");
        o = new Account("ABC", "ABC ACc", Account.TYPE_CREDIT);
        instance = new Account("3", "My account", Account.TYPE_CREDIT);

        expResult = 1;
        result = instance.compareTo(o);
        System.out.println("result = " + result);
        assertTrue(expResult <= result);

        System.out.println("reverse the order");
        expResult = -1;
        result = o.compareTo(instance);
        System.out.println("result = " + result);
        assertTrue(expResult >= result);

        System.out.println("compare equals");
        o = new Account("CODE1", "NAME", Account.TYPE_FOLDER);
        instance = new Account("CODE1", "NAME DIFF", Account.TYPE_CREDIT);
        expResult = 0;
        result = o.compareTo(instance);
        System.out.println("result = " + result);
        assertTrue(expResult == result);
    }

    /**
     * Test of getCode method, of class Account.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCode method, of class Account.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = "";
        Account instance = new Account();
        instance.setCode(code);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Account.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Account.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Account instance = new Account();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class Account.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Account instance = new Account();
        int expResult = 0;
        int result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newChild method, of class Account.
     */
    @Test
    public void testNewChild() throws Exception {
        System.out.println("newChild");
        String code = "";
        String name = "";
        int type = 0;
        Account instance = new Account();
        Account expResult = null;
        Account result = instance.newChild(code, name, type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrency method, of class Account.
     */
    @Test
    public void testGetCurrency() {
        System.out.println("getCurrency");
        Account instance = new Account();
        Currency expResult = null;
        Currency result = instance.getCurrency();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrency method, of class Account.
     */
    @Test
    public void testSetCurrency() {
        System.out.println("setCurrency");
        Currency currency = null;
        Account instance = new Account();
        instance.setCurrency(currency);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCrAmount method, of class Account.
     */
    @Test
    public void testGetCrAmount() {
        System.out.println("getCrAmount");
        Account instance = new Account();
        double expResult = 0.0;
        double result = instance.getCrAmount();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCrAmount method, of class Account.
     */
    @Test
    public void testSetCrAmount() {
        System.out.println("setCrAmount");
        double crAmount = 0.0;
        Account instance = new Account();
        instance.setCrAmount(crAmount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDrAmount method, of class Account.
     */
    @Test
    public void testGetDrAmount() {
        System.out.println("getDrAmount");
        Account instance = new Account();
        double expResult = 0.0;
        double result = instance.getDrAmount();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDrAmount method, of class Account.
     */
    @Test
    public void testSetDrAmount() {
        System.out.println("setDrAmount");
        double drAmount = 0.0;
        Account instance = new Account();
        instance.setDrAmount(drAmount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of debit method, of class Account.
     */
    @Test
    public void testDebit() {
        System.out.println("debit");
        double amount = 0.0;
        Account instance = new Account();
        instance.debit(amount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of credit method, of class Account.
     */
    @Test
    public void testCredit() {
        System.out.println("credit");
        double amount = 0.0;
        Account instance = new Account();
        instance.credit(amount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBalance method, of class Account.
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");
        Account instance = new Account();
        double expResult = 0.0;
        double result = instance.getBalance();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clone method, of class Account.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        Account instance = new Account();
        Account expResult = null;
        Account result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
