package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

/**
 * Definition for a boolean data type.<br>
 *
 * This class manages boolean values.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleBoolean
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -2959917551393893583L;

    /**
     * The property that contains the data value as a boolean value.
     *
     * @see SensibleBoolean#isFlag()
     * @see SensibleBoolean#setFlag(boolean)
     */
    private boolean flag;

    /**
     * The "flag" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_FLAG = "flag"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * String representation of a <code>true</code> value. Configurable in the properties file
     * <code>boolean.trueAsString</code>. Default value is <code>true</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    public static final String BOOLEAN_TRUE_AS_STRING =
        getConfigurationProperty("boolean.trueAsString", "true"); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * String representation of a <code>false</code> value. Configurable in the properties file
     * <code>boolean.falseAsString</code>. Default value is <code>false</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    public static final String BOOLEAN_FALSE_AS_STRING =
        getConfigurationProperty("boolean.falseAsString", "false"); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * Default constructor.
     */
    public SensibleBoolean() {

        super();

        setFlag(false);
    }

    /**
     * Constructor that sets the boolean value.
     *
     * @param flag the source value
     */
    public SensibleBoolean(boolean flag) {

        this();

        setFlag(flag);
    }

    /**
     * Constructor that sets the boolean value using the given <code>SensibleBoolean</code>
     * object.
     *
     * @param flag the source value
     */
    public SensibleBoolean(SensibleBoolean flag) {

        this();

        setFlag(flag);
    }

    /**
     * Constructor that sets the boolean value using the given <code>Boolean</code> object.
     *
     * @param flag the source value
     */
    public SensibleBoolean(Boolean flag) {

        this();

        setFlag(flag);
    }

    /**
     * Constructor that sets the boolean value using the given <code>String</code> object. The
     * strings used to initialize the flag are the constant fields
     * <code>BOOLEAN_TRUE_AS_STRING</code> and <code>BOOLEAN_FALSE_AS_STRING</code>. The string
     * comparision is performed ignoring the case.<br>
     *
     * A <code>java.lang.IllegalArgumentException</code> exception is thrown if the string does
     * not represent a valid boolean value.
     *
     * @param source the string object used to initialize the flag
     *
     * @see SensibleBoolean#BOOLEAN_TRUE_AS_STRING
     * @see SensibleBoolean#BOOLEAN_FALSE_AS_STRING
     */
    public SensibleBoolean(String source) {

        this();

        setFlag(source);
    }

    /**
     * Constructor that sets the boolean value using the given integer value. If the integer value
     * is 0, the flag will be <code>false</code>, otherwise it will be <code>true</code> (as in
     * C language).
     *
     * @param source the integer value used to initialize the flag
     */
    public SensibleBoolean(int source) {

        this();

        setFlag(source);
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in an insertion action fired in a <code>SensibleTextField</code> object.
     * The method checks whether the proposed value matches the data type format. For the boolean
     * data type an insert action is never allowed because a boolean value can not be used in a text
     * field.
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

        return false;
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
     * @see SensibleBoolean#allowInsert(int, String)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        return false;
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in a remove action fired in a <code>SensibleTextField</code> object. The
     * method checks whether the proposed value matches the data type format. For the boolean data
     * type a remove action is never allowed because a boolean value can not be used in a text
     * field.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     */
    protected boolean allowRemove(int offset, int length) {

        return false;
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
     * @see SensibleBoolean#allowRemove(int, int)
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
     * <code>setValid(boolean)</code> method to change the data value valid state. For the boolean
     * data type, the given string is compared with the strings in the constant fields
     * <code>BOOLEAN_TRUE_AS_STRING</code> and <code>BOOLEAN_FALSE_AS_STRING</code>. The
     * comparison is performed ignoring the case. The boolean data type is always valid.<br>
     *
     * A <code>java.lang.IllegalArgumentException</code> exception is thrown if the string does
     * not represent a valid boolean value.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleBoolean#BOOLEAN_TRUE_AS_STRING
     * @see SensibleBoolean#BOOLEAN_FALSE_AS_STRING
     */
    protected void changeValue(String newValue) {

        boolean oldFlag = flag;
        String oldValue = value;

        if (newValue.equalsIgnoreCase(BOOLEAN_TRUE_AS_STRING)) {
            flag = true;
        } else if (newValue.equalsIgnoreCase(BOOLEAN_FALSE_AS_STRING)) {
            flag = false;
        } else {
            valueChangingInSet = false;
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BOOL_ERR_INVALID_STRING")); //$NON-NLS-1$
        }

        value = Boolean.toString(flag);

        if (oldFlag != flag) {
            valueChangingInSet = true;
        }

        firePropertyChange(JAVA_BEAN_PROPERTY_FLAG, oldFlag, flag);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(true);

        if (oldFlag != flag) {
            valueChangingInSet = false;
        }
    }

    /**
     * Returns the <code>flag</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBoolean#flag
     * @see SensibleBoolean#isFlag()
     * @see SensibleBoolean#setFlag(boolean)
     */
    public boolean booleanValue() {

        return flag;
    }

    /**
     * Clears the data value. Actually it changes the flag value to <code>false</code>.
     */
    public void clear() {

        setFlag(false);
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given boolean value and returns
     * whether both represent the same boolean value.
     *
     * @param target the target value
     *
     * @return whether this and the given value represent the same boolean value
     */
    public boolean equals(boolean target) {

        return this.flag == target;
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given <code>Boolean</code>
     * object and returns whether both objects represent the same boolean value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same boolean value
     */
    public boolean equals(Boolean target) {

        if (target == null) {
            return false;
        }

        return this.flag == target.booleanValue();
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given <code>String</code>
     * object and returns whether both objects represent the same boolean value. The strings used to
     * convert the given string to a boolean value are the constant fields
     * <code>BOOLEAN_TRUE_AS_STRING</code> and <code>BOOLEAN_FALSE_AS_STRING</code>. The string
     * comparision is performed ignoring the case.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same boolean value
     *
     * @see SensibleBoolean#BOOLEAN_TRUE_AS_STRING
     * @see SensibleBoolean#BOOLEAN_FALSE_AS_STRING
     */
    public boolean equals(String target) {

        if (target == null) {
            return false;
        }

        if (target.equalsIgnoreCase(BOOLEAN_TRUE_AS_STRING)) {
            return this.flag;
        } else if (target.equalsIgnoreCase(BOOLEAN_FALSE_AS_STRING)) {
            return !this.flag;
        } else {
            return false;
        }
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given integer value and returns
     * whether both represent the same boolean value. If the integer value is 0, the flag will be
     * <code>false</code>, otherwise it will be <code>true</code> (as in C language).
     *
     * @param target the target value
     *
     * @return whether this and the given value represent the same boolean value
     */
    public boolean equals(int target) {

        return (target == 0 && !this.flag) || (target != 0 && this.flag);
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given
     * <code>SensibleInteger</code> object and returns whether both represent the same boolean
     * value. If the integer value is 0, the flag will be <code>false</code>, otherwise it will
     * be <code>true</code> (as in C language).
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same boolean value
     */
    public boolean equals(SensibleInteger target) {

        if (target == null) {
            return false;
        }

        return (target.intValue() == 0 && !this.flag) || (target.intValue() != 0 && this.flag);
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given <code>Integer</code>
     * object and returns whether both represent the same boolean value. If the integer value is 0,
     * the flag will be <code>false</code>, otherwise it will be <code>true</code> (as in C
     * language).
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same boolean value
     */
    public boolean equals(Integer target) {

        if (target == null) {
            return false;
        }

        return (target.intValue() == 0 && !this.flag) || (target.intValue() != 0 && this.flag);
    }

    /**
     * Compares this <code>SensibleBoolean</code> object with the given object and returns whether
     * both objects represent the same boolean value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same boolean value
     *
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object target) {

        if (this == target) {
            return true;
        }

        if (!(target instanceof SensibleBoolean)) {
            return false;
        }

        SensibleBoolean sensibleTarget = (SensibleBoolean) target;

        return this.flag == sensibleTarget.flag;
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        super.firePropertyChangeEvents();

        firePropertyChange(JAVA_BEAN_PROPERTY_FLAG, null, flag);
    }

    /**
     * Returns a hash code value for the object.
     *
     * The hash code is <code>0</code> if the flag is <code>false</code> and <code>-1</code>
     * if the flag is <code>true</code>.
     *
     * @return a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return this.flag ? -1 : 0;
    }

    /**
     * Returns the <code>flag</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBoolean#flag
     * @see SensibleBoolean#booleanValue()
     * @see SensibleBoolean#setFlag(boolean)
     */
    public boolean isFlag() {

        return flag;
    }

    /**
     * Returns <code>true</code> if the data value is clear. A boolean value is never clear so the
     * method returns always <code>false</code>.
     *
     * @return boolean
     */
    public boolean isClear() {

        return false;
    }

    /**
     * Changes the <code>flag</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleBoolean#flag
     * @see SensibleBoolean#isFlag()
     * @see SensibleBoolean#setValue(boolean)
     */
    public void setFlag(boolean newValue) {

        setValue(newValue);
    }

    /**
     * Changes the data value using the given <code>SensibleBoolean</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleBoolean#setValue(boolean)
     */
    public void setFlag(SensibleBoolean newValue) {

        setValue(newValue.booleanValue());
    }

    /**
     * Changes the data value using the given <code>Boolean</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleBoolean#setValue(boolean)
     */
    public void setFlag(Boolean newValue) {

        setValue(newValue.booleanValue());
    }

    /**
     * Changes the data value using the given <code>String</code> object. The strings used to
     * convert the given string to a boolean value are the constant fields
     * <code>BOOLEAN_TRUE_AS_STRING</code> and <code>BOOLEAN_FALSE_AS_STRING</code>. The string
     * comparision is performed ignoring the case.<br>
     *
     * A <code>java.lang.IllegalArgumentException</code> exception is thrown if the string does
     * not represent a valid boolean value.
     *
     * @param newValue the string representation of the new value
     *
     * @see SensibleBoolean#setValue(boolean)
     * @see SensibleBoolean#BOOLEAN_TRUE_AS_STRING
     * @see SensibleBoolean#BOOLEAN_FALSE_AS_STRING
     */
    public void setFlag(String newValue) {

        if (newValue.equalsIgnoreCase(BOOLEAN_TRUE_AS_STRING)) {
            setValue(true);
        } else if (newValue.equalsIgnoreCase(BOOLEAN_FALSE_AS_STRING)) {
            setValue(false);
        } else {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BOOL_ERR_INVALID_STRING")); //$NON-NLS-1$
        }
    }

    /**
     * Changes the data value using the given integer value. If the integer value is 0, the flag
     * will be <code>false</code>, otherwise it will be <code>true</code> (as in C language).
     *
     * @param newValue the new value
     *
     * @see SensibleBoolean#setValue(boolean)
     */
    public void setFlag(int newValue) {

        if (newValue == 0) {
            setValue(false);
        } else {
            setValue(true);
        }
    }

    /**
     * Changes the data value using the given integer value and fires the property change event.
     *
     * @param newValue the new value
     */
    private void setValue(boolean newValue) {

        boolean oldFlag = flag;
        String oldValue = value;

        flag = newValue;
        value = Boolean.toString(flag);

        valueChangingInSet = true;

        firePropertyChange(JAVA_BEAN_PROPERTY_FLAG, oldFlag, flag);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(true);

        valueChangingInSet = false;
    }

    /**
     * Returns the data value as a string using the properties <code>BOOLEAN_TRUE_AS_STRING</code>
     * and <code>BOOLEAN_FALSE_AS_STRING</code>.
     *
     * @return a string representation of the data value
     *
     * @see SensibleBoolean#BOOLEAN_TRUE_AS_STRING
     * @see SensibleBoolean#BOOLEAN_FALSE_AS_STRING
     */
    public String toString() {

        return flag ? BOOLEAN_TRUE_AS_STRING : BOOLEAN_FALSE_AS_STRING;
    }

    /**
     * Returns a string representation valid for sorting data. For the boolean data type this
     * representation is the value string (always <code>"true"</code> or <code>"false"</code>).
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        return value;
    }

    /**
     * Returns a string representation valid for sql language operations. For the boolean data type
     * this representation is the value string (always <code>"true"</code> or <code>"false"</code>).
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return value;
    }
}
