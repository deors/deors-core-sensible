package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.util.Locale;

import javax.swing.text.BadLocationException;

import deors.core.commons.StringToolkit;

/**
 * Definition for a string data type.<br>
 *
 * This class manages strings with a maximum length.<br>
 *
 * @author deors
 * @version 1.0
 */
@SuppressWarnings("PMD.CloneMethodMustImplementCloneable")
public final class SensibleString
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -9094516955662635248L;

    /**
     * The maximum length of the string. A value of <code>-1</code> (default) means there is no
     * limit.
     *
     * @see SensibleString#getMaxLength()
     * @see SensibleString#setMaxLength(int)
     */
    private int maxLength = -1;

    /**
     * Casing mode.
     *
     * @see SensibleString#getCasingMode()
     * @see SensibleString#setCasingMode(String)
     * @see SensibleString#NO_CASING
     * @see SensibleString#TO_LOWER_CASE
     * @see SensibleString#TO_UPPER_CASE
     * @see SensibleString#CAPITALIZE
     */
    private String casingMode = DEFAULT_CASING_MODE;

    /**
     * Allowed characters.
     *
     * @see SensibleString#getAllowedCharacters()
     * @see SensibleString#setAllowedCharacters(String)
     */
    private String allowedCharacters = SensibleContext.BLANK;

    /**
     * The string casing is not altered.
     */
    public static final String NO_CASING = "NO"; //$NON-NLS-1$

    /**
     * The string is lower cased.
     */
    public static final String TO_LOWER_CASE = "LC"; //$NON-NLS-1$

    /**
     * The string is upper cased.
     */
    public static final String TO_UPPER_CASE = "UC"; //$NON-NLS-1$

    /**
     * The string is capitalized.
     */
    public static final String CAPITALIZE = "CP"; //$NON-NLS-1$

    /**
     * Base 10 decimal, used to calculate upper limit in strings which value can be
     * converted into an integer value.
     */
    private static final int BASE_TEN = 10;

    /**
     * The "allowedCharacters" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_ALLOWED_CHARACTERS = "allowedCharacters"; //$NON-NLS-1$

    /**
     * The "casingMode" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_CASING_MODE = "casingMode"; //$NON-NLS-1$

    /**
     * The "maxLength" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_MAX_LENGTH = "maxLength"; //$NON-NLS-1$

    /**
     * The "string" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_STRING = "string"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * The default casing mode. Configurable in the properties file using the key
     * <code></code>. Default value is <code></code>.
     * The valid values are <code>NO_CASING</code>, <code>TO_LOWER_CASE</code>,
     * <code>TO_UPPER_CASE</code> and <code>CAPITALIZE</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     * @see SensibleString#NO_CASING
     * @see SensibleString#TO_LOWER_CASE
     * @see SensibleString#TO_UPPER_CASE
     * @see SensibleString#CAPITALIZE
     */
    private static final String DEFAULT_CASING_MODE =
        getConfigurationProperty("format.defaultCasingMode", NO_CASING, //$NON-NLS-1$
            new String[] {NO_CASING, TO_LOWER_CASE, TO_UPPER_CASE, CAPITALIZE});

    /**
     * Default constructor.
     */
    @SuppressWarnings("PMD.StringInstantiation")
    public SensibleString() {

        super();

        controlsDocument = true;

        setString(new String());
    }

    /**
     * Constructor that sets the maximum length of the string.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the maximum length
     * is less than <code>-1</code> or zero.
     *
     * @param maxLength the maximum length
     */
    public SensibleString(int maxLength) {

        this();

        setMaxLength(maxLength);
    }

    /**
     * Constructor that sets the string value using the given <code>String</code> object value.
     *
     * @param source the source string
     */
    public SensibleString(String source) {

        this();

        setString(source);
    }

    /**
     * Constructor that sets the string value using the given <code>String</code> object value and
     * the given maximum length. If the given string length is greater than the given maximum length
     * and the maximum length is not equal to <code>-1</code>, the final string is truncated.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the maximum length
     * is less than <code>-1</code> or zero.
     *
     * @param maxLength the maximum length
     * @param source the source string
     */
    public SensibleString(int maxLength, String source) {

        this();

        setMaxLength(maxLength);
        setString(source);
    }

    /**
     * Constructor that sets the string value using the given <code>SensibleString</code>
     * object value.
     *
     * @param source the source string
     */
    public SensibleString(SensibleString source) {

        this();

        setString(source.value);
    }

    /**
     * Constructor that sets the string value using the given <code>SensibleString</code> object
     * value and the given maximum length. If the given string length is greater than the given
     * maximum length and the maximum length is not equal to <code>-1</code>, the final string is
     * truncated.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the maximum length
     * is less than <code>-1</code> or zero.
     *
     * @param maxLength the maximum length
     * @param source the source string
     */
    public SensibleString(int maxLength, SensibleString source) {

        this();

        setMaxLength(maxLength);
        setString(source.value);
    }

    /**
     * Returns a new <code>SensibleString</code> object cloned from this
     * object which value is this object value plus the given value only
     * if the string value can be converted into an integer value.
     *
     * @param augend the value to be added
     *
     * @return the <code>SensibleString</code> object with the new integer value
     */
    public SensibleDataType add(int augend) {

        try {
            SensibleString newValue = (SensibleString) this.clone();
            try {
                int convertedValue = Integer.parseInt(getString());
                if (getMaxLength() == -1) {
                    newValue.setString(Integer.toString(convertedValue + augend));
                } else if (convertedValue + augend < Math.pow(BASE_TEN, getMaxLength()) - 1) {
                    newValue.setString(Integer.toString(convertedValue + augend));
                }
            } catch (NumberFormatException nfe) {
                nfe = null;
            }
            return newValue;
        } catch (CloneNotSupportedException cnse) {
            return null;
        }
    }

    /**
     * No implementation provided for this method.
     *
     * @param offset ignored
     * @param s ignored
     *
     * @return <code>false</code>
     *
     * @see SensibleString#allowInsert(int, String, SensibleTextField,
     *                                 SensibleTextField.SensibleTextFieldDocument)
     */
    protected boolean allowInsert(int offset, String s) {

        return false;
    }

    /**
     * Used internally when the type controls the document to check whether the data type allows a
     * change in an insertion action fired in a <code>SensibleTextField</code> object. The method
     * checks whether the proposed value matches the data type format. For the string data type an
     * insert action is allowed if the resulting string length is less than or equal to the maximum
     * string length. The casing mode is applied before changing the data value.
     *
     * @param offset the insert starting point
     * @param s the string to be inserted
     * @param textField the text field
     * @param document the text field document
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        if (maxLength != -1 && value.length() + s.length() > maxLength) {
            return false;
        }

        if (allowedCharacters != null && allowedCharacters.length() != 0) {
            for (int i = 0; i < s.length(); i++) {
                if (allowedCharacters.indexOf(s.charAt(i)) == -1) {
                    return false;
                }
            }
        }

        StringBuffer sb = new StringBuffer();

        if (offset == 0) {
            sb.append(s);
            sb.append(value);
        } else {
            sb.append(value.substring(0, offset));
            sb.append(s);
            sb.append(value.substring(offset));
        }

        String newValue = sb.toString();

        if (casingMode.equals(TO_LOWER_CASE)) {
            newValue = newValue.toLowerCase(Locale.getDefault());
        } else if (casingMode.equals(TO_UPPER_CASE)) {
            newValue = newValue.toUpperCase(Locale.getDefault());
        } else if (casingMode.equals(CAPITALIZE)) {
            newValue = StringToolkit.capitalize(newValue);
        }

        try {
            document.removeFromParent(0, value.length());
            if (textField.isPasswordField()) {
                String echos = StringToolkit.repeatCharacter(
                    textField.getEchoCharacter(), newValue.length());
                document.insertStringFromParent(0, echos, null);
            } else {
                document.insertStringFromParent(0, newValue, null);
            }
        } catch (BadLocationException bde) {
            return false;
        }

        changeValue(newValue);

        textField.setCaretPosition(offset + s.length());

        return true;
    }

    /**
     * No implementation provided for this method.
     *
     * @param offset ignored
     * @param length ignored
     *
     * @return <code>false</code>
     *
     * @see SensibleString#allowRemove(int, int, SensibleTextField,
     *                                 SensibleTextField.SensibleTextFieldDocument)
     */
    protected boolean allowRemove(int offset, int length) {

        return false;
    }

    /**
     * Used internally when the type controls the document to check whether the data type allows a
     * change in a remove action fired in a <code>SensibleTextField</code> object. The method
     * checks whether the proposed value matches the data type format. For the string data type a
     * remove action is always allowed. The casing mode is applied before changing the data value.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     * @param textField the text field
     * @param document the text field document
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     */
    protected boolean allowRemove(int offset, int length, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        StringBuffer sb = new StringBuffer();

        sb.append(value.substring(0, offset));
        sb.append(value.substring(offset + length));

        String newValue = sb.toString();

        if (casingMode.equals(TO_LOWER_CASE)) {
            newValue = newValue.toLowerCase(Locale.getDefault());
        } else if (casingMode.equals(TO_UPPER_CASE)) {
            newValue = newValue.toUpperCase(Locale.getDefault());
        } else if (casingMode.equals(CAPITALIZE)) {
            newValue = StringToolkit.capitalize(newValue);
        }

        try {
            document.removeFromParent(0, value.length());
            if (textField.isPasswordField()) {
                String echos = StringToolkit.repeatCharacter(
                    textField.getEchoCharacter(), newValue.length());
                document.insertStringFromParent(0, echos, null);
            } else {
                document.insertStringFromParent(0, newValue, null);
            }
        } catch (BadLocationException bde) {
            return false;
        }

        changeValue(newValue);

        textField.setCaretPosition(offset);

        return true;
    }

    /**
     * Used internally to change the data value. This method uses the given string to change the
     * data value. If the new value is valid, fires the property change events, one for the
     * <code>value</code> property and another for the property in which the particular data value
     * is stored (if this property exists). If necessary, the implementation has to invoke the
     * <code>setValid(boolean)</code> method to change the data value valid state. For the string
     * data type, if the new string length is greater than the maximum string length, the final
     * string is truncated. If the data is required, the new value will be valid if the string
     * length is greater than zero. If the data is not required, the value is always valid. The
     * casing mode is applied before changing the data value.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     */
    protected void changeValue(String newValue) {

        String oldValue = value;

        String tempValue = newValue;

        if (maxLength != -1 && tempValue.length() > maxLength) {
            tempValue = tempValue.substring(0, maxLength);
        }

        if (allowedCharacters != null && allowedCharacters.length() != 0) {
            for (int i = 0; i < newValue.length(); i++) {
                if (allowedCharacters.indexOf(newValue.charAt(i)) == -1) {
                    if (valueChangingInSet) {
                        valueChangingInSet = false;
                    }

                    throw new IllegalArgumentException(
                        SensibleContext.getMessage("STR_ERR_INVALID_STRING")); //$NON-NLS-1$
                }
            }
        }

        if (casingMode.equals(TO_LOWER_CASE)) {
            tempValue = tempValue.toLowerCase(Locale.getDefault());
        } else if (casingMode.equals(TO_UPPER_CASE)) {
            tempValue = tempValue.toUpperCase(Locale.getDefault());
        } else if (casingMode.equals(CAPITALIZE)) {
            tempValue = StringToolkit.capitalize(tempValue);
        }

        value = tempValue;

        if (!newValue.equals(tempValue)) {
            valueChangingInSet = true;
        }

        firePropertyChange(JAVA_BEAN_PROPERTY_STRING, oldValue, value);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(required ? tempValue.length() > 0 : true);

        if (!newValue.equals(tempValue)) {
            valueChangingInSet = false;
        }
    }

    /**
     * Returns a clone of this object.<br>
     *
     * A <code>java.lang.InternalError</code> error is thrown if cloning is not supported.
     *
     * @return a clone of this object
     *
     * @throws CloneNotSupportedException if the clone could not be done
     *
     * @see SensibleDataType#clone()
     * @see SensibleString#setMaxLength(int)
     */
    public SensibleString clone()
        throws CloneNotSupportedException {

        SensibleString obj = (SensibleString) super.clone();

        obj.setMaxLength(getMaxLength());
        obj.setCasingMode(getCasingMode());

        return obj;
    }

    /**
     * Compares this <code>SensibleString</code> object with the given <code>String</code>
     * object and returns whether both objects represent the same string value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same string value
     */
    public boolean equals(String target) {

        return value.equals(target);
    }

    /**
     * Compares this <code>SensibleString</code> object with the given object and returns whether
     * both objects represent the same string value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same string value
     *
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object target) {

        if (this == target) {
            return true;
        }

        if (!(target instanceof SensibleString)) {
            return false;
        }

        SensibleString sensibleTarget = (SensibleString) target;

        return this.value.equals(sensibleTarget.value);
    }

    /**
     * Compares this <code>SensibleString</code> object with the given <code>String</code>
     * object ignoring the case and returns whether both objects represent the same string value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same string value ignoring the case
     */
    public boolean equalsIgnoreCase(String target) {

        return value.equalsIgnoreCase(target);
    }

    /**
     * Compares this <code>SensibleString</code> object with the given <code>SensibleString</code>
     * object ignoring the case and returns whether both objects represent the same string value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same string value ignoring the case
     */
    public boolean equalsIgnoreCase(SensibleString target) {

        return value.equalsIgnoreCase(target.value);
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        super.firePropertyChangeEvents();

        firePropertyChange(JAVA_BEAN_PROPERTY_STRING, null, value);
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_LENGTH, null, maxLength);
        firePropertyChange(JAVA_BEAN_PROPERTY_CASING_MODE, null, casingMode);
        firePropertyChange(JAVA_BEAN_PROPERTY_ALLOWED_CHARACTERS, null, allowedCharacters);
    }

    /**
     * Returns the <code>allowedCharacters</code> property value.
     *
     * @return the property value
     *
     * @see SensibleString#allowedCharacters
     * @see SensibleString#setAllowedCharacters(String)
     */
    public String getAllowedCharacters() {

        return allowedCharacters;
    }

    /**
     * Returns the <code>casingMode</code> property value.
     *
     * @return the property value
     *
     * @see SensibleString#casingMode
     * @see SensibleString#setCasingMode(String)
     */
    public String getCasingMode() {

        return casingMode;
    }

    /**
     * Returns the <code>maxLength</code> property value.
     *
     * @return the property value
     *
     * @see SensibleString#maxLength
     * @see SensibleString#setMaxLength(int)
     */
    public int getMaxLength() {

        return maxLength;
    }

    /**
     * Returns the data value.
     *
     * @return the data value
     */
    public String getString() {

        return value;
    }

    /**
     * Returns a hash code value for the object. Actually the method behaves
     * the same than <code>SensibleDataType.hashCode()</code>.
     *
     * @return a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        return value.intern().hashCode();
    }

    /**
     * Returns the value length.
     *
     * @return the value length
     */
    public int length() {

        return value.length();
    }

    /**
     * Changes the <code>allowedCharacters</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleString#allowedCharacters
     * @see SensibleString#getAllowedCharacters()
     */
    public void setAllowedCharacters(String newValue) {

        String oldValue = allowedCharacters;
        allowedCharacters = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_ALLOWED_CHARACTERS, oldValue, newValue);
    }

    /**
     * Changes the <code>casingMode</code> property value and fires the property change event. The
     * valid values are <code>NO_CASING</code>, <code>TO_LOWER_CASE</code>,
     * <code>TO_UPPER_CASE</code> and <code>CAPITALIZE</code>.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new casing
     * mode is not valid.
     *
     * @param newValue the property new value
     *
     * @see SensibleString#casingMode
     * @see SensibleString#getCasingMode()
     * @see SensibleString#NO_CASING
     * @see SensibleString#TO_LOWER_CASE
     * @see SensibleString#TO_UPPER_CASE
     * @see SensibleString#CAPITALIZE
     */
    public void setCasingMode(String newValue) {

        if (!newValue.equals(NO_CASING)
            && !newValue.equals(TO_LOWER_CASE)
            && !newValue.equals(TO_UPPER_CASE)
            && !newValue.equals(CAPITALIZE)) {

            throw new IllegalArgumentException(
                SensibleContext.getMessage("STR_ERR_INVALID_CASING_MODE")); //$NON-NLS-1$
        }

        String oldValue = casingMode;
        casingMode = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_CASING_MODE, oldValue, newValue);

        if (value.length() != 0) {
            setValue(value);
        }
    }

    /**
     * Changes the <code>maxLength</code> property value and fires the property change event. If
     * the value length is greater than the maximum string length and the maximum length is not
     * equal to <code>-1</code>, the final string is truncated.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the maximum length
     * is less than <code>-1</code> or zero.
     *
     * @param newValue the property new value
     *
     * @see SensibleString#maxLength
     * @see SensibleString#getMaxLength()
     */
    public void setMaxLength(int newValue) {

        if (newValue < -1 || newValue == 0) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("STR_ERR_INVALID_MAX_LENGTH")); //$NON-NLS-1$
        }

        int oldValue = maxLength;
        maxLength = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_LENGTH, oldValue, newValue);

        if (maxLength != -1 && value.length() > maxLength) {
            setValue(value);
        }
    }

    /**
     * Changes the data value using the given <code>String</code> object.
     *
     * @param newValue the new value
     *
     * @see SensibleDataType#setValue(String)
     */
    public void setString(String newValue) {

        setValue(newValue);
    }

    /**
     * Changes the data value using the given <code>SensibleString</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleDataType#setValue(String)
     */
    public void setString(SensibleString newValue) {

        setValue(newValue.value);
    }

    /**
     * Returns the data value.
     *
     * @return the data value
     */
    public String stringValue() {

        return value;
    }

    /**
     * Returns a <code>SensibleString</code> object which value is a substring beginning in the
     * given position of this string value. The returned string maximum length will be the same than
     * this object maximum length.
     *
     * @return the substring
     *
     * @param begin the beginning position
     */
    public SensibleString substring(int begin) {

        return new SensibleString(maxLength, value.substring(begin));
    }

    /**
     * Returns a <code>SensibleString</code> object which value is a substring between the given
     * positions of this string value. The returned string maximum length will be the same than this
     * object maximum length.
     *
     * @return the substring
     *
     * @param begin the beginning position
     * @param end the ending position
     */
    public SensibleString substring(int begin, int end) {

        return new SensibleString(maxLength, value.substring(begin, end));
    }

    /**
     * Returns a new <code>SensibleString</code> object cloned from this
     * object which value is this object value minus the given value only
     * if the string value can be converted into an integer value.
     *
     * @param subtraend the value to be subtracted
     *
     * @return the <code>SensibleString</code> object with the new integer value
     */
    public SensibleDataType subtract(int subtraend) {

        try {
            SensibleString newValue = (SensibleString) this.clone();
            try {
                int convertedValue = Integer.parseInt(getString());
                if (convertedValue - subtraend > 0) {
                    newValue.setString(Integer.toString(convertedValue - subtraend));
                }
            } catch (NumberFormatException nfe) {
                nfe = null;
            }
            return newValue;
        } catch (CloneNotSupportedException cnse) {
            return null;
        }
    }

    /**
     * Returns a string representation valid for sorting data. For the string data type this
     * representation is the value string.
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        return toString();
    }

    /**
     * Returns a string representation valid for sql language operations. For the string data type
     * this representation is the value string surrounded by simple quotes.
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return '\'' + toString() + '\'';
    }
}
