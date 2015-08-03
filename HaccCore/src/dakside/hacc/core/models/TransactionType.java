/*
 *  Copyright (C) 2009 michael
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

import org.dakside.utils.Validator;
import java.util.ArrayList;

/**
 * Type of a transaction <br/>
 * e.g.: expenses, borrow, loan, pay rental
 * @author michael
 */
public class TransactionType implements Comparable<TransactionType> {

    private String code;
    private String name;
    private ArrayList<TransactionAction> actions;

    //added by michael
    public TransactionType(String code, String name, TransactionAction[] actions) {
        this.code = code;
        this.name = name;

        //should we throw exception here?
        if (actions == null) {
            return;
        }
        for (TransactionAction action : actions) {
            getActions().add(action);
        }
    }

    public TransactionType() {
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the actions
     */
    public ArrayList<TransactionAction> getActions() {
        //@todo Test this
        if (actions == null) {
            actions = new ArrayList<TransactionAction>();
        }
        return actions;
    }

    public TransactionAction[] getActionArray() {
        return getActions().toArray(new TransactionAction[0]);
    }

    @Override
    public String toString() {
        return "[" + getCode() + "] - " + getName();
    }

    public void add(TransactionAction action) {
        getActions().add(action);
    }

    public int compareTo(TransactionType o) {
        if (o == null) {
            return -1;
        } else {
            return Validator.compareByString(this.toString(), o.toString());
        }
    }
}
