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
package dakside.hacc.reports;

import dakside.hacc.modules.accounting.reports.BalanceSheetDataRetriever;
import dakside.reports.DataRetriever;
import dakside.reports.ReportGenerator;
import dakside.reports.ReportTemplate;
import dakside.reports.xarl.XARTemplate;
import org.dakside.utils.ResourceCentre;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class XARHelper {

    public static InputStream render(Object context, ResourceCentre rc, String templateKey, DataRetriever retriever, Object[] args) {
        ReportTemplate template = getTemplate(context, rc, templateKey);
        return render(template, retriever, args);
    }

    /**
     * Render with no arguments
     * @param context
     * @param rc
     * @param templateKey
     * @return
     */
    public static InputStream render(Object context, ResourceCentre rc, String templateKey, DataRetriever retriever) {
        ReportTemplate template = getTemplate(context, rc, templateKey);
        return render(template, retriever, new Object[0]);
    }

    public static InputStream render(ReportTemplate template, DataRetriever retriever, Object[] args) {
        //create report generator object
        ReportGenerator generator = new ReportGenerator(template, retriever);

        //generate report
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (generator.render(output, args)) {
            ByteArrayInputStream content = new ByteArrayInputStream(output.toByteArray());
            if (content != null) {
                return content;
            }
        }
        //by default cannot generate anything
        return null;
    }

    /**
     * get XAR template
     * @param context
     * @param name
     * @return
     */
    public static ReportTemplate getTemplate(Object context, String path) {
        InputStream input = context.getClass().getResourceAsStream(path);
        XARTemplate template = new XARTemplate(input);
        return template;
    }

    /**
     * get XAR template
     * @param context
     * @param name
     * @return
     */
    public static ReportTemplate getTemplate(Object context, ResourceCentre rc, String name) {
        String path = rc.getString(name);
        InputStream input = context.getClass().getResourceAsStream(path);
        XARTemplate template = new XARTemplate(input);
        return template;
    }
}
