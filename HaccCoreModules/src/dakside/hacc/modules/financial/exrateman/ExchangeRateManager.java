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

package dakside.hacc.modules.financial.exrateman;
import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.ExchangeRateDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.ExchangeRate;
import dakside.hacc.core.models.ExchangeRateType;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;




public class ExchangeRateManager {
    private static ExchangeRateManager instance;
    
    protected ExchangeRateManager(){};
    
    public static ExchangeRateManager getInstance(){
        if (instance == null) instance = new ExchangeRateManager();
        return instance;
    }

    private ExchangeRateDAO getExchangeRateDAO() throws DAOException{
        DAOFactory factory =  Configuration.getInstance().getDAOFactory();
        ExchangeRateDAO dao = null;
        if (factory != null) {
            dao = factory.getExchangeRateDAO();
        }
        return dao;
    }
    public void save(ExchangeRate rate) {
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            dao.save(rate);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(ExchangeRate rate){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            dao.delete(rate);
        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExchangeRate[] getAllExchangeRates(){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getAllExchangeRates();

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ExchangeRate[] getExchangeRatesByDate(Date date){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getExchangeRatesByDate(date);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ExchangeRate[] getExchangeRatesByType(String type){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getExchangeRatesByType(type);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void save(ExchangeRateType type) {
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            dao.save(type);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(ExchangeRateType type){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            dao.delete(type);
        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExchangeRateType[] getAllExchangeRateTypes(){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getAllExchangeRateTypes();

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ExchangeRate[] getAllExchangeRateByDateRange(Date startDate, Date endDate){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getExchangeRatesByDateRange(startDate, endDate);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ExchangeRate[] getAllExchangeRateByDateRangeAndType(Date startDate, Date endDate, String type){
        try {
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getExchangeRatesByDateRangeAndType(startDate, endDate, type);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ExchangeRate[] getExchangeRatesByCriteria(Currency fromCur, Currency toCur, Date sDate, Date eDate, ExchangeRateType type){
        try {
            System.out.println("fromCur: " + fromCur + "\ttoCur: " + toCur + "\tsDate: " + sDate + "\teDate: " + eDate + "\ttype: " + type);
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getExchangeRatesByCriteria(fromCur, toCur, sDate, eDate, type);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ExchangeRate[] getExchangeRatesByCurrencies(Currency fromCur, Currency toCur){
        try {
            System.out.println("fromCur: " + fromCur + "\ttoCur: " + toCur);
            ExchangeRateDAO dao = getExchangeRateDAO();
            return dao.getExchangeRatesByCurrencies(fromCur, toCur);

        } catch (DAOException ex) {
            Logger.getLogger(ExchangeRateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
