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

package dakside.reports.arl.blocks;

import dakside.reports.arl.ARRuntime;
import java.util.Collection;
import java.util.Iterator;

/**
 * For code block
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ForCodeBlock extends CodeBlock {

    private String tempVariable;
    private String arrayName;

    public ForCodeBlock() {
    }

    public ForCodeBlock(String tempVariable, String arrayName) {
        setTempVariable(tempVariable);
        setArrayName(arrayName);
    }

    public ForCodeBlock(ARRuntime runtime) {
        super(runtime);
    }

    @Override
    public void execute() {
        Object arr = runtime.get(arrayName);
        //enumeration
        if (arr instanceof Object[]) {
            Object[] objArray = (Object[]) arr;
            for (Object obj : objArray) {
                runtime.set(tempVariable, obj);
                super.execute();
            }
        } else if (arr instanceof Collection) {
            Iterator iter = ((Collection) arr).iterator();
            while (iter.hasNext()) {
                runtime.set(tempVariable, iter.next());
                super.execute();
            }
        }
    }

    /**
     * @return the tempVariable
     */
    public String getTempVariable() {
        return tempVariable;
    }

    /**
     * @param tempVariable the tempVariable to set
     */
    public void setTempVariable(String tempVariable) {
        this.tempVariable = tempVariable;
    }

    /**
     * @return the arrayName
     */
    public String getArrayName() {
        return arrayName;
    }

    /**
     * @param arrayName the arrayName to set
     */
    public void setArrayName(String arrayName) {
        this.arrayName = arrayName;
    }
}
