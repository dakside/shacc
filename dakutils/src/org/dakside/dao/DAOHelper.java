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

import org.dakside.dao.DAOException;
import org.dakside.exceptions.ArgumentException;
import org.dakside.exceptions.ArgumentException;
import org.dakside.utils.Validator;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class DAOHelper extends Validator {
    /**
     * If arg is null, throw new DAOException
     * @param arg
     * @param message
     * @throws DAOException
     */
    public static void daoArgumentNotNull(Object arg, String message) throws DAOException{
        if(arg == null){
            throw new DAOException(message, new ArgumentException());
        }
    }

    public static void daoArgumentNotNull(Object arg) throws DAOException{
        daoArgumentNotNull(arg, "Argument cannot be null");
    }

}
