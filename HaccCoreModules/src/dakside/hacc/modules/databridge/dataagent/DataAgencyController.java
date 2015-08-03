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

package dakside.hacc.modules.databridge.dataagent;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DataAgencyController {
    public static int save(Transaction[] trx){
        try {
            TransactionDAO dao = Configuration.getInstance().getDAOFactory().getTransactionDAO();
            return dao.save(trx);
        } catch (Exception ex) {
            Logger.getLogger(DataAgencyController.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
