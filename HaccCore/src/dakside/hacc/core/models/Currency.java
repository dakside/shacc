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
package dakside.hacc.core.models;

import dakside.hacc.core.helpers.exceptions.CurrencyNotFound;

/**
 * Currency model
 * @author michael
 */
public class Currency {

    private String isoCode;
     

    public Currency(String isoCode) throws CurrencyNotFound{
        setIsoCode(isoCode);        
    }

    /**
     * @return the isoCode
     */
    public String getIsoCode() {
        return isoCode;
    }

    /**
     * @param isoCode the isoCode to set
     */
    public void setIsoCode(String isoCode) throws CurrencyNotFound{
        try{
            this.isoCode = java.util.Currency.getInstance(isoCode).getCurrencyCode();
        }catch (IllegalArgumentException e){
            throw new CurrencyNotFound("Currency cannot be found");
        }
    }

    /**
     * @return the symbol
     */
    public String getSymbol() throws CurrencyNotFound{
        try{
            return java.util.Currency.getInstance(isoCode).getSymbol();
        }catch (IllegalArgumentException e){
            throw new CurrencyNotFound("Currency cannot be found");
        }
    }

   

    @Override
   public String toString(){
       return getIsoCode();
    }
}
