package dakside.hacc.core.dao.db4o;

import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.ExchangeRateDAO;
import org.dakside.utils.DateTimeHelper;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.ExchangeRate;
import dakside.hacc.core.models.ExchangeRateType;
import java.util.Date;
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
public class DB4OExchangeRateDAOTest {
    public static DAOFactory factory = null;

    public DB4OExchangeRateDAOTest() {
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
     * Test of save method, of class DB4OExchangeRateDAO.
     */

    public int countExchangeRate(){
        ExchangeRateDAO dao = factory.getExchangeRateDAO();
        return dao.getAllExchangeRates().length;
    }

    /**
     * Test of getAllExchangeRates method, of class DB4OExchangeRateDAO.
     */
    @Test
    public void testGetAllExchangeRates() {
        System.out.println("getAllExchangeRates");
        ExchangeRateDAO instance = factory.getExchangeRateDAO();

        ExchangeRate[] result = instance.getAllExchangeRates();
        if(result == null || result.length == 0) return;

        try{
            String usd = "USD";
            CurrencyDAO dao = factory.getCurrencyDAO();
            Currency fromCur = dao.getCurrency(usd);
            String vnd = "VND";
            Currency toCur = dao.getCurrency(vnd);
            Date fromDate = DateTimeHelper.toDate("15/01/2010");
            Date toDate = DateTimeHelper.toDate("15/01/2010");
            double rates = 18500;
            ExchangeRateType type = new ExchangeRateType("User", 0);
            ExchangeRate usd2vnd = new ExchangeRate(fromCur,toCur,fromDate,toDate,rates,type);
            instance.save(usd2vnd);

            String sgd = "SGD";
            Currency toCur1 = dao.getCurrency(sgd);
            double rates1 = 1.34;
            ExchangeRate usd2sgd = new ExchangeRate(fromCur,toCur1,fromDate,toDate,rates1,type);
            instance.save(usd2sgd);

            ExchangeRate[] results = instance.getAllExchangeRates();

            if (results == null) fail();
            assertEquals(2, results.length);

        }
        catch (DAOException ex){
            ex.printStackTrace();
        }


    }

    @Test(expected = DAOException.class)
    public void testSave() throws Exception {
        System.out.println("save");
        //test save null account
        ExchangeRate rate = null;
        ExchangeRateDAO instance = factory.getExchangeRateDAO();
        instance.save(rate);
        
        String usd = "USD";
        Currency fromCur = factory.getCurrencyDAO().getCurrency(usd);
        String vnd = "VND";
        Currency toCur = factory.getCurrencyDAO().getCurrency(vnd);
        Date fromDate = DateTimeHelper.toDate("01/01/2010");
        Date toDate = DateTimeHelper.toDate("14/01/2010");
        double rates = 18000;
        ExchangeRateType type = new ExchangeRateType("User", 0);

        ExchangeRate usd2vnd = new ExchangeRate(fromCur,toCur,fromDate,toDate,rates,type);
        int before = countExchangeRate();
        instance.save(usd2vnd);
        int after = countExchangeRate();

        System.out.println("before: " + before + "\t after: " + after);
        assertEquals(after,before + 1);
        

    }

    /**
     * Test of delete method, of class DB4OExchangeRateDAO.
     */
    @Test(expected = DAOException.class)
    public void testDelete() throws Exception {
        System.out.println("delete");
        ExchangeRate rate = null;
        ExchangeRateDAO instance = factory.getExchangeRateDAO();
        instance.delete(rate);

        String usd = "USD";
        Currency fromCur = factory.getCurrencyDAO().getCurrency(usd);
        String sgd = "SGD";
        Currency toCur = factory.getCurrencyDAO().getCurrency(sgd);
        Date fromDate = DateTimeHelper.toDate("01/01/2010");
        Date toDate = DateTimeHelper.toDate("14/01/2010");
        double rates = 1.34;
        ExchangeRateType type = new ExchangeRateType("User", 0);

        ExchangeRate usd2sgd = new ExchangeRate(fromCur,toCur,fromDate,toDate,rates,type);
        instance.save(usd2sgd);


        ExchangeRate[] results = instance.getAllExchangeRates();
        if (results == null || results.length==0) return;
        int before = countExchangeRate();
        instance.delete(results[0]);
        int after = countExchangeRate();
        if(before==0) return;
        System.out.println("before: " + before + "\t after: " + after);
        assertEquals(before, after+1);
        
    }

    

    /**
     * Test of getExchangeRatesByDate method, of class DB4OExchangeRateDAO.
     */
    @Test
    public void testGetExchangeRatesByDate() {
        System.out.println("getExchangeRatesByDate");
        ExchangeRateDAO instance = factory.getExchangeRateDAO();

        try{
            String usd = "USD";
            Currency fromCur = factory.getCurrencyDAO().getCurrency(usd);
            String vnd = "VND";
            Currency toCur = factory.getCurrencyDAO().getCurrency(vnd);
            Date fromDate = DateTimeHelper.toDate("01/01/2010");
            Date toDate = DateTimeHelper.toDate("04/01/2010");
            double rates = 19000;
            ExchangeRateType type = new ExchangeRateType("User", 0);
            ExchangeRate usd2vnd = new ExchangeRate(fromCur,toCur,fromDate,toDate,rates,type);

            instance.save(usd2vnd);
            Date date = DateTimeHelper.toDate("02/01/2010");
            ExchangeRate[] result = instance.getExchangeRatesByDate(date);

            if (result == null) fail();
            System.out.println("result length: " + result.length);
            assertTrue(result.length >= 1);
        }catch(DAOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Test of getExchangeRatesByType method, of class DB4OExchangeRateDAO.
     */
    @Test
    public void testGetExchangeRatesByType() {
        System.out.println("getExchangeRatesByType"); 
        ExchangeRateDAO instance = factory.getExchangeRateDAO();

        try{
            String usd = "USD";
            Currency fromCur = factory.getCurrencyDAO().getCurrency(usd);
            String vnd = "VND";
            Currency toCur = factory.getCurrencyDAO().getCurrency(vnd);
            Date fromDate = DateTimeHelper.toDate("05/01/2010");
            Date toDate = DateTimeHelper.toDate("06/01/2010");
            double rates = 19000;
            ExchangeRateType type = new ExchangeRateType("User", 0);
            ExchangeRate usd2vnd = new ExchangeRate(fromCur,toCur,fromDate,toDate,rates,type);

            instance.save(usd2vnd);

            ExchangeRate[] result = instance.getExchangeRatesByType("user");

            if (result == null) fail();
            System.out.println("result length: " + result.length);
            assertTrue(result.length >= 1);
        }catch(DAOException ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void testGetExchangeRatesByCriteria(){
        /*System.out.println("getExchangeRatesByCriteria");
        ExchangeRateDAO instance = factory.getExchangeRateDAO();

        try{
            String usd = "USD";
            Currency fromCur = factory.getCurrencyDAO().getCurrency(usd);
            String vnd = "VND";
            Currency toCur = factory.getCurrencyDAO().getCurrency(vnd);
            Date fromDate = DateTimeHelper.toDate("05/01/2010");
            Date toDate = DateTimeHelper.toDate("06/01/2010");
            double rates = 19000;
            ExchangeRateType type = new ExchangeRateType("User", 0);
            ExchangeRate usd2vnd = new ExchangeRate(fromCur,toCur,fromDate,toDate,rates,type);

            instance.save(usd2vnd);

            ExchangeRate[] result = instance.getExchangeRatesByType("user");

            if (result == null) fail();
            System.out.println("result length: " + result.length);
            assertTrue(result.length >= 1);
        }catch(DAOException ex){
            ex.printStackTrace();
        }*/
    }

}