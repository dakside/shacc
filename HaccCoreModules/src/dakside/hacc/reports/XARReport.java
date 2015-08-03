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

import dakside.reports.DataRetriever;
import org.dakside.utils.ResourceCentre;
import java.io.InputStream;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class XARReport {

    protected final ResourceCentre rc = ResourceCentre.getInstance(this);
    protected String templateKey = null;
    protected DataRetriever retriever = null;

    public XARReport(String key, DataRetriever retriever) {
        templateKey = key;
        this.retriever = retriever;
    }

    public InputStream render(Object[] args) {
        return XARHelper.render(this, rc, templateKey, retriever, args);
    }
}
