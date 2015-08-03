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
package dakside.hacc.modules.databridge;

import org.dakside.duck.dakwizard.WizardDialog;
import dakside.hacc.modules.databridge.dataagent.ImportLoaderWizardModel;
import dakside.hacc.modules.databridge.synccentre.SyncCentreView;
import java.awt.Component;
import org.dakside.duck.plugins.Function;
import org.dakside.duck.plugins.Unloadable;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DataBridgeModule implements Unloadable {

    private static Component viewDataImport = null;
    private static Component viewSyncCentre = null;

    public void unload() {
        viewDataImport = null;
        viewSyncCentre = null;
    }

//    @Command(Text = "Import Data", Description = "Import Data",
//    IconPath = "icon_dataimport.png", Category = "Import/Export", Location = Command.STARTPAGE)
//    public Component getDataImportViewStartPage() {
//        return getDataImportView();
//    }
//
//    private Component getDataImportView() {
//        if (viewDataImport == null) {
//            viewDataImport = new DataAgentView();
//        }
//        return viewDataImport;
//    }



    @Function(Text = "ImportData", Description = "ImportDataDesc",
    IconPath = "ImportExportIcon", Category = "Import/Export", Location = Function.TOOLBAR)
    public void getDataImportViewToolbar() {
        //start wizard
        ImportLoaderWizardModel model =
                (ImportLoaderWizardModel) WizardDialog.showDialog(
                new ImportLoaderWizardModel(), "Data Import");
    }

    @Function(Text = "SyncCentre", Description = "SyncCentreDesc",
    IconPath = "icon_synccentre.png", Category = "Import/Export", Location = Function.STARTPAGE)
    public Component getSyncCentreView() {
        if (viewSyncCentre == null) {
            viewSyncCentre = new SyncCentreView();
        }
        return viewSyncCentre;
    }
}
