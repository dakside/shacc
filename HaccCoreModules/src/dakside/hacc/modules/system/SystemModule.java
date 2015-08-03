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
package dakside.hacc.modules.system;

import dakside.hacc.modules.system.appstat.StatisticView;
import dakside.hacc.modules.system.backup.BackupController;
import dakside.hacc.modules.system.cleaner.CleanerView;
import dakside.hacc.modules.system.config.ConfigurationView;
import dakside.hacc.modules.system.initialsetup.InitialSetupView;
import java.awt.Component;
import org.dakside.duck.plugins.Function;
import org.dakside.duck.plugins.Unloadable;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class SystemModule implements Unloadable {

    private static Component viewConfig = null;
    private static Component viewAppStat = null;
    private static Component viewInitialSetup = null;
    private static Component viewCleaner = null;

    public synchronized void unload() {
        viewConfig = null;
        viewAppStat = null;
        viewInitialSetup = null;
        viewCleaner = null;
    }

    @Function(Text = "Application Settings", Description = "<html> Config SHACC<br/>",
    IconPath = "icon_cfg.png", Category = "Configuration", Location = Function.STARTPAGE)
    public Component getAppConfigView() {
        if (viewConfig == null) {
            viewConfig = new ConfigurationView();
        }
        return viewConfig;
    }

    @Function(Text = "Application Statistics",
    Description = "<html> Show application statistic. Included:<br/>"
    + "Database information<br/>"
    + "Application information.",
    IconPath = "icon_appstat.png", Category = "Configuration", Location = Function.STARTPAGE)
    public Component getAppStatView() {
        if (viewAppStat == null) {
            viewAppStat = new StatisticView();
        }
        return viewAppStat;
    }

    @Function(Text = "Initial Setup",
    Description = "Create sample data when the application is started at the first time.",
    IconPath = "icon_appstat.png", Category = "Configuration", Location = Function.STARTPAGE)
    public Component getInitialSetupView() {
        if (viewInitialSetup == null) {
            viewInitialSetup = new InitialSetupView();
        }
        return viewInitialSetup;
    }

    @Function(Text = "Database cleaning",
    Description = "Clean trash from database.",
    IconPath = "icon_appstat.png", Category = "Configuration", Location = Function.STARTPAGE)
    public Component getCleanerView() {
        if (viewCleaner == null) {
            viewCleaner = new CleanerView();
        }
        return viewCleaner;
    }

    @Function(Text = "backupDB",
    Description = "backupDB_desc",
    IconPath = "icon_appstat.png", Category = "mnuSystem", Location = Function.MENU)
    public void processBackup() {
        BackupController.backup();
    }

    @Function(Text = "backupDB",
    Description = "backupDB_desc",
    IconPath = "icon_appstat.png", Category = "mnuSystem", Location = Function.TOOLBAR)
    public void processBackup_Toolbar() {
        BackupController.backup();
    }
}
