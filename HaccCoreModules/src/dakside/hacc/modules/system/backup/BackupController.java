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
package dakside.hacc.modules.system.backup;

import dakside.hacc.core.config.AppMessages;
import dakside.hacc.core.config.Configuration;
import org.dakside.duck.helpers.SwingHelper;
import org.dakside.utils.DateTimeHelper;
import org.dakside.utils.FileUtil;
import org.dakside.utils.PathHelper;
import org.dakside.utils.ResourceCentre;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dakside.duck.plugins.AppCentral;
import org.dakside.duck.plugins.AppCentralAPI;
import org.dakside.duck.plugins.Message;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class BackupController {

    private ResourceCentre rc = ResourceCentre.getInstance(this);
    private static final Logger logger = Logger.getLogger(BackupController.class.getName());

    public static void backup() {
        (new BackupController()).process();
    }

    public void process() {
        AppCentralAPI api = AppCentral.getAPIDelegate();
        try {
            String path = Configuration.getInstance().getInfo().getConnectionString();
            File dbFile = new File(path);

            //notify user first
            if (SwingHelper.confirm((rc.getString("warning_shutdown")))) {
                //disconnect
                api.sendMessage(new Message(AppMessages.DISCONNECT));
                //backup
                File dbFolder = dbFile.getParentFile();
                if (dbFolder.isDirectory()) {
                    //prepare backup folder
                    File backupFolder = new File(PathHelper.combine(dbFolder.getAbsolutePath(), "backup"));
                    backupFolder.mkdirs();
                    logger.log(Level.INFO, "Backup folder = {0}", backupFolder);

                    //copy file
                    String timestamp = DateTimeHelper.toString(new Date(), "yyyyMMdd_hhmmss");
                    File copyTo = new File(backupFolder.getAbsolutePath(), "backup_" + timestamp + "_" + dbFile.getName());
                    logger.log(Level.INFO, "Copy from {0} to {1}", new Object[]{dbFile, copyTo});
                    FileUtil.copy(dbFile, copyTo);

                    //notify user
                    api.popup(rc.getMessage("backup_ok", new Object[]{copyTo.getAbsolutePath()}));
                }

                //connect
                api.sendMessage(new Message(AppMessages.CONNECT));
            }
        } catch (Exception e) {
            api.popup(rc.getString("cannot_backup"));
            logger.log(Level.SEVERE, "CANNOT BACKUP FILE", e);
        }

    }
}
