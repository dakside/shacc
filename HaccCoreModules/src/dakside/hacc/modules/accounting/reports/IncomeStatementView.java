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
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.reports.XHTMLReport;
import org.dakside.utils.Localizable;
import org.dakside.utils.ResourceCentre;
import java.awt.Component;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.dakside.duck.plugins.AppConstants;
import org.dakside.duck.plugins.Command;
import org.dakside.duck.plugins.CommandInvoker;
import org.dakside.duck.plugins.DefaultCommandInvoker;
import org.dakside.duck.plugins.ToolbarContainer;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class IncomeStatementView extends XHTMLReport implements ToolbarContainer, Localizable {

    private ResourceCentre rc = ResourceCentre.getInstance(this);
    private static final Logger logger = Logger.getLogger(BalanceSheetView.class.getName());

    public IncomeStatementView() {
        initializeComponents();
        renderReport();
    }

    private void initializeComponents() {
        btnExport = new javax.swing.JButton();
        btnExport.setText(rc.getString("btnExport.text"));
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setName("btnExport"); // NOI18N
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExport.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportToPDF("IncomeStatement.pdf");
            }
        });

        //add year chooser
        reportYear = new JSpinner();
        reportYear.setModel(new SpinnerNumberModel(2010, 2009, 2014, 1));
        reportYear.setSize(70, 70);
        reportYear.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                renderReport();
            }
        });

        reportYearLabel = new JLabel("Report Year");
//        yearChooserPanel = new JPanel();
//        yearChooserPanel.add(new JLabel(rc.getString("txtReportYear.text")));
//        yearChooserPanel.add(new JLabel("Report Year"));
//        yearChooserPanel.add(reportYear);
    }

    private void renderReport() {
//        int thisYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
        //render report to stream
//        InputStream stream = IncomeStatementReport.getInstance().render(new Object[]{thisYear});
        InputStream stream = IncomeStatementReport.getInstance().render(new Object[]{reportYear.getValue()});
        if (stream != null) {
            //render to view (display)
            super.render(stream);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="TOOLBAR CONTAINER SUPPORT">
    private Component[] toolbar;
    private javax.swing.JButton btnExport;
    private javax.swing.JSpinner reportYear;
    private javax.swing.JLabel reportYearLabel;
    private javax.swing.JPanel yearChooserPanel;

    public Component[] getToolbarItems() {
        if (toolbar == null) {
            toolbar = new Component[]{reportYearLabel, reportYear, btnExport};
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
                renderReport();
                return true;
            }
        });

        return myInvoker;
    }

    //END OF TOOLBAR CONTAINER SUPPORT
    //</editor-fold> 
    public void localize() {
        if (btnExport != null) {
            btnExport.setText(rc.getString("btnExport.text"));
        }
        if (reportYearLabel != null) {
            reportYearLabel.setText(rc.getString("txtReportYear.text"));
        }
    }
}
