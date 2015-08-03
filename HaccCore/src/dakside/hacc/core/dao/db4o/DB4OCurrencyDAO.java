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
import com.db4o.query.Query;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.models.Currency;

/**
 *
 * @author michael
 */
public class DB4OCurrencyDAO implements CurrencyDAO {

    protected ObjectContainer db = null;

    public DB4OCurrencyDAO(ObjectContainer db) {
        this.db = db;
    }    

    public Currency[] getAllCurrencies() {
        ObjectSet<Currency> currencies = db.query(Currency.class);
        return currencies.toArray(new Currency[0]);
    }

    public Currency getCurrency(String isoCode) throws DAOException {
        try {
            Query q = db.query();
            q.constrain(Currency.class);
            q.descend("isoCode").constrain(isoCode);

            ObjectSet<Currency> currencies = q.execute();
            if (currencies.size() == 1) {
                return currencies.get(0);
            }
        } catch (Exception ex) {
            throw new DAOException("Cannot get currency from database", ex);
        }
        return null;
    }
}
