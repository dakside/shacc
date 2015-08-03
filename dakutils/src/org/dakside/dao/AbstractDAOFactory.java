/*
 * Copyright (C) 2014 Le Tuan Anh <tuananh.ke@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dakside.dao;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public abstract class AbstractDAOFactory {

    public static final int DB4O_DATABASE = 0;
    public static final int MYSQL_DATABASE = 1;
    public static final int DERBY_DATABASE = 2;
    public static final int PGSQL_DATABASE = 4;
    public static final int SQLITE_DATABASE = 8;

    /**
     * Shutdown DAO (very important for embedded database) Can be ignored
     */
    public abstract void shutdown();

}
