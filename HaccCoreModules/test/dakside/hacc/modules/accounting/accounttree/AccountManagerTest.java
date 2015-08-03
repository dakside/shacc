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
package dakside.hacc.modules.accounting.accounttree;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import dakside.hacc.core.config.Configuration;
import org.dakside.dao.ConnectionInfo;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountManagerTest {

    public AccountManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSampleAccountTree method, of class AccountManager.
     */
    @Test
    public void testGetSampleAccountTree() throws ArgumentException, DAOException {
        //setup config
        File f = new File("D:/testdb4o.db");
        f.delete();
        Configuration.getInstance().setInfo(new ConnectionInfo("D:/testdb4o.db", DAOFactory.DB4O_DATABASE));

        //save a sample tree
        System.out.println("getSampleAccountTree");
        AccountManager instance = new AccountManager();
        Account root = TestHelper.getSampleAccountTree(null);
        instance.save(root);

        System.out.println("Now we shutdown the db");
        Configuration.getInstance().getDAOFactory().shutdown();

        System.out.println("Test retrieve account tree");
        root = instance.getAccountTree();
        assertNotNull(root);

        //test save here
        Account[] children = root.getChildrenArray();
        System.out.println(children[0] + " - " + children[0].getTypeString());

        System.out.println("Test update");
        //now we change account type & name
        children[0].setType(Account.TYPE_DEBIT);
        String uniName = "An unique name";
        children[0].setName(uniName);
        //now we save root
        instance.save(root);

        //now shutdown again
        Configuration.getInstance().getDAOFactory().shutdown();

        //now test if we can retrieve again
        System.out.println("Test retrieve updated value");
        root = instance.getAccountTree();
        children = root.getChildrenArray();
        assertEquals(children[0].getType(), Account.TYPE_DEBIT);
        assertEquals(children[0].getName(), uniName);

        //now test new child
        Account acc3 = root.getChildrenArray()[2];
        acc3.getChildren().add(new Account("C", "Unknown", Account.TYPE_CREDIT));
        System.out.println("Account 3 has " + acc3.getChildrenArray().length + " children.");
        instance.save(root);
        Configuration.getInstance().getDAOFactory().shutdown();

        //now test retrieve
        root = instance.getAccountTree();
        acc3 = root.getChildrenArray()[2];
        assertEquals(3, acc3.getChildren().size());

        //shutdown
    }

    @Test
    public void testTest() {
        System.out.println("Test deep save");
        String fileName = "D:/testdb4o2.db";
        File f = new File(fileName);
        f.delete();
        f = null;

        com.db4o.config.Configuration cf = Db4o.newConfiguration();
        cf.objectClass("dakside.hacc.modules.accountmanager.TestItem").cascadeOnUpdate(true);
        ObjectContainer db = Db4o.openFile(cf, fileName);
        //store
        TestItem root = new TestItem("Root");
        root.add(new TestItem("11"));
        TestItem lvl1 = new TestItem("12");
        root.add(lvl1);
        lvl1.add(new TestItem("121"));

        db.store(root);
        db.close();

        //open again
        cf = Db4o.newConfiguration();
        cf.objectClass("dakside.hacc.modules.accountmanager.TestItem").cascadeOnUpdate(true);
        db = Db4o.openFile(cf, fileName);
        root = db.query(new Predicate<TestItem>() {

            @Override
            public boolean match(TestItem et) {
                return "Root".equals(et.name);
            }
        }).next();
        System.out.println(root);
        root.items[0].add(new TestItem("111"));
        db.store(root);
        db.close();

        //open last
        //open again
        cf = Db4o.newConfiguration();
        cf.objectClass("dakside.hacc.modules.accountmanager.TestItem").cascadeOnUpdate(true);
        db = Db4o.openFile(cf, fileName);
        root = db.query(new Predicate<TestItem>() {

            @Override
            public boolean match(TestItem et) {
                return "Root".equals(et.name);
            }
        }).next();
        assertEquals(1, root.items[0].items.length);
    }

    /**
     * Test of save method, of class AccountManager.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Account root = null;
        AccountManager instance = null;
        instance.save(root);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllAccounts method, of class AccountManager.
     */
    @Test
    public void testGetAllAccounts() {
        System.out.println("getAllAccounts");
        AccountManager instance = null;
        Account[] expResult = null;
        Account[] result = instance.getAllAccounts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccountTree method, of class AccountManager.
     */
    @Test
    public void testGetAccountTree() {
        System.out.println("getAccountTree");
        AccountManager instance = null;
        Account expResult = null;
        Account result = instance.getAccountTree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class AccountManager.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Account[] accounts = null;
        AccountManager instance = null;
        instance.delete(accounts);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
