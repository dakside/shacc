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
package dakside.hacc;

import dakside.hacc.core.config.AppMessages;
import org.dakside.utils.ResourceCentre;
import org.dakside.duck.helpers.SwingHelper;
import org.dakside.utils.Localizable;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.dakside.duck.appui.DuckFrame;
import org.dakside.duck.plugins.AppCentralAPI;
import org.dakside.duck.plugins.AppConstants;
import org.dakside.duck.plugins.AppTab;
import org.dakside.duck.plugins.Message;

/**
 * The application's main frame.
 */
public class HaccView extends FrameView implements AppCentralAPI, Localizable {

    private final ResourceCentre rc = ResourceCentre.getInstance(this);
    private static final Logger logger = Logger.getLogger(HaccView.class.getName());

    /**
     * Get HaccView instance
     *
     * @return
     */
    public static HaccView getInstance() {
        //Get view
        return (HaccView) HaccApp.getApplication().getMainView();
    }

    public HaccView(SingleFrameApplication app) {
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

        //XXX manual load start-page
        onFormStartup();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainTabPane = new javax.swing.JTabbedPane();
        mainToolbar = new javax.swing.JToolBar();
        btnStartPage = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnRefresh = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        mnuReport = new javax.swing.JMenu();
        mnuSystem = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        mnuResetWindows = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        mnuLicense = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        menuTabPaneControl = new javax.swing.JPopupMenu();
        mnuClose = new javax.swing.JMenuItem();
        mnuCloseAll = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        mainTabPane.setComponentPopupMenu(menuTabPaneControl);
        mainTabPane.setInheritsPopupMenu(true);
        mainTabPane.setName("mainTabPane"); // NOI18N
        mainTabPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mainTabPaneStateChanged(evt);
            }
        });

        mainToolbar.setFloatable(false);
        mainToolbar.setRollover(true);
        mainToolbar.setName("mainToolbar"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(dakside.hacc.HaccApp.class).getContext().getActionMap(HaccView.class, this);
        btnStartPage.setAction(actionMap.get("showStartPage")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dakside.hacc.HaccApp.class).getContext().getResourceMap(HaccView.class);
        btnStartPage.setText(resourceMap.getString("btnStartPage.text")); // NOI18N
        btnStartPage.setFocusable(false);
        btnStartPage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnStartPage.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnStartPage.setName("btnStartPage"); // NOI18N
        btnStartPage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(btnStartPage);

        jSeparator3.setName("jSeparator3"); // NOI18N
        mainToolbar.add(jSeparator3);

        btnNew.setText(resourceMap.getString("btnNew.text")); // NOI18N
        btnNew.setEnabled(false);
        btnNew.setFocusable(false);
        btnNew.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNew.setName("btnNew"); // NOI18N
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        mainToolbar.add(btnNew);

        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setEnabled(false);
        btnSave.setFocusable(false);
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        mainToolbar.add(btnSave);

        btnDelete.setText(resourceMap.getString("btnDelete.text")); // NOI18N
        btnDelete.setEnabled(false);
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        mainToolbar.add(btnDelete);

        jSeparator2.setName("jSeparator2"); // NOI18N
        mainToolbar.add(jSeparator2);

        btnRefresh.setText(resourceMap.getString("btnRefresh.text")); // NOI18N
        btnRefresh.setEnabled(false);
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        mainToolbar.add(btnRefresh);

        jSeparator1.setName("jSeparator1"); // NOI18N
        mainToolbar.add(jSeparator1);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
            .addComponent(mainTabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(mainToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainTabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setAction(actionMap.get("showStartPage")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        mnuReport.setText(resourceMap.getString("mnuReport.text")); // NOI18N
        mnuReport.setName("mnuReport"); // NOI18N
        menuBar.add(mnuReport);

        mnuSystem.setText(resourceMap.getString("mnuSystem.text")); // NOI18N
        mnuSystem.setName("mnuSystem"); // NOI18N
        menuBar.add(mnuSystem);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        mnuResetWindows.setAction(actionMap.get("resetWindows")); // NOI18N
        mnuResetWindows.setText(resourceMap.getString("mnuResetWindows.text")); // NOI18N
        mnuResetWindows.setName("mnuResetWindows"); // NOI18N
        jMenu1.add(mnuResetWindows);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        mnuLicense.setAction(actionMap.get("showLicense")); // NOI18N
        mnuLicense.setText(resourceMap.getString("mnuLicense.text")); // NOI18N
        mnuLicense.setName("mnuLicense"); // NOI18N
        helpMenu.add(mnuLicense);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setText(resourceMap.getString("statusMessageLabel.text")); // NOI18N
        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
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

        menuTabPaneControl.setName("menuTabPaneControl"); // NOI18N

        mnuClose.setAction(actionMap.get("closeCurrentTab")); // NOI18N
        mnuClose.setText(resourceMap.getString("mnuClose.text")); // NOI18N
        mnuClose.setName("mnuClose"); // NOI18N
        menuTabPaneControl.add(mnuClose);

        mnuCloseAll.setText(resourceMap.getString("mnuCloseAll.text")); // NOI18N
        mnuCloseAll.setEnabled(false);
        mnuCloseAll.setName("mnuCloseAll"); // NOI18N
        menuTabPaneControl.add(mnuCloseAll);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void mainTabPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mainTabPaneStateChanged
        //change tab
        refreshToolbar();
    }//GEN-LAST:event_mainTabPaneStateChanged

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        duckFrame.invokeCommand(AppConstants.Commands.NEW);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        duckFrame.invokeCommand(AppConstants.Commands.SAVE);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (JOptionPane.showConfirmDialog(duckFrame.getActiveTab(),
                rc.getString("confirm_delete"),
                rc.getString("confirm_delete_title"), JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            duckFrame.invokeCommand(AppConstants.Commands.DELETE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        duckFrame.invokeCommand(AppConstants.Commands.REFRESH);
    }//GEN-LAST:event_btnRefreshActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStartPage;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTabbedPane mainTabPane;
    private javax.swing.JToolBar mainToolbar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPopupMenu menuTabPaneControl;
    private javax.swing.JMenuItem mnuClose;
    private javax.swing.JMenuItem mnuCloseAll;
    private javax.swing.JMenuItem mnuLicense;
    private javax.swing.JMenu mnuReport;
    private javax.swing.JMenuItem mnuResetWindows;
    private javax.swing.JMenu mnuSystem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JDialog licenseBox;

    private DuckFrame duckFrame;

    //<editor-fold defaultstate="collapsed" desc="Form's actions">
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = HaccApp.getApplication().getMainFrame();
            aboutBox = new HaccAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        HaccApp.getApplication().show(aboutBox);
    }

    @Action
    public void closeCurrentTab() {
        Component component = mainTabPane.getSelectedComponent();
        if (component == startPage) {
            startPage = null; //remove start page
        }
        mainTabPane.remove(component);
    }

    /**
     * Close all available tab but start page
     */
    public void closeAllTabsButStartPage() {
        Component[] views = mainTabPane.getComponents();
        for (Component component : views) {
            if (component != startPage) {
                mainTabPane.remove(component);
            }
        }
    }
    private StartPage startPage = null;

    @Action
    public void resetWindows() {
        getFrame().setExtendedState(Frame.NORMAL);
        int w = 800;
        int h = 500;
        getFrame().setSize(w, h);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) Math.round((d.getWidth() - w) / 2);
        int y = (int) Math.round((d.getHeight() - h) / 2);
        getFrame().setLocation(x, y);
    }

    @Action
    public void showStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
            startPage.addActiveStateChangedListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    refreshExtensions();
                }
            });
        }
        duckFrame.show(rc.getString("Start_Page"), startPage);
    }

    @Action
    public void showLicense() {
        if (licenseBox == null) {
            JFrame mainFrame = HaccApp.getApplication().getMainFrame();
            licenseBox = new FormLicense(mainFrame);
            licenseBox.setLocationRelativeTo(mainFrame);
        }
        HaccApp.getApplication().show(licenseBox);
    }

    //</editor-fold>
    /**
     * do tasks when this form startup
     */
    private void onFormStartup() {
        duckFrame = new DuckFrame(menuBar, mainToolbar, mainTabPane, rc);
        //default menu construction
        constructMenu();
        //init commands
        duckFrame.initCommand();

        //show startup page
        showStartPage();

        //refresh extensions
        refreshExtensions();

        //localization
        localize();
    }
    //<editor-fold defaultstate="collapsed" desc="Toolbars and menus">

    private void refreshExtensions() {
        duckFrame.refreshExtensions(startPage.isActivated());
    }

    private void refreshToolbar() {
        duckFrame.refreshToolbar();
        refreshButtons();
        setStatusMessage("");
    }

    /**
     * Toolbar plugin buttons
     */
    /**
     * Default menu construction
     */
    private void constructMenu() {
        //XXX sample menu setup
//        JMenu mnuFile = FlexUIHelper.constructMenu(rc.getString("mnuFile.path"), rc.getString("mnuFile.text"), mnuReport);
//        FlexUIHelper.constructMenu(rc.getString("mnuReport.path"), rc.getString("mnuReport.text"), mnuReport);
//        FlexUIHelper.constructMenu(rc.getString("mnuExt.path"), rc.getString("mnuExt.text"), mnuReport);
//        FlexUIHelper.constructMenu(rc.getString("mnuLang.path"), rc.getString("mnuLang.text"), mnuReport);
//
//        RuntimeMenu mnuNew = new RuntimeMenu("mnuNew", "New", RuntimeMenu.MENUITEM);
//        JMenuItem item = mnuNew.bindTo(mnuFile);
//
//        item.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                //do nothing
//            }
//        });
    }

    //</editor-fold>
    /**
     *
     * @param view
     */
    public void showView(Object view) {
        if (view instanceof AppTab) {
            AppTab m = (AppTab) view;
            duckFrame.show(m.getTitle(), m.getView());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Com interaction">
    public void popup(String message) {
        SwingHelper.alert(message, rc.getString("Warning"));
    }

    public void setStatusMessage(String text) {
        statusMessageLabel.setText(text);
    }

    public void refreshButtons() {
        if (duckFrame.getCurrentInvoker() != null) {
            btnNew.setEnabled(duckFrame.getCurrentInvoker().support(AppConstants.Commands.NEW));
            btnSave.setEnabled(duckFrame.getCurrentInvoker().support(AppConstants.Commands.SAVE));
            btnDelete.setEnabled(duckFrame.getCurrentInvoker().support(AppConstants.Commands.DELETE));
            btnRefresh.setEnabled(duckFrame.getCurrentInvoker().support(AppConstants.Commands.REFRESH));
        } else {
            btnNew.setEnabled(false);
            btnSave.setEnabled(false);
            btnDelete.setEnabled(false);
            btnRefresh.setEnabled(false);
        }
    }

    public void setAppTitle(String text) {
        //prefix + " - " + text
        ResourceCentre apprc = ResourceCentre.getInstance(getApplication());
        if (text == null || text.trim().isEmpty()) {
            getFrame().setTitle(apprc.getString("Application.title"));
        } else {
            getFrame().setTitle(apprc.getString("Application.title") + " - " + text);
        }
    }

    public Object sendMessage(Message message) {
        if (Message.valid(message)) {
            if (message.getMessage().equals(AppMessages.CONNECT)) {
                startPage.loadDB();
                //connect database
                return new Message(AppMessages.SUCCESSFUL);
            } else if (message.getMessage().equals(AppMessages.DISCONNECT)) {
                startPage.unloadDB();
                //disconnect database
                return new Message(AppMessages.SUCCESSFUL);
            }
            return new Message(AppMessages.UNKNOWN_COMMAND);
        } else {
            return new Message(AppMessages.NOT_AVAILABLE);
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Localization">

    public void localize() {
        mnuSystem.setText(rc.getString("mnuSystem.text"));
        duckFrame.setIcon(btnStartPage, "btnStartPage.icon");
        duckFrame.setIcon(btnNew, "btnNew.icon");
        duckFrame.setIcon(btnSave, "btnSave.icon");
        duckFrame.setIcon(btnDelete, "btnDelete.icon");
        duckFrame.setIcon(btnRefresh, "btnRefresh.icon");
        duckFrame.setIcon(mnuReport, "mnuReport.icon");
    }
    //</editor-fold>
}
