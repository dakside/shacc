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
package dakside.hacc.modules.accounting.reports;

import dakside.hacc.reports.XARReport;

/**
 * Income Statement Report (renderer)
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class IncomeStatementReport extends XARReport {

    private IncomeStatementReport() {
        super("income_statement_template", new IncomeStatementDataRetriever());
    }
    private static IncomeStatementReport instance = null;

    public static synchronized IncomeStatementReport getInstance() {
        if (instance == null) {
            instance = new IncomeStatementReport();
        }
        return instance;
    }
}
