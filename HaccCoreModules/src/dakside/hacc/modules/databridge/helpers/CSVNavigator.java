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
package dakside.hacc.modules.databridge.helpers;

import dakside.csv.CSVLine;
import org.dakside.utils.DateTimeHelper;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Navigate through line<br/>
 * When set a non-empty line,
 * call next() the first time will get the first element.
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class CSVNavigator {

    private static final Logger logger = Logger.getLogger(CSVNavigator.class.getName());
    private CSVLine currentLine = null;
    private int col = 0;
    private DateFormat dateFormatter = null;
    private NumberFormat integerFormatter = null;
    private NumberFormat doubleFormatter = null;

    /**
     * Create a navigator with all default parser<br/>
     *  dateFormatter = DateFormat.getInstance();<br/>
     * this.dateFormatter.setLenient(false);<br/>
     *  integerFormatter = NumberFormat.getIntegerInstance();<br/>
     *  integerFormatter.setRoundingMode(RoundingMode.UNNECESSARY);<br/>
     *  doubleFormatter = NumberFormat.getInstance();
     */
    public CSVNavigator() {
        this.dateFormatter = DateFormat.getInstance();
        this.dateFormatter.setLenient(false);

        this.integerFormatter = NumberFormat.getIntegerInstance();
        this.integerFormatter.setRoundingMode(RoundingMode.UNNECESSARY);

        this.doubleFormatter = NumberFormat.getInstance();
    }

    public CSVNavigator(DateFormat dateFormatter, NumberFormat integerFormatter, NumberFormat doubleFormatter) {
        this.dateFormatter = dateFormatter;
        this.integerFormatter = integerFormatter;
        this.doubleFormatter = doubleFormatter;
    }

    /**
     * Set line to navigate
     * @param line
     */
    public void setLine(CSVLine line) {
        currentLine = line;
        col = -1;
    }

    public boolean hasNext() {
        return currentLine != null && col + 1 >= 0 && col + 1 < currentLine.size();
    }

    public Object next() {
        if (hasNext()) {
            col++;
            return currentLine.getElementAt(col);
        } else {
            return null;
        }
    }

    /**
     * Read next value as string
     * @return
     */
    public String nextString() {
        Object obj = next();
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return obj != null ? obj.toString() : null;
        }
    }

    /**
     * Read next value as double
     * @return
     * @throws NumberFormatException
     * @throws NullPointerException
     */
    public Double nextDouble() {
        Object obj = next();
        if (obj instanceof Double) {
            return (Double) obj;
        } else {
            try {
                if (obj != null) {
                    return getDoubleFormatter().parse(obj.toString()).doubleValue();
                }
            } catch (ParseException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return null;
    }

    public Integer nextInteger() {
        Object obj = next();
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else {
            try {
                if (obj != null) {
                    return getIntegerFormatter().parse(obj.toString()).intValue();
                }
            } catch (ParseException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return null;
    }

    public Byte nextByte() {
        Object obj = next();
        if (obj instanceof Byte) {
            return (Byte) obj;
        } else {
            try {
                if (obj != null) {
                    return getIntegerFormatter().parse(obj.toString()).byteValue();
                }
            } catch (ParseException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return null;
    }
    private String[] trueValues = getTrueValues();

    private String[] getTrueValues() {
        String[] data = {"true", "yes", "y", "correct"};
        Arrays.sort(data);
        return data;
    }
    private String[] falseValues = getFalseValues();

    private String[] getFalseValues() {
        String[] data = {"false", "no", "n", "incorrect"};
        Arrays.sort(data);
        return data;
    }

    /**
     * next value as boolean
     * @return data will be treated ignore case
     * true: true/yes/correct/y
     * false: false/no/incorrect/n
     * null: others
     */
    public Boolean nextBoolean() {
        Object obj = next();
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else {
            if (obj != null) {
                String val = obj.toString();
                if (Arrays.binarySearch(trueValues, val) >= 0) {
                    return Boolean.TRUE;
                } else if (Arrays.binarySearch(falseValues, val) >= 0) {
                    return Boolean.FALSE;
                }
            }
        }
        return null;
    }

    public Short nextShort() {
        Object obj = next();
        if (obj instanceof Short) {
            return (Short) obj;
        } else {
            try {
                if (obj != null) {
                    return getIntegerFormatter().parse(obj.toString()).shortValue();
                }
            } catch (ParseException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return null;
    }

    public Long nextLong() {
        Object obj = next();
        if (obj instanceof Long) {
            return (Long) obj;
        } else {
            try {
                if (obj != null) {
                    return getIntegerFormatter().parse(obj.toString()).longValue();
                }
            } catch (ParseException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return null;
    }

    public Float nextFloat() {
        Object obj = next();
        if (obj instanceof Float) {
            return (Float) obj;
        } else {
            try {
                if (obj != null) {
                    return getDoubleFormatter().parse(obj.toString()).floatValue();
                }
            } catch (ParseException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return null;
    }

    /**
     * Read next value as date
     * @return null if cannot parse
     * @throws NumberFormatException
     * @throws NullPointerException
     */
    public Date nextDate() throws NumberFormatException, NullPointerException {
        Object obj = next();
        if (obj instanceof Date) {
            return (Date) obj;
        } else if (obj != null) {
            try {
                if (obj != null) {
                    return getDateFormatter().parse(obj.toString());
                }
            } catch (ParseException ex) {
                //logger.log(Level.WARNING, null, ex);
                //try to use default format
                return DateTimeHelper.toDate(obj.toString(), "MM/dd/yyyy");
            }
        }
        return null;
    }

    /**
     * @return the dateFormatter
     */
    public DateFormat getDateFormatter() {
        return dateFormatter;
    }

    /**
     * @param dateFormatter the dateFormatter to set
     */
    public void setDateFormatter(DateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    /**
     * @return the integerFormatter
     */
    public NumberFormat getIntegerFormatter() {
        return integerFormatter;
    }

    /**
     * @param integerFormatter the integerFormatter to set
     */
    public void setIntegerFormatter(NumberFormat integerFormatter) {
        this.integerFormatter = integerFormatter;
    }

    /**
     * @return the doubleFormatter
     */
    public NumberFormat getDoubleFormatter() {
        return doubleFormatter;
    }

    /**
     * @param doubleFormatter the doubleFormatter to set
     */
    public void setDoubleFormatter(NumberFormat doubleFormatter) {
        this.doubleFormatter = doubleFormatter;
    }
}
