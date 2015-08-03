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
import org.dakside.duck.plugins.AppConstants;
import org.dakside.duck.plugins.Command;
import org.dakside.duck.plugins.CommandInvoker;
import org.dakside.duck.plugins.DefaultCommandInvoker;
import org.dakside.duck.plugins.ToolbarContainer;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class BalanceSheetView extends XHTMLReport implements ToolbarContainer, Localizable {

    private static final Logger logger = Logger.getLogger(BalanceSheetView.class.getName());
    private ResourceCentre rc = ResourceCentre.getInstance(this);

    public BalanceSheetView() {
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
                exportToPDF("BalanceSheet.pdf");
            }
        });
    }

    private void renderReport() {
        InputStream stream = BalanceSheet.getInstance().render(new Object[0]);
        if (stream != null) {
            super.render(stream);
        }
    }
    //TOOLBAR CONTAINER SUPPORT
    private Component[] toolbar;
    private javax.swing.JButton btnExport;

    public Component[] getToolbarItems() {
        if (toolbar == null) {
            toolbar = new Component[]{btnExport};
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

    public void localize() {
        if (btnExport != null) {
            btnExport.setText(rc.getString("btnExport.text"));
        }
    }
    //END OF TOOLBAR CONTAINER SUPPORT
}
