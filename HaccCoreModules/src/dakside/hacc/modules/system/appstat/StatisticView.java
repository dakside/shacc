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
/*
 * StatisticView.java
 *
 * Created on Dec 27, 2009, 1:41:45 PM
 */
package dakside.hacc.modules.system.appstat;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import dakside.hacc.core.dao.CurrencyDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.dao.DAOFactory;
import dakside.hacc.core.dao.TransactionDAO;
import dakside.hacc.core.models.Account;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.Currency;
import dakside.hacc.core.models.Transaction;
import dakside.hacc.core.models.TransactionType;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dakside.duck.plugins.AppConstants;
import org.dakside.duck.plugins.Command;
import org.dakside.duck.plugins.CommandInvoker;
import org.dakside.duck.plugins.DefaultCommandInvoker;
import org.dakside.duck.plugins.FunctionFacade;
import org.dakside.duck.plugins.FunctionGroup;
import org.dakside.duck.plugins.FunctionPool;
import org.dakside.duck.plugins.ToolbarContainer;
import org.dakside.utils.SystemHelper;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class StatisticView extends javax.swing.JPanel implements ToolbarContainer {

    /** Creates new form StatisticView */
    public StatisticView() {
        initComponents();
        loadStatistic();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDBInfo = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtApplicationInfo = new javax.swing.JTextArea();

        jLabel1.setText("Database statistic");
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtDBInfo.setColumns(20);
        txtDBInfo.setEditable(false);
        txtDBInfo.setRows(5);
        txtDBInfo.setName("txtDBInfo"); // NOI18N
        jScrollPane1.setViewportView(txtDBInfo);

        jLabel2.setText("Application statistic");
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtApplicationInfo.setColumns(20);
        txtApplicationInfo.setEditable(false);
        txtApplicationInfo.setRows(5);
        txtApplicationInfo.setName("txtApplicationInfo"); // NOI18N
        jScrollPane2.setViewportView(txtApplicationInfo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void loadStatistic() {
        //clear textboxes
        txtDBInfo.setText("");
        txtApplicationInfo.setText("");

        //log db info
        try {
            DAOFactory factory = Configuration.getInstance().getDAOFactory();
            AccountDAO dao = factory.getAccountDAO();
            TransactionDAO trxDAO = factory.getTransactionDAO();
            CurrencyDAO curDAO = factory.getCurrencyDAO();
            //count accounts
            Account[] accounts = dao.getAllAccounts();
            logDB("Accounts: " + accounts.length);
            //count account period
            AccountPeriod[] ap = dao.getAllAccountPeriods();
            logDB("Accounts period: " + ap.length);
            //count transaction types
            TransactionType[] trxTypes = trxDAO.getAllTransactionTypes();
            logDB("Transaction types: " + trxTypes.length);
            //transactions
            Transaction[] transactions = trxDAO.getAllTransactions();
            logDB("Transaction: " + transactions.length);
            //currencies
            Currency[] currencies = curDAO.getAllCurrencies();
            logDB("Currencies: " + currencies.length);
        } catch (DAOException ex) {
            Logger.getLogger(StatisticView.class.getName()).log(Level.SEVERE, null, ex);
        }

        //log used files
        logDB("Data file  : " + Configuration.getInstance().getInfo().getConnectionString());
        logDB("Config file: " + Configuration.getInstance().getConfigFilePath());

        //log applications
        FunctionFacade[] modules = FunctionPool.getInstance().getAllCommands();
        FunctionGroup[] groups = FunctionPool.getInstance().getAllGroups();
        logApp("Modules count: " + modules.length);
        for (FunctionGroup group : groups) {
            logApp("Group: " + group.getTitle());
            for (FunctionFacade module : group.toArray()) {
                logApp("  -> " + module.getInfo().Text());
            }

        }
    }

    private void logDB(String message) {
        txtDBInfo.append(message);
        txtDBInfo.append(SystemHelper.getLineSeparator());
    }

    private void logApp(String message) {
        txtApplicationInfo.append(message);
        txtApplicationInfo.append(SystemHelper.getLineSeparator());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtApplicationInfo;
    private javax.swing.JTextArea txtDBInfo;
    // End of variables declaration//GEN-END:variables

//TOOLBAR CONTAINER SUPPORT
    private Component[] toolbar;

    public Component[] getToolbarItems() {
        if (toolbar == null) {
            toolbar = new Component[0];
        }
        return toolbar;
    }
    private DefaultCommandInvoker myInvoker = null;

    public final synchronized CommandInvoker getInvoker() {
        if (myInvoker == null) {
            myInvoker = new DefaultCommandInvoker();
        }

        myInvoker.add(AppConstants.Commands.REFRESH, new Command() {

            public Object invoke(Object[] args) {
                loadStatistic();
                return true;
            }
        });
        return myInvoker;
    }
    //END OF TOOLBAR CONTAINER SUPPORT
}
