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

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class StaticTextElement extends Element {

    public StaticTextElement(StringBuffer input) {
        super(input);
    }

    private void flush(int start, int end) {
        index = end;
        if (end - start < 1) {
            return; //empty text, no need to flush

        }
        CharSequence data = getInput().substring(start, end).replace("\\", "\\\\").replace("\"", "\\\"").
                replace("\r", "\\r").replace("\n", "\\n").
                replace("\b", "\\b").replace("\t", "\\t").replace("\f", "\\f");

        buffer.append("out.echo(\"" + data + "\");").append(CRLF);
    }

    @Override
    public void search() {
        Matcher m = tokens.matcher(getInput());
        while (isReadable()) {
            //search for token
            if (m.find(index)) {
                //flush
                flush(index, m.start());
                //create element
                Element elem = create(m.toMatchResult());
                elem.search();
                //update index
                setIndex(elem.getIndex());
                //add buffer
                buffer.append(elem.getBuffer());
            } else {
                //no more token, flush all
                flush(index, getInput().length());
                index = getInput().length();
            }
        }
    }
    private static final Pattern tokens = Pattern.compile("(\\{%)|(\\{\\{)");
    private static final int CODEBLOCK = 1;
    private static final int QUICKECHO = 2;

    private Element create(MatchResult toMatchResult) {
        if (toMatchResult.group(CODEBLOCK) != null) {
            Element elem = new CodeBlockElement(getInput());
            elem.setIndex(toMatchResult.start() + toMatchResult.group().length());
            return elem;

        } else if (toMatchResult.group(QUICKECHO) != null) {
            Element elem = new QuickEchoElement(getInput());
            elem.setIndex(toMatchResult.start() + toMatchResult.group().length());
            return elem;
        } else {
            throw new RuntimeException("Not supported elements");
        }
    }
}
