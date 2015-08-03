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
package dakside.utils;

import org.dakside.utils.DateTimeHelper;
import java.util.Calendar;
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
public class DateTimeHelperTest {

    public DateTimeHelperTest() {
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
     * Test of toDate method, of class DateTimeHelper.
     */
    @Test
    public void testToDate_String() {
        System.out.println("toDate");
        String val = "";
        Date expResult = null;
        Date result = DateTimeHelper.toDate(val);
        assertEquals(expResult, result);
    }

    /**
     * Test of toDate method, of class DateTimeHelper.
     */
    @Test
    public void testToDate_String_String() {
        System.out.println("toDate");
        String val = "";
        String format = "";
        Date expResult = null;
        Date result = DateTimeHelper.toDate(val, format);
        assertEquals(expResult, result);
    }

    /**
     * Test of toDateStart method, of class DateTimeHelper.
     */
    @Test
    public void testToDateStart_String() {
        System.out.println("toDateStart");
        String val = "";
        Date expResult = null;
        Date result = DateTimeHelper.toDateStart(val);
        assertEquals(expResult, result);
    }

    /**
     * Test of toDateEnd method, of class DateTimeHelper.
     */
    @Test
    public void testToDateEnd_String() {
        System.out.println("toDateEnd");
        String val = "";
        Date expResult = null;
        Date result = DateTimeHelper.toDateEnd(val);
        assertEquals(expResult, result);
    }

    /**
     * Test of toDateStart method, of class DateTimeHelper.
     */
    @Test
    public void testToDateStart_Date() {
        System.out.println("toDateStart");
        Date date = null;
        Date expResult = null;
        Date result = DateTimeHelper.toDateStart(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of toDateEnd method, of class DateTimeHelper.
     */
    @Test
    public void testToDateEnd_Date() {
        System.out.println("toDateEnd");
        Date date = null;
        Date expResult = null;
        Date result = DateTimeHelper.toDateEnd(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMonth method, of class DateTimeHelper.
     */
    @Test
    public void testGetMonth() {
        System.out.println("getMonth");
        Date date = null;
        String expResult = "";
        String result = DateTimeHelper.getMonth(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class DateTimeHelper.
     */
    @Test
    public void testToString_Date() {
        System.out.println("toString");
        Date val = null;
        String expResult = "";
        String result = DateTimeHelper.toString(val);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class DateTimeHelper.
     */
    @Test
    public void testToString_Date_String() {
        System.out.println("toString");
        Date val = null;
        String format = "";
        String expResult = "";
        String result = DateTimeHelper.toString(val, format);
        assertEquals(expResult, result);
    }

    /**
     * Test of thisYear method, of class DateTimeHelper.
     */
    @Test
    public void testThisYear() {
        System.out.println("thisYear");
        int expResult = 2010;
        int result = DateTimeHelper.thisYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringSmart method, of class DateTimeHelper.
     */
    @Test
    public void testToStringSmart() {
        System.out.println("toStringSmart");
        Date transactionDate = null;
        String expResult = "";
        String result = DateTimeHelper.toStringSmart(transactionDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of isBetween method, of class DateTimeHelper.
     */
    @Test
    public void testIsBetween() {
        System.out.println("isBetween");
        Date compareDate = null;
        Date dateFrom = null;
        Date dateTo = null;
        boolean expResult = false;
        boolean result = DateTimeHelper.isBetween(compareDate, dateFrom, dateTo);
        assertEquals(expResult, result);

        Calendar c = Calendar.getInstance();
        compareDate = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        dateTo = c.getTime();

        result = DateTimeHelper.isBetween(compareDate, dateFrom, dateTo);
        assertTrue("Test fromDate null", result);

        c.add(Calendar.DAY_OF_MONTH, -2);
        dateFrom = c.getTime();
        dateTo = null;
        result = DateTimeHelper.isBetween(compareDate, dateFrom, dateTo);
        assertTrue("Test dateTo null", result);

        c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        dateFrom = c.getTime();
        dateTo = null;
        result = DateTimeHelper.isBetween(compareDate, dateFrom, dateTo);
        assertFalse("Test dateTo null, dateFrom after compare date", result);

        c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        dateFrom = null;
        dateTo = c.getTime();
        result = DateTimeHelper.isBetween(compareDate, dateFrom, dateTo);
        assertFalse("Test dateFrom null, dateTo before compare date", result);
    }
}
