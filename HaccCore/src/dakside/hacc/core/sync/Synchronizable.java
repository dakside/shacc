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

package dakside.hacc.core.sync;

import java.util.Date;

/**
 * Synchronizable interface<br/>
 * Any model implements this interface can be synchronized via SHACC sync interface
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public interface Synchronizable {
    public String getFingerprint();
    public Date getTimeStamp();
    /**
     * serialize all properties to string format
     * better encode with base64
     * @return
     */
    public String serialize();
    /**
     * Read properties from string.<br/>
     * The sync-engine of SHACC will create a 
     * 
     * @param properties
     * @return
     */
    public Object deserialize(String properties);
}
