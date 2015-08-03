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
 */
package dakside.hacc.core.dao.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Constraint;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import org.dakside.dao.DAOException;
import org.dakside.dao.DAOHelper;
import dakside.hacc.core.dao.ExchangeRateDAO;
import org.dakside.utils.DateTimeHelper;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.ExchangeRate;
import dakside.hacc.core.models.ExchangeRateType;
import java.util.Date;

/**
 *
 * @author michael
 */
public class DB4OExchangeRateDAO implements ExchangeRateDAO {
    private ObjectContainer db = null;
    public DB4OExchangeRateDAO(ObjectContainer db){
        this.db = db;        
    }

    public void save(ExchangeRate rate)throws DAOException{
        DAOHelper.daoArgumentNotNull(rate);
        db.store(rate);
    }

    public void delete(ExchangeRate rate) throws DAOException{
        DAOHelper.daoArgumentNotNull(rate);
        db.delete(rate);
    }

    public ExchangeRate[] getAllExchangeRates(){
        ObjectSet<ExchangeRate> rates = db.query(ExchangeRate.class);
        return rates.toArray(new ExchangeRate[0]);
    }


    public ExchangeRate[] getExchangeRatesByDate(Date date){
        Date startDate = DateTimeHelper.toDateStart(date);
        Date endDate = DateTimeHelper.toDateEnd(date);
        return getExchangeRatesByDateRange(startDate, endDate);
    }

    /**
     * return all exchange rates of a type
     * order by fromDate ascending
     * @param type
     * @return ExchangeRate[]
     */
    public ExchangeRate[] getExchangeRatesByType(String type){
        Query query = db.query();
        query.constrain(ExchangeRate.class);
        query.descend("fromDate").orderAscending();
        query.descend("type").descend("name").constrain(type);
        ObjectSet<ExchangeRate> results = query.execute();
        return results.toArray(new ExchangeRate[0]);
    }

    /**
     * returns exchange rates that has fromDate and toDate overlap with startDate and endDate
     * order by fromDate ascending
     * @param startDate
     * @param endDate
     * @return ExchangeRate[]
     */
    public ExchangeRate[] getExchangeRatesByDateRange(Date sDate, Date eDate){
        Date startDate = DateTimeHelper.toDateStart(sDate);
        Date endDate = DateTimeHelper.toDateEnd(eDate);
        if (startDate.after(endDate)) return new ExchangeRate[0];

        Query query = db.query();        
        query.descend("fromDate").orderAscending();
        
        Constraint fromSmaller = query.descend("fromDate").constrain(endDate).smaller();
        Constraint from = query.descend("fromDate").constrain(endDate).equal().or(fromSmaller);
        
        Constraint toGreater = query.descend("toDate").constrain(startDate).greater();
        Constraint to = query.descend("toDate").constrain(startDate).equal().or(toGreater);

        query.constrain(ExchangeRate.class).and(from).and(to);
        ObjectSet<ExchangeRate> results = query.execute();
        return results.toArray(new ExchangeRate[0]);
    }

    public void save(ExchangeRateType type) throws DAOException{
        DAOHelper.daoArgumentNotNull(type);
        db.store(type);
    }

    public void delete(ExchangeRateType type) throws DAOException{
        DAOHelper.daoArgumentNotNull(type);
        db.delete(type);
    }

    public ExchangeRateType[] getAllExchangeRateTypes(){
        ObjectSet<ExchangeRateType> result = db.query(ExchangeRateType.class);
        return result.toArray(new ExchangeRateType[0]);
    }

    public ExchangeRate[] getExchangeRatesByDateRangeAndType(Date sDate, Date eDate, String type) {
        Date startDate = DateTimeHelper.toDateStart(sDate);
        Date endDate = DateTimeHelper.toDateEnd(eDate);
        if (startDate.after(endDate)) return new ExchangeRate[0];

        Query query = db.query();
        query.descend("fromDate").orderAscending();

        Constraint fromSmaller = query.descend("fromDate").constrain(endDate).smaller();
        Constraint from = query.descend("fromDate").constrain(endDate).equal().or(fromSmaller);

        Constraint toGreater = query.descend("toDate").constrain(startDate).greater();
        Constraint to = query.descend("toDate").constrain(startDate).equal().or(toGreater);

        Constraint cstrType = query.descend("type").descend("name").constrain(type);

        query.constrain(ExchangeRate.class).and(from).and(to).and(cstrType);
        ObjectSet<ExchangeRate> results = query.execute();
        return results.toArray(new ExchangeRate[0]);
    }

    public ExchangeRate[] getExchangeRatesByCurrencies(Currency fromCur, Currency toCur){
        Query query = db.query();
        query.descend("fromDate").orderAscending();

        Constraint cstrFromCur = query.descend("fromCurrency").descend("isoCode").constrain(fromCur.getIsoCode());

        Constraint cstrToCur = query.descend("toCurrency").descend("isoCode").constrain(toCur.getIsoCode());

        query.constrain(ExchangeRate.class).and(cstrFromCur).and(cstrToCur);

        ObjectSet<ExchangeRate> results = query.execute();
        return results.toArray(new ExchangeRate[0]);
    }

    /**
     * get Exchange Rate based on criteria, if all criteria are null, the method will return all exchange rates
     * @param fromCur
     * @param toCur
     * @param sDate
     * @param eDate
     * @param type
     * @return
     */
    /*
    public ExchangeRate[] getExchangeRatesByCriteria(Currency fromCur, Currency toCur, Date sDate, Date eDate, ExchangeRateType type){
        Date startDate = DateTimeHelper.toDateStart(sDate);
        Date endDate = DateTimeHelper.toDateEnd(eDate);
        if (startDate.after(endDate)) return new ExchangeRate[0];

        Query query = db.query();
        query.descend("fromDate").orderAscending();

        Constraint cstrFromCur = query.descend("fromCurrency").descend("isoCode").constrain(fromCur.getIsoCode());

        Constraint cstrToCur = query.descend("toCurrency").descend("isoCode").constrain(toCur.getIsoCode());

        Constraint cstrFromSmaller = query.descend("fromDate").constrain(endDate).smaller();
        Constraint cstrFrom = query.descend("fromDate").constrain(endDate).equal().or(cstrFromSmaller);

        Constraint cstrToGreater = query.descend("toDate").constrain(startDate).greater();
        Constraint cstrTo = query.descend("toDate").constrain(startDate).equal().or(cstrToGreater);

        Constraint cstrType = query.descend("type").descend("name").constrain(type);

        Constraint c = query.constrain(ExchangeRate.class);

        if (fromCur != null)
            query.constrain(ExchangeRate.class).and(cstrFromCur);
        if (toCur != null)
            query.constrain(ExchangeRate.class).and(cstrToCur);
        if (sDate != null)
            query.constrain(ExchangeRate.class).and(cstrFrom);
        if (eDate != null)
            query.constrain(ExchangeRate.class).and(cstrTo);
        if (type != null)
            query.constrain(ExchangeRate.class).and(cstrType);

        

        ObjectSet<ExchangeRate> results = query.execute();
        return results.toArray(new ExchangeRate[0]);
    }*/

    public ExchangeRate[] getExchangeRatesByCriteria(Currency fromCur, Currency toCur, Date sDate, Date eDate, ExchangeRateType type){
        final Currency fFromCur = fromCur;
        final Currency fToCur = toCur;
        final Date fSDate = DateTimeHelper.toDateStart(sDate);
        final Date fEDate = DateTimeHelper.toDateEnd(eDate);
        final ExchangeRateType fType = type;

        ObjectSet<ExchangeRate> results = db.query(new Predicate<ExchangeRate>() {
            public boolean match(ExchangeRate rate){
                boolean matched = true;
                if(fFromCur != null){
                    matched = matched && rate.getFromCurrency().getIsoCode().compareTo(fFromCur.getIsoCode()) == 0;                    
                }
                if(fToCur != null){
                    matched = matched && rate.getToCurrency().getIsoCode().compareTo(fToCur.getIsoCode()) == 0;                    
                }
                if(fSDate != null){
                    matched = matched && rate.getToDate().after(fSDate);                    
                }
                if(fEDate != null){
                    matched = matched && rate.getFromDate().before(fEDate);                    
                }
                if(fType != null){
                    matched = matched && rate.getType().getName().compareTo(fType.getName()) == 0;                    
                }
                return matched;
            }
        });

        return results.toArray(new ExchangeRate[0]);
    }


}
