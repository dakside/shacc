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
package dakside.hacc.modules.accounting.reports.test;

import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class MockDAO {

    private static DAOFactory cachedDAO = null;

    private final static String DEFAULT_DB = "~/.shacc/shacc.db";

    public static DAOFactory getDAO() throws ArgumentException, DAOException {
        return getDAO(DEFAULT_DB);
    }

    public static DAOFactory getDAO(String dbPath) throws ArgumentException, DAOException {
        try {
            if (cachedDAO == null) {
                cachedDAO = DAOFactory.getDAOFactory(new ConnectionInfo(dbPath));
            }
            return cachedDAO;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void shutdown() {
        try {
            getDAO().shutdown();
        } catch (Exception ex) {
            Logger.getLogger(MockDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cachedDAO = null;
        }
    }
}
