/*
 *  Copyright (C) 2010 michael
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
package dakside.hacc.modules.financial;

import dakside.hacc.modules.financial.currencyman.CurrencyManageView;
import dakside.hacc.modules.financial.exrateman.ExchangeRateManageView;
import java.awt.Component;
import org.dakside.duck.plugins.Function;
import org.dakside.duck.plugins.Unloadable;

/**
 * Curency management module
 * @author michael
 */
public class FinancialModule implements Unloadable {

    private static Component viewCurrencyMgmt = null;
    private static Component viewExchangeRateMgmt = null;

    public synchronized void unload() {
        viewCurrencyMgmt = null;
        viewExchangeRateMgmt = null;
    }

    @Function(Text = "Currency management", Description = "Create/edit currencies",
    IconPath = "icon_curmgmt.png", Category = "Financial", Location = Function.STARTPAGE)
    public Component getCurMgmtView() {
        if (viewCurrencyMgmt == null) {
            viewCurrencyMgmt = new CurrencyManageView();
        }
        return viewCurrencyMgmt;
    }

    @Function(Text = "ExchangeRate Management", Description = "Manage exchange rate",
    IconPath = "icon_exrt.png", Category = "Financial", Location = Function.STARTPAGE)
    public Component getExRateMgmtView() {
        if (viewExchangeRateMgmt == null) {
            viewExchangeRateMgmt = new ExchangeRateManageView();
        }
        return viewExchangeRateMgmt;
    }
}

