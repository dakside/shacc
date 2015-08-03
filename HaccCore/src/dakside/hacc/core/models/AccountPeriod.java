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
package dakside.hacc.core.models;

import org.dakside.utils.Validator;
import org.dakside.exceptions.ArgumentException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Account Period<br/>
 * Status must be OPENING or CLOSED, otherwise an exception will be throw.
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountPeriod implements Cloneable {

    public static final int OPENING = 0;
    public static final int CLOSED = 1;
    public static final String CURRENT = "Current";
    public static final String PASSED = "Passed";
    public static final String FUTURE = "Future";
    private String title;
    private Date fromDate;
    private Date toDate;
    private int status = OPENING;

    public AccountPeriod() {
    }

    public AccountPeriod(String title) {
        setTitle(title);
    }

    public AccountPeriod(String title, Date fromDate, Date toDate, int status) throws ArgumentException {
        setTitle(title);
        setFromDate(fromDate);
        setToDate(toDate);
        setStatus(status);
    }

    public AccountPeriod(String title, Date fromDate, Date toDate) throws ArgumentException {
        setTitle(title);
        setFromDate(fromDate);
        setToDate(toDate);
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) throws ArgumentException {
        if (fromDate != null && this.toDate != null) {
            if (this.toDate.before(fromDate)) {
                throw new ArgumentException("Invalid From Date");
            }
        }
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) throws ArgumentException {
        if (this.fromDate != null && toDate != null) {
            if (toDate.before(this.fromDate)) {
                throw new ArgumentException("Invalid From Date");
            }
        }
        this.toDate = toDate;
    }

    /**
     * Account status (AccountPeriod.OPENING or AccountPeriod.CLOSED)
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * State of an account period (passed, current or future)
     * @return
     */
    public String getState() {
        if (isFuture()) {
            return AccountPeriod.FUTURE;
        }
        if (isPassed()) {
            return AccountPeriod.PASSED;
        }
        return AccountPeriod.CURRENT;
    }

    /**
     * Get status string (Opening or Closed)
     * @return
     */
    public String getStatusString() {
        //XXX localize
        if (status == OPENING) {
            return "Opening";
        } else if (status == CLOSED) {
            return "Closed";
        } else {
            return "Unknown";
        }
    }

    /**
     * Account period status (OPENING or CLOSED)
     * @param status the status to set
     */
    public void setStatus(int status) throws ArgumentException {
        if (status != CLOSED && status != OPENING) {
            throw new ArgumentException("Invalid status");
        }
        this.status = status;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        if (title == null) {
            return "";
        }
        return getTitle();
    }

    @Override
    public AccountPeriod clone() {
        AccountPeriod ap = new AccountPeriod();
        try {
            ap.setFromDate(fromDate);
            ap.setToDate(toDate);
            ap.setTitle(title);
            ap.setStatus(status);
        } catch (ArgumentException ex) {
            Logger.getLogger(AccountPeriod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ap;
    }

    public boolean equals(AccountPeriod other) {
        //TODO test this
        return other != null
                && Validator.equals(getTitle(), other.getTitle())
                && Validator.equals(getFromDate(), other.getFromDate())
                && Validator.equals(getToDate(), other.getToDate())
                && Validator.equals(getStatus(), other.getStatus());

    }

    public boolean isPassed() {
        return getToDate() != null && getToDate().before(new Date());
    }

    public boolean isFuture(){
        return getFromDate() != null && getFromDate().after(new Date());
    }
}
