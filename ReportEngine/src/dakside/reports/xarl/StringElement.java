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

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class StringElement extends Element {

    char escChar = '\'';

    StringElement(StringBuffer input, char escChar) {
        super(input);
        this.escChar = escChar;
    }

    @Override
    public void search() {
        boolean open = false;
        while (isReadable()) {
            char c = getInput().charAt(index);
            buffer.append(c);
            index++;
            if (c == escChar) {
                if (open) {
                    return;
                } else {
                    open = true;
                }
            } else if (c == '\\') {
                //escape next char
                buffer.append(getInput().charAt(index + 1));
                index++;
            }
        }
        throw new RuntimeException("Not closed string");
    }
}
