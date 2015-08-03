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
 * CleanerView.java
 *
 * Created on Apr 13, 2010, 12:06:39 AM
 */
package dakside.hacc.modules.system.cleaner;

import dakside.hacc.core.config.Configuration;
import dakside.hacc.core.dao.AccountDAO;
import org.dakside.dao.DAOException;
import dakside.hacc.core.models.JournalEntry;
import org.dakside.utils.ResourceCentre;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class CleanerView extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(CleanerView.class.getName());
    private ResourceCentre rc = null;

    /** Creates new form CleanerView */
    public CleanerView() {
        initComponents();
        localize();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCleaner = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();

        btnCleaner.setText("btnCleaner");
        btnCleaner.setName("btnCleaner"); // NOI18N
        btnCleaner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanerActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtLog.setColumns(20);
        txtLog.setRows(5);
        txtLog.setName("txtLog"); // NOI18N
        jScrollPane1.setViewportView(txtLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(btnCleaner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCleaner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCleanerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanerActionPerformed
        clean();
    }//GEN-LAST:event_btnCleanerActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCleaner;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

    /**
     * localize this view
     */
    private void localize() {
        rc = ResourceCentre.getInstance(this);

        btnCleaner.setText(rc.getString("btnCleaner"));
    }

    private void log(String message) {
        txtLog.append(message);
        txtLog.append("\r\n");
    }

    private void clean() {
        //1. clean config file
        log(rc.getString("clean_config"));

        //2. clean database
        log(rc.getString("clean_db"));
        try {
            //Find broken journal entry (JE without transaction)
            AccountDAO dao = Configuration.getInstance().getDAOFactory().getAccountDAO();
            JournalEntry[] entries = dao.getAllJournalEntries();

            int broken = 0;
            for (JournalEntry entry : entries) {
                if (entry.getTransaction() == null) {
                    dao.delete(entry);
                    broken++;
                }
            }
            log("Found & removed " + broken + " broken entries.");
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, "Error happened while cleaning database", ex);
        }

        //finish
        log(rc.getString("finish"));
    }
}
