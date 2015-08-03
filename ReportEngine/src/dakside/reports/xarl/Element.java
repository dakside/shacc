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

package dakside.reports.xarl;

import org.dakside.utils.SystemHelper;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public abstract class Element {

    protected static final String CRLF = SystemHelper.getLineSeparator();
    protected StringBuffer buffer = new StringBuffer();
    private StringBuffer input = null;
    protected int index = 0;

    public Element(String data) {
        this.input = new StringBuffer(data);
    }

    public Element(StringBuffer input) {
        this.input = input;
    }

    /**
     * @return the buffer
     */
    public StringBuffer getBuffer() {
        return buffer;
    }

    /**
     * @return the input
     */
    public StringBuffer getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(StringBuffer input) {
        this.input = input;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isReadable() {
        return index >= 0 && index < input.length();
    }

    public abstract void search();
}
