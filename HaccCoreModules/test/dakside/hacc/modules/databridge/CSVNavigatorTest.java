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

import dakside.hacc.modules.databridge.helpers.CSVNavigator;
import dakside.csv.CSVLine;
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
public class CSVNavigatorTest {

    private static CSVNavigator instance = null;
    CSVLine line = null;

    public CSVNavigatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new CSVNavigator();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        line = new CSVLine().add("abc").
                add(123).
                add(23.5).add("01/17/2010");
        instance.setLine(line);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setLine method, of class CSVNavigator.
     */
    @Test
    public void testNavigate() {
        System.out.println("Test navigate line");
        assertEquals("abc", instance.nextString());
        assertEquals(new Integer(123), instance.nextInteger());
        assertEquals(new Double(23.5), instance.nextDouble());
    }

    /**
     * Test of hasNext method, of class CSVNavigator.
     */
    @Test
    public void testHasNext() {
    }

    /**
     * Test of next method, of class CSVNavigator.
     */
    @Test
    public void testNext() {
        System.out.println("test next() method");
        int actualFound = 0;
        int expectedFound = line.size();
        while (instance.hasNext()) {
            instance.next();
            actualFound++;
        }
        assertEquals(expectedFound, actualFound);
    }

    /**
     * Test of nextString method, of class CSVNavigator.
     */
    @Test
    public void testNextString() {
        System.out.println("nextString() method");

        String expResult = "abc";
        String result = instance.nextString();
        assertEquals(expResult, result);

        expResult = "123";
        result = instance.nextString();
        assertEquals(expResult, result);

        expResult = "23.5";
        result = instance.nextString();
        assertEquals(expResult, result);
    }

    /**
     * Test of nextDouble method, of class CSVNavigator.
     */
    @Test
    public void testNextDouble() {
        System.out.println("nextDouble");

        Double expResult = null; //abc is not a double
        Double result = instance.nextDouble();
        assertEquals(expResult, result);

        expResult = 123.0;
        result = instance.nextDouble();
        assertEquals(expResult, result);

        expResult = 23.5;
        result = instance.nextDouble();
        assertEquals(expResult, result);
    }

    /**
     * Test of nextInteger method, of class CSVNavigator.
     */
    @Test
    public void testNextInteger() {
        System.out.println("nextInteger");

        Integer expResult = null; //abc is not an integer
        Integer result = instance.nextInteger();
        assertEquals(expResult, result);

        expResult = 123;
        result = instance.nextInteger();
        assertEquals(expResult, result);

        expResult = null; //23.5 is not an integer
        result = instance.nextInteger();
        assertEquals(expResult, result);
    }

    /**
     * Test of nextDate method, of class CSVNavigator.
     */
    @Test
    public void testNextDate() {
        System.out.println("nextDate");

        Date expResult = null; //abc is not an integer
        Date result = instance.nextDate();
        assertEquals(expResult, result);

        expResult = null;
        result = instance.nextDate();
        assertEquals(expResult, result);

        expResult = null;
        result = instance.nextDate();
        assertEquals(expResult, result);

        expResult = new Date(2010, 17, 1);
        result = instance.nextDate();
        assertEquals(expResult, result);
    }
}
