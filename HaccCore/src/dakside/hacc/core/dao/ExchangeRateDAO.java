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
package dakside.hacc.core.dao;

import org.dakside.dao.DAOException;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.ExchangeRate;
import dakside.hacc.core.models.ExchangeRateType;
import java.util.Date;

/**
 *
 * @author michael
 */
public interface ExchangeRateDAO {
    public void save(ExchangeRate rate) throws DAOException;
    public void delete(ExchangeRate rate) throws DAOException;
    public ExchangeRate[] getAllExchangeRates();
    public ExchangeRate[] getExchangeRatesByCriteria(Currency fromCur, Currency toCur, Date fromDate, Date toDate, ExchangeRateType type);
    public ExchangeRate[] getExchangeRatesByDate(Date date);
    public ExchangeRate[] getExchangeRatesByDateRange(Date startDate, Date endDate);
    public ExchangeRate[] getExchangeRatesByType(String type);
    public ExchangeRate[] getExchangeRatesByDateRangeAndType(Date startDate, Date endDate, String type);
    public ExchangeRate[] getExchangeRatesByCurrencies(Currency fromCur, Currency toCur);
    public void save(ExchangeRateType type) throws DAOException;
    public void delete(ExchangeRateType type) throws DAOException;
    public ExchangeRateType[] getAllExchangeRateTypes();
}
