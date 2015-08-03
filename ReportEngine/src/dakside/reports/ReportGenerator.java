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
package dakside.reports;

import java.io.OutputStream;

/**
 * Report generator template class
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public final class ReportGenerator {

    private ReportTemplate template;
    private ReportData data;
    private DataRetriever retriever;

    public ReportGenerator(ReportTemplate template, DataRetriever retriever) {
        this.template = template;
        this.retriever = retriever;
    }

    protected final ReportTemplate getTemplate() {
        return this.template;
    }

    protected final void setTemplate(ReportTemplate template) {
        this.template = template;
    }

    public final boolean render(OutputStream output, Object[] args) {
        try {
            this.data = retriever.retrieveData(args);
            if (data == null) {
                return false;
            }
            this.template.render(output, data);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
