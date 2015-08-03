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
package dakside.hacc.modules.accounting.reports.test;

import dakside.hacc.reports.XARReport;
import java.io.InputStream;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class BalanceSheetReport extends XARReport {

    private BalanceSheetReport() {
        super("balance_sheet_template", new BalanceSheetDataRetriever());
    }
    private static BalanceSheetReport instance = null;

    public static synchronized BalanceSheetReport getInstance() {
        if (instance == null) {
            instance = new BalanceSheetReport();
        }
        return instance;
    }

    public static void main(String[] args) {
        BalanceSheetReport report = getInstance();
        InputStream stream = report.render(new Object[]{2009, 2010});
    }
}
