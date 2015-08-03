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
package dakside.hacc.core.helpers;

import org.dakside.utils.Validator;
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
public class ValidatorTest {

    public ValidatorTest() {
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
     * Test of argumentNotNull method, of class Validator.
     */
    @Test(expected = ArgumentException.class)
    public void testArgumentNotNull_Object() throws Exception {
        System.out.println("argumentNotNull");
        Object arg = null;
        Validator.argumentNotNull(arg);
    }

    /**
     * Test of argumentNotNull method, of class Validator.
     */
    @Test(expected = ArgumentException.class)
    public void testArgumentNotNull_Object_String() throws Exception {
        System.out.println("argumentNotNull");
        Object arg = null;
        String message = null;
        Validator.argumentNotNull(arg, message);
    }

    /**
     * Test of equals method, of class Validator.
     */
    @Test
    public void testEquals_String_String() {
        System.out.println("equals");
        boolean expResult = true;
        boolean result = Validator.equals("", "");
        assertEquals(expResult, result);

        //1 null 1 not null
        expResult = false;
        result = Validator.equals(null, "");
        assertEquals(expResult, result);

        expResult = false;
        result = Validator.equals("", null);
        assertEquals(expResult, result);

        //both null
        expResult = true;
        String str1 = null;
        String str2 = null;
        result = Validator.equals(str1, str2);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Validator.
     */
    @Test
    public void testEquals_Date_Date() {
        System.out.println("equals");
        Date date1 = null;
        Date date2 = null;
        boolean expResult = true;
        boolean result = Validator.equals(date1, date2);
        assertEquals(expResult, result);

        //1 not null
        date1 = new Date();
        date2 = null;
        expResult = false;
        result = Validator.equals(date1, date2);
        assertEquals(expResult, result);

        //1 not null
        date1 = null;
        date2 = new Date();
        expResult = false;
        result = Validator.equals(date1, date2);
        assertEquals(expResult, result);

        date1 = new Date(10000);
        date2 = new Date(10000);
        expResult = true;
        result = Validator.equals(date1, date2);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Validator.
     */
    @Test
    public void testEquals_int_int() {
        System.out.println("equals");
        int num1 = 0;
        int num2 = 0;
        boolean result = Validator.equals(num1, num2);
        assertTrue(result);
    }

    /**
     * Test of equals method, of class Validator.
     */
    @Test
    public void testEquals_Object_Object() {
        System.out.println("equals");
        Object obj1 = null;
        Object obj2 = null;
        boolean expResult = true;
        boolean result = Validator.equals(obj1, obj2);
        assertEquals(expResult, result);

        Object b = "";
        Object x = b;
        expResult = true;
        result = Validator.equals(b, x);
        assertEquals(expResult, result);

        b = "";
        x = "";
        expResult = true;
        result = Validator.equals(b, x);
        assertEquals(expResult, result);
    }
}
