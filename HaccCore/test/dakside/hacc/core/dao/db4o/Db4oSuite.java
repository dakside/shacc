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
package dakside.hacc.core.dao.db4o;

import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dakside.utils.SystemHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({dakside.hacc.core.dao.db4o.DB4OTransactionDAOTest.class, dakside.hacc.core.dao.db4o.DB4OAccountDAOTest.class, dakside.hacc.core.dao.db4o.DB4ODAOFactoryTest.class})
public class Db4oSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public static DAOFactory buildFactory() throws ArgumentException {
        try {
            System.out.println("Test get account DAO");
            int dbType = DAOFactory.DB4O_DATABASE;
            //XXX test DB file path (change this if test on linux)

            String path = SystemHelper.getTempDir() +
                    SystemHelper.getPathSeparator()
                    + "testdb.db";
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            ConnectionInfo info = new ConnectionInfo(f.getAbsolutePath(), dbType);
            return DAOFactory.getDAOFactory(info);
        } catch (DAOException ex) {
            Logger.getLogger(Db4oSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
