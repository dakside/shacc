/*
 *  Copyright (C) 2010 michael
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

/**
 *
 * @author michael
 */
package dakside.hacc.modules.financial.currencyman;

import dakside.hacc.core.helpers.exceptions.CurrencyNotFound;
import dakside.hacc.core.models.Currency;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author michael
 */
public class CurrencyManageView extends javax.swing.JPanel {

    /** Creates new form CurrencyManageView */
    public CurrencyManageView() {
        initComponents();
        loadUIData();
    }

    public void loadUIData(){
        //@toto: rewrite this mess plz :(
        try {
            DefaultListModel model = new DefaultListModel();

            CurrencyManager manager = CurrencyManager.getInstance();
            Currency[] usedCurrency = manager.getAllCurrency();
            Currency[] unusedCurrency = manager.getUnusedCurrency();
            System.out.println("usedCurrency length = " + usedCurrency.length);
            System.out.println("unusedCurrency length " + unusedCurrency.length);

            DefaultListModel usedModel = new DefaultListModel();
            DefaultListModel unusedModel = new DefaultListModel();
            for (int i = 0; i <= usedCurrency.length - 1; i++) {
                usedModel.add(i, usedCurrency[i]);
            }
            for (int i = 0; i <= unusedCurrency.length - 1; i++) {
                unusedModel.add(i, unusedCurrency[i]);
            }
            lstUnused.setModel(unusedModel);
            lstUsed.setModel(usedModel);
        } catch (Exception ex) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstUnused = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstUsed = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        lstUnused.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstUnused.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        lstUnused.setName("lstUnused"); // NOI18N
        lstUnused.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lstUnusedFocusGained(evt);
                lstUnusedFocusGained1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lstUnusedFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(lstUnused);

        jButton1.setText(">>");
        jButton1.setMaximumSize(new java.awt.Dimension(66, 29));
        jButton1.setMinimumSize(new java.awt.Dimension(66, 29));
        jButton1.setName("btnAdd"); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(66, 29));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("<<");
        jButton2.setMaximumSize(new java.awt.Dimension(86, 29));
        jButton2.setMinimumSize(new java.awt.Dimension(86, 29));
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(86, 29));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        lstUsed.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstUsed.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        lstUsed.setName("lstUsed"); // NOI18N
        lstUsed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lstUsedFocusGained(evt);
                lstUsedFocusGained1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lstUsedFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(lstUsed);

        jLabel1.setText("Unused Currency");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Currency In Use");
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(72, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int first = lstUsed.getMinSelectionIndex();
        int last = lstUsed.getMaxSelectionIndex();

        DefaultListModel unusedModel = (DefaultListModel) lstUnused.getModel();
        DefaultListModel usedModel = (DefaultListModel) lstUsed.getModel();

        System.out.println("first: " + first + "\t last: " + last);
        if (first > -1 && last > -1 && first <= last) {
            //remove selected index
            for (int i = first; i <= last; i++) {
                Currency cur = (Currency) usedModel.getElementAt(i);
                CurrencyManager.getInstance().delete(cur);
                System.out.println("delete: " + cur);
                unusedModel.addElement(cur);
            }


            usedModel.removeRange(first, last);


        }
}//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int first = lstUnused.getMinSelectionIndex();
        int last = lstUnused.getMaxSelectionIndex();

        DefaultListModel unusedModel = (DefaultListModel) lstUnused.getModel();
        DefaultListModel usedModel = (DefaultListModel) lstUsed.getModel();

        System.out.println("first: " + first + "\t last: " + last);
        if (first > -1 && last > -1 && first <= last) {
            //remove selected index
            for (int i = first; i <= last; i++) {
                Currency cur = (Currency) unusedModel.getElementAt(i);
                CurrencyManager.getInstance().save(cur);
                System.out.println("save: " + cur);
                usedModel.addElement(cur);
            }
            unusedModel.removeRange(first, last);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void lstUnusedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstUnusedFocusGained
        jButton2.setEnabled(false);
    }//GEN-LAST:event_lstUnusedFocusGained

    private void lstUsedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstUsedFocusGained
        jButton1.setEnabled(false);
    }//GEN-LAST:event_lstUsedFocusGained

    private void lstUnusedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstUnusedFocusLost
        jButton2.setEnabled(true);
    }//GEN-LAST:event_lstUnusedFocusLost

    private void lstUsedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstUsedFocusLost
        jButton1.setEnabled(true);
    }//GEN-LAST:event_lstUsedFocusLost

    private void lstUnusedFocusGained1(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstUnusedFocusGained1
        lstUsed.clearSelection();
    }//GEN-LAST:event_lstUnusedFocusGained1

    private void lstUsedFocusGained1(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstUsedFocusGained1
        lstUnused.clearSelection();
    }//GEN-LAST:event_lstUsedFocusGained1
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstUnused;
    private javax.swing.JList lstUsed;
    // End of variables declaration//GEN-END:variables
}