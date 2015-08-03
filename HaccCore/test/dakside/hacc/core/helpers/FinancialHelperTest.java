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
package dakside.hacc.core.helpers;

import org.dakside.utils.FinancialHelper;
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
public class FinancialHelperTest {

    public FinancialHelperTest() {
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
     * Test of formatCurrency method, of class FinancialHelper.
     */
    @Test
    public void testFormatCurrency() {
        System.out.println("formatCurrency");
        //test SGD
        double amount = 10.90;
        String expResult = "10.90";
        String result = FinancialHelper.formatCurrency(amount);
        System.out.println("get result:" + result);
        System.out.println("exp result:" + expResult);
        assertEquals(expResult, result);
        //test VND
        amount = 150000;
        expResult = "150,000.00";
        result = FinancialHelper.formatCurrency(amount);
        System.out.println("get result:" + result);
        System.out.println("exp result:" + expResult);
        assertEquals(expResult, result);
        //test VND
        amount = 0;
        expResult = "00.00";
        result = FinancialHelper.formatCurrency(amount);
        System.out.println("get result:" + result);
        System.out.println("exp result:" + expResult);
        assertEquals(expResult, result);
    }
}
