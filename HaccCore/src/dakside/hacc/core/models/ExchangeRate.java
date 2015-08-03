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
package dakside.hacc.core.models;

import org.dakside.utils.DateTimeHelper;
import java.util.Date;

/**
 *
 * @author michael
 */
public class ExchangeRate {
    private Currency fromCurrency, toCurrency;
    private Date fromDate, toDate;
    private double rate;
    private ExchangeRateType type;

    /**Exchange rate is one way only
     * automatically create an inversed rate if missing
     * @param fromCurrency
     * @param toCurrency
     * @param fromDate
     * @param toDate
     * @param rate
     * @param type
     */
    public ExchangeRate(Currency fromCurrency, Currency toCurrency, Date fromDate, Date toDate, double rate, ExchangeRateType type){
        setFromCurrency(fromCurrency);
        setToCurrency(toCurrency);
        setFromDate(fromDate);
        setToDate(toDate);
        setRate(rate);
        setType(type);
        
    }

   
    /**
     * @return the fromCurrency
     */
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    /**
     * @param fromCurrency the fromCurrency to set
     */
    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    /**
     * @return the toCurrency
     */
    public Currency getToCurrency() {
        return toCurrency;
    }

    /**
     * @param toCurrency the toCurrency to set
     */
    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }


    /**
     * @return the type
     */
    public ExchangeRateType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ExchangeRateType type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return getFromCurrency().toString() + "-" + getToCurrency() + ":" + DateTimeHelper.toStringSmart(fromDate) + " - " + DateTimeHelper.toStringSmart(toDate) + "(" + type + ")";
    }
   
}
