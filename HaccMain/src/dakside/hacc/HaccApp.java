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

/*
 * HaccApp.java
 */
import dakside.hacc.core.config.ConfigEntry;
import dakside.hacc.core.config.Configuration;
import org.dakside.utils.ResourceCentre;
import java.util.Locale;
import java.util.logging.Logger;
import org.dakside.duck.plugins.AppCentral;
import org.dakside.duck.plugins.AppCentralAPI;
import org.dakside.duck.plugins.FunctionPool;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class HaccApp extends SingleFrameApplication {

    private static final Logger logger = Logger.getLogger(HaccApp.class.getName());

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        try {
            //hardcode set locale
            String language = Configuration.getInstance().getConfig(ConfigEntry.LANGUAGE).toString();
            if (language != null && !language.trim().isEmpty()) {
                ResourceCentre.setGlobalLocale(new Locale(language));
            } else {
                // TODO: Show a wizard dialog to select language & setup
                //Default language is vi
                ResourceCentre.setGlobalLocale(new Locale("en"));
                //store vi to default config
                Configuration.getInstance().saveConfig(ConfigEntry.LANGUAGE, "vi");
            }
        } catch (Exception ex) {
            //any problem? -> set default language
            //ResourceCentre.setGlobalLocale(new Locale("vi"));
        }

        FunctionPool.getInstance().scan();
        FrameView mainView = new HaccView(this);
        AppCentral.initApp((AppCentralAPI) mainView);
        show(mainView);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of HaccApp
     */
    public static HaccApp getApplication() {
        return Application.getInstance(HaccApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(HaccApp.class, args);
    }
}
