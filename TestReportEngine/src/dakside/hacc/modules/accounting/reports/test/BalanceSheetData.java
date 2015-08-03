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

import dakside.hacc.core.models.Account;
import org.dakside.utils.FinancialHelper;
import org.dakside.utils.SystemHelper;

/**
 * Balance Sheet data for 1 year
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class BalanceSheetData {

    private Account assets = null;
    private Account liabilities = null;
    private Account equity = null;

    public BalanceSheetData() {
    }

    public BalanceSheetData(Account assets, Account liabilities, Account equity) {
        this.assets = assets;
        this.liabilities = liabilities;
        this.equity = equity;
    }

    @Override
    public String toString() {
        return "Assets: " + assets + " - " + assets.getBalance() + SystemHelper.getLineSeparator()
                + "Liabilities: " + liabilities + " - " + liabilities.getBalance() + SystemHelper.getLineSeparator()
                + "Equity: " + equity + " - " + equity.getBalance() + SystemHelper.getLineSeparator()
                + "Asset balance: " + getAssetBalanceString() + SystemHelper.getLineSeparator()
                + "Liabilities balance: " + getLiabilitiesBalanceString() + SystemHelper.getLineSeparator()
                + "Equity balance: " + getEquityBalanceString();
    }

    public String getAssetBalanceString() {
        return FinancialHelper.formatCurrency(assets.getBalance());
    }

    public String getLiabilitiesBalanceString() {
        return FinancialHelper.formatCurrency(liabilities.getBalance());
    }

    public String getEquityBalanceString() {
        return FinancialHelper.formatCurrency(equity.getBalance());
    }

    /**
     * @return the assets
     */
    public Account getAssets() {
        return assets;
    }

    /**
     * @param assets the assets to set
     */
    public void setAssets(Account assets) {
        this.assets = assets;
    }

    /**
     * @return the liabilities
     */
    public Account getLiabilities() {
        return liabilities;
    }

    /**
     * @param liabilities the liabilities to set
     */
    public void setLiabilities(Account liabilities) {
        this.liabilities = liabilities;
    }

    /**
     * @return the equity
     */
    public Account getEquity() {
        return equity;
    }

    /**
     * @param equity the equity to set
     */
    public void setEquity(Account equity) {
        this.equity = equity;
    }
}
