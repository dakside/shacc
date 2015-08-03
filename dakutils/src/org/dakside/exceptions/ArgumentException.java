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

package org.dakside.exceptions;

/**
 * Argument null exception
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ArgumentException extends Exception{

    public ArgumentException(){}

    public ArgumentException(String message){
        super(message);
    }

    public ArgumentException(Throwable throwable){
        super(throwable);
    }

    public ArgumentException(String string, Throwable throwable) {
        super(string, throwable);
    }
}
