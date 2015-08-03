/*
 *  Copyright (C) 2009 Le Tuan Anh
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
package dakside.hacc.modules.accounting.periodman;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.comparators.AccountPeriodComparator;
import dakside.hacc.modules.accounting.accounttree.AccountManager;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Account Period module's controller
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountPeriodController {

    private static final Logger logger = Logger.getLogger(AccountManager.class.getName());
    private AccountPeriodView view = null;

    public AccountPeriodController(AccountPeriodView view) {
        setView(view);
    }

    /**
     * @return the view
     */
    public AccountPeriodView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(AccountPeriodView view) {
        this.view = view;
    }

    /**
     * Get all account periods
     * @return
     */
    public AccountPeriod[] getAllAccountPeriods() {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                AccountPeriod[] aplist = dao.getAllAccountPeriods();
                Arrays.sort(aplist, AccountPeriodComparator.getAscInstance());
                return aplist;
            }
        } catch (DAOException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Save account period
     * @param ap
     */
    void save(AccountPeriod ap) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                dao.save(ap);
            }
        } catch (ArgumentException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete an account period
     * @param selectedPeriod
     */
    boolean delete(AccountPeriod selectedPeriod) {
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            if (factory != null) {
                AccountDAO dao = factory.getAccountDAO();
                dao.delete(selectedPeriod);
            }
            return true;
        } catch (ArgumentException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

