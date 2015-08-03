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
package testreportengine;

import org.dakside.dao.DAOException;
import org.dakside.exceptions.ArgumentException;
import dakside.hacc.modules.accounting.reports.test.BalanceSheetDataRetriever;
import dakside.hacc.modules.accounting.reports.test.IncomeStatementDataRetriever;
import dakside.hacc.modules.accounting.reports.test.MockDAO;
import dakside.reports.DataRetriever;
import dakside.reports.ReportGenerator;
import dakside.reports.ReportTemplate;
import dakside.reports.xarl.XARTemplate;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class TestReportEngineView extends FrameView {

    public TestReportEngineView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        setup();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = TestReportEngineApp.getApplication().getMainFrame();
            aboutBox = new TestReportEngineAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        TestReportEngineApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        fSScrollPane1 = new org.xhtmlrenderer.simple.FSScrollPane();
        reportView = new org.xhtmlrenderer.simple.XHTMLPanel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        cboTemplate = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cboDataSource = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSource = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        txtDBPath = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnGenerate = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTabbedPane1ComponentShown(evt);
            }
        });

        fSScrollPane1.setName("fSScrollPane1"); // NOI18N

        reportView.setName("reportView"); // NOI18N

        javax.swing.GroupLayout reportViewLayout = new javax.swing.GroupLayout(reportView);
        reportView.setLayout(reportViewLayout);
        reportViewLayout.setHorizontalGroup(
            reportViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );
        reportViewLayout.setVerticalGroup(
            reportViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );

        fSScrollPane1.setViewportView(reportView);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(testreportengine.TestReportEngineApp.class).getContext().getResourceMap(TestReportEngineView.class);
        jTabbedPane1.addTab(resourceMap.getString("fSScrollPane1.TabConstraints.tabTitle"), fSScrollPane1); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jToolBar2.add(jLabel2);

        cboTemplate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTemplate.setName("cboTemplate"); // NOI18N
        cboTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTemplateActionPerformed(evt);
            }
        });
        jToolBar2.add(cboTemplate);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jToolBar2.add(jLabel3);

        cboDataSource.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDataSource.setName("cboDataSource"); // NOI18N
        jToolBar2.add(cboDataSource);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtSource.setColumns(20);
        txtSource.setRows(5);
        txtSource.setName("txtSource"); // NOI18N
        jScrollPane1.setViewportView(txtSource);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jToolBar1.add(jLabel1);

        txtDBPath.setText(resourceMap.getString("txtDBPath.text")); // NOI18N
        txtDBPath.setName("txtDBPath"); // NOI18N
        jToolBar1.add(txtDBPath);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(testreportengine.TestReportEngineApp.class).getContext().getActionMap(TestReportEngineView.class, this);
        btnConnect.setAction(actionMap.get("connectDB")); // NOI18N
        btnConnect.setText(resourceMap.getString("btnConnect.text")); // NOI18N
        btnConnect.setFocusable(false);
        btnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConnect.setName("btnConnect"); // NOI18N
        btnConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnConnect);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar1.add(jSeparator1);

        btnGenerate.setAction(actionMap.get("generateReport")); // NOI18N
        btnGenerate.setText(resourceMap.getString("btnGenerate.text")); // NOI18N
        btnGenerate.setFocusable(false);
        btnGenerate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerate.setName("btnGenerate"); // NOI18N
        btnGenerate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnGenerate);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTabbedPane1ComponentShown
        generateReport();
    }//GEN-LAST:event_jTabbedPane1ComponentShown

    private void cboTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTemplateActionPerformed
        try {
            Item i = (Item) cboTemplate.getSelectedItem();
            if (i == null) {
                return;
            }
            String TEMPLATE = (String) i.getValue();
            txtSource.read(new InputStreamReader(this.getClass().getResourceAsStream(TEMPLATE)), this);
        } catch (Exception ex) {
            Logger.getLogger(TestReportEngineView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cboTemplateActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JComboBox cboDataSource;
    private javax.swing.JComboBox cboTemplate;
    private org.xhtmlrenderer.simple.FSScrollPane fSScrollPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private org.xhtmlrenderer.simple.XHTMLPanel reportView;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextField txtDBPath;
    private javax.swing.JTextArea txtSource;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    private DataRetriever getCurrentRetriever() {
        try {
            return (DataRetriever) ((Item) cboDataSource.getSelectedItem()).getValue();
        } catch (Exception e) {
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Initial setup">
    private class Item {

        private String name;
        private Object value;

        public Item(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.getName();
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(Object value) {
            this.value = value;
        }
    }

    private void setup() {
        cboTemplate.removeAllItems();
        cboDataSource.removeAllItems();
        //init templates
        cboTemplate.addItem(new Item("Income Statement", "/dakside/hacc/modules/accounting/reports/test/resources/IncomeStatement.xhtml"));
        cboTemplate.addItem(new Item("Balance Sheet", "/dakside/hacc/modules/accounting/reports/test/resources/BalanceSheet.xhtml"));
        cboTemplate.addItem(new Item("Test JS", "/dakside/hacc/modules/accounting/reports/test/resources/XARTemplate.xhtml"));

        cboDataSource.addItem(new Item("Balance Sheet", new BalanceSheetDataRetriever()));
        cboDataSource.addItem(new Item("IncomeStatement", new IncomeStatementDataRetriever()));
    }

    //</editor-fold>
    @Action
    public void generateReport() {
        jTabbedPane1.setSelectedComponent(fSScrollPane1);
        //create data retriever
        DataRetriever retriever = getCurrentRetriever();
        //Init report template
        ReportTemplate template = new XARTemplate(txtSource.getText());
        //create report generator object
        ReportGenerator generator = new ReportGenerator(template, retriever);

        //generate report
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (generator.render(output, new Object[]{2009, 2010})) {
            ByteArrayInputStream content = new ByteArrayInputStream(output.toByteArray());
            if (content != null) {
                try {
                    reportView.setDocument(content, "/");
                } catch (Exception ex) {
                    Logger.getLogger(TestReportEngineView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Action
    public void connectDB() {
        try {
            MockDAO.shutdown();
            MockDAO.getDAO(txtDBPath.getText());
        } catch (ArgumentException ex) {
            Logger.getLogger(TestReportEngineView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(TestReportEngineView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
