package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import deors.core.commons.StringToolkit;

/**
 * Definition for a big decimal data type.<br>
 *
 * The class manages numbers with defined or infinite precision.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleBigDecimal
    extends SensibleDataType {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 2211769342534521967L;

    /**
     * The property that contains the data value as a <code>java.math.BigDecimal</code> object.
     *
     * @see SensibleBigDecimal#getNumber()
     * @see SensibleBigDecimal#setNumber(java.math.BigDecimal)
     */
    private BigDecimal number = new BigDecimal(0);

    /**
     * The maximum integer digits allowed. A value of <code>-1</code> (default) means there is no
     * limit in the integer part precission.
     *
     * @see SensibleBigDecimal#getMaxIntegerDigits()
     * @see SensibleBigDecimal#setMaxIntegerDigits(int)
     */
    private int maxIntegerDigits = -1;

    /**
     * The maximum fractional digits allowed. A value of <code>-1</code> (default) means there is
     * no limit in the fractional part precission.
     *
     * @see SensibleBigDecimal#getMaxFractionalDigits()
     * @see SensibleBigDecimal#setMaxFractionalDigits(int)
     */
    private int maxFractionalDigits = -1;

    /**
     * Whether the value can be a negative value. The property default value is <code>true</code>.
     *
     * @see SensibleBigDecimal#isNegativeValuesAllowed()
     * @see SensibleBigDecimal#setNegativeValuesAllowed(boolean)
     */
    private boolean negativeValuesAllowed = true;

    /**
     * The decimal separator.
     *
     * @see SensibleBigDecimal#getDecimalSeparator()
     * @see SensibleBigDecimal#setDecimalSeparator(char)
     * @see SensibleBigDecimal#DEFAULT_DECIMAL_SEPARATOR
     */
    private char decimalSeparator = DEFAULT_DECIMAL_SEPARATOR;

    /**
     * The group separator.
     *
     * @see SensibleBigDecimal#getGroupSeparator()
     * @see SensibleBigDecimal#setGroupSeparator(char)
     * @see SensibleBigDecimal#DEFAULT_GROUP_SEPARATOR
     */
    private char groupSeparator = DEFAULT_GROUP_SEPARATOR;

    /**
     * Pattern used to check strings.
     *
     * @see SensibleBigDecimal#checkFormat(String)
     * @see SensibleBigDecimal#preparePattern()
     */
    private Pattern pattern;

    /**
     * The <code>java.text.DecimalFormat</code> object used to format numbers into strings.
     *
     * @see SensibleBigDecimal#checkFormat(BigDecimal)
     * @see SensibleBigDecimal#prepareDecimalFormat()
     */
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0"); //$NON-NLS-1$

    /**
     * Token used while parsing numbers.
     */
    private static final String PATTERN_MINUS = "-"; //$NON-NLS-1$

    /**
     * Token used while parsing numbers.
     */
    private static final String PATTERN_MINUS_ZERO = "-0"; //$NON-NLS-1$

    /**
     * A period character.
     */
    private static final String PERIOD = "."; //$NON-NLS-1$

    /**
     * The "decimalSeparator" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DECIMAL_SEPARATOR = "decimalSeparator"; //$NON-NLS-1$

    /**
     * The "groupSeparator" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_GROUP_SEPARATOR = "groupSeparator"; //$NON-NLS-1$

    /**
     * The "maxIntegerDigits" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_MAX_INTEGER_DIGITS = "maxIntegerDigits"; //$NON-NLS-1$

    /**
     * The "maxFractionalDigits" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_MAX_FRACTIONAL_DIGITS = "maxFractionalDigits"; //$NON-NLS-1$

    /**
     * The "negativeValuesAllowed" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_NEGATIVE_VALUES_ALLOWED = "negativeValuesAllowed"; //$NON-NLS-1$

    /**
     * The "number" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_NUMBER = "number"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * The default decimal separator character. Configurable in the properties file using the key
     * <code>format.defaultDecimalSeparator</code>. Default value is <code>,</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, char)
     */
    private static final char DEFAULT_DECIMAL_SEPARATOR =
        getConfigurationProperty("format.defaultDecimalSeparator", ','); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * The default group separator character. Configurable in the properties file using the key
     * <code>format.defaultGroupSeparator</code>. Default value is <code>.</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, char)
     */
    private static final char DEFAULT_GROUP_SEPARATOR =
        getConfigurationProperty("format.defaultGroupSeparator", '.');

    /**
     * Default constructor.
     *
     * @see SensibleBigDecimal#prepareDecimalFormat()
     * @see SensibleBigDecimal#preparePattern()
     */
    public SensibleBigDecimal() {

        super();

        prepareDecimalFormat();
        preparePattern();

        controlsDocument = true;

        setNumber(SensibleContext.BLANK);
    }

    /**
     * Constructor that sets the number precission. A value of <code>-1</code> in any or both of
     * the parameters means there is no limit in the precission of the integer and/or the fractional
     * part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the maximum
     * integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
    }

    /**
     * Constructor that sets the number precission and whether negative values are allowed. A value
     * of <code>-1</code> in any or both of the precission parameters means there is no limit in
     * the precission of the integer and/or the fractional part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the maximum
     * integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param negativeValuesAllowed whether negative values are allowed
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits,
                              boolean negativeValuesAllowed) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNegativeValuesAllowed(negativeValuesAllowed);
    }

    /**
     * Constructor that sets the number value using the given <code>java.math.BigDecimal</code>
     * object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * valid.
     *
     * @param number the source value
     */
    public SensibleBigDecimal(BigDecimal number) {

        this();

        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>java.math.BigDecimal</code>
     * object value and the number precission. A value of <code>-1</code> in any or both of the
     * precission parameters means there is no limit in the precission of the integer and/or the
     * fractional part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * valid or the maximum integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param number the source value
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits, BigDecimal number) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>java.math.BigDecimal</code>
     * object value and the number precission and whether negative values are allowed. A value of
     * <code>-1</code> in any or both of the precission parameters means there is no limit in the
     * precission of the integer and/or the fractional part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * valid or the maximum integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param negativeValuesAllowed whether negative values are allowed
     * @param number the source value
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits,
                              boolean negativeValuesAllowed, BigDecimal number) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNegativeValuesAllowed(negativeValuesAllowed);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>SensibleBigDecimal</code>
     * object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * valid.
     *
     * @param number the source value
     */
    public SensibleBigDecimal(SensibleBigDecimal number) {

        this();

        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>SensibleBigDecimal</code>
     * object value and the number precission. A value of <code>-1</code> in any or both of the
     * precission parameters means there is no limit in the precission of the integer and/or the
     * fractional part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * valid or the maximum integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param number the source value
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits,
                              SensibleBigDecimal number) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>SensibleBigDecimal</code>
     * object value and the number precission and whether negative values are allowed. A value of
     * <code>-1</code> in any or both of the precission parameters means there is no limit in the
     * precission of the integer and/or the fractional part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * valid or the maximum integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param negativeValuesAllowed whether negative values are allowed
     * @param number the source value
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits,
                              boolean negativeValuesAllowed, SensibleBigDecimal number) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNegativeValuesAllowed(negativeValuesAllowed);
        setNumber(number);
    }

    /**
     * Constructor that sets the number value using the given <code>String</code> object value.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the string is not
     * a valid number.
     *
     * @param source the string representation of the source value
     */
    public SensibleBigDecimal(String source) {

        this();

        setNumber(source);
    }

    /**
     * Constructor that sets the number value using the given <code>String</code> object value and
     * the number precission. A value of <code>-1</code> in any or both of the precission
     * parameters means there is no limit in the precission of the integer and/or the fractional
     * part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the string is not
     * a valid number or the maximum integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param source the string representation of the source value
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits, String source) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNumber(source);
    }

    /**
     * Constructor that sets the number value using the given <code>String</code> object value and
     * the number precission and whether negative values are allowed. A value of <code>-1</code>
     * in any or both of the precission parameters means there is no limit in the precission of the
     * integer and/or the fractional part of the number.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the string is not
     * a valid number or the maximum integer and/or fractional digits values are less than -1.
     *
     * @param maxIntegerDigits the maximum integer digits
     * @param maxFractionalDigits the maximum fractional digits
     * @param negativeValuesAllowed whether negative values are allowed
     * @param source the string representation of the source value
     */
    public SensibleBigDecimal(int maxIntegerDigits, int maxFractionalDigits,
                              boolean negativeValuesAllowed, String source) {

        this();

        setMaxIntegerDigits(maxIntegerDigits);
        setMaxFractionalDigits(maxFractionalDigits);
        setNegativeValuesAllowed(negativeValuesAllowed);
        setNumber(source);
    }

    /**
     * Returns a new <code>SensibleBigDecimal</code> object which value
     * is this object value plus the given value.
     *
     * @param augend the value to be added
     *
     * @return the <code>SensibleBigDecimal</code> object with the new value
     */
    @Override
    public SensibleBigDecimal add(int augend) {

        SensibleBigDecimal newValue = returnCopy();
        newValue.setNumber(getNumber().add(new BigDecimal(augend)));
        return newValue;
    }

    /**
     * Returns a new <code>SensibleBigDecimal</code> object which value
     * is this object value plus the given value.
     *
     * @param augend the value to be added
     *
     * @return the <code>SensibleBigDecimal</code> object with the new value
     */
    public SensibleBigDecimal add(SensibleBigDecimal augend) {

        SensibleBigDecimal newValue = returnCopy();
        newValue.setNumber(getNumber().add(augend.getNumber()));
        return newValue;
    }

    /**
     * No implementation provided for this method.
     *
     * @param offset ignored
     * @param s ignored
     *
     * @return <code>false</code>
     *
     * @see SensibleBigDecimal#allowInsert(int, String, SensibleTextField,
     *                                     SensibleTextField.SensibleTextFieldDocument)
     */
    protected boolean allowInsert(int offset, String s) {

        return false;
    }

    /**
     * Used internally when the type controls the document to check whether the data type allows a
     * change in an insertion action fired in a <code>SensibleTextField</code> object. The method
     * checks whether the proposed value matches the data type format. For the big decimal data type
     * a remove action is allowed if the resulting string represents a valid big decimal number
     * checking the current precission and the negative values flag.
     *
     * @param offset the insertion point
     * @param s the string to be inserted
     * @param textField the text field
     * @param document the text field document
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     * @see SensibleBigDecimal#checkFormat(String)
     */
    protected boolean allowInsert(int offset, String s, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        if (s.indexOf(groupSeparator) != -1) {
            return false;
        }

        if (s.indexOf('-') != -1 && !negativeValuesAllowed) {
            return false;
        }

        if (s.startsWith(PATTERN_MINUS) && value.startsWith(PATTERN_MINUS)) {
            return false;
        }

        if (s.indexOf(decimalSeparator) != -1 && maxFractionalDigits == 0) {
            return false;
        }

        // removes group separator characters
        StringBuilder sb = new StringBuilder();

        sb.append(StringToolkit.removeAll(value.substring(0, offset), groupSeparator));
        sb.append(StringToolkit.removeAll(s, groupSeparator));
        sb.append(StringToolkit.removeAll(value.substring(offset), groupSeparator));

        String checkString = sb.toString();

        if (checkZero(checkString)) {
            try {
                document.insertStringFromParent(offset, s, null);
            } catch (BadLocationException bde) {
                return false;
            }

            insertString(offset, s);

            return true;
        }

        BigDecimal checkNumber = checkFormat(checkString);

        if (checkNumber == null) {
            return false;
        }

        String newValue = checkFormat(checkNumber);

        if (checkString.charAt(checkString.length() - 1) == decimalSeparator) {
            newValue = newValue.concat(Character.toString(decimalSeparator));
        }

        if (newValue.equals(value)) {
            return true;
        }

        // counts digits and separators to position the caret
        int newOffset = offset + s.length();
        int decOffset = value.indexOf(decimalSeparator);
        if ((decOffset != -1 && offset <= decOffset) || decOffset == -1) {
            if (value.length() == offset) {
                newOffset = newValue.length();
            } else {
                String postInsertion = value.substring(offset);
                newOffset = newValue.lastIndexOf(postInsertion);
                if (newOffset > 0 && newValue.charAt(newOffset - 1) == groupSeparator) {
                    newOffset--;
                }
            }
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

        textField.setCaretPosition(newOffset);

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
     * @see SensibleBigDecimal#allowRemove(int, int, SensibleTextField,
     *                                     SensibleTextField.SensibleTextFieldDocument)
     */
    protected boolean allowRemove(int offset, int length) {

        return false;
    }

    /**
     * Used internally when the type controls the document to check whether the data type allows a
     * change in a remove action fired in a <code>SensibleTextField</code> object. The method
     * checks whether the proposed value matches the data type format. For the big decimal data type
     * a remove action is allowed if the resulting string represents a valid big decimal number
     * checking the current precission and the negative values flag.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     * @param textField the text field
     * @param document the text field document
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     * @see SensibleBigDecimal#checkFormat(String)
     */
    protected boolean allowRemove(int offset, int length, SensibleTextField textField,
                                  SensibleTextField.SensibleTextFieldDocument document) {

        int localOffset = offset;

        // checks for group separator deletion
        if (length == 1 && value.charAt(offset) == groupSeparator) {
            if (textField.getCaretPosition() > offset) {
                localOffset--;
            } else {
                localOffset++;
            }
        }

        // removes group separator characters
        StringBuilder sb = new StringBuilder();

        String preRemove = StringToolkit.removeAll(value.substring(0, localOffset), groupSeparator);
        String postRemove =
            StringToolkit.removeAll(value.substring(localOffset + length), groupSeparator);

        sb.append(preRemove);
        sb.append(postRemove);

        String checkString = sb.toString();

        if (checkString.length() == 0) {
            try {
                document.removeFromParent(0, value.length());
                remove(0, value.length());
            } catch (BadLocationException bde) {
                return false;
            }

            return true;
        } else if (checkZero(checkString)) {
            try {
                document.removeFromParent(offset, length);
            } catch (BadLocationException bde) {
                return false;
            }

            remove(offset, length);

            return true;
        } else {
            BigDecimal checkNumber = checkFormat(checkString);

            if (checkNumber == null) {
                return false;
            }

            String newValue = checkFormat(checkNumber);

            if (checkString.charAt(checkString.length() - 1) == decimalSeparator) {
                newValue = newValue.concat(Character.toString(decimalSeparator));
            }

            if (newValue.equals(value)) {
                return true;
            }

            // counts digits and separators to position the caret
            int newOffset = localOffset;
            int decOffset = value.indexOf(decimalSeparator);
            if ((decOffset != -1 && localOffset <= decOffset) || decOffset == -1) {
                if (value.length() == localOffset + length) {
                    newOffset = newValue.length();
                } else {
                    String postDeletion = value.substring(localOffset + length);

                    if (postDeletion.charAt(0) == decimalSeparator) {
                        postDeletion = postDeletion.substring(1);
                    }

                    if (localOffset == 0 || (localOffset == 1 && value.charAt(0) == '-')) {
                        postDeletion =
                            StringToolkit.trim(postDeletion, new char[] {'0', groupSeparator},
                                StringToolkit.TRIM_LEFT);
                    }

                    newOffset = newValue.lastIndexOf(postDeletion);
                    if (newOffset > 0 && newValue.charAt(newOffset - 1) == groupSeparator) {
                        newOffset--;
                    }
                }
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

            textField.setCaretPosition(newOffset);

            return true;
        }
    }

    /**
     * Returns the <code>number</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#number
     * @see SensibleBigDecimal#getNumber()
     * @see SensibleBigDecimal#setNumber(java.math.BigDecimal)
     */
    public BigDecimal bigDecimalValue() {

        return number;
    }

    /**
     * Used internally to change the data value. This method uses the given string to change the
     * data value. If the new value is valid, fires the property change events, one for the
     * <code>value</code> property and another for the property in which the particular data value
     * is stored (if this property exists). If necessary, the implementation has to invoke the
     * <code>setValid(boolean)</code> method to change the data value valid state.
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the string does
     * not represent a valid big decimal value.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     * @see SensibleBigDecimal#checkFormat(String)
     */
    protected void changeValue(String newValue) {

        BigDecimal oldNumber = number;
        String oldValue = value;
        boolean newValid = valid;

        BigDecimal checkedValue = checkFormat(newValue);

        if (checkedValue == null) {
            if (valueChangingInSet) {
                valueChangingInSet = false;
            }

            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_INVALID_STRING")); //$NON-NLS-1$
        }

        number = checkedValue;
        value = newValue;
        newValid = true;

        firePropertyChange(JAVA_BEAN_PROPERTY_NUMBER, oldNumber, number);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(newValid);
    }

    /**
     * Checks the format of the given string and returns the <code>java.math.BigDecimal</code>
     * represented by the given string or <code>null</code> if the given string is not a valid big
     * decimal value.
     *
     * @return the big decimal value represented by the given string or <code>null</code>
     *
     * @param checkString the string to be checked
     *
     * @see SensibleBigDecimal#pattern
     * @see SensibleBigDecimal#checkPrecission(BigDecimal)
     * @see SensibleBigDecimal#checkZero(String)
     * @see SensibleBigDecimal#parseString(String)
     */
    public BigDecimal checkFormat(String checkString) {

        // checks for null or empty strings
        if (checkZero(checkString)) {
            return BigDecimal.ZERO;
        }

        // checks for strings with the '-' character
        // if negative values are not allowed
        if (!negativeValuesAllowed && checkString.indexOf('-') != -1) {
            return null;
        }

        BigDecimal temp = null;

        // checks the existence of group separators
        if (checkString.indexOf(groupSeparator) == -1) {
            String ufs = StringToolkit.replace(checkString, decimalSeparator, PERIOD);

            try {
                temp = new BigDecimal(ufs);
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            // checks the string format
            Matcher m = pattern.matcher(checkString);

            if (m.matches()) {
                temp = parseString(checkString);
            } else {
                return null;
            }
        }

        if (!checkPrecission(temp)) {
            return null;
        }

        return temp;
    }

    /**
     * Checks the precission of the given number and returns a formatted string using the decimal
     * and group separators or <code>null</code> if the given number precission is not valid.
     *
     * @return the number formatted as a string or <code>null</code>
     *
     * @param checkNumber the number to be checked
     *
     * @see SensibleBigDecimal#checkPrecission(BigDecimal)
     */
    public String checkFormat(BigDecimal checkNumber) {

        if (!checkPrecission(checkNumber)) {
            return null;
        }

        return formatNumber(checkNumber);
    }

    /**
     * Checks the precission of the given number.
     *
     * @return whether the given number represents a valid big decimal value considering the defined
     *         max integer and fractional digits
     *
     * @param checkNumber the number to be checked
     *
     * @see SensibleBigDecimal#maxIntegerDigits
     * @see SensibleBigDecimal#maxFractionalDigits
     * @see SensibleBigDecimal#negativeValuesAllowed
     */
    private boolean checkPrecission(BigDecimal checkNumber) {

        if (!negativeValuesAllowed && checkNumber.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        if (maxIntegerDigits != -1) {
            BigInteger intPart = checkNumber.toBigInteger();

            if (intPart.abs().compareTo(
                BigInteger.TEN.pow(maxIntegerDigits).subtract(BigInteger.ONE)) > 0) {
                return false;
            }
        }

        return maxFractionalDigits == -1
            || checkNumber.scale() <= maxFractionalDigits;
    }

    /**
     * Checks whether the given string is equivalent to zero but needs a different behavior.
     *
     * @return whether the given string is equivalent to zero
     *
     * @param checkString the string to be checked
     */
    private boolean checkZero(String checkString) {

        return checkString.isEmpty()
               || checkString.equals(PATTERN_MINUS)
               || checkString.equals(PATTERN_MINUS_ZERO)
               || checkString.equals(String.valueOf(decimalSeparator))
               || checkString.equals(PATTERN_MINUS + decimalSeparator)
               || checkString.equals(PATTERN_MINUS_ZERO + decimalSeparator);
    }

    /**
     * Compares this <code>SensibleBigDecimal</code> object with the given object and returns an
     * integer value as established in the <code>Comparable</code> interface. The method compares
     * the numeric values if the target object is a <code>java.math.BigDecimal</code> object or a
     * <code>SensibleBigDecimal</code> object while other target objects are compared in the super
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

        if (target instanceof BigDecimal) {
            return this.number.compareTo((BigDecimal) target);
        } else if (target instanceof SensibleBigDecimal) {
            return this.number.compareTo(((SensibleBigDecimal) target).number);
        } else {
            return super.compareTo(target);
        }
    }

    /**
     * Compares this <code>SensibleBigDecimal</code> object with the given
     * <code>java.math.BigDecimal</code> object and returns whether both objects represent the
     * same big decimal value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same big decimal value
     */
    public boolean equals(BigDecimal target) {

        if (target == null) {
            return false;
        }

        return this.number.equals(target);
    }

    /**
     * Compares this <code>SensibleBigDecimal</code> object with the given <code>String</code>
     * object and returns whether both objects represent the same big decimal value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same big decimal value
     */
    public boolean equals(String target) {

        if (target == null) {
            return false;
        }

        return this.value.equals(target);
    }

    /**
     * Compares this <code>SensibleBigDecimal</code> object with the given object and returns
     * whether both objects represent the same big decimal value.
     *
     * @param target the target object
     *
     * @return whether this and the given object represent the same big decimal value
     *
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object target) {

        if (this == target) {
            return true;
        }

        if (!(target instanceof SensibleBigDecimal)) {
            return false;
        }

        SensibleBigDecimal sensibleTarget = (SensibleBigDecimal) target;

        return this.number.equals(sensibleTarget.number);
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        super.firePropertyChangeEvents();

        firePropertyChange(JAVA_BEAN_PROPERTY_NUMBER, null, number);
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_INTEGER_DIGITS, null, maxIntegerDigits);
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_FRACTIONAL_DIGITS, null, maxFractionalDigits);
        firePropertyChange(JAVA_BEAN_PROPERTY_NEGATIVE_VALUES_ALLOWED, null, negativeValuesAllowed);
        firePropertyChange(JAVA_BEAN_PROPERTY_DECIMAL_SEPARATOR, null, decimalSeparator);
        firePropertyChange(JAVA_BEAN_PROPERTY_GROUP_SEPARATOR, null, groupSeparator);
    }

    /**
     * Returns a string with the given number formatted using the current group and decimal
     * separator or <code>null</code> if the given number is <code>null</code>. The
     * <code>java.text.DecimalFormat</code> class only returns at most eight fractional digits.
     * This method appends the fractional part to the formatted integer part.
     *
     * @param n the number to be formatted
     *
     * @return a string with the number formatted
     */
    private String formatNumber(BigDecimal n) {

        StringBuilder sb = new StringBuilder();

        if (n.toBigInteger().longValue() == 0 && n.signum() == -1) {
            sb.append('-');
        }

        sb.append(decimalFormat.format(n.toBigInteger()));

        String temp = n.toString();
        int decPos = temp.indexOf('.');
        if (decPos != -1) {
            sb.append(decimalSeparator);
            sb.append(temp.substring(decPos + 1));
        }

        return sb.toString();
    }

    /**
     * Returns the <code>decimalSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#decimalSeparator
     * @see SensibleBigDecimal#setDecimalSeparator(char)
     */
    public char getDecimalSeparator() {

        return decimalSeparator;
    }

    /**
     * Returns the <code>groupSeparator</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#groupSeparator
     * @see SensibleBigDecimal#setGroupSeparator(char)
     */
    public char getGroupSeparator() {

        return groupSeparator;
    }

    /**
     * Returns the <code>maxIntegerDigits</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#maxIntegerDigits
     * @see SensibleBigDecimal#setMaxIntegerDigits(int)
     */
    public int getMaxIntegerDigits() {

        return maxIntegerDigits;
    }

    /**
     * Returns the <code>maxFractionalDigits</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#maxFractionalDigits
     * @see SensibleBigDecimal#setMaxFractionalDigits(int)
     */
    public int getMaxFractionalDigits() {

        return maxFractionalDigits;
    }

    /**
     * Returns the <code>number</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#number
     * @see SensibleBigDecimal#bigDecimalValue()
     * @see SensibleBigDecimal#setNumber(java.math.BigDecimal)
     */
    public BigDecimal getNumber() {

        return number;
    }

    /**
     * Returns a hash code value for the object. Actually the method behaves
     * the same than <code>SensibleDataType.hashCode()</code>.
     *
     * @return a hash code value for this object
     */
    public int hashCode() {

        return value.intern().hashCode();
    }

    /**
     * Returns <code>true</code> if the data value is clear. Actually it checks whether the data
     * value is zero.
     *
     * @return whether the data value is clear
     */
    public boolean isClear() {

        return number.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Returns the <code>negativeValuesAllowed</code> property value.
     *
     * @return the property value
     *
     * @see SensibleBigDecimal#negativeValuesAllowed
     * @see SensibleBigDecimal#setNegativeValuesAllowed(boolean)
     */
    public boolean isNegativeValuesAllowed() {

        return negativeValuesAllowed;
    }

    /**
     * Returns a <code>java.math.BigDecimal</code> object parsed from the given string using the
     * current decimal and group separator.
     *
     * @param s the string to be parsed
     *
     * @return the number represented by the string
     */
    private BigDecimal parseString(String s) {

        String temp = null;

        temp = StringToolkit.removeAll(s, groupSeparator);
        temp = temp.replace(decimalSeparator, '.');

        return new BigDecimal(temp);
    }

    /**
     * Prepares the <code>java.text.DecimalFormat</code> object used to format numbers into
     * strings. The format depends on the decimal and group separators, so it needs to be
     * re-instanced when any of them change.
     *
     * @see SensibleBigDecimal#decimalSeparator
     * @see SensibleBigDecimal#groupSeparator
     * @see SensibleBigDecimal#checkFormat(BigDecimal)
     */
    private void prepareDecimalFormat() {

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setDecimalSeparator(decimalSeparator);
        dfs.setGroupingSeparator(groupSeparator);

        decimalFormat.setDecimalFormatSymbols(dfs);
    }

    /**
     * Prepares the pattern used to check strings. The pattern contains the decimal and group
     * separators, so the pattern needs to be recompiled when any of them change.
     *
     * @see SensibleBigDecimal#decimalSeparator
     * @see SensibleBigDecimal#groupSeparator
     * @see SensibleBigDecimal#checkFormat(String)
     */
    private void preparePattern() {

        StringBuilder regex = new StringBuilder();

        regex.append("-?(?:\\d{1,3}(?:(["); //$NON-NLS-1$
        regex.append(groupSeparator);
        regex.append("])\\d{3})?(?:\\1\\d{3})*)?(["); //$NON-NLS-1$
        regex.append(decimalSeparator);
        regex.append("]|((?!\\1)[,]\\d*))?"); //$NON-NLS-1$

        pattern = Pattern.compile(regex.toString());
    }

    /**
     * Returns a copy of this SensibleBigDecimal object.
     *
     * @return a copy of this object
     */
    @Override
    public SensibleBigDecimal returnCopy() {

        return new SensibleBigDecimal(
            getMaxIntegerDigits(),
            getMaxFractionalDigits(),
            isNegativeValuesAllowed(),
            getNumber());
    }

    /**
     * Changes the <code>decimalSeparator</code> property value and fires the property change
     * event.
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value
     * format is not valid.
     *
     * @param newValue the property new value
     *
     * @see SensibleBigDecimal#decimalSeparator
     * @see SensibleBigDecimal#getDecimalSeparator()
     * @see SensibleBigDecimal#prepareDecimalFormat()
     * @see SensibleBigDecimal#preparePattern()
     */
    public void setDecimalSeparator(char newValue) {

        char oldValue = decimalSeparator;
        decimalSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DECIMAL_SEPARATOR, oldValue, newValue);

        prepareDecimalFormat();
        preparePattern();

        if (!isClear()) {
            setValue(number);
        }
    }

    /**
     * Changes the <code>groupSeparator</code> property value and fires the property change event.
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value
     * format is not valid.
     *
     * @param newValue the property new value
     *
     * @see SensibleBigDecimal#groupSeparator
     * @see SensibleBigDecimal#getGroupSeparator()
     * @see SensibleBigDecimal#prepareDecimalFormat()
     * @see SensibleBigDecimal#preparePattern()
     */
    public void setGroupSeparator(char newValue) {

        char oldValue = groupSeparator;
        groupSeparator = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_GROUP_SEPARATOR, oldValue, newValue);

        prepareDecimalFormat();
        preparePattern();

        if (!isClear()) {
            setValue(number);
        }
    }

    /**
     * Changes the <code>maxIntegerDigits</code> property value and fires the property change
     * event. The change is only allowed if number is zero.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * zero or the maximum integer digits value is less than -1.
     *
     * @param newValue the property new value
     *
     * @see SensibleBigDecimal#maxIntegerDigits
     * @see SensibleBigDecimal#getMaxIntegerDigits()
     */
    public void setMaxIntegerDigits(int newValue) {

        if (number.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_NO_FORMAT_CHANGE")); //$NON-NLS-1$
        }

        if (newValue < -1) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_INVALID_MAX_DIGITS")); //$NON-NLS-1$
        }

        int oldValue = maxIntegerDigits;
        maxIntegerDigits = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_INTEGER_DIGITS, oldValue, newValue);
    }

    /**
     * Changes the <code>maxFractionalDigits</code> property value and fires the property change
     * event. The change is only allowed if number is zero.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * zero or the maximum fractional digits value is less than -1.
     *
     * @param newValue the property new value
     *
     * @see SensibleBigDecimal#maxFractionalDigits
     * @see SensibleBigDecimal#getMaxFractionalDigits()
     */
    public void setMaxFractionalDigits(int newValue) {

        if (number.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_NO_FORMAT_CHANGE")); //$NON-NLS-1$
        }

        if (newValue < -1) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_INVALID_MAX_DECIMALS")); //$NON-NLS-1$
        }

        int oldValue = maxFractionalDigits;
        maxFractionalDigits = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_MAX_FRACTIONAL_DIGITS, oldValue, newValue);
    }

    /**
     * Changes the <code>negativeAllowed</code> property value and fires the property change
     * event. The change is only allowed if number is zero.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the number is not
     * zero.
     *
     * @param newValue the property new value
     *
     * @see SensibleBigDecimal#negativeValuesAllowed
     * @see SensibleBigDecimal#isNegativeValuesAllowed()
     */
    public void setNegativeValuesAllowed(boolean newValue) {

        if (number.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_NO_FORMAT_CHANGE")); //$NON-NLS-1$
        }

        boolean oldValue = negativeValuesAllowed;
        negativeValuesAllowed = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_NEGATIVE_VALUES_ALLOWED, oldValue, newValue);
    }

    /**
     * Changes the <code>number</code> property value and fires the property change event.
     *
     * @param newValue the new value
     *
     * @see SensibleBigDecimal#number
     * @see SensibleBigDecimal#getNumber()
     * @see SensibleBigDecimal#setValue(java.math.BigDecimal)
     */
    public void setNumber(BigDecimal newValue) {

        setValue(newValue);
    }

    /**
     * Changes the data value using the given <code>SensibleBigDecimal</code> object value.
     *
     * @param newValue the new value
     *
     * @see SensibleBigDecimal#setValue(java.math.BigDecimal)
     */
    public void setNumber(SensibleBigDecimal newValue) {

        setValue(newValue.number);
    }

    /**
     * Changes the data value using the given <code>String</code> object.
     *
     * @param newValue the string representation of the new value
     *
     * @see SensibleDataType#setValue(String)
     */
    public void setNumber(String newValue) {

        setValue(newValue);
    }

    /**
     * Changes the data value using the given <code>java.math.BigDecimal</code> value and fires
     * the property change event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the new value
     * format is not valid.
     *
     * @param newValue the new value
     */
    private void setValue(BigDecimal newValue) {

        BigDecimal oldNumber = number;
        String oldValue = value;

        String checkedValue = checkFormat(newValue);

        if (checkedValue == null) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("BIGD_ERR_INVALID_NUMBER")); //$NON-NLS-1$
        }

        number = newValue;
        value = checkedValue;

        valueChangingInSet = true;

        firePropertyChange(JAVA_BEAN_PROPERTY_NUMBER, oldNumber, number);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, oldValue, value);

        setValid(true);

        valueChangingInSet = false;
    }

    /**
     * Returns a new <code>SensibleBigDecimal</code> object which value
     * is this object value minus the given value.
     *
     * @param subtraend the value to be subtracted
     *
     * @return the <code>SensibleBigDecimal</code> object with the new value
     */
    @Override
    public SensibleBigDecimal subtract(int subtraend) {

        SensibleBigDecimal newValue = returnCopy();
        newValue.setNumber(getNumber().subtract(new BigDecimal(subtraend)));
        return newValue;
    }

    /**
     * Returns a new <code>SensibleBigDecimal</code> object which value
     * is this object value minus the given value.
     *
     * @param subtraend the value to be subtracted
     *
     * @return the <code>SensibleBigDecimal</code> object with the new value
     */
    public SensibleBigDecimal subtract(SensibleBigDecimal subtraend) {

        SensibleBigDecimal newValue = returnCopy();
        newValue.setNumber(getNumber().subtract(subtraend.getNumber()));
        return newValue;
    }

    /**
     * Return the number formatted using the current group and decimal separator.
     *
     * @return the number formatted
     */
    public String toStringFormatted() {

        return formatNumber(number);
    }

    /**
     * Returns a string representation valid for sorting data. For the big decimal data type the
     * method aligns the integer part in a string long enough to match any valid number with the
     * current integer part precision and then appends the decimal part.
     *
     * @return a string representation valid for sorting data
     */
    public String toStringForSort() {

        if (maxIntegerDigits == -1) {
            return value;
        }

        String intPart;
        String decPart;

        int dot = value.indexOf(decimalSeparator);

        if (dot == -1) {
            intPart = value;
            decPart = SensibleContext.BLANK;
        } else {
            intPart = value.substring(0, dot);
            decPart = value.substring(dot);
        }

        int size = maxIntegerDigits + (negativeValuesAllowed ? 1 : 0);

        if (intPart.length() != size) {
            char[] temp = new char[size - intPart.length()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = ' ';
            }
            intPart = new String(temp).concat(intPart);
        }

        return intPart + decPart;
    }

    /**
     * Returns a string representation valid for sql language operations. For the big decimal data
     * type this representation is the value string.
     *
     * @return a string representation valid for sql language operations
     */
    public String toStringForSQL() {

        return toString();
    }
}
