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
import java.util.ArrayList;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class IfCodeBlock extends CodeBlock {

    private ArrayList<IfConditionBlock> conditions = new ArrayList<IfConditionBlock>();

    public IfCodeBlock() {
    }

    public IfCodeBlock(ARRuntime runtime) {
        super(runtime);
    }

    @Override
    public void setRuntime(ARRuntime runtime) {
        for (IfConditionBlock ifCon : conditions) {
            ifCon.getBlock().setRuntime(runtime);
        }
        super.setRuntime(runtime);
    }

    @Override
    public void execute() {
        for (IfConditionBlock block : conditions) {
            if (evaluate(block.getCondition())) {
                block.getBlock().execute();
                break;
            }
        }
        super.execute();
    }

    /**
     * Evaluate a condition (a variable not null and not empty)
     * @param condition
     * @return
     */
    private final boolean evaluate(String condition) {
        if (condition.trim().length() == 0) {
            //else block, always true
            return true;
        }
        //implement simple evaluate only (not null
        Object value = runtime.get(condition);
        return value != null && value.toString().length() > 0;
    }

    public void addCondition(IfConditionBlock block) {
        if (block != null) {
            conditions.add(block);
        }
    }
}
