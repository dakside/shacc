/*
 *  Copyright (C) 2009 Le Tuan Anh
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
package dakside.hacc.modules.accounting.accounttree;

import java.util.Arrays;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class TestItem {

    public String name;
    public TestItem[] items;

    public TestItem() {
        items = new TestItem[0];
    }

    public TestItem(String name) {
        this.name = name;
        items = new TestItem[0];
    }

    public void add(TestItem item) {
        items = Arrays.copyOf(items, items.length + 1);
        items[items.length - 1] = item;
    }

    public String toString(){
        StringBuffer builder = new StringBuffer();
        builder.append(name).append("\r\n");
        for(TestItem item : items){
            builder.append(item).append("\r\n");
        }
        return builder.toString();
    }
}
