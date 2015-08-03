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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class XARParser {

    private static final Logger logger = Logger.getLogger(XARParser.class.getName());

    private XARParser() {
    }

    static StringBuffer parse(InputStream input) {
        StringBuffer buffer = readInput(input);
        return parse(buffer);
    }

    static StringBuffer parse(String raw) {
        return parse(new StringBuffer(raw));
    }

    static StringBuffer parse(StringBuffer raw) {
        StaticTextElement template = new StaticTextElement(raw);
        template.search();
        return template.getBuffer();
    }

    private static StringBuffer readInput(InputStream input) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(input));
        StringBuffer raw = new StringBuffer();
        char[] buffer = new char[4096];
        int readChars = -1;
        try {
            while ((readChars = reader.read(buffer)) > 0) {
                raw.append(buffer, 0, readChars);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read template content", ex);
        }
        return raw;
    }
}
