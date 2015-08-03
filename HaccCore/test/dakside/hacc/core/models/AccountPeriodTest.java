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
public class AccountPeriodTest {

    public AccountPeriodTest() {
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
     * Test of getStatusString method, of class AccountPeriod.
     */
    @Test
    public void testGetStatusString() throws ArgumentException {
        System.out.println("getStatusString");
        AccountPeriod instance = new AccountPeriod();
        //by default is Opening
        String expResult = "Opening";
        String result = instance.getStatusString();
        assertEquals(expResult, result);

        instance.setStatus(AccountPeriod.CLOSED);
        expResult = "Closed";
        result = instance.getStatusString();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStatus method, of class AccountPeriod.
     */
    @Test(expected=ArgumentException.class)
    public void testSetStatus() throws Exception {
        System.out.println("Test set status for account period");
        //test opening
        int status = AccountPeriod.OPENING;
        AccountPeriod instance = new AccountPeriod();
        instance.setStatus(status);
        assertEquals(status, instance.getStatus());
        //test closed
        status = AccountPeriod.CLOSED;
        instance.setStatus(status);
        assertEquals(status, instance.getStatus());
        //test invalid
        status = -1;
        instance.setStatus(status);
    }

    /**
     * Test of toString method, of class AccountPeriod.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        AccountPeriod instance = new AccountPeriod();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class AccountPeriod.
     */
    @Test
    public void testClone() throws ArgumentException {
        System.out.println("test clone");
        AccountPeriod instance = new AccountPeriod("Myself", new Date(), new Date());
        AccountPeriod clonedInstance = instance.clone();
        assertNotSame(instance, clonedInstance);
        assertTrue(instance.equals(clonedInstance));
    }
}
