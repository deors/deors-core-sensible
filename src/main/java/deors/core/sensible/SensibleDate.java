package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.util.Calendar;
import java.util.Date;

import deors.core.commons.StringToolkit;

/**
 * Definition for a date data type.<br>
 *
 * This class manages dates with customizable format.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleDate
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 8338353786562597489L;

    /**
     * The date day.
     *
     * @see SensibleDate#getDay()
     * @see SensibleDate#setDay(int)
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     */
    private int day;

    /**
     * The date month.
     *
     * @see SensibleDate#getMonth()
     * @see SensibleDate#setMonth(int)
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     */
    private int month;

    /**
     * The date year.
     *
     * @see SensibleDate#getYear()
     * @see SensibleDate#setYear(int)
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     */
    private int year;

    /**
     * The date format.
     *
     * @see SensibleDate#getDateFormat()
     * @see SensibleDate#setDateFormat(String)
     * @see SensibleDate#DEFAULT_DATE_FORMAT
     * @see SensibleDate#DMY_DATE_FORMAT
     * @see SensibleDate#MDY_DATE_FORMAT
     * @see SensibleDate#YMD_DATE_FORMAT
     */
    private String dateFormat = DEFAULT_DATE_FORMAT;

    /**
     * The date separator.
     *
     * @see SensibleDate#getDateSeparator()
     * @see SensibleDate#setDateSeparator(char)
     * @see SensibleDate#DEFAULT_DATE_SEPARATOR
     */
    private char dateSeparator = DEFAULT_DATE_SEPARATOR;

    /**
     * Whether the date is complete.
     *
     * @see SensibleDate#isComplete()
     * @see SensibleDate#setComplete(boolean)
     */
    private boolean complete;

    /**
     * Whether the proposed date could be a valid date.
     */
    private boolean proposedValid = true;

    /**
     * The number of items in the proposed date.
     */
    private int proposedItems;

    /**
     * The proposed day.
     */
    private int proposedDay;

    /**
     * The proposed month.
     */
    private int proposedMonth;

    /**
     * The proposed year.
     */
    private int proposedYear;

    /**
     * Constant that specifies a day/month/year date format.
     */
    public static final String DMY_DATE_FORMAT = "DMY"; //$NON-NLS-1$

    /**
     * Constant that specifies a month/day/year date format.
     */
    public static final String MDY_DATE_FORMAT = "MDY"; //$NON-NLS-1$

    /**
     * Constant that specifies a year/month/day date format.
     */
    public static final String YMD_DATE_FORMAT = "YMD"; //$NON-NLS-1$

    /**
     * The number of days for each month.
     */
    private static final int[] DAYS_PER_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * The zero character used to pad date/time tokens.
     */
    private static final char ZERO_CHAR = '0';

    /**
     * The "complete" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_COMPLETE = "complete"; //$NON-NLS-1$

    /**
     * The "dateFormat" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATE_FORMAT = "dateFormat"; //$NON-NLS-1$

    /**
     * The "dateSeparator" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATE_SEPARATOR = "dateSeparator"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * The default date format. Configurable in the properties file using the key
     * <code>format.defaultDateFormat</code>. Default value is <code>DMY_DATE_FORMAT</code>
     * The valid values are <code>DMY_DATE_FORMAT</code>, <code>MDY_DATE_FORMAT</code>
     * and <code>YMD_DATE_FORMAT</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     * @see SensibleDate#DMY_DATE_FORMAT
     * @see SensibleDate#MDY_DATE_FORMAT
     * @see SensibleDate#YMD_DATE_FORMAT
     */
    private static final String DEFAULT_DATE_FORMAT =
        getConfigurationProperty("format.defaultDateFormat", DMY_DATE_FORMAT, //$NON-NLS-1$
            new String[] {DMY_DATE_FORMAT, MDY_DATE_FORMAT, YMD_DATE_FORMAT});

    /**
     * The default date separator character. Configurable in the properties file using the key
     * <code>format.defaultDateSeparator</code>. Default value is <code>/</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, char)
     */
    private static final char DEFAULT_DATE_SEPARATOR =
        getConfigurationProperty("format.defaultDateSeparator", '/'); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * Default constructor.
     */
    public SensibleDate() {

        super();

        setDate(SensibleContext.BLANK);
    }

    /**
     * Constructor that sets the date using the given integer values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid date.
     *
     * @param year the date year
     * @param month the date month
     * @param day the date day
     */
    public SensibleDate(int year, int month, int day) {

        this();

        setDate(year, month, day);
    }

    /**
     * Constructor that sets the date using the given <code>SensibleDate</code> object value.
     *
     * @param date the date value
     */
    public SensibleDate(SensibleDate date) {

        this();

        setDate(date);
    }

    /**
     * Constructor that sets the date using the given <code>java.util.Calendar</code> object
     * value.
     *
     * @param date the calendar object used to set the date value
     */
    public SensibleDate(Calendar date) {

        this();

        setDate(date);
    }

    /**
     * Constructor that sets the date using the given <code>java.util.Date</code> object
     * value.
     *
     * @param date the date object used to set the date value
     */
    public SensibleDate(Date date) {

        this();

        setDate(date);
    }

    /**
     * Constructor that sets the date using the given <code>String</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid date.
     *
     * @param source the string object used to set the date value
     */
    public SensibleDate(String source) {

        this();

        setDate(source);
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in an insertion action fired in a <code>SensibleTextField</code> object.
     * The method checks whether the proposed value matches the data type format. For the date data
     * type an insert action is allowed if the final string contains only numbers and the date
     * separator and that the final string matches a valid date.
     *
     * @param offset the insert starting point
     * @param s the string to be inserted
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     * @see SensibleDate#checkDate(String)
     */
    protected boolean allowInsert(int offset, String s) {

        for (int i = 0, n = s.length(); i < n; i++) {
            if ((s.charAt(i) < '0' || s.charAt(i) > '9') && s.charAt(i) != dateSeparator) {
                return false;
            }
        }

        if (offset == 0) {
            return checkDate(s + value);
        }

        return checkDate(value.substring(0, offset) + s + value.substring(offset));
    }

    /**
     * No implementation provided for this method.
     *
     * @param offset ignored
     * @param s ignored
     * @param textField ignored
     * @param document ignored
     *
     * @return <code>false</code>
     *
     * @see SensibleDate#allowInsert(int, String)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in a remove action fired in a <code>SensibleTextField</code> object. The
     * method checks whether the proposed value matches the data type format. For the date data type
     * a remove action is allowed if the final string matches a valid date.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     * @see SensibleDate#checkDate(String)
     */
    protected boolean allowRemove(int offset, int length) {

        return checkDate(value.substring(0, offset) + value.substring(offset + length));
    }

    /**
     * No implementation provided for this method.
     *
     * @param offset ignored
     * @param length ignored
     * @param textField ignored
     * @param document ignored
     *
     * @return <code>false</code>
     *
     * @see SensibleDate#allowRemove(int, int)
     */
    protected boolean allowRemove(int offset, int length, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Used internally to change the data value. This method uses the given string to change the
     * data value. If the new value is valid, fires the property change events, one for the
     * <code>value</code> property and another for the property in which the particular data value
     * is stored (if this property exists). If necessary, the implementation has to invoke the
     * <code>setValid(boolean)</code> method to change the data value valid state.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the string does
     * not represent a valid date and the change value action is invoked from a setter method.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleDate#setComplete(boolean)
     * @see SensibleDate#checkDate(String)
     */
    protected void changeValue(String newValue) {

        final int itemCount = 3;

        String oldValue = value;

        if (!checkDate(newValue)
            && valueChangingInSet && newValue.length() != 0) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT")); //$NON-NLS-1$
        }

        this.year = proposedYear;
        this.month = proposedMonth;
        this.day = proposedDay;
        this.value = newValue;

        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(proposedValid);
        setComplete(proposedItems == itemCount && proposedValid);

        emptyProposed();
    }

    /**
     * Returns the date value as a <code>java.util.Calendar</code> object.
     *
     * @return the date as a <code>Calendar</code> object
     */
    public Calendar calendarValue() {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month - 1);
        now.set(Calendar.DAY_OF_MONTH, day);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        return now;
    }

    /**
     * Checks that the given integer values represent a valid date.
     *
     * @param checkYear the date year to be checked
     * @param checkMonth the date month to be checked
     * @param checkDay the date day to be checked
     *
     * @return whether the given integer values represent a valid date
     */
    private boolean checkDate(int checkYear, int checkMonth, int checkDay) {

        final int lastMonth = 12;
        final int lastDay = 31;
        final int february = 2;

        if (checkYear <= 0 || checkMonth <= 0 || checkDay <= 0) {
            return false;
        }

        if (checkMonth > lastMonth || checkDay > lastDay) {
            return false;
        }

        if (checkMonth != february && checkDay > DAYS_PER_MONTH[checkMonth - 1]) {
            return false;
        }

        if (checkMonth == february) {
            if (!isLeapYear(checkYear) && checkDay > DAYS_PER_MONTH[checkMonth - 1]) {
                return false;
            }

            if (isLeapYear(checkYear) && checkDay > DAYS_PER_MONTH[checkMonth - 1] + 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks that the given string represents a valid date. When this method is invoked due to a
     * text field action, the string can represent an incomplete date. When this method is invoked
     * due to a setter method action, the string can be only a completed date. The given string
     * items are saved in the proposed-date properties.
     *
     * @param s the string to be checked
     *
     * @return whether the given string represents a valid date
     */
    private boolean checkDate(String s) {

        final int dayItemSize = 2;
        final int monthItemSize = 2;
        final int yearItemSize = 4;
        final int lastMonth = 12;
        final int lastDay = 31;
        final int february = 2;
        final int itemCount = 3;

        if (s.length() == 0) {
            emptyProposed();
            return true;
        }

        int parsedItems = 1;
        String parsedItem1 = SensibleContext.BLANK;
        String parsedItem2 = SensibleContext.BLANK;
        String parsedItem3 = SensibleContext.BLANK;

        // parses the string
        int sepPos1 = s.indexOf(dateSeparator);
        if (sepPos1 == -1) {
            parsedItem1 = s;
        } else {
            parsedItem1 = s.substring(0, sepPos1);

            int sepPos2 = s.indexOf(dateSeparator, sepPos1 + 1);
            if (sepPos2 == -1) {
                parsedItems++;
                parsedItem2 = s.substring(sepPos1 + 1);
            } else {
                parsedItems++;
                parsedItem2 = s.substring(sepPos1 + 1, sepPos2);

                if (s.indexOf(dateSeparator, sepPos2 + 1) != -1) {
                    return false;
                }

                if (sepPos2 != s.length() - 1) {
                    parsedItems++;
                    parsedItem3 = s.substring(sepPos2 + 1);
                }
            }
        }

        int parsedYear = 0;
        int parsedMonth = 0;
        int parsedDay = 0;
        boolean parsedValid = true;

        // checks the string format
        if (dateFormat.equals(DMY_DATE_FORMAT)) {
            if (parsedItem1.length() > dayItemSize
                || parsedItem2.length() > monthItemSize
                || parsedItem3.length() > yearItemSize) {
                return false;
            }

            try {
                if (parsedItem1.length() != 0) {
                    parsedDay = Integer.parseInt(parsedItem1);
                }

                if (parsedItem2.length() != 0) {
                    parsedMonth = Integer.parseInt(parsedItem2);
                }

                if (parsedItem3.length() != 0) {
                    parsedYear = Integer.parseInt(parsedItem3);
                }
            } catch (NumberFormatException nfe) {
                return false;
            }
        } else if (dateFormat.equals(MDY_DATE_FORMAT)) {
            if (parsedItem1.length() > monthItemSize
                || parsedItem2.length() > dayItemSize
                || parsedItem3.length() > yearItemSize) {
                return false;
            }

            try {
                if (parsedItem1.length() != 0) {
                    parsedMonth = Integer.parseInt(parsedItem1);
                }

                if (parsedItem2.length() != 0) {
                    parsedDay = Integer.parseInt(parsedItem2);
                }

                if (parsedItem3.length() != 0) {
                    parsedYear = Integer.parseInt(parsedItem3);
                }
            } catch (NumberFormatException nfe) {
                return false;
            }
        } else if (dateFormat.equals(YMD_DATE_FORMAT)) {
            if (parsedItem1.length() > yearItemSize
                || parsedItem2.length() > monthItemSize
                || parsedItem3.length() > dayItemSize) {
                return false;
            }

            try {
                if (parsedItem1.length() != 0) {
                    parsedYear = Integer.parseInt(parsedItem1);
                }

                if (parsedItem2.length() != 0) {
                    parsedMonth = Integer.parseInt(parsedItem2);
                }

                if (parsedItem3.length() != 0) {
                    parsedDay = Integer.parseInt(parsedItem3);
                }
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        // checks the integer values
        if (parsedDay == 0 || parsedMonth == 0) {
            parsedValid = false;
        }

        if (parsedMonth > lastMonth || parsedDay > lastDay) {
            // parsedValid = false;
            return false;
        }

        if (parsedMonth != 0 && parsedMonth <= lastMonth) {
            if (parsedMonth != february && parsedDay > DAYS_PER_MONTH[parsedMonth - 1]) {
                // parsedValid = false;
                return false;
            }

            if (parsedMonth == february) {
                if (parsedYear != 0 && !isLeapYear(parsedYear)
                    && parsedDay > DAYS_PER_MONTH[parsedMonth - 1]) {
                    parsedValid = false;
                    // return false;
                } else if ((parsedYear == 0 || isLeapYear(parsedYear))
                    && parsedDay > DAYS_PER_MONTH[parsedMonth - 1] + 1) {
                    // parsedValid = false;
                    return false;
                }
            }
        }

        // saves the proposed date
        proposedValid = parsedItems == itemCount && parsedValid;
        proposedItems = parsedItems;
        proposedYear = parsedYear;
        proposedMonth = parsedMonth;
        proposedDay = parsedDay;

        if (valueChangingInSet) {
            return proposedValid;
        }

        return true;
    }

    /**
     * Clears the data value. Actually it changes the year, month and day to zero.
     */
    public void clear() {

        setValue(SensibleContext.BLANK);
    }

    /**
     * Returns the date value as a <code>java.util.Date</code> object.
     *
     * @return the date as a <code>Date</code> object
     */
    public Date dateValue() {

        Calendar now = calendarValue();

        return now.getTime();
    }

    /**
     * Empty the proposed date properties.
     */
    private void emptyProposed() {

        proposedValid = true;
        proposedItems = 0;
        proposedYear = 0;
        proposedMonth = 0;
        proposedDay = 0;
    }

    /**
     * Compares this <code>SensibleDate</code> object with the given integer values and returns
     * whether both object and values represent the same date.
     *
     * @param targetYear the target year value
     * @param targetMonth the target month value
     * @param targetDay the target day value
     *
     * @return whether this and the given values represent the same date
     */
    public boolean equals(int targetYear, int targetMonth, int targetDay) {

        return year == targetYear
               && month == targetMonth
               && day == targetDay;
    }

    /**
     * Compares this <code>SensibleDate</code> object with the given
     * <code>java.util.Calendar</code> object and returns whether both objects represent the same
     * date.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same date
     */
    public boolean equals(Calendar target) {

        if (target == null) {
            return false;
        }

        return year == target.get(target.YEAR)
               && month == target.get(target.MONTH)
               && day == target.get(target.DAY_OF_MONTH);
    }

    /**
     * Compares this <code>SensibleDate</code> object with the given object and returns whether
     * both objects represent the same date.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same date
     *
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object target) {

        if (this == target) {
            return true;
        }

        if (target == null) {
            return false;
        }

        if (!(target instanceof SensibleDate)) {
            return false;
        }

        SensibleDate sensibleTarget = (SensibleDate) target;

        return this.year == sensibleTarget.year
            && this.month == sensibleTarget.month
            && this.day == sensibleTarget.day;
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        super.firePropertyChangeEvents();

        firePropertyChange(JAVA_BEAN_PROPERTY_COMPLETE, null, complete);
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_FORMAT, null, dateFormat);
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_SEPARATOR, null, dateSeparator);
    }

    /**
     * Formats a date using the current format and the given integer values.
     *
     * @return the date formatted
     *
     * @param formatYear the date year
     * @param formatMonth the date month
     * @param formatDay the date day
     */
    private String formatDate(int formatYear, int formatMonth, int formatDay) {

        StringBuffer sb = new StringBuffer();

        if (dateFormat.equals(DMY_DATE_FORMAT)) {
            sb.append(formatDay);
            sb.append(dateSeparator);
            sb.append(formatMonth);
            sb.append(dateSeparator);
            sb.append(formatYear);
        } else if (dateFormat.equals(MDY_DATE_FORMAT)) {
            sb.append(formatMonth);
            sb.append(dateSeparator);
            sb.append(formatDay);
            sb.append(dateSeparator);
            sb.append(formatYear);
        } else if (dateFormat.equals(YMD_DATE_FORMAT)) {
            sb.append(formatYear);
            sb.append(dateSeparator);
            sb.append(formatMonth);
            sb.append(dateSeparator);
            sb.append(formatDay);
        }

        return sb.toString();
    }

    /**
     * Returns the date value as an array of integer values.
     *
     * @return the year, month and day as an array of integer values
     *
     * @see SensibleDate#setDate(int, int, int)
     * @see SensibleDate#year
     * @see SensibleDate#getYear()
     * @see SensibleDate#setYear(int)
     * @see SensibleDate#month
     * @see SensibleDate#getMonth()
     * @see SensibleDate#setMonth(int)
     * @see SensibleDate#day
     * @see SensibleDate#getDay()
     * @see SensibleDate#setDay(int)
     */
    public int[] getDate() {

        return new int[] {year, month, day};
    }

    /**
     * Returns the <code>dateFormat</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDate#dateFormat
     * @see SensibleDate#setDateFormat(String)
     * @see SensibleDate#DMY_DATE_FORMAT
     * @see SensibleDate#MDY_DATE_FORMAT
     * @see SensibleDate#YMD_DATE_FORMAT
     */
    public String getDateFormat() {

        return dateFormat;
    }

    /**
     * Returns the <code>dateSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDate#dateSeparator
     * @see SensibleDate#setDateSeparator(char)
     */
    public char getDateSeparator() {

        return dateSeparator;
    }

    /**
     * Returns the <code>day</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDate#day
     * @see SensibleDate#setDay(int)
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     */
    public int getDay() {

        return day;
    }

    /**
     * Returns the <code>month</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDate#month
     * @see SensibleDate#setMonth(int)
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     */
    public int getMonth() {

        return month;
    }

    /**
     * Returns the <code>year</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDate#year
     * @see SensibleDate#setYear(int)
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     */
    public int getYear() {

        return year;
    }

    /**
     * Returns a hash code value for the object.
     *
     * The hash code is calculated multipliying all the integer fields.
     *
     * @return a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return year * month * day;
    }

    /**
     * Returns <code>true</code> if the data value is clear. Actually it checks that the year,
     * month and day are all equal to zero.
     *
     * @return whether the data value is clear
     */
    public boolean isClear() {

        return year == 0 && month == 0 && day == 0;
    }

    /**
     * Returns the <code>complete</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDate#complete
     * @see SensibleDate#setComplete(boolean)
     */
    public boolean isComplete() {

        return complete;
    }

    /**
     * Returns whether the given year is leap.
     *
     * @param year the year
     *
     * @return whether the given year is leap
     */
    public static boolean isLeapYear(int year) {

        final int gregorianLimit = 1582;
        final int leapYearCycleSize = 4;
        final int yearsPerCentury = 100;

        boolean isLeap;

        if (year < gregorianLimit) {
            isLeap = (year % leapYearCycleSize == 0) && (year % yearsPerCentury != 0);
        } else {
            isLeap = (year % leapYearCycleSize == 0)
                     && ((year % yearsPerCentury != 0)
                         || (year % (yearsPerCentury * leapYearCycleSize) == 0));
        }

        return isLeap;
    }

    /**
     * Changes the <code>complete</code> property value and fires the property change event.
     *
     * @param newValue the new value
     *
     * @see SensibleDate#complete
     * @see SensibleDate#isComplete()
     */
    public void setComplete(boolean newValue) {

        boolean oldValue = complete;
        complete = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_COMPLETE, oldValue, newValue);
    }

    /**
     * Changes the date value using the given integer values.
     *
     * @param newYear the date new year
     * @param newMonth the date new month
     * @param newDay the date new day
     *
     * @see SensibleDate#getDate()
     * @see SensibleDate#setValue(int, int, int)
     * @see SensibleDate#year
     * @see SensibleDate#getYear()
     * @see SensibleDate#setYear(int)
     * @see SensibleDate#month
     * @see SensibleDate#getMonth()
     * @see SensibleDate#setMonth(int)
     * @see SensibleDate#day
     * @see SensibleDate#getDay()
     * @see SensibleDate#setDay(int)
     */
    public void setDate(int newYear, int newMonth, int newDay) {

        setValue(newYear, newMonth, newDay);
    }

    /**
     * Changes the date value using the given <code>SensibleDate</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleDate#setValue(int, int, int)
     */
    public void setDate(SensibleDate newValue) {

        setValue(newValue.year, newValue.month, newValue.day);
    }

    /**
     * Changes the date value using the given <code>java.util.Calendar</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleDate#setValue(int, int, int)
     */
    public void setDate(Calendar newValue) {

        setValue(
            newValue.get(Calendar.YEAR),
            newValue.get(Calendar.MONTH) + 1,
            newValue.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Changes the date value using the given <code>java.util.Date</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleDate#setDate(java.util.Calendar)
     */
    public void setDate(Date newValue) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newValue);
        setDate(calendar);
    }

    /**
     * Changes the date value using the given <code>String</code> object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid date.
     *
     * @param newValue the string representation of the new value
     *
     * @see SensibleDate#setValue(String)
     */
    public void setDate(String newValue) {

        setValue(newValue);
    }

    /**
     * Changes the <code>dateFormat</code> property value and fires the property change event. The
     * valid values are <code>DMY_DATE_FORMAT</code>, <code>MDY_DATE_FORMAT</code> and
     * <code>YMD_DATE_FORMAT</code>.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new date
     * format is not valid.
     *
     * @param newValue the property new value
     *
     * @see SensibleDate#dateFormat
     * @see SensibleDate#getDateFormat()
     * @see SensibleDate#DEFAULT_DATE_FORMAT
     * @see SensibleDate#DMY_DATE_FORMAT
     * @see SensibleDate#MDY_DATE_FORMAT
     * @see SensibleDate#YMD_DATE_FORMAT
     */
    public void setDateFormat(String newValue) {

        if (!newValue.equals(DMY_DATE_FORMAT) && !newValue.equals(MDY_DATE_FORMAT)
            && !newValue.equals(YMD_DATE_FORMAT)) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT")); //$NON-NLS-1$
        }

        String oldValue = newValue;
        dateFormat = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_FORMAT, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day);
        }
    }

    /**
     * Changes the <code>dateSeparator</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDate#dateSeparator
     * @see SensibleDate#getDateSeparator()
     * @see SensibleDate#DEFAULT_DATE_SEPARATOR
     */
    public void setDateSeparator(char newValue) {

        char oldValue = dateSeparator;
        dateSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_SEPARATOR, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day);
        }
    }

    /**
     * Changes the <code>day</code> property value.
     *
     * @param newDay the date new day
     *
     * @see SensibleDate#day
     * @see SensibleDate#getDay()
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     * @see SensibleDate#setValue(int, int, int)
     */
    public void setDay(int newDay) {

        setValue(year, month, newDay);
    }

    /**
     * Changes the <code>month</code> property value.
     *
     * @param newMonth the date new month
     *
     * @see SensibleDate#month
     * @see SensibleDate#getMonth()
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     * @see SensibleDate#setValue(int, int, int)
     */
    public void setMonth(int newMonth) {

        setValue(year, newMonth, day);
    }

    /**
     * Changes the <code>year</code> property value.
     *
     * @param newYear the date new year
     *
     * @see SensibleDate#year
     * @see SensibleDate#getYear()
     * @see SensibleDate#getDate()
     * @see SensibleDate#setDate(int, int, int)
     * @see SensibleDate#setValue(int, int, int)
     */
    public void setYear(int newYear) {

        // leap year bug
        // if changing the year on a valid 29/2 the set fails
        // we should roll the date to 1/3
        int newMonth = month;
        int newDay = day;
        final int february = 2;
        final int days29 = 29;
        if (month == february && day == days29 && !isLeapYear(newYear)) {
            newMonth++;
            newDay = 1;
        }

        setValue(newYear, newMonth, newDay);
    }

    /**
     * Changes the date value using the given integer values and fires the property change event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid date.
     *
     * @param newYear the date new year
     * @param newMonth the date new month
     * @param newDay the date new day
     *
     * @see SensibleDate#checkDate(int, int, int)
     * @see SensibleDate#formatDate(int, int, int)
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleDate#setComplete(boolean)
     */
    private void setValue(int newYear, int newMonth, int newDay) {

        String oldValue = value;

        if (checkDate(newYear, newMonth, newDay)) {
            this.year = newYear;
            this.month = newMonth;
            this.day = newDay;

            value = formatDate(year, month, day);

            valueChangingInSet = true;

            firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

            setValid(true);
            setComplete(true);

            valueChangingInSet = false;
        } else {
            throw new IllegalArgumentException(SensibleContext.getMessage("DATE_ERR_INVALID_DATE")); //$NON-NLS-1$
        }
    }

    /**
     * Returns the date as a string in DD/MM/YYYY format.
     *
     * @return the date as a string in DD/MM/YYYY format
     */
    public String toStringDMY() {

        final int tl = 2;
        final int yl = 4;

        StringBuffer sb = new StringBuffer();

        sb.append(StringToolkit.padLeft(Integer.toString(day), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(month), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(year), yl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns the date as a string in MM/DD/YYYY format.
     *
     * @return the date as a string in MM/DD/YYYY format
     */
    public String toStringMDY() {

        final int tl = 2;
        final int yl = 4;

        StringBuffer sb = new StringBuffer();

        sb.append(StringToolkit.padLeft(Integer.toString(month), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(day), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(year), yl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns the date as a string in YYYY/MM/DD format.
     *
     * @return the date as a string in YYYY/MM/DD format
     */
    public String toStringYMD() {

        final int tl = 2;
        final int yl = 4;

        StringBuffer sb = new StringBuffer();

        sb.append(StringToolkit.padLeft(Integer.toString(year), yl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(month), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(day), tl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns a string representation valid for sorting data. For the date data type this
     * representation is the value string in YYYY/MM/DD format.
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        return toStringYMD();
    }

    /**
     * Returns a string representation valid for sql language operations. For the date data type
     * this representation is the value string surrounded by simple quotes.
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return '\'' + toString() + '\'';
    }
}
