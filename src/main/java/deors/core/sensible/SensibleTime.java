package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.util.Calendar;
import java.util.Date;

import deors.core.commons.StringToolkit;

/**
 * Definition for a time data type.<br>
 *
 * This class manages times with precision of a second and 24 hour format. A time with only hour
 * and minutes informed is considered a valid time.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleTime
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 3540984967448466881L;

    /**
     * The time hour.
     *
     * @see SensibleTime#getHour()
     * @see SensibleTime#setHour(int)
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     */
    private int hour;

    /**
     * The time minute.
     *
     * @see SensibleTime#getMinute()
     * @see SensibleTime#setMinute(int)
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     */
    private int minute;

    /**
     * The time second.
     *
     * @see SensibleTime#getSecond()
     * @see SensibleTime#setSecond(int)
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     */
    private int second;

    /**
     * The time separator.
     *
     * @see SensibleTime#getTimeSeparator()
     * @see SensibleTime#setTimeSeparator(char)
     * @see SensibleTime#DEFAULT_TIME_SEPARATOR
     */
    private char timeSeparator = DEFAULT_TIME_SEPARATOR;

    /**
     * Whether the time has second precission.
     *
     * @see SensibleTime#isTimeWithSeconds()
     * @see SensibleTime#setTimeWithSeconds(boolean)
     * @see SensibleTime#DEFAULT_TIME_WITH_SECONDS
     */
    private boolean timeWithSeconds = DEFAULT_TIME_WITH_SECONDS;

    /**
     * Whether the time is complete.
     *
     * @see SensibleTime#isComplete()
     * @see SensibleTime#setComplete(boolean)
     */
    private boolean complete;

    /**
     * Whether the proposed time could be a valid time.
     */
    private boolean proposedValid = true;

    /**
     * The number of items in the proposed time.
     */
    private int proposedItems;

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
     * The zero character used to pad date/time tokens.
     */
    private static final char ZERO_CHAR = '0';

    /**
     * The "complete" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_COMPLETE = "complete"; //$NON-NLS-1$

    /**
     * The "timeSeparator" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_TIME_SEPARATOR = "timeSeparator"; //$NON-NLS-1$

    /**
     * The "timeWithSeconds" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_TIME_WITH_SECONDS = "timeWithSeconds"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

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
     * Default constructor.
     */
    public SensibleTime() {

        super();

        setTime(SensibleContext.BLANK);
    }

    /**
     * Constructor that sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     */
    public SensibleTime(boolean timeWithSeconds) {

        this();

        setTimeWithSeconds(timeWithSeconds);
    }

    /**
     * Constructor that sets the time using the given integer values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid time.
     *
     * @param hour the time hour
     * @param minute the time minute
     * @param second the time second
     */
    public SensibleTime(int hour, int minute, int second) {

        this();

        setTime(hour, minute, second);
    }

    /**
     * Constructor that sets the time using the given integer values and sets the 'time with
     * seconds' flag.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid time.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param hour the time hour
     * @param minute the time minute
     * @param second the time second
     */
    public SensibleTime(boolean timeWithSeconds, int hour, int minute, int second) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTime(hour, minute, second);
    }

    /**
     * Constructor that sets the time using the given <code>SensibleTime</code> object value.
     *
     * @param time the time value
     */
    public SensibleTime(SensibleTime time) {

        this();

        setTime(time);
    }

    /**
     * Constructor that sets the time using the given <code>SensibleTime</code> object value and
     * sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param time the time value
     */
    public SensibleTime(boolean timeWithSeconds, SensibleTime time) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTime(time);
    }

    /**
     * Constructor that sets the time using the given <code>java.util.Calendar</code> object
     * value.
     *
     * @param time the calendar object used to set the time value
     */
    public SensibleTime(Calendar time) {

        this();

        setTime(time);
    }

    /**
     * Constructor that sets the time using the given <code>java.util.Calendar</code> object value
     * and sets the 'time with seconds' flag.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param time the calendar object used to set the time value
     */
    public SensibleTime(boolean timeWithSeconds, Calendar time) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTime(time);
    }

    /**
     * Constructor that sets the time using the given <code>String</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid time.
     *
     * @param source the string object used to set the time value
     */
    public SensibleTime(String source) {

        this();

        setTime(source);
    }

    /**
     * Constructor that sets the time using the given <code>String</code> object value and sets
     * the 'time with seconds' flag.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * object does not represent a valid time.
     *
     * @param timeWithSeconds the 'time with seconds' flag
     * @param source the string object used to set the time value
     */
    public SensibleTime(boolean timeWithSeconds, String source) {

        this();

        setTimeWithSeconds(timeWithSeconds);
        setTime(source);
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in an insertion action fired in a <code>SensibleTextField</code> object.
     * The method checks whether the proposed value matches the data type format. For the time data
     * type an insert action is allowed if the final string contains only numbers and the time
     * separator and that the final string matches a valid time.
     *
     * @param offset the insert starting point
     * @param s the string to be inserted
     *
     * @return whether the proposed value is a valid time type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     * @see SensibleTime#checkTime(String)
     */
    protected boolean allowInsert(int offset, String s) {

        for (int i = 0, n = s.length(); i < n; i++) {
            if ((s.charAt(i) < '0' || s.charAt(i) > '9') && s.charAt(i) != timeSeparator) {
                return false;
            }
        }

        if (offset == 0) {
            return checkTime(s + value);
        }

        return checkTime(value.substring(0, offset) + s + value.substring(offset));
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
     * @see SensibleTime#allowInsert(int, String)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in a remove action fired in a <code>SensibleTextField</code> object. The
     * method checks whether the proposed value matches the data type format. For the time data type
     * a remove action is allowed if the final string matches a valid time.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @return whether the proposed value is a valid time type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     * @see SensibleTime#checkTime(String)
     */
    protected boolean allowRemove(int offset, int length) {

        return checkTime(value.substring(0, offset) + value.substring(offset + length));
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
     * @see SensibleTime#allowRemove(int, int)
     */
    protected boolean allowRemove(int offset, int length, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Returns the time value as a <code>java.util.Calendar</code> object.
     *
     * @return the time as a <code>Calendar</code> object
     */
    public Calendar calendarValue() {

        Calendar now = Calendar.getInstance();
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
     * not represent a valid time and the change value action is invoked from a setter method.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#changeValue(String)
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleTime#setComplete(boolean)
     * @see SensibleTime#checkTime(String)
     * @see SensibleTime#timeWithSeconds
     */
    protected void changeValue(String newValue) {

        final int itemCount = 3;

        String oldValue = value;

        if (valueChangingInSet
            && !checkTime(newValue)) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT")); //$NON-NLS-1$
        }

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
     * Checks that the given integer values represent a valid time.
     *
     * @param checkHour the time hour to be checked
     * @param checkMinute the time minute to be checked
     * @param checkSecond the time second to be checked
     *
     * @return whether the given integer values represent a valid time
     *
     * @see SensibleTime#timeWithSeconds
     */
    private boolean checkTime(int checkHour, int checkMinute, int checkSecond) {

        final int lastHour = 23;
        final int lastMinute = 59;
        final int lastSecond = 59;

        if (checkHour < 0 || checkMinute < 0 || checkSecond < 0) {
            return false;
        }

        if (checkHour > lastHour || checkMinute > lastMinute || checkSecond > lastSecond) {
            return false;
        }

        return true;
    }

    /**
     * Checks that the given string represents a valid time. When this method is invoked due to a
     * text field action, the string can represent an incomplete time. When this method is invoked
     * due to a setter method action, the string can be only a completed time (but does not need to
     * have the second part informed). The given string items are saved in the proposed-time
     * properties.
     *
     * @param s the string to be checked
     *
     * @return whether the given string represents a valid time
     *
     * @see SensibleTime#timeWithSeconds
     */
    private boolean checkTime(String s) {

        final int itemSize = 2;
        final int lastHour = 23;
        final int lastMinute = 59;
        final int lastSecond = 59;
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
        int sepPos1 = s.indexOf(timeSeparator);
        if (sepPos1 == -1) {
            parsedItem1 = s;
        } else {
            parsedItem1 = s.substring(0, sepPos1);

            int sepPos2 = s.indexOf(timeSeparator, sepPos1 + 1);
            if (sepPos2 == -1) {
                parsedItems++;
                parsedItem2 = s.substring(sepPos1 + 1);
            } else {
                if (!timeWithSeconds) {
                    return false;
                }

                parsedItems++;
                parsedItem2 = s.substring(sepPos1 + 1, sepPos2);

                if (s.indexOf(timeSeparator, sepPos2 + 1) != -1) {
                    return false;
                }

                if (sepPos2 != s.length() - 1) {
                    parsedItems++;
                    parsedItem3 = s.substring(sepPos2 + 1);
                }
            }
        }

        int parsedHour = 0;
        int parsedMinute = 0;
        int parsedSecond = 0;
        boolean parsedValid = true;

        // checks the string format
        if (parsedItem1.length() > itemSize
            || parsedItem2.length() > itemSize
            || parsedItem3.length() > itemSize) {
            return false;
        }

        try {
            if (parsedItem1.length() != 0) {
                parsedHour = Integer.parseInt(parsedItem1);
            }

            if (parsedItem2.length() != 0) {
                parsedMinute = Integer.parseInt(parsedItem2);
            }

            if (parsedItem3.length() != 0) {
                parsedSecond = Integer.parseInt(parsedItem3);
            }

            if (parsedItem1.length() == 0
                || parsedItem2.length() == 0
                || (timeWithSeconds && parsedItem3.length() == 0)
                || (parsedItem2.length() != 0 && parsedItem2.length() != itemSize)
                || (parsedItem3.length() != 0 && parsedItem3.length() != itemSize)) {
                parsedValid = false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }

        // checks the integer values
        if (parsedHour > lastHour || parsedMinute > lastMinute || parsedSecond > lastSecond) {
            // parsedValid = false;
            return false;
        }

        // saves the proposed time
        proposedValid =
            ((timeWithSeconds && parsedItems == itemCount)
             || (!timeWithSeconds && parsedItems == itemCount - 1))
            && parsedValid;
        proposedItems = parsedItems;
        proposedHour = parsedHour;
        proposedMinute = parsedMinute;
        proposedSecond = parsedSecond;

        // a time with no second part is a valid and complete time
        // if the 'with seconds' is false
        if (valueChangingInSet) {
            return proposedValid;
        }

        return true;
    }

    /**
     * Clears the data value. Actually it changes the hour, minute and second to zero.
     */
    public void clear() {

        setValue(SensibleContext.BLANK);
    }

    /**
     * Returns the time value as a <code>java.util.Date</code> object.
     *
     * @return the time as a <code>Date</code> object
     */
    public Date dateValue() {

        Calendar now = calendarValue();

        return now.getTime();
    }

    /**
     * Empty the proposed time properties.
     */
    private void emptyProposed() {

        proposedValid = true;
        proposedItems = 0;
        proposedHour = 0;
        proposedMinute = 0;
        proposedSecond = 0;
    }

    /**
     * Compares this <code>SensibleTime</code> object with the given integer values and returns
     * whether both object and values represent the same time.
     *
     * @param targetHour the target hour value
     * @param targetMinute the target minute value
     * @param targetSecond the target second value
     *
     * @return whether this and the given values represent the same time
     */
    public boolean equals(int targetHour, int targetMinute, int targetSecond) {

        return hour == targetHour
               && minute == targetMinute
               && second == targetSecond;
    }

    /**
     * Compares this <code>SensibleTime</code> object with the given
     * <code>java.util.Calendar</code> object and returns whether both objects represent the same
     * time.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same time
     */
    public boolean equals(Calendar target) {

        if (target == null) {
            return false;
        }

        return hour == target.get(target.HOUR_OF_DAY)
               && minute == target.get(target.MINUTE)
               && second == target.get(target.SECOND);
    }

    /**
     * Compares this <code>SensibleTime</code> object with the given object and returns whether
     * both objects represent the same time.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same time
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

        if (!(target instanceof SensibleTime)) {
            return false;
        }

        SensibleTime sensibleTarget = (SensibleTime) target;

        return this.hour == sensibleTarget.hour && this.minute == sensibleTarget.minute
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
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_SEPARATOR, null, timeSeparator);
    }

    /**
     * Formats a time using the given integer values and the object 'time with seconds' flag.
     *
     * @return the time formatted
     *
     * @param formatHour the time hour
     * @param formatMinute the time minute
     * @param formatSecond the time second
     *
     * @see SensibleTime#timeWithSeconds
     */
    private String formatTime(int formatHour, int formatMinute, int formatSecond) {

        final int tl = 2;

        StringBuilder sb = new StringBuilder();

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
     * Returns the <code>hour</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTime#hour
     * @see SensibleTime#setHour(int)
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     */
    public int getHour() {

        return hour;
    }

    /**
     * Returns the <code>minute</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTime#minute
     * @see SensibleTime#setMinute(int)
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     */
    public int getMinute() {

        return minute;
    }

    /**
     * Returns the <code>second</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTime#second
     * @see SensibleTime#setSecond(int)
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     */
    public int getSecond() {

        return second;
    }

    /**
     * Returns the time value as an array of integer values.
     *
     * @return the hour, minute and second as an array of integer values
     *
     * @see SensibleTime#setTime(int, int, int)
     * @see SensibleTime#hour
     * @see SensibleTime#getHour()
     * @see SensibleTime#setHour(int)
     * @see SensibleTime#minute
     * @see SensibleTime#getMinute()
     * @see SensibleTime#setMinute(int)
     * @see SensibleTime#second
     * @see SensibleTime#getSecond()
     * @see SensibleTime#setSecond(int)
     */
    public int[] getTime() {

        return new int[] {hour, minute, second};
    }

    /**
     * Returns the <code>timeSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTime#timeSeparator
     * @see SensibleTime#setTimeSeparator(char)
     */
    public char getTimeSeparator() {

        return timeSeparator;
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

        return hour * minute * second;
    }

    /**
     * Returns <code>true</code> if the data value is clear. Actually it checks that the hour,
     * minute and second are all equal to zero.
     *
     * @return whether the data value is clear
     */
    public boolean isClear() {

        return hour == 0 && minute == 0 && second == 0;
    }

    /**
     * Returns the <code>complete</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTime#complete
     * @see SensibleTime#setComplete(boolean)
     */
    public boolean isComplete() {

        return complete;
    }

    /**
     * Returns the <code>timeWithSeconds</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTime#timeWithSeconds
     * @see SensibleTime#setTimeWithSeconds(boolean)
     */
    public boolean isTimeWithSeconds() {

        return timeWithSeconds;
    }

    /**
     * Changes the <code>complete</code> property value and fires the property change event.
     *
     * @param newValue the new value
     *
     * @see SensibleTime#complete
     * @see SensibleTime#isComplete()
     */
    public void setComplete(boolean newValue) {

        boolean oldValue = complete;
        complete = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_COMPLETE, oldValue, newValue);
    }

    /**
     * Changes the <code>hour</code> property value.
     *
     * @param newHour the time new hour
     *
     * @see SensibleTime#hour
     * @see SensibleTime#getHour()
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     * @see SensibleTime#setValue(int, int, int)
     */
    public void setHour(int newHour) {

        setValue(newHour, minute, second);
    }

    /**
     * Changes the <code>minute</code> property value.
     *
     * @param newMinute the time new minute
     *
     * @see SensibleTime#minute
     * @see SensibleTime#getMinute()
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     * @see SensibleTime#setValue(int, int, int)
     */
    public void setMinute(int newMinute) {

        setValue(hour, newMinute, second);
    }

    /**
     * Changes the <code>second</code> property value.
     *
     * @param newSecond the time new second
     *
     * @see SensibleTime#second
     * @see SensibleTime#getSecond()
     * @see SensibleTime#getTime()
     * @see SensibleTime#setTime(int, int, int)
     * @see SensibleTime#setValue(int, int, int)
     */
    public void setSecond(int newSecond) {

        setValue(hour, minute, newSecond);
    }

    /**
     * Changes the time value using the given integer values.
     *
     * @param newHour the time new hour
     * @param newMinute the time new minute
     * @param newSecond the time new second
     *
     * @see SensibleTime#getTime()
     * @see SensibleTime#setValue(int, int, int)
     * @see SensibleTime#hour
     * @see SensibleTime#getHour()
     * @see SensibleTime#setHour(int)
     * @see SensibleTime#minute
     * @see SensibleTime#getMinute()
     * @see SensibleTime#setMinute(int)
     * @see SensibleTime#second
     * @see SensibleTime#getSecond()
     * @see SensibleTime#setSecond(int)
     */
    public void setTime(int newHour, int newMinute, int newSecond) {

        setValue(newHour, newMinute, newSecond);
    }

    /**
     * Changes the time value using the given <code>SensibleTime</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleTime#setValue(int, int, int)
     */
    public void setTime(SensibleTime newValue) {

        setValue(newValue.hour, newValue.minute, newValue.second);
    }

    /**
     * Changes the time value using the given <code>java.util.Calendar</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleTime#setValue(int, int, int)
     */
    public void setTime(Calendar newValue) {

        setValue(
            newValue.get(newValue.HOUR_OF_DAY),
            newValue.get(newValue.MINUTE),
            newValue.get(newValue.SECOND));
    }

    /**
     * Changes the time value using the given <code>String</code> object.
     *
     * @param newValue the string representation of the new value
     *
     * @see SensibleTime#setValue(String)
     */
    public void setTime(String newValue) {

        setValue(newValue);
    }

    /**
     * Changes the <code>timeSeparator</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTime#timeSeparator
     * @see SensibleTime#getTimeSeparator()
     * @see SensibleTime#DEFAULT_TIME_SEPARATOR
     */
    public void setTimeSeparator(char newValue) {

        char oldValue = timeSeparator;
        timeSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_SEPARATOR, oldValue, newValue);

        if (!isClear()) {
            setValue(hour, minute, second);
        }
    }

    /**
     * Changes the <code>timeWithSeconds</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTime#timeWithSeconds
     * @see SensibleTime#isTimeWithSeconds()
     */
    public void setTimeWithSeconds(boolean newValue) {

        boolean oldValue = timeWithSeconds;
        timeWithSeconds = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_TIME_WITH_SECONDS, oldValue, newValue);

        if (!isClear()) {
            setValue(hour, minute, second);
        }
    }

    /**
     * Changes the time value using the given integer values and fires the property change event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given integer
     * values does not represent a valid time.
     *
     * @param newHour the time new hour
     * @param newMinute the time new minute
     * @param newSecond the time new second
     *
     * @see SensibleTime#checkTime(int, int, int)
     * @see SensibleTime#formatTime(int, int, int)
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleTime#setComplete(boolean)
     */
    private void setValue(int newHour, int newMinute, int newSecond) {

        String oldValue = value;

        if (checkTime(newHour, newMinute, newSecond)) {
            this.hour = newHour;
            this.minute = newMinute;

            if (timeWithSeconds) {
                this.second = newSecond;
            } else {
                this.second = 0;
            }

            value = formatTime(hour, minute, second);

            valueChangingInSet = true;

            firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

            setValid(true);
            setComplete(true);

            valueChangingInSet = false;
        } else {
            throw new IllegalArgumentException(SensibleContext.getMessage("TIME_ERR_INVALID_TIME")); //$NON-NLS-1$
        }
    }

    /**
     * Returns the time as a string in HH:MM:SS format.
     *
     * @return the date as a string in HH:MM:SS format
     */
    public String toStringHMS() {

        final int tl = 2;

        StringBuilder sb = new StringBuilder();

        sb.append(StringToolkit.padLeft(Integer.toString(hour), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(minute), tl, ZERO_CHAR));
        sb.append(DEFAULT_TIME_SEPARATOR);
        sb.append(StringToolkit.padLeft(Integer.toString(second), tl, ZERO_CHAR));

        return sb.toString();
    }

    /**
     * Returns a string representation valid for sorting data. For the time data type this
     * representation is the value string in HH:MM:SS format.
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        return toString();
    }

    /**
     * Returns a string representation valid for sql language operations. For the time data type
     * this representation is the value string surrounded by simple quotes.
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return '\'' + toString() + '\'';
    }
}
