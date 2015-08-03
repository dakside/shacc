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

/**
 * To print static text
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class StaticCodeBlock extends CodeBlock {

    private String staticText = "";

    public StaticCodeBlock(ARRuntime runtime) {
        super(runtime);
    }

    public StaticCodeBlock(ARRuntime runtime, String staticText) {
        super(runtime);
        setStaticText(staticText);
    }

    public StaticCodeBlock(String staticText) {
        super(null);
        setStaticText(staticText);
    }

    /**
     * @return the staticText
     */
    public String getStaticText() {
        return staticText;
    }

    /**
     * @param staticText the staticText to set
     */
    public void setStaticText(String staticText) {
        this.staticText = staticText;
    }

    @Override
    public void execute() {
        runtime.write(staticText);
        super.execute();
    }
}
