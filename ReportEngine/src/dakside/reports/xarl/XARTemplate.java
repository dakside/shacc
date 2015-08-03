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
package dakside.reports.xarl;

import dakside.reports.ReportData;
import dakside.reports.ReportTemplate;
import dakside.reports.arl.ReportRuntime;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Extensible active report
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class XARTemplate implements ReportTemplate {

    private static final Logger logger = Logger.getLogger(XARTemplate.class.getName());
    String code = null;

    public XARTemplate(InputStream raw) {
        this.code = XARParser.parse(raw).toString();
    }

    public XARTemplate(String raw) {
        this.code = XARParser.parse(raw).toString();
    }

    /**
     * Render this report
     * @param output
     * @param data
     * @return
     */
    public boolean render(OutputStream output, ReportData data) {
        ReportRuntime runtime = new ReportRuntime(output);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        //set data to runtime
        String[] keys = data.getKeys();
        for (String key : keys) {
            engine.getContext().setAttribute(key, data.getData(key), ScriptContext.ENGINE_SCOPE);
        }
        engine.getContext().setAttribute("out", runtime, ScriptContext.ENGINE_SCOPE);

        try {
            //render
            engine.eval(this.code);
            runtime.flush();
        } catch (ScriptException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void debug() {
        System.out.println(code);
    }
}
