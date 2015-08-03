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

import org.dakside.exceptions.ArgumentException;
import dakside.hacc.core.models.Account;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Account tree
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountTree extends JTree {

    /**
     * Default Constructor
     */
    public AccountTree() {
        TreeModelListener l = createTreeModelListener();
    }

    /**
     * Get selected account object
     * @return null if no account object is selected
     */
    public Account getSelectedAccount() {
        TreePath path = getSelectionPath();
        return getAccountFromPath(path);
    }

    /**
     * Get account object from a tree path (path to a node)
     * @param path
     * @return
     */
    public Account getAccountFromPath(TreePath path) {
        DefaultMutableTreeNode node = getNodeFromPath(path);
        if (node != null) {
            //is root
            if (path.getPathCount() == 1) {
            }
            Object nodeData = node.getUserObject();
            if (nodeData instanceof Account) {
                return (Account) nodeData;
            } else if (path.getPathCount() == 1) {
                //is root
                Account rootAccount = null;
                try {
                    rootAccount = new Account("R", "Accounts", Account.TYPE_FOLDER);
                } catch (ArgumentException ex) {
                    Logger.getLogger(AccountTree.class.getName()).log(Level.SEVERE, null, ex);
                }

                //adopt all children
                Enumeration<DefaultMutableTreeNode> children = node.children();
                while (children.hasMoreElements()) {
                    DefaultMutableTreeNode child = children.nextElement();
                    Account childAccount = getAccountFromNode(child);
                    if (childAccount != null) {
                        rootAccount.getChildren().add(rootAccount);
                    }
                }
                return rootAccount;
            }
        }
        return null;

    }

    /**
     * Get DefaultMutableTreeNode object from path
     * @param path
     * @return
     */
    private DefaultMutableTreeNode getNodeFromPath(TreePath path) {
        if (path != null) {
            //others
            Object obj = path.getLastPathComponent();


            if (obj instanceof DefaultMutableTreeNode) {
                return (DefaultMutableTreeNode) obj;


            }
        }
        return null;


    }

    /**
     * Get account object from a node
     * @param node
     * @return null if cannot find
     */
    private Account getAccountFromNode(DefaultMutableTreeNode node) {
        if (node != null && node.getUserObject() instanceof Account) {
            return (Account) node.getUserObject();


        }
        return null;


    }

    /**
     * Add a new account to a treepath
     * @param account
     * @param path
     */
    public void addAccount(Account account, TreePath path) {
        DefaultMutableTreeNode parentNode = getNodeFromPath(path);
        //add node
        if (parentNode != null) {
            if (parentNode.getUserObject() == account) {
                //update
                reloadModel();
            } else {
                //insert
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(account);
                //add to parent node
                parentNode.add(childNode);
                //get parent account
                Account parentAccount = getAccountFromPath(path);

                if (parentAccount != null) {
                    //save reference to parent account
                    parentAccount.getChildren().add(account);
                }
            }
        }
        reloadModel();
        //expand current path
        TreePath p = path;


        while (p != null) {
            expandPath(p);
            p = p.getParentPath();


        }
    }

    public void getAllAccountsFromNode(Account acc, HashSet<Account> accounts) {
        if (acc != null) {
            accounts.add(acc);
            for (Account child : acc.getChildrenArray()) {
                getAllAccountsFromNode(child, accounts);
            }
        }
    }

    /**
     * Remove an account from tree
     * @param path
     * @return accounts to be removed
     */
    public Account[] removeNode(TreePath path) {
        HashSet<Account> accounts = new HashSet<Account>();
        if (path != null && path.getPathCount() > 1) {
            //get node to be removed
            DefaultMutableTreeNode node = getNodeFromPath(path);
            //get all child accounts
            Account acc = getAccountFromNode(node);
            getAllAccountsFromNode(acc, accounts);

            //get parent node
            TreePath parentPath = path.getParentPath();
            DefaultMutableTreeNode parentNode = getNodeFromPath(parentPath);
            //remove child from parent
            parentNode.remove(node);
        }
        reloadModel();
        return accounts.toArray(new Account[0]);
    }

    /**
     * Remove selection acount
     */
    public Account[] removeAccountAtSelection() {
        return removeNode(getSelectionPath());
    }

    /**
     * Insert a new account at selection path
     */
    public void insertAccountAtSelection() {
        try {
            addAccount(new Account("C", "Unknown Account", Account.TYPE_FOLDER), this.getSelectionPath());
        } catch (ArgumentException ex) {
            Logger.getLogger(AccountTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reload model change
     */
    protected void reloadModel() {
        ((DefaultTreeModel) getModel()).reload();


    }

    /**
     * Propagate throw an account tree and create tree node
     * @param parentAccount
     * @param parentNode
     */
    void propagate(Account parentAccount, DefaultMutableTreeNode parentNode) {
        if (parentAccount != null && parentNode != null) {
            //save parent account
            parentNode.setUserObject(parentAccount);


            for (Account childAccount : parentAccount.getChildrenArray()) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childAccount);

                parentNode.insert(childNode, parentNode.getChildCount());
                propagate(
                        childAccount, childNode);


            }
        }
    }

    /**
     * Clear all tree node
     */
    private void clearTree() {
        if (getModel() != null
                && (getModel().getRoot() instanceof DefaultMutableTreeNode)) {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
            //if root contains account
            root.removeAllChildren();
            root.setUserObject(null);
        }
    }

    /**
     * Load an accounting tree
     * @param root
     */
    void loadTree(Account root) {
        //validate parameters
        if (getModel() == null
                || root == null
                || !(getModel().getRoot() instanceof DefaultMutableTreeNode)) {
            return;
        }

        clearTree();
        propagate(root, (DefaultMutableTreeNode) getModel().getRoot());
        setSelectionPath(new TreePath(getModel().getRoot()));
        reloadModel();
        repaint();
    }

    /**
     * Get root object of accounting tree
     * @return null if not found
     */
    Account toAccountTree() {
        if (getModel() != null
                && (getModel().getRoot() instanceof DefaultMutableTreeNode)) {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
            //if root contains account
            if (root.getUserObject() instanceof Account) {
                adoptChildren(root, (Account) root.getUserObject());
                return (Account) root.getUserObject();
            }
        }
        return null;

    }

    private void adoptChildren(DefaultMutableTreeNode parentNode, Account parentAccount) {
        parentAccount.getChildren().clear();
        Enumeration<DefaultMutableTreeNode> children = parentNode.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode childNode = children.nextElement();
            if (childNode.getUserObject() instanceof Account) {
                Account childAccount = (Account) childNode.getUserObject();
                parentAccount.getChildren().add(childAccount);
                adoptChildren(childNode, childAccount);
            }
        }
    }
}
