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
package dakside.reports.arl;

import dakside.reports.ReportData;
import dakside.reports.ReportTemplate;
import dakside.reports.arl.blocks.CodeBlock;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.logging.Logger;

/**
 * HAR text active report
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ARTemplate implements ReportTemplate {

    private static final Logger logger = Logger.getLogger(ARTemplate.class.getName());
    CodeBlock template = null;

    public ARTemplate(InputStream template) {
        this.template = ARParser.parse(template);
    }

    public ARTemplate(Reader template) throws RuntimeException {
        if (template instanceof BufferedReader) {
            this.template = ARParser.parse((BufferedReader) template);
        } else if (template != null) {
            this.template = ARParser.parse(new BufferedReader(template));
        } else {
            throw new RuntimeException("Invalid template");
        }
    }

    /**
     * Render this report
     * @param output
     * @param data
     * @return
     */
    public boolean render(OutputStream output, ReportData data) {
        ARRuntime runtime = new ARRuntime(output);
        //reset runtime
        this.template.setRuntime(runtime);
        //get all reports keys
        String[] keys = data.getKeys();
        for (String key : keys) {
            template.getRuntime().set(key, data.getData(key));
        }
        //render
        this.template.execute();
        return true;
    }
}
