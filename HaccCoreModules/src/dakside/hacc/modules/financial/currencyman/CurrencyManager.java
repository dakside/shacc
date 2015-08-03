/*
 *  Copyright (C) 2010 michael
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


/**
 *
 * @author michael
 */
package dakside.hacc.modules.financial.currencyman;
import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.WritableCurrencyDAO;
import dakside.hacc.core.helpers.exceptions.CurrencyNotFound;
import dakside.hacc.core.models.Currency;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencyManager {
    private static CurrencyManager instance = null;
       //TODO init name map from properties file
    private static final HashMap<String,String> NAME_MAP = new HashMap<String,String>(){
        {
        put("SGD","Singapore Dollar");
        put("USD","US Dollar");
        put("VND","Vietnam Dong");
        put("MYR","Malaysian Ringgit");
        }
    };

    protected CurrencyManager(){
    }

    public static CurrencyManager getInstance(){
        if(instance == null)
            instance = new CurrencyManager();
        return instance;
    }

    private CurrencyDAO getCurrencyDAO() throws DAOException{
        DAOFactory factory =  Configuration.getInstance().getDAOFactory();
        CurrencyDAO dao = null;
            if (factory != null) {
                dao = factory.getCurrencyDAO();
            }
        return dao;
    }

    private WritableCurrencyDAO getWritableCurrencyDAO() throws DAOException{
        DAOFactory factory =  Configuration.getInstance().getDAOFactory();
        WritableCurrencyDAO dao = null;
            if (factory != null) {
                dao = factory.getWritableCurrencyDAO();
            }
        return dao;
    }

    /**
     * save a currency in database
     * @param cur
     */
    void save(Currency cur) {
        try {
            WritableCurrencyDAO dao = getWritableCurrencyDAO();
            dao.save(cur);

        } catch (DAOException ex) {
            Logger.getLogger(CurrencyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * delete a currency object from database
     * @param cur
     */
    void delete(Currency cur){
        try {
            WritableCurrencyDAO dao = getWritableCurrencyDAO();
            dao.delete(cur);

        } catch (DAOException ex) {
            Logger.getLogger(CurrencyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * get all currency in use
     * @return
     */
    public Currency[] getAllCurrency(){
        try {
            CurrencyDAO dao = getCurrencyDAO();
            Currency[] result = dao.getAllCurrencies();
            Arrays.sort(result, new CurrencyComparator());
            return result;

        } catch (DAOException ex) {
            Logger.getLogger(CurrencyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Currency[] getAllAvailableCurrency(){
        Currency[] result = new Currency[NAME_MAP.size()];
        int count = 0;
        try{
            for (String isoCode:NAME_MAP.keySet()){
                result[count++] = new Currency(isoCode);
            }
        }catch(CurrencyNotFound ex){
            System.out.println(ex);
        }
        Arrays.sort(result, new CurrencyComparator());
        return result;
    }

    //currency in getAllAvalaibleCurrency but not in getAllCurrency
    //assume both are sorted
    public Currency[] getUnusedCurrency(){
        Currency[] usedCurrency = getAllCurrency();
        Currency[] availableCurrency = getAllAvailableCurrency();
        System.out.println("used: " + usedCurrency.length);
        System.out.println("available:" + availableCurrency.length);

        if(usedCurrency == null || usedCurrency.length == 0) return availableCurrency;
        
        Currency[] result = new Currency[availableCurrency.length - usedCurrency.length];
        int countUsed = 0;

        for (int i = 0; i <= availableCurrency.length - 1; i++){
            //System.out.println("available:" + availableCurrency[i].getIsoCode() + " \t countUsed: " + countUsed + "\t used:" + usedCurrency[countUsed]);
            if (countUsed < usedCurrency.length && availableCurrency[i].getIsoCode().compareTo(usedCurrency[countUsed].getIsoCode()) == 0){
                countUsed++;
            }else{
                result[i - countUsed] = availableCurrency[i];
            }
        }
        return result;
    }




}
