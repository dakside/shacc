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
package dakside.hacc.core.factories;

import dakside.hacc.core.config.ConfigEntry;
import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.models.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Get currency from database
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DefaultCurrencyFactory implements CurrencyFactory {

    public Currency getCurreny(String isoCode) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                CurrencyDAO dao = factory.getCurrencyDAO();
                return dao.getCurrency(isoCode);
            }
        } catch (DAOException ex) {
            Logger.getLogger(DefaultCurrencyFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Currency getDefaultCurrency() {
        String code = Configuration.getInstance().getConfig(ConfigEntry.BASE_CURRENCY).getValueString();
        return getCurreny(code);
    }
}
