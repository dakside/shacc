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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class QuickEchoElement extends Element {

    private static final Pattern tokens = Pattern.compile("(\"|')|(\\}\\})");
    private static final int STRING = 1;
    private static final int CLOSE = 2;

    public QuickEchoElement(StringBuffer input) {
        super(input);
    }

    @Override
    public void search() {
        Matcher m = tokens.matcher(getInput());
        while (isReadable()) {
            //search for token
            buffer.append("out.echo(");
            if (m.find(index)) {
                //flush
                buffer.append(getInput(), index, m.start());
                //create element
                if (m.group(STRING) != null) {
                    Element elem = new StringElement(getInput(), m.group(STRING).charAt(0));
                    elem.setIndex(m.start());
                    elem.search();
                    //update index
                    setIndex(elem.getIndex());
                    //add buffer
                    buffer.append(elem.getBuffer());
                } else if (m.group(CLOSE) != null) {
                    //close and return
                    setIndex(m.start() + m.group().length());
                    //convert to an echo call
                    buffer.append(");");
                    return;
                }
            } else {
                throw new RuntimeException("Not closed code block.");
            }
        }
    }
}
