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

/*
 * JournalVoucherView.java
 *
 * Created on Jan 5, 2010, 10:43:53 PM
 */
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.core.models.AccountEntry;
import dakside.hacc.core.models.AccountPeriod;
import dakside.hacc.core.models.JournalEntry;
import dakside.hacc.core.models.comparators.AccountPeriodComparator;
import org.dakside.duck.helpers.SwingHelper;
import dakside.hacc.modules.accounting.accountposting.AccountingController;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.dakside.duck.plugins.AppConstants;
import org.dakside.duck.plugins.Command;
import org.dakside.duck.plugins.CommandInvoker;
import org.dakside.duck.plugins.DefaultCommandInvoker;
import org.dakside.duck.plugins.ToolbarContainer;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class JournalVoucherView extends javax.swing.JPanel implements ToolbarContainer {

    AccountingController controller = null;

    /** Creates new form JournalVoucherView */
    public JournalVoucherView() {
        initComponents();
        onFormLoad();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customToolbar = new javax.swing.JToolBar();
        btnUnpost = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblJournalEntry = new JournalEntryTable();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        cboPeriod = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAccountEntry = new AccountEntryTable();

        customToolbar.setFloatable(false);
        customToolbar.setRollover(true);
        customToolbar.setName("customToolbar"); // NOI18N

        btnUnpost.setText("Unpost");
        btnUnpost.setFocusable(false);
        btnUnpost.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUnpost.setName("btnUnpost"); // NOI18N
        btnUnpost.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUnpost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnpostActionPerformed(evt);
            }
        });
        customToolbar.add(btnUnpost);

        jPanel6.setName("jPanel6"); // NOI18N

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setText("Journal Entries");
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblJournalEntry.setModel(new JournalEntryTableModel(

        ));
        tblJournalEntry.setName("tblJournalEntry"); // NOI18N
        jScrollPane1.setViewportView(tblJournalEntry);

        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jLabel1.setText("Account Period");
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar1.add(jLabel1);

        cboPeriod.setName("cboPeriod"); // NOI18N
        cboPeriod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboPeriodItemStateChanged(evt);
            }
        });
        cboPeriod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeriodActionPerformed(evt);
            }
        });
        jToolBar1.add(cboPeriod);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setText("Account entries");
        jLabel3.setName("jLabel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tblAccountEntry.setModel(new AccountEntryTableModel());
        tblAccountEntry.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblAccountEntry.setName("tblAccountEntry"); // NOI18N
        jScrollPane2.setViewportView(tblAccountEntry);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboPeriodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboPeriodItemStateChanged
        refreshJournalVoucher();
    }//GEN-LAST:event_cboPeriodItemStateChanged

    private void cboPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeriodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPeriodActionPerformed

    private void btnUnpostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnpostActionPerformed
        JournalEntryTable tbl = (JournalEntryTable) tblJournalEntry;
        JournalEntry[] entries = tbl.getSelectedJournalEntry();
        if (entries != null && entries.length > 0) {
            for (JournalEntry entry : entries) {
                controller.unpost(entry);
            }
        }
        refresh();
    }//GEN-LAST:event_btnUnpostActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUnpost;
    private javax.swing.JComboBox cboPeriod;
    private javax.swing.JToolBar customToolbar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblAccountEntry;
    private javax.swing.JTable tblJournalEntry;
    // End of variables declaration//GEN-END:variables

    private void onFormLoad() {
        tblJournalEntry.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                refreshAccountEntries();
            }
        });

        controller = new AccountingController();

        refresh();
    }

    private void refreshJournalVoucher() {
        AccountPeriod period = cboPeriod.getSelectedItem() instanceof AccountPeriod
                ? (AccountPeriod) cboPeriod.getSelectedItem() : null;

        JournalEntry[] entries = controller.getJournalEntriesOf(period);
        //sort journal entry
        Arrays.sort(entries, JournalEntry.getComparator());
        ((JournalEntryTable) tblJournalEntry).loadJournalEntries(entries);
    }

    private void refreshAccountEntries() {
        JournalEntryTable tbl = (JournalEntryTable) tblJournalEntry;
        JournalEntry[] entries = tbl.getSelectedJournalEntry();

        //update account entry list
        AccountEntryTable tblAccEntry = (AccountEntryTable) tblAccountEntry;
        AccountEntryTableModel accEntrymodel = (AccountEntryTableModel) tblAccEntry.getModel();
        accEntrymodel.removeAll();

        if (entries != null && entries.length > 0) {
            boolean canUnpost = true;
            for (JournalEntry je : entries) {
                canUnpost = canUnpost & (je.getAccountPeriod() != null
                        && je.getAccountPeriod().getStatus() == AccountPeriod.OPENING);

                for (AccountEntry accEntry : je.getAccountEntries()) {
                    accEntrymodel.add(accEntry);
                }
            }
            //can unpost or not?
            btnUnpost.setEnabled(canUnpost);
        } else {
            btnUnpost.setEnabled(false);
        }
    }

    private void refresh() {
        Object currentPeriod = null;
        if (cboPeriod.getItemCount() > 0) {
            currentPeriod = cboPeriod.getSelectedItem();
        }

        //select periods list
        AccountPeriod[] periods = controller.getAllOpeningPeriods();
        //sort the list
        Arrays.sort(periods, AccountPeriodComparator.getAscInstance());

        ArrayList items = new ArrayList(periods.length + 1);
        items.add("All");
        Collections.addAll(items, periods);

        //auto select current accounting period
        if (currentPeriod == null) {
            for (AccountPeriod period : periods) {
                if (period != null && AccountPeriod.CURRENT.equals(period.getState())) {
                    currentPeriod = period;
                }
            }
        }

        SwingHelper.initCombobox(cboPeriod, items.toArray(), currentPeriod);
        refreshJournalVoucher();
        refreshAccountEntries();
    }
    //TOOLBAR CONTAINER SUPPORT
    private Component[] toolbar;

    public Component[] getToolbarItems() {
        if (toolbar == null) {
            toolbar = customToolbar.getComponents();
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
                refresh();
                return true;
            }
        });

        return myInvoker;
    }
    //END OF TOOLBAR CONTAINER SUPPORT
}
