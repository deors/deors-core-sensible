package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.util.Calendar;
import java.util.Date;

import deors.core.commons.StringToolkit;

/**
 * Definition for a date and time data type.<br>
 *
 * This class manages dates with customizable format and times with precision of a second
 * and 24 hour format. A time with only hour and minutes informed is considered a valid time.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleDateTime
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 4916474988469900800L;

    /**
     * The date/time day.
     *
     * @see SensibleDateTime#getDay()
     * @see SensibleDateTime#setDay(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    private int day;

    /**
     * The date/time month.
     *
     * @see SensibleDateTime#getMonth()
     * @see SensibleDateTime#setMonth(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    private int month;

    /**
     * The date/time year.
     *
     * @see SensibleDateTime#getYear()
     * @see SensibleDateTime#setYear(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    private int year;

    /**
     * The date/time hour.
     *
     * @see SensibleDateTime#getHour()
     * @see SensibleDateTime#setHour(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    private int hour;

    /**
     * The date/time minute.
     *
     * @see SensibleDateTime#getMinute()
     * @see SensibleDateTime#setMinute(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    private int minute;

    /**
     * The date/time second.
     *
     * @see SensibleDateTime#getSecond()
     * @see SensibleDateTime#setSecond(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    private int second;

    /**
     * The date format.
     *
     * @see SensibleDateTime#getDateFormat()
     * @see SensibleDateTime#setDateFormat(String)
     * @see SensibleDateTime#DEFAULT_DATE_FORMAT
     * @see SensibleDateTime#DMY_DATE_FORMAT
     * @see SensibleDateTime#MDY_DATE_FORMAT
     * @see SensibleDateTime#YMD_DATE_FORMAT
     */
    private String dateFormat = DEFAULT_DATE_FORMAT;

    /**
     * The date separator.
     *
     * @see SensibleDateTime#getDateSeparator()
     * @see SensibleDateTime#setDateSeparator(char)
     * @see SensibleDateTime#DEFAULT_DATE_SEPARATOR
     */
    private char dateSeparator = DEFAULT_DATE_SEPARATOR;

    /**
     * The date/time separator.
     *
     * @see SensibleDateTime#getDateTimeSeparator()
     * @see SensibleDateTime#setDateTimeSeparator(char)
     * @see SensibleDateTime#DEFAULT_DATETIME_SEPARATOR
     */
    private char dateTimeSeparator = DEFAULT_DATETIME_SEPARATOR;

    /**
     * The time separator.
     *
     * @see SensibleDateTime#getTimeSeparator()
     * @see SensibleDateTime#setTimeSeparator(char)
     * @see SensibleDateTime#DEFAULT_TIME_SEPARATOR
     */
    private char timeSeparator = DEFAULT_TIME_SEPARATOR;

    /**
     * Whether the time has second precision.
     *
     * @see SensibleDateTime#isTimeWithSeconds()
     * @see SensibleDateTime#setTimeWithSeconds(boolean)
     * @see SensibleDateTime#DEFAULT_TIME_WITH_SECONDS
     */
    private boolean timeWithSeconds = DEFAULT_TIME_WITH_SECONDS;

    /**
     * Whether the time is optional (assumed 0:00:00 if optional and not set).
     *
     * @see SensibleDateTime#isTimeOptional()
     * @see SensibleDateTime#setTimeOptional(boolean)
     * @see SensibleDateTime#DEFAULT_TIME_OPTIONAL
     */
    private boolean timeOptional = DEFAULT_TIME_OPTIONAL;

    /**
     * Whether the date/time is complete.
     *
     * @see SensibleDateTime#isComplete()
     * @see SensibleDateTime#setComplete(boolean)
     */
    private boolean complete;

    /**
     * Whether the proposed date/time could be a valid date/time.
     */
    private boolean proposedValid = true;

    /**
     * The number of items in the proposed date/time.
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
     * The proposed hour.
     */
    private int proposedHour;

    /**
     * The proposed minute.
     */
    private int proposedMinute;

    /**
     * The proposed second.
     */
    private int proposedSecond;

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
     * The "dateTimeSeparator" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATE_TIME_SEPARATOR = "dateTimeSeparator"; //$NON-NLS-1$

    /**
     * The "timeSeparator" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_TIME_SEPARATOR = "timeSeparator"; //$NON-NLS-1$

    /**
     * The "timeWithSeconds" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_TIME_WITH_SECONDS = "timeWithSeconds"; //$NON-NLS-1$

    /**
     * The "timeOptional" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_TIME_OPTIONAL = "timeOptional"; //$NON-NLS-1$

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
     * The default date/time separator character. Configurable in the properties file using the key
     * <code>format.defaultDateTimeSeparator</code>. Default value is <code>space</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, char)
     */
    private static final char DEFAULT_DATETIME_SEPARATOR =
        getConfigurationProperty("format.defaultDateTimeSeparator", ' '); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * The default time separator character. Configurable in the properties file using the key
     * <code>format.defaultTimeSeparator</code>. Default value is <code>:</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, char)
     */
    private static final char DEFAULT_TIME_SEPARATOR =
        getConfigurationProperty("format.defaultTimeSeparator", ':'); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * The default 'time with seconds' flag. Configurable in the properties file using the key
     * <code>format.defaultTimeWithSeconds</code>. Default value is <code></code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, boolean)
     */
    private static final boolean DEFAULT_TIME_WITH_SECONDS =
        getConfigurationProperty("format.defaultTimeWithSeconds", true); //$NON-NLS-1$

    /**
     * The default 'time optional' flag. Configurable in the properties file
     * <code>format.defaultTimeOptional</code>. Default value is <code></code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    private static final boolean DEFAULT_TIME_OPTIONAL =
        getConfigurationProperty("format.defaultTimeOptional", false); //$NON-NLS-1$

    /**
     * Default constructor.
     */
    public SensibleDateTime() {

        super();

        setDateTime(SensibleContext.BLANK);
    }

    /**
     * Constructor that sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     */
    public SensibleDateTime(boolean timeWithSeconds) {

        this();

        setTimeWithSeconds(timeWithSeconds);
    }

    /**
     * Constructor that sets the 'time with seconds' and 'time optional' flags.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param timeOptional the 'time optional' flag
     */
    public SensibleDateTime(boolean timeWithSeconds, boolean timeOptional) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTimeOptional(timeOptional);
    }

    /**
     * Constructor that sets the date/time using the given integer values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid date/time.
     *
     * @param year the date/time year
     * @param month the date/time month
     * @param day the date/time day
     * @param hour the date/time hour
     * @param minute the date/time minute
     * @param second the date/time second
     */
    public SensibleDateTime(int year, int month, int day, int hour, int minute, int second) {

        this();

        setDateTime(year, month, day, hour, minute, second);
    }

    /**
     * Constructor that sets the date/time using the given integer values and sets the 'time with
     * seconds' flag.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid date/time.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param year the date/time year
     * @param month the date/time month
     * @param day the date/time day
     * @param hour the date/time hour
     * @param minute the date/time minute
     * @param second the date/time second
     */
    public SensibleDateTime(boolean timeWithSeconds, int year, int month, int day,
                            int hour, int minute, int second) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setDateTime(year, month, day, hour, minute, second);
    }

    /**
     * Constructor that sets the date/time using the given integer values and sets the 'time with
     * seconds' and 'time optional' flags.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid date/time.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param timeOptional the 'time optional' flag
     * @param year the date/time year
     * @param month the date/time month
     * @param day the date/time day
     * @param hour the date/time hour
     * @param minute the date/time minute
     * @param second the date/time second
     */
    public SensibleDateTime(boolean timeWithSeconds, boolean timeOptional,
                            int year, int month, int day,
                            int hour, int minute, int second) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTimeOptional(timeOptional);
        setDateTime(year, month, day, hour, minute, second);
    }

    /**
     * Constructor that sets the date/time using the given <code>SensibleDateTime</code> object
     * value.
     *
     * @param dateTime the date/time value
     */
    public SensibleDateTime(SensibleDateTime dateTime) {

        this();

        setDateTime(dateTime);
    }

    /**
     * Constructor that sets the date/time using the given <code>SensibleDateTime</code> object
     * value and sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param dateTime the date/time value
     */
    public SensibleDateTime(boolean timeWithSeconds, SensibleDateTime dateTime) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setDateTime(dateTime);
    }

    /**
     * Constructor that sets the date/time using the given <code>SensibleDateTime</code> object
     * value and sets the 'time with seconds' and 'time optional' flags.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param timeOptional the 'time optional' flag
     * @param dateTime the date/time value
     */
    public SensibleDateTime(boolean timeWithSeconds, boolean timeOptional,
                            SensibleDateTime dateTime) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTimeOptional(timeOptional);
        setDateTime(dateTime);
    }

    /**
     * Constructor that sets the date/time using the given <code>SensibleDate</code> and
     * <code>SensibleTime</code> objects values.
     *
     * @param date the date value
     * @param time the time value
     */
    public SensibleDateTime(SensibleDate date, SensibleTime time) {

        this();

        setDateTime(date, time);
    }

    /**
     * Constructor that sets the date/time using the given <code>SensibleDate</code> and
     * <code>SensibleTime</code> objects values and sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param date the date value
     * @param time the time value
     */
    public SensibleDateTime(boolean timeWithSeconds, SensibleDate date, SensibleTime time) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setDateTime(date, time);
    }

    /**
     * Constructor that sets the date/time using the given <code>SensibleDate</code> and
     * <code>SensibleTime</code> objects values and sets the 'time with seconds' and
     * 'time optional' flags.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param timeOptional the 'time optional' flag
     * @param date the date value
     * @param time the time value
     */
    public SensibleDateTime(boolean timeWithSeconds, boolean timeOptional,
                            SensibleDate date, SensibleTime time) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTimeOptional(timeOptional);
        setDateTime(date, time);
    }

    /**
     * Constructor that sets the date/time using the given <code>java.util.Calendar</code> object
     * value.
     *
     * @param dateTime the calendar object used to set the date/time value
     */
    public SensibleDateTime(Calendar dateTime) {

        this();

        setDateTime(dateTime);
    }

    /**
     * Constructor that sets the date/time using the given <code>java.util.Calendar</code> object
     * value and sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param dateTime the calendar object used to set the date/time value
     */
    public SensibleDateTime(boolean timeWithSeconds, Calendar dateTime) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setDateTime(dateTime);
    }

    /**
     * Constructor that sets the date/time using the given <code>java.util.Calendar</code> object
     * value and sets the 'time with seconds' and 'time optional' flags.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param timeOptional the 'time optional' flag
     * @param dateTime the calendar object used to set the date/time value
     */
    public SensibleDateTime(boolean timeWithSeconds, boolean timeOptional,
                            Calendar dateTime) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTimeOptional(timeOptional);
        setDateTime(dateTime);
    }

    /**
     * Constructor that sets the date/time using the given <code>String</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid date.
     *
     * @param source the string object used to set the date/time value
     */
    public SensibleDateTime(String source) {

        this();

        setDateTime(source);
    }

    /**
     * Constructor that sets the date/time using the given <code>String</code> object value and
     * sets the 'time with seconds' flag.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid date.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param source the string object used to set the date/time value
     */
    public SensibleDateTime(boolean timeWithSeconds, String source) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setDateTime(source);
    }

    /**
     * Constructor that sets the date/time using the given <code>String</code> object value and
     * sets the 'time with seconds' and 'time optional' flags.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid date.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param timeOptional the 'time optional' flag
     * @param source the string object used to set the date/time value
     */
    public SensibleDateTime(boolean timeWithSeconds, boolean timeOptional,
                            String source) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTimeOptional(timeOptional);
        setDateTime(source);
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in an insertion action fired in a <code>SensibleTextField</code> object.
     * The method checks whether the proposed value matches the data type format. For the date/time
     * data type an insert action is allowed if the final string contains only numbers and the date
     * and time separators and that the final string matches a valid date/time.
     *
     * @param offset the insert starting point
     * @param s the string to be inserted
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     * @see SensibleDateTime#checkDateTime(String)
     */
    protected boolean allowInsert(int offset, String s) {

        for (int i = 0, n = s.length(); i < n; i++) {
            if ((s.charAt(i) < '0' || s.charAt(i) > '9')
                && s.charAt(i) != dateSeparator
                && s.charAt(i) != dateTimeSeparator
                && s.charAt(i) != timeSeparator) {
                return false;
            }
        }

        if (offset == 0) {
            return checkDateTime(s + value);
        }

        return checkDateTime(value.substring(0, offset) + s + value.substring(offset));
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
     * @see SensibleDateTime#allowInsert(int, String)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in a remove action fired in a <code>SensibleTextField</code> object. The
     * method checks whether the proposed value matches the data type format. For the date/time data
     * type a remove action is allowed if the final string matches a valid date/time.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     * @see SensibleDateTime#checkDateTime(String)
     */
    protected boolean allowRemove(int offset, int length) {

        return checkDateTime(value.substring(0, offset) + value.substring(offset + length));
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
     * @see SensibleDateTime#allowRemove(int, int)
     */
    protected boolean allowRemove(int offset, int length, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Returns the date/time value as a <code>java.util.Calendar</code> object.
     *
     * @return the date/time as a <code>Calendar</code> object
     */
    public Calendar calendarValue() {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month - 1);
        now.set(Calendar.DAY_OF_MONTH, day);
        now.set(Calendar.HOUR_OF_DAY, hour);
        now.set(Calendar.MINUTE, minute);
        now.set(Calendar.SECOND, second);
        now.set(Calendar.MILLISECOND, 0);

        return now;
    }

    /**
     * Used internally to change the data value. This method uses the given string to change the
     * data value. If the new value is valid, fires the property change events, one for the
     * <code>value</code> property and another for the property in which the particular data value
     * is stored (if this property exists). If necessary, the implementation has to invoke the
     * <code>setValid(boolean)</code> method to change the data value valid state.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the string does
     * not represent a valid date/time and the change value action is invoked from a setter method.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleDateTime#setComplete(boolean)
     * @see SensibleDateTime#checkDateTime(String)
     */
    protected void changeValue(String newValue) {

        final int itemCount = 6;

        String oldValue = value;

        if (!checkDateTime(newValue)
            && valueChangingInSet) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT")); //$NON-NLS-1$
        }

        this.year = proposedYear;
        this.month = proposedMonth;
        this.day = proposedDay;
        this.hour = proposedHour;
        this.minute = proposedMinute;
        this.second = proposedSecond;
        this.value = newValue;

        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(proposedValid);
        setComplete((proposedItems == itemCount || proposedItems == itemCount - 1) && proposedValid);

        emptyProposed();
    }

    /**
     * Checks that the given integer values represent a valid date/time.
     *
     * @param checkYear the date/time year to be checked
     * @param checkMonth the date/time month to be checked
     * @param checkDay the date/time day to be checked
     * @param checkHour the date/time hour to be checked
     * @param checkMinute the date/time minute to be checked
     * @param checkSecond the date/time second to be checked
     *
     * @return whether the given integer values represent a valid date/time
     *
     * @see SensibleDateTime#timeWithSeconds
     */
    private boolean checkDateTime(int checkYear, int checkMonth, int checkDay, int checkHour,
                                  int checkMinute, int checkSecond) {

        final int lastMonth = 12;
        final int lastDay = 31;
        final int february = 2;
        final int lastHour = 23;
        final int lastMinute = 59;
        final int lastSecond = 59;

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

        if (checkHour < 0 || checkMinute < 0 || checkSecond < 0) {
            return false;
        }

        if (checkHour > lastHour || checkMinute > lastMinute || checkSecond > lastSecond) {
            return false;
        }

        return true;
    }

    /**
     * Checks that the given string represents a valid date/time. When this method is invoked due to
     * a text field action, the string can represent an incomplete date/time. When this method is
     * invoked due to a setter method action, the string can be only a completed date/time. The
     * given string items are saved in the proposed-date/time properties.
     *
     * @param s the string to be checked
     *
     * @return whether the given string represents a valid date/time
     */
    private boolean checkDateTime(String s) {

        final int dayItemSize = 2;
        final int monthItemSize = 2;
        final int yearItemSize = 4;
        final int timeItemSize = 2;
        final int lastMonth = 12;
        final int lastDay = 31;
        final int february = 2;
        final int lastHour = 23;
        final int lastMinute = 59;
        final int lastSecond = 59;
        final int itemCount = 6;
        final String zeroTime = "0:00:00"; //$NON-NLS-1$

        if (s.length() == 0) {
            emptyProposed();
            return true;
        }

        String datePart = SensibleContext.BLANK;
        String timePart = SensibleContext.BLANK;

        // parses the string
        int dtSepPos = s.indexOf(dateTimeSeparator);
        if (dtSepPos == -1) {
            // the date/time separator is not present
            // the value must contain only a date or only a time
            if (s.indexOf(dateSeparator) == -1) {
                if (s.indexOf(timeSeparator) == -1) {
                    // when no separators are present we asume
                    // that the user is trying to enter a date
                    datePart = s;
                } else {
                    // only the time separator is present
                    timePart = s;
                }
            } else {
                if (s.indexOf(timeSeparator) == -1) {
                    // only the date separator is present
                    datePart = s;
                } else {
                    // both the date and the time separators
                    // can not be present in the same part
                    return false;
                }
            }
        } else {
            datePart = s.substring(0, dtSepPos);
            timePart = s.substring(dtSepPos + 1);
        }

        int parsedItems = 1;
        String parsedItem1 = SensibleContext.BLANK;
        String parsedItem2 = SensibleContext.BLANK;
        String parsedItem3 = SensibleContext.BLANK;
        String parsedItem4 = SensibleContext.BLANK;
        String parsedItem5 = SensibleContext.BLANK;
        String parsedItem6 = SensibleContext.BLANK;

        // parses the date
        if (datePart.length() != 0) {
            int sepPos1 = datePart.indexOf(dateSeparator);
            if (sepPos1 == -1) {
                parsedItem1 = datePart;
            } else {
                parsedItem1 = datePart.substring(0, sepPos1);

                int sepPos2 = datePart.indexOf(dateSeparator, sepPos1 + 1);
                if (sepPos2 == -1) {
                    parsedItems++;
                    parsedItem2 = datePart.substring(sepPos1 + 1);
                } else {
                    parsedItems++;
                    parsedItem2 = datePart.substring(sepPos1 + 1, sepPos2);

                    if (datePart.indexOf(dateSeparator, sepPos2 + 1) != -1) {
                        return false;
                    }

                    if (sepPos2 != datePart.length() - 1) {
                        parsedItems++;
                        parsedItem3 = datePart.substring(sepPos2 + 1);
                    }
                }
            }
        }

        // parses the time
        if (timePart.length() == 0 && timeOptional) {
            timePart = zeroTime;
        }

        if (timePart.length() != 0) {
            int sepPos1 = timePart.indexOf(timeSeparator);
            if (sepPos1 == -1) {
                parsedItems++;
                parsedItem4 = timePart;
            } else {
                parsedItems++;
                parsedItem4 = timePart.substring(0, sepPos1);

                int sepPos2 = timePart.indexOf(timeSeparator, sepPos1 + 1);
                if (sepPos2 == -1) {
                    parsedItems++;
                    parsedItem5 = timePart.substring(sepPos1 + 1);
                } else {
                    if (!timeWithSeconds && !timeOptional) {
                        return false;
                    }

                    parsedItems++;
                    parsedItem5 = timePart.substring(sepPos1 + 1, sepPos2);

                    if (timePart.indexOf(timeSeparator, sepPos2 + 1) != -1) {
                        return false;
                    }

                    if (sepPos2 != timePart.length() - 1) {
                        parsedItems++;
                        parsedItem6 = timePart.substring(sepPos2 + 1);
                    }
                }
            }
        }

        int parsedYear = 0;
        int parsedMonth = 0;
        int parsedDay = 0;
        int parsedHour = 0;
        int parsedMinute = 0;
        int parsedSecond = 0;
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

        if (parsedItem4.length() > timeItemSize
            || parsedItem5.length() > timeItemSize
            || parsedItem6.length() > timeItemSize) {
            return false;
        }

        try {
            if (parsedItem4.length() != 0) {
                parsedHour = Integer.parseInt(parsedItem4);
            }

            if (parsedItem5.length() != 0) {
                parsedMinute = Integer.parseInt(parsedItem5);
            }

            if (parsedItem6.length() != 0) {
                parsedSecond = Integer.parseInt(parsedItem6);
            }

            if (parsedItem4.length() == 0
                || parsedItem5.length() == 0
                || (timeWithSeconds && parsedItem6.length() == 0)
                || (parsedItem5.length() != 0 && parsedItem5.length() != timeItemSize)
                || (parsedItem6.length() != 0 && parsedItem6.length() != timeItemSize)) {
                parsedValid = false;
            }
        } catch (NumberFormatException nfe) {
            return false;
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

        if (parsedHour > lastHour || parsedMinute > lastMinute || parsedSecond > lastSecond) {
            // parsedValid = false;
            return false;
        }

        // saves the proposed date
        // saves the proposed time
        proposedValid =
            ((timeWithSeconds && parsedItems == itemCount)
             || (!timeWithSeconds && parsedItems == itemCount - 1))
            && parsedValid;
        proposedItems = parsedItems;
        proposedYear = parsedYear;
        proposedMonth = parsedMonth;
        proposedDay = parsedDay;
        proposedHour = parsedHour;
        proposedMinute = parsedMinute;
        proposedSecond = parsedSecond;

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
     * Returns the date/time value as a <code>java.util.Date</code> object.
     *
     * @return the date/time as a <code>Date</code> object
     */
    public Date dateValue() {

        Calendar now = calendarValue();

        return now.getTime();
    }

    /**
     * Empty the proposed date/time properties.
     */
    private void emptyProposed() {

        proposedValid = true;
        proposedItems = 0;
        proposedYear = 0;
        proposedMonth = 0;
        proposedDay = 0;
        proposedHour = 0;
        proposedMinute = 0;
        proposedSecond = 0;
    }

    /**
     * Compares this <code>SensibleDateTime</code> object with the given integer values and
     * returns whether both object and values represent the same date/time.
     *
     * @param targetYear the target year value
     * @param targetMonth the target month value
     * @param targetDay the target day value
     * @param targetHour the target hour value
     * @param targetMinute the target minute value
     * @param targetSecond the target second value
     *
     * @return whether this and the given values represent the same date
     */
    public boolean equals(int targetYear, int targetMonth, int targetDay, int targetHour,
                          int targetMinute, int targetSecond) {

        return year == targetYear
               && month == targetMonth
               && day == targetDay
               && hour == targetHour
               && minute == targetMinute
               && second == targetSecond;
    }

    /**
     * Compares this <code>SensibleDateTime</code> object with the given
     * <code>java.util.Calendar</code> object and returns whether both objects represent the same
     * date/time.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same date/time
     */
    public boolean equals(Calendar target) {

        if (target == null) {
            return false;
        }

        return year == target.get(target.YEAR)
               && month == target.get(target.MONTH)
               && day == target.get(target.DAY_OF_MONTH)
               && hour == target.get(target.HOUR_OF_DAY)
               && minute == target.get(target.MINUTE)
               && second == target.get(target.SECOND);
    }

    /**
     * Compares this <code>SensibleDateTime</code> object with the given object and returns
     * whether both objects represent the same date/time.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same date/time
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

        if (!(target instanceof SensibleDateTime)) {
            return false;
        }

        SensibleDateTime sensibleTarget = (SensibleDateTime) target;

        return this.year == sensibleTarget.year
               && this.month == sensibleTarget.month
               && this.day == sensibleTarget.day
               && this.hour == sensibleTarget.hour
               && this.minute == sensibleTarget.minute
               && this.second == sensibleTarget.second;
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        super.firePropertyChangeEvents();

        firePropertyChange(JAVA_BEAN_PROPERTY_COMPLETE, null, complete);
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_WITH_SECONDS, null, timeWithSeconds);
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_FORMAT, null, dateFormat);
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_SEPARATOR, null, dateSeparator);
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_SEPARATOR, null, timeSeparator);
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_TIME_SEPARATOR, null, dateTimeSeparator);
    }

    /**
     * Formats a date/time using the current format and the given integer values.
     *
     * @return the date/time formatted
     *
     * @param formatYear the date/time year
     * @param formatMonth the date/time month
     * @param formatDay the date/time day
     * @param formatHour the date/time hour
     * @param formatMinute the date/time minute
     * @param formatSecond the date/time second
     *
     * @see SensibleDateTime#timeWithSeconds
     */
    private String formatDateTime(int formatYear, int formatMonth, int formatDay, int formatHour,
                                  int formatMinute, int formatSecond) {

        final int tl = 2;

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

        sb.append(dateTimeSeparator);

        sb.append(StringToolkit.padLeft(Integer.toString(formatHour), tl, ZERO_CHAR));
        sb.append(timeSeparator);
        sb.append(StringToolkit.padLeft(Integer.toString(formatMinute), tl, ZERO_CHAR));

        if (timeWithSeconds) {
            sb.append(timeSeparator);
            sb.append(StringToolkit.padLeft(Integer.toString(formatSecond), tl, ZERO_CHAR));
        }

        return sb.toString();
    }

    /**
     * Returns the <code>dateFormat</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#dateFormat
     * @see SensibleDateTime#setDateFormat(String)
     * @see SensibleDateTime#DMY_DATE_FORMAT
     * @see SensibleDateTime#MDY_DATE_FORMAT
     * @see SensibleDateTime#YMD_DATE_FORMAT
     */
    public String getDateFormat() {

        return dateFormat;
    }

    /**
     * Returns the <code>dateSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#dateSeparator
     * @see SensibleDateTime#setDateSeparator(char)
     */
    public char getDateSeparator() {

        return dateSeparator;
    }

    /**
     * Returns the date/time value as an array of integer values.
     *
     * @return the year, month, day, hour, minute and second as an array of integer values
     *
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#year
     * @see SensibleDateTime#getYear()
     * @see SensibleDateTime#setYear(int)
     * @see SensibleDateTime#month
     * @see SensibleDateTime#getMonth()
     * @see SensibleDateTime#setMonth(int)
     * @see SensibleDateTime#day
     * @see SensibleDateTime#getDay()
     * @see SensibleDateTime#setDay(int)
     * @see SensibleDateTime#hour
     * @see SensibleDateTime#getHour()
     * @see SensibleDateTime#setHour(int)
     * @see SensibleDateTime#minute
     * @see SensibleDateTime#getMinute()
     * @see SensibleDateTime#setMinute(int)
     * @see SensibleDateTime#second
     * @see SensibleDateTime#getSecond()
     * @see SensibleDateTime#setSecond(int)
     */
    public int[] getDateTime() {

        return new int[] {year, month, day, hour, minute, second};
    }

    /**
     * Returns the <code>dateTimeSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#dateTimeSeparator
     * @see SensibleDateTime#setDateTimeSeparator(char)
     */
    public char getDateTimeSeparator() {

        return dateTimeSeparator;
    }

    /**
     * Returns the <code>day</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#day
     * @see SensibleDateTime#setDay(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    public int getDay() {

        return day;
    }

    /**
     * Returns the <code>hour</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#hour
     * @see SensibleDateTime#setHour(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    public int getHour() {

        return hour;
    }

    /**
     * Returns the <code>minute</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#minute
     * @see SensibleDateTime#setMinute(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    public int getMinute() {

        return minute;
    }

    /**
     * Returns the <code>month</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#month
     * @see SensibleDateTime#setMonth(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    public int getMonth() {

        return month;
    }

    /**
     * Returns the <code>second</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#second
     * @see SensibleDateTime#setSecond(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     */
    public int getSecond() {

        return second;
    }

    /**
     * Returns the <code>timeSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#timeSeparator
     * @see SensibleDateTime#setTimeSeparator(char)
     */
    public char getTimeSeparator() {

        return timeSeparator;
    }

    /**
     * Returns the <code>year</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#year
     * @see SensibleDateTime#setYear(int)
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
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

        return year * month * day * hour * minute * second;
    }

    /**
     * Returns <code>true</code> if the data value is clear. Actually it checks that the year,
     * month, day, hour, minute and second are all equal to zero.
     *
     * @return whether the data value is clear
     */
    public boolean isClear() {

        return year == 0 && month == 0 && day == 0 && hour == 0 && minute == 0 && second == 0;
    }

    /**
     * Returns the <code>complete</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#complete
     * @see SensibleDateTime#setComplete(boolean)
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

        return SensibleDate.isLeapYear(year);
    }

    /**
     * Returns the <code>timeWithSeconds</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#timeWithSeconds
     * @see SensibleDateTime#setTimeWithSeconds(boolean)
     */
    public boolean isTimeWithSeconds() {

        return timeWithSeconds;
    }

    /**
     * Returns the <code>timeOptional</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDateTime#timeOptional
     * @see SensibleDateTime#setTimeOptional(boolean)
     */
    public boolean isTimeOptional() {

        return timeOptional;
    }

    /**
     * Changes the <code>complete</code> property value and fires the property change event.
     *
     * @param newValue the new value
     *
     * @see SensibleDateTime#complete
     * @see SensibleDateTime#isComplete()
     */
    public void setComplete(boolean newValue) {

        boolean oldValue = complete;
        complete = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_COMPLETE, oldValue, newValue);
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
     * @see SensibleDateTime#dateFormat
     * @see SensibleDateTime#getDateFormat()
     * @see SensibleDateTime#DEFAULT_DATE_FORMAT
     * @see SensibleDateTime#DMY_DATE_FORMAT
     * @see SensibleDateTime#MDY_DATE_FORMAT
     * @see SensibleDateTime#YMD_DATE_FORMAT
     */
    public void setDateFormat(String newValue) {

        if (!newValue.equals(DMY_DATE_FORMAT) && !newValue.equals(MDY_DATE_FORMAT)
            && !newValue.equals(YMD_DATE_FORMAT)) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT")); //$NON-NLS-1$
        }

        String oldValue = newValue;
        dateFormat = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_FORMAT, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day, hour, minute, second);
        }
    }

    /**
     * Changes the <code>dateSeparator</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDateTime#dateSeparator
     * @see SensibleDateTime#getDateSeparator()
     * @see SensibleDateTime#DEFAULT_DATE_SEPARATOR
     */
    public void setDateSeparator(char newValue) {

        char oldValue = dateSeparator;
        dateSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_SEPARATOR, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day, hour, minute, second);
        }
    }

    /**
     * Changes the date/time value using the given integer values.
     *
     * @param newYear the date/time new year
     * @param newMonth the date/time new month
     * @param newDay the date/time new day
     * @param newHour the date/time new hour
     * @param newMinute the date/time new minute
     * @param newSecond the date/time new second
     *
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     * @see SensibleDateTime#year
     * @see SensibleDateTime#getYear()
     * @see SensibleDateTime#setYear(int)
     * @see SensibleDateTime#month
     * @see SensibleDateTime#getMonth()
     * @see SensibleDateTime#setMonth(int)
     * @see SensibleDateTime#day
     * @see SensibleDateTime#getDay()
     * @see SensibleDateTime#setDay(int)
     * @see SensibleDateTime#hour
     * @see SensibleDateTime#getHour()
     * @see SensibleDateTime#setHour(int)
     * @see SensibleDateTime#minute
     * @see SensibleDateTime#getMinute()
     * @see SensibleDateTime#setMinute(int)
     * @see SensibleDateTime#second
     * @see SensibleDateTime#getSecond()
     * @see SensibleDateTime#setSecond(int)
     */
    public void setDateTime(int newYear, int newMonth, int newDay, int newHour, int newMinute,
                            int newSecond) {

        setValue(newYear, newMonth, newDay, newHour, newMinute, newSecond);
    }

    /**
     * Changes the date/time value using the given <code>SensibleDateTime</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setDateTime(SensibleDateTime newValue) {

        setValue(
            newValue.year, newValue.month, newValue.day,
            newValue.hour, newValue.minute, newValue.second);
    }

    /**
     * Changes the date/time value using the given using the given <code>SensibleDate</code> and
     * <code>SensibleTime</code> objects values.
     *
     * @param newDateValue the new date value
     * @param newTimeValue the new time value
     *
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setDateTime(SensibleDate newDateValue, SensibleTime newTimeValue) {

        setValue(
            newDateValue.getYear(), newDateValue.getMonth(), newDateValue.getDay(),
            newTimeValue.getHour(), newTimeValue.getMinute(), newTimeValue.getSecond());
    }

    /**
     * Changes the date/time value using the given <code>java.util.Calendar</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setDateTime(Calendar newValue) {

        setValue(
            newValue.get(Calendar.YEAR),
            newValue.get(Calendar.MONTH) + 1,
            newValue.get(Calendar.DAY_OF_MONTH),
            newValue.get(Calendar.HOUR_OF_DAY),
            newValue.get(Calendar.MINUTE),
            newValue.get(Calendar.SECOND));
    }

    /**
     * Changes the date/time value using the given <code>String</code> object.
     *
     * @param newValue the string representation of the new value
     *
     * @see SensibleDateTime#setValue(String)
     */
    public void setDateTime(String newValue) {

        setValue(newValue);
    }

    /**
     * Changes the <code>dateTimeSeparator</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDateTime#dateTimeSeparator
     * @see SensibleDateTime#getDateTimeSeparator()
     * @see SensibleDateTime#DEFAULT_DATETIME_SEPARATOR
     */
    public void setDateTimeSeparator(char newValue) {

        char oldValue = dateTimeSeparator;
        dateTimeSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATE_TIME_SEPARATOR, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day, hour, minute, second);
        }
    }

    /**
     * Changes the <code>day</code> property value.
     *
     * @param newDay the date/time new day
     *
     * @see SensibleDateTime#day
     * @see SensibleDateTime#getDay()
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setDay(int newDay) {

        setValue(year, month, newDay, hour, minute, second);
    }

    /**
     * Changes the <code>hour</code> property value.
     *
     * @param newHour the date/time new hour
     *
     * @see SensibleDateTime#hour
     * @see SensibleDateTime#getHour()
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setHour(int newHour) {

        setValue(year, month, day, newHour, minute, second);
    }

    /**
     * Changes the <code>minute</code> property value.
     *
     * @param newMinute the date/time new minute
     *
     * @see SensibleDateTime#minute
     * @see SensibleDateTime#getMinute()
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setMinute(int newMinute) {

        setValue(year, month, day, hour, newMinute, second);
    }

    /**
     * Changes the <code>month</code> property value.
     *
     * @param newMonth the date/time new month
     *
     * @see SensibleDateTime#month
     * @see SensibleDateTime#getMonth()
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setMonth(int newMonth) {

        setValue(year, newMonth, day, hour, minute, second);
    }

    /**
     * Changes the <code>second</code> property value.
     *
     * @param newSecond the date/time new second
     *
     * @see SensibleDateTime#second
     * @see SensibleDateTime#getSecond()
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
     */
    public void setSecond(int newSecond) {

        setValue(year, month, day, hour, minute, newSecond);
    }

    /**
     * Changes the <code>year</code> property value.
     *
     * @param newYear the date/time new year
     *
     * @see SensibleDateTime#year
     * @see SensibleDateTime#getYear()
     * @see SensibleDateTime#getDateTime()
     * @see SensibleDateTime#setDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#setValue(int, int, int, int, int, int)
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

        setValue(newYear, newMonth, newDay, hour, minute, second);
    }

    /**
     * Changes the <code>timeSeparator</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDateTime#timeSeparator
     * @see SensibleDateTime#getTimeSeparator()
     * @see SensibleDateTime#DEFAULT_TIME_SEPARATOR
     */
    public void setTimeSeparator(char newValue) {

        char oldValue = timeSeparator;
        timeSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_SEPARATOR, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day, hour, minute, second);
        }
    }

    /**
     * Changes the <code>timeWithSeconds</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDateTime#timeWithSeconds
     * @see SensibleDateTime#isTimeWithSeconds()
     */
    public void setTimeWithSeconds(boolean newValue) {

        boolean oldValue = timeWithSeconds;
        timeWithSeconds = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_WITH_SECONDS, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day, hour, minute, second);
        }
    }

    /**
     * Sets the <code>timeOptional</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDateTime#timeOptional
     * @see SensibleDateTime#isTimeOptional()
     */
    public void setTimeOptional(boolean newValue) {

        boolean oldValue = timeOptional;
        timeOptional = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_OPTIONAL, oldValue, newValue);

        if (!isClear()) {
            setValue(year, month, day, hour, minute, second);
        }
    }

    /**
     * Changes the date/time value using the given integer values and fires the property change
     * event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid date/time.
     *
     * @param newYear the date/time new year
     * @param newMonth the date/time new month
     * @param newDay the date/time new day
     * @param newHour the date/time new hour
     * @param newMinute the date/time new minute
     * @param newSecond the date/time new second
     *
     * @see SensibleDateTime#checkDateTime(int, int, int, int, int, int)
     * @see SensibleDateTime#formatDateTime(int, int, int, int, int, int)
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleDateTime#setComplete(boolean)
     */
    private void setValue(int newYear, int newMonth, int newDay, int newHour, int newMinute,
                          int newSecond) {

        String oldValue = value;

        if (checkDateTime(newYear, newMonth, newDay, newHour, newMinute, newSecond)) {
            this.year = newYear;
            this.month = newMonth;
            this.day = newDay;
            this.hour = newHour;
            this.minute = newMinute;

            if (timeWithSeconds) {
                this.second = newSecond;
            } else {
                this.second = 0;
            }

            value = formatDateTime(year, month, day, hour, minute, second);

            valueChangingInSet = true;

            firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

            setValid(true);
            setComplete(true);

            valueChangingInSet = false;
        } else {
            throw new IllegalArgumentException(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM")); //$NON-NLS-1$
        }
    }

    /**
     * Returns the date/time as a string in DD/MM/YYYY HH:MM:SS format.
     *
     * @return the date/time as a string in DD/MM/YYYY HH:MM:SS format
     */
    public String toStringDMYHMS() {

        final int tl = 2;
        final int yl = 4;

        StringBuffer sb = new StringBuffer();

        sb.append(StringToolkit.padLeft(Integer.toString(day), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(month), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(year), yl, ZERO_CHAR));
        sb.append(DEFAULT_DATETIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(hour), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(minute), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(second), tl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns the date/time as a string in MM/DD/YYYY HH:MM:SS format.
     *
     * @return the date/time as a string in MM/DD/YYYY HH:MM:SS format
     */
    public String toStringMDYHMS() {

        final int tl = 2;
        final int yl = 4;

        StringBuffer sb = new StringBuffer();

        sb.append(StringToolkit.padLeft(Integer.toString(month), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(day), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(year), yl, ZERO_CHAR));
        sb.append(DEFAULT_DATETIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(hour), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(minute), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(second), tl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns the date/time as a string in YYYY/MM/DD HH:MM:SS format.
     *
     * @return the date/time as a string in YYYY/MM/DD HH:MM:SS format
     */
    public String toStringYMDHMS() {

        final int tl = 2;
        final int yl = 4;

        StringBuffer sb = new StringBuffer();

        sb.append(StringToolkit.padLeft(Integer.toString(year), yl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(month), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATE_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(day), tl, ZERO_CHAR));
        sb.append(DEFAULT_DATETIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(hour), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(minute), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(second), tl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns a string representation valid for sorting data. For the date/time data type this
     * representation is the value string in YYYY/MM/DD HH:MM:SS format.
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        return toStringYMDHMS();
    }

    /**
     * Returns a string representation valid for sql language operations. For the date/time data
     * type this representation is the value string surrounded by simple quotes.
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return '\'' + toString() + '\'';
    }
}
