package deors.core.sensible;

/**
 * Definition for an integer data type.<br>
 *
 * This class manages integer values with a valid range.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleInteger
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -5238494845978124401L;

    /**
     * The property that contains the data value as an integer.
     *
     * @see SensibleInteger#getNumber()
     * @see SensibleInteger#setNumber(int)
     */
    private int number;

    /**
     * The minimum valid value. Numbers less than this value are not valid. Its default value is
     * <code>Integer.MIN_VALUE</code>.
     *
     * @see SensibleInteger#getMinValue()
     * @see SensibleInteger#setMinValue(int)
     * @see java.lang.Integer#MIN_VALUE
     */
    private int minValue = Integer.MIN_VALUE;

    /**
     * The maximum valid value. Numbers greater than this value are not valid. Its default value is
     * <code>Integer.MAX_VALUE</code>.
     *
     * @see SensibleInteger#getMaxValue()
     * @see SensibleInteger#setMaxValue(int)
     * @see java.lang.Integer#MAX_VALUE
     */
    private int maxValue = Integer.MAX_VALUE;

    /**
     * The "minValue" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_MIN_VALUE = "minValue"; //$NON-NLS-1$

    /**
     * The "maxValue" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_MAX_VALUE = "maxValue"; //$NON-NLS-1$

    /**
     * The "number" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_NUMBER = "number"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * Token used while parsing numbers.
     */
    private static final String PATTERN_MINUS = "-"; //$NON-NLS-1$

    /**
     * Token used while parsing numbers.
     */
    private static final String PATTERN_ZERO = "0"; //$NON-NLS-1$

    /**
     * Default constructor.
     */
    public SensibleInteger() {

        super();

        setNumber(SensibleContext.BLANK);
    }

    /**
     * Constructor that sets the valid range.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value.
     *
     * @param minValue the minimum valid value
     * @param maxValue the maximum valid value
     */
    public SensibleInteger(int minValue, int maxValue) {

        this();

        setRange(minValue, maxValue);
    }

    /**
     * Constructor that sets the number value using the given integer value.
     *
     * @param number the source value
     */
    public SensibleInteger(int number) {

        this();

        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given integer value if the number is between
     * the minimum and the maximum values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value or the given value is not in range.
     *
     * @param minValue the minimum valid value
     * @param maxValue the maximum valid value
     * @param number the source value
     */
    public SensibleInteger(int minValue, int maxValue, int number) {

        this();

        setRange(minValue, maxValue);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>SensibleInteger</code>
     * object value.
     *
     * @param number the source value
     */
    public SensibleInteger(SensibleInteger number) {

        this();

        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>SensibleInteger</code> object
     * value and the given range if the number is between the minimum and the maximum values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value or the given value is not in range.
     *
     * @param minValue the minimum valid value
     * @param maxValue the maximum valid value
     * @param number the source value
     */
    public SensibleInteger(int minValue, int maxValue, SensibleInteger number) {

        this();

        setRange(minValue, maxValue);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>Integer</code> object value.
     *
     * @param number the source value
     */
    public SensibleInteger(Integer number) {

        this();

        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>Integer</code> object value
     * and the given range if the number is between the minimum and the maximum values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value or the given value is not in range.
     *
     * @param minValue the minimum valid value
     * @param maxValue the maximum valid value
     * @param number the source value
     */
    public SensibleInteger(int minValue, int maxValue, Integer number) {

        this();

        setRange(minValue, maxValue);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>String</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * does not represent a valid integer number.
     *
     * @param source the string representation of the source value
     */
    public SensibleInteger(String source) {

        this();

        setNumber(source);
    }

    /**
     * Constructor that sets the number value using the given <code>String</code> object value and
     * the given range if the number is between the minimum and the maximum values.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value or the given value is not in range or the given string does
     * not represent a valid integer number.
     *
     * @param minValue the minimum valid value
     * @param maxValue the maximum valid value
     * @param source the string representation of the source value
     */
    public SensibleInteger(int minValue, int maxValue, String source) {

        this();

        setRange(minValue, maxValue);
        setNumber(source);
    }

    /**
     * Returns a new <code>SensibleInteger</code> object which value
     * is this object value plus the given value.
     *
     * @param augend the value to be added
     *
     * @return the <code>SensibleInteger</code> object with the new value
     */
    public SensibleInteger add(int augend) {

        SensibleInteger newValue = returnCopy();
        if (getNumber() >= getMaxValue()) {
            newValue.setNumber(getMaxValue());
        } else if (getNumber() < getMinValue()) {
            newValue.setNumber(getMinValue());
        } else {
            newValue.setNumber(getNumber() + augend);
        }
        return newValue;
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in an insertion action fired in a <code>SensibleTextField</code> object.
     * The method checks whether the proposed value matches the data type format. For the integer
     * data type an insert action is allowed if the final string represents a valid integer value
     * and there is no extra leading zero digits.
     *
     * @param offset the insert starting point
     * @param s the string to be inserted
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     */
    protected boolean allowInsert(int offset, String s) {

        if (s.equals(PATTERN_ZERO) && value.isEmpty()) {
            return true;
        }

        if (s.startsWith(PATTERN_ZERO)) {
            if (offset == 0 && !value.isEmpty()) {
                return false;
            }
            if (offset == 1 && (value.startsWith(PATTERN_MINUS) || value.startsWith(PATTERN_ZERO))) {
                return false;
            }
        }

        if (minValue >= 0 && s.equals(PATTERN_MINUS)) {
            return false;
        }

        if (value.isEmpty() && s.equals(PATTERN_MINUS)) {
            return true;
        }

        try {
            Integer.parseInt(value.substring(0, offset) + s + value.substring(offset));
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
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
     * @see SensibleInteger#allowInsert(int, String)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in a remove action fired in a <code>SensibleTextField</code> object. The
     * method checks whether the proposed value matches the data type format. For the integer data
     * type a remove action is always allowed.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     */
    protected boolean allowRemove(int offset, int length) {

        return true;
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
     * @see SensibleInteger#allowRemove(int, int)
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
     * An <code>IllegalArgumentException</code> exception is thrown if the given string
     * does not represent a valid integer value, or the new value is not in range and the change
     * value action is invoked from a setter method.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     */
    protected void changeValue(String newValue) {

        int oldNumber = number;
        String oldValue = value;
        boolean newValid = valid;

        if (newValue.isEmpty() || newValue.equals(PATTERN_MINUS)) {
            number = 0;
            value = newValue;
            newValid = 0 >= minValue && 0 <= maxValue;
        } else {
            try {
                int i = Integer.parseInt(newValue);

                if (valueChangingInSet
                    && (i < minValue || i > maxValue)) {

                    valueChangingInSet = false;
                    throw new IllegalArgumentException(
                        SensibleContext.getMessage("INT_ERR_VALUE_NOT_IN_RANGE")); //$NON-NLS-1$
                }

                number = i;
                value = Integer.toString(i);
                newValid = i >= minValue && i <= maxValue;
            } catch (NumberFormatException nfe) {
                if (valueChangingInSet) {
                    valueChangingInSet = false;
                }

                throw new IllegalArgumentException(
                    SensibleContext.getMessage("INT_ERR_INVALID_STRING"), nfe); //$NON-NLS-1$
            }
        }

        if (!newValue.equals(value)) {
            valueChangingInSet = true;
        }

        firePropertyChange(JAVA_BEAN_PROPERTY_NUMBER, oldNumber, number);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(newValid);

        if (!newValue.equals(value)) {
            valueChangingInSet = false;
        }
    }

    /**
     * Compares this <code>SensibleInteger</code> object with the given object and returns an
     * integer value as established in the <code>Comparable</code> interface. The method compares
     * the numeric values if the target object is an <code>Integer</code> object or a
     * <code>SensibleInteger</code> object while other target objects are compared in the super
     * class.
     *
     * @param target the target object
     *
     * @return a negative integer value if this object is less than the given object, zero if both
     *         objects represent the same value, and a positive integer value if this object is
     *         greater than the given object
     *
     * @see SensibleDataType#compareTo(Object)
     */
    public int compareTo(Object target) {

        if (target instanceof Integer) {
            return Integer.compare(number, ((Integer) target).intValue());
        } else if (target instanceof SensibleInteger) {
            return Integer.compare(number, ((SensibleInteger) target).number);
        }

        return super.compareTo(target);
    }

    /**
     * Compares this <code>SensibleInteger</code> object with the given integer value and returns
     * whether both represent the same integer value.
     *
     * @param target the target value
     *
     * @return whether this and the given value represent the same integer value
     */
    public boolean equals(int target) {

        return this.number == target;
    }

    /**
     * Compares this <code>SensibleInteger</code> object with the given <code>Integer</code>
     * object and returns whether both objects represent the same integer value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same integer value
     */
    public boolean equals(Integer target) {

        if (target == null) {
            return false;
        }

        return this.number == target.intValue();
    }

    /**
     * Compares this <code>SensibleInteger</code> object with the given <code>String</code>
     * object and returns whether both objects represent the same integer value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same integer value
     */
    public boolean equals(String target) {

        if (target == null) {
            return false;
        }

        return this.value.equals(target);
    }

    /**
     * Compares this <code>SensibleInteger</code> object with the given object and returns whether
     * both objects represent the same integer value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same integer value
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

        if (!(target instanceof SensibleInteger)) {
            return false;
        }

        SensibleInteger sensibleTarget = (SensibleInteger) target;

        return this.number == sensibleTarget.number;
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        super.firePropertyChangeEvents();

        firePropertyChange(JAVA_BEAN_PROPERTY_NUMBER, null, number);
        firePropertyChange(JAVA_BEAN_PROPERTY_MIN_VALUE, null, minValue);
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_VALUE, null, maxValue);
    }

    /**
     * Returns the <code>minValue</code> property value.
     *
     * @return the property value
     *
     * @see SensibleInteger#minValue
     * @see SensibleInteger#setMinValue(int)
     */
    public int getMinValue() {

        return minValue;
    }

    /**
     * Returns the <code>maxValue</code> property value.
     *
     * @return the property value
     *
     * @see SensibleInteger#maxValue
     * @see SensibleInteger#setMaxValue(int)
     */
    public int getMaxValue() {

        return maxValue;
    }

    /**
     * Returns the <code>number</code> property value.
     *
     * @return the property value
     *
     * @see SensibleInteger#number
     * @see SensibleInteger#intValue()
     * @see SensibleInteger#integerValue()
     * @see SensibleInteger#setNumber(int)
     */
    public int getNumber() {

        return number;
    }

    /**
     * Returns a hash code value for the object.
     *
     * The hash code is the integer value.
     *
     * @return a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return number;
    }

    /**
     * Returns the <code>number</code> property value.
     *
     * @return the property value
     *
     * @see SensibleInteger#number
     * @see SensibleInteger#getNumber()
     * @see SensibleInteger#integerValue()
     * @see SensibleInteger#setNumber(int)
     */
    public int intValue() {

        return number;
    }

    /**
     * Returns the <code>number</code> property value.
     *
     * @return the property value
     *
     * @see SensibleInteger#number
     * @see SensibleInteger#getNumber()
     * @see SensibleInteger#intValue()
     * @see SensibleInteger#setNumber(int)
     */
    public int integerValue() {

        return number;
    }

    /**
     * Returns <code>true</code> if the data value is clear. Actually it checks whether the data
     * value is equal to zero.
     *
     * @return whether the data value is clear
     */
    public boolean isClear() {

        return number == 0;
    }

    /**
     * Returns a copy of this SensibleInteger object.
     *
     * @return a copy of this object
     */
    @Override
    public SensibleInteger returnCopy() {

        return new SensibleInteger(
            getMinValue(),
            getMaxValue(),
            getNumber());
    }

    /**
     * Changes the <code>minValue</code> property value and fires the property change event. It
     * checks if the number is in range and sets the valid property.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value.
     *
     * @param newValue the property new value
     *
     * @see SensibleInteger#minValue
     * @see SensibleInteger#getMinValue()
     */
    public void setMinValue(int newValue) {

        if (newValue > maxValue) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("INT_ERR_INVALID_LIMIT")); //$NON-NLS-1$
        }

        int oldValue = minValue;
        minValue = newValue;

        firePropertyChange(JAVA_BEAN_PROPERTY_MIN_VALUE, oldValue, minValue);

        setValid(number > minValue);
    }

    /**
     * Changes the <code>maxValue</code> property value and fires the property change event. It
     * checks if the number is in range and sets the valid property.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value.
     *
     * @param newValue the property new value
     *
     * @see SensibleInteger#maxValue
     * @see SensibleInteger#getMaxValue()
     */
    public void setMaxValue(int newValue) {

        if (newValue < minValue) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("INT_ERR_INVALID_LIMIT")); //$NON-NLS-1$
        }

        int oldValue = maxValue;
        maxValue = newValue;

        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_VALUE, oldValue, maxValue);

        setValid(number < maxValue);
    }

    /**
     * Changes the <code>number</code> property value and fires the property change event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value is
     * not in range.
     *
     * @param newValue the property new value
     *
     * @see SensibleInteger#number
     * @see SensibleInteger#getNumber()
     * @see SensibleInteger#setValue(int)
     */
    public void setNumber(int newValue) {

        setValue(newValue);
    }

    /**
     * Changes the data value using the given <code>SensibleInteger</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value is
     * not in range.
     *
     * @param newValue the new value
     *
     * @see SensibleInteger#setValue(int)
     */
    public void setNumber(SensibleInteger newValue) {

        setValue(newValue.number);
    }

    /**
     * Changes the data value using the given <code>Integer</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value is
     * not in range.
     *
     * @param newValue the new value
     *
     * @see SensibleInteger#setValue(int)
     */
    public void setNumber(Integer newValue) {

        setValue(newValue.intValue());
    }

    /**
     * Changes the data value using the given <code>String</code> object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value is
     * not in range.
     *
     * @param newValue the string representation of the new value
     *
     * @see SensibleDataType#setValue(String)
     */
    public void setNumber(String newValue) {

        setValue(newValue);
    }

    /**
     * Changes the valid range and fires the property change events. It checks if the number is in
     * range and sets the valid property.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the minimum value
     * is greater than the maximum value.
     *
     * @param newMinValue the new minimum value
     * @param newMaxValue the new maximum value
     */
    public void setRange(int newMinValue, int newMaxValue) {

        if (newMinValue > newMaxValue) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("INT_ERR_INVALID_LIMIT")); //$NON-NLS-1$
        }

        int oldMinValue = minValue;
        minValue = newMinValue;

        firePropertyChange(JAVA_BEAN_PROPERTY_MIN_VALUE, oldMinValue, minValue);

        int oldMaxValue = maxValue;
        maxValue = newMaxValue;

        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_VALUE, oldMaxValue, maxValue);

        setValid(!(number < minValue || number > maxValue));
    }

    /**
     * Changes the data value using the given integer value and fires the property change event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value is
     * not in range.
     *
     * @param newValue the new value
     */
    private void setValue(int newValue) {

        if (newValue < minValue || newValue > maxValue) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("INT_ERR_VALUE_NOT_IN_RANGE")); //$NON-NLS-1$
        }

        int oldNumber = number;
        String oldValue = value;

        number = newValue;
        value = Integer.toString(newValue);

        valueChangingInSet = true;

        firePropertyChange(JAVA_BEAN_PROPERTY_NUMBER, oldNumber, number);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(true);

        valueChangingInSet = false;
    }

    /**
     * Returns a new <code>SensibleInteger</code> object which value
     * is this object value minus the given value.
     *
     * @param subtraend the value to be subtracted
     *
     * @return the <code>SensibleInteger</code> object with the new value
     */
    public SensibleInteger subtract(int subtraend) {

        SensibleInteger newValue = returnCopy();
        if (getNumber() > getMaxValue()) {
            newValue.setNumber(getMaxValue());
        } else if (getNumber() <= getMinValue()) {
            newValue.setNumber(getMinValue());
        } else {
            newValue.setNumber(getNumber() - subtraend);
        }
        return newValue;
    }

    /**
     * Returns a string representation valid for sorting data. For the integer data type this
     * representation is the value string padded to right in a field of 11 character width.
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        final int size = 11;

        char[] temp = (toString().length() == size) ? null : new char[size - toString().length()];

        if (temp != null) {
            for (int i = 0; i < temp.length; i++) {
                temp[i] = ' ';
            }
        }

        return (toString().length() == size) ? toString() : (new String(temp)) + toString();
    }

    /**
     * Returns a string representation valid for sql language operations. For the integer data type
     * this representation is the value string.
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return toString();
    }
}
