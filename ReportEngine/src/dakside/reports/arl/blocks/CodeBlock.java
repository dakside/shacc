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
 * Generic code block
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
//freeze at current state, develop in the future
public class CodeBlock {

    protected ARRuntime runtime = null;

    public CodeBlock() {
    }

    public CodeBlock(ARRuntime runtime) {
        this.runtime = runtime;
    }
    private ArrayList<CodeBlock> blocks = new ArrayList<CodeBlock>();

    public void setRuntime(ARRuntime runtime) {
        this.runtime = runtime;
        for (CodeBlock block : blocks) {
            block.setRuntime(runtime);
        }
    }

    public void add(CodeBlock block) {
        if (block != null) {
            blocks.add(block);
        }
    }

    public void clear() {
        blocks.clear();
    }

    public CodeBlock[] getBlocks() {
        return blocks.toArray(new CodeBlock[0]);
    }

    /**
     * override this method to take effect
     */
    public void execute() {
        //by default, execute all inner block
        for (CodeBlock block : blocks) {
            block.execute();
        }
        //flush runtime output after finished
        runtime.flush();
    }

    public ARRuntime getRuntime() {
        return this.runtime;
    }
}
