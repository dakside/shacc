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
package dakside.reports.arl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dakside.utils.SystemHelper;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ReportRuntime {

    private HashMap<String, Object> runtime = new HashMap<String, Object>();
    private BufferedWriter output = null;

    public ReportRuntime(OutputStream stream) {
        this.output = new BufferedWriter(new OutputStreamWriter(stream));
    }

    protected BufferedWriter getOutput() {
        return output;
    }

    public void flush() {
        try {
            getOutput().flush();
        } catch (IOException ex) {
            Logger.getLogger(ReportRuntime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(String str) {
        try {
            getOutput().write(str);
        } catch (IOException ex) {
            Logger.getLogger(ReportRuntime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeln(String str) {
        write(str);
        write(SystemHelper.getLineSeparator());
    }

    public void echo(String str) {
        write(str);
    }

    public void print(String str) {
        write(str);
    }

    public void println(String str) {
        writeln(str);
    }

    protected HashMap<String, Object> getRuntime() {
        return this.runtime;
    }

    Object getRuntimeVariable(String variable) {
        return runtime.get(variable);
    }
}
