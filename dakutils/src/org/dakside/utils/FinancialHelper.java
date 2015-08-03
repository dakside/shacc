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
package org.dakside.utils;

import java.text.DecimalFormat;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class FinancialHelper {

    private static final String currency_format = "currency_format";

    /**
     * Format currency (without currency code)
     * @param amount
     * @return
     */
    public static String formatCurrency(double amount) {
        //@todo hardcode for localization?
        ResourceCentre rc = ResourceCentre.getInstance(new FinancialHelper());
        String format = rc.getString(currency_format);
        if (format.length() > 0 && !currency_format.equals(format));
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(amount);
    }
}
