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
import org.dakside.dao.DAOException;
import org.dakside.dao.DAOHelper;
import dakside.hacc.core.dao.WritableCurrencyDAO;
import dakside.hacc.core.models.Currency;



/**
 *
 * @author michael
 */
public class DB4OWritableCurrencyDAO extends DB4OCurrencyDAO implements WritableCurrencyDAO{

    public DB4OWritableCurrencyDAO(ObjectContainer db) {
        super(db);
    }

    public void save(Currency cur) throws DAOException {
        DAOHelper.daoArgumentNotNull(cur);
        db.store(cur);
    }

    public void delete(Currency cur) throws DAOException {
        DAOHelper.daoArgumentNotNull(cur);
        db.delete(cur);
    }
}
