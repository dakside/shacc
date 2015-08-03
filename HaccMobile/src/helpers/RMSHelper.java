/*
 *  Copyright (C) 2010 VangAnh
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

package helpers;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Vanganh
 */
public class RMSHelper {

    /**
     * open a record store
     * @param store
     * @return a record store
     */
    public static RecordStore openRecordStore(String store) {
        RecordStore rs;
        try {
            rs = RecordStore.openRecordStore(store, true);
            return rs;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * close a record store
     * @param rs
     */
    public static void closeRecordStore(RecordStore rs){
        try {
            rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }
}