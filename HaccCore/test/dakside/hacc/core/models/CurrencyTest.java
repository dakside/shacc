package dakside.hacc.core.models;

import dakside.hacc.core.helpers.exceptions.CurrencyNotFound;
import dakside.hacc.core.models.Currency;

import org.junit.AfterClass;
import org.junit.BeforeClass;




import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michael
 */
public class CurrencyTest {

    public CurrencyTest() {
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
     * Test of getSymbol method, of class Currency.
     */
    @Test
    public void testGetSymbol() throws CurrencyNotFound{
        System.out.println("getSymbol");
        Currency instance = new Currency("SGD");
        String expResult = "$";
        String result = instance.getSymbol();
        assertEquals(expResult, result);      
    }
    
    @Test
    public void testInvalidCurrency() throws CurrencyNotFound{
        System.out.println("testInvalidCurrency");
        Currency instance = new Currency("Make sure this does not exist");
        System.out.println("symbol: " + instance.getSymbol());
        System.out.println("ISO code: " + instance.getIsoCode());
    }

}