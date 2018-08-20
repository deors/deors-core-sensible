package deors.core.sensible;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * Definition for an abstract data type.<br>
 *
 * All data types in the <i>Sensible Architecture</i> extend this class. This class provides the
 * common properties and methods to all data types. It also provides the default implementation for
 * methods that do not depend on the defined data type, as the property change support methods.<br>
 *
 * @author deors
 * @version 1.0
 */
public abstract class SensibleDataType
    extends BasicPropertyChangeSupport
    implements Comparable<Object>, Serializable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 8426137567609590233L;

    /**
     * This is the data value as a string and it is common for all types. When necessary, the
     * derived classes can have another property for the particular data type (i.e. an integer
     * value). The <code>String</code> class constructor is used to assert that a new instance
     * is created. If the same string instance is reused the property change events may signal
     * other properties as being changed.
     *
     * @see SensibleDataType#getValue()
     * @see SensibleDataType#setValue(String)
     */
    protected String value = new String();

    /**
     * Whether the data value is valid. This property is also used in text fields to change the
     * component foreground.
     *
     * @see SensibleDataType#isValid()
     * @see SensibleDataType#setValid(boolean)
     */
    protected boolean valid = true;

    /**
     * Whether the data is required. If the data is required then it must be informed to be valid.
     * This property is also used in text fields to change the component background.
     *
     * @see SensibleDataType#isRequired()
     * @see SensibleDataType#setRequired(boolean)
     */
    protected boolean required;

    /**
     * Whether the data is considered a key property when it is used inside a
     * <code>SensibleObject</code> class definition.
     *
     * @see SensibleDataType#isKey()
     * @see SensibleDataType#setKey(boolean)
     * @see SensibleObject
     */
    protected boolean key;

    /**
     * Whether the data is not writable. A read only property can not be changed in a text field
     * although the component is editable.
     *
     * @see SensibleDataType#isReadOnly()
     * @see SensibleDataType#setReadOnly(boolean)
     * @see SensibleTextField
     */
    protected boolean readOnly;

    /**
     * Used internally to see if a <code>changeValue(String)</code> method invokation comes from a
     * setter method action (i.e., the <code>setValue()</code> methods, or the
     * <code>setText()</code> method in the <code>SensibleTextField</code> class) or comes from
     * a text field insert/remove action.
     */
    protected boolean valueChangingInSet;

    /**
     * When <code>true</code> a text field insert/remove action calls the <code>allowInsert</code>/<code>allowRemove</code>
     * method with the document as a parameter.
     */
    protected boolean controlsDocument;

    /**
     * The "key" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_KEY = "key"; //$NON-NLS-1$

    /**
     * The "readOnly" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_READ_ONLY = "readOnly"; //$NON-NLS-1$

    /**
     * The "required" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_REQUIRED = "required"; //$NON-NLS-1$

    /**
     * The "valid" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALID = "valid"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * Default constructor. This is an abstract class and therefore it cannot be instantiated.
     */
    protected SensibleDataType() {

        super();
    }

    /**
     * Returns a new object cloned from this object which value
     * is this object value plus the given value.
     *
     * @param augend the value to be added
     *
     * @return the object with the new value
     */
    public SensibleDataType add(int augend) {
        return returnCopy();
    }

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in an insertion action fired in a <code>SensibleTextField</code> object.
     * The method checks whether the proposed value matches the data type format.
     *
     * @param offset the insertion point
     * @param s the string to be inserted
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#insertString(int, String,
     *                                                               javax.swing.text.AttributeSet)
     */
    protected abstract boolean allowInsert(int offset, String s);

    /**
     * Used internally when the type controls the document to check whether the data type allows a
     * change in an insertion action fired in a <code>SensibleTextField</code> object. The method
     * checks whether the proposed value matches the data type format.
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
     */
    protected abstract boolean allowInsert(int offset, String s, SensibleTextField textField,
                                           SensibleTextField.SensibleTextFieldDocument document);

    /**
     * Used internally when the type does not control the document to check whether the data type
     * allows a change in a remove action fired in a <code>SensibleTextField</code> object. The
     * method checks whether the proposed value matches the data type format.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @return whether the proposed value is a valid data type value
     *
     * @see SensibleTextField.SensibleTextFieldDocument#remove(int, int)
     */
    protected abstract boolean allowRemove(int offset, int length);

    /**
     * Used internally when the type controls the document to check whether the data type allows a
     * change in a remove action fired in a <code>SensibleTextField</code> object. The method
     * checks whether the proposed value matches the data type format.
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
    protected abstract boolean allowRemove(int offset, int length, SensibleTextField textField,
                                           SensibleTextField.SensibleTextFieldDocument document);

    /**
     * Used internally to change the data value. This method uses the given string to change the
     * data value. If the new value is valid, fires the property change events, one for the
     * <code>value</code> property and another for the property in which the particular data value
     * is stored (if this property exists). If necessary, the implementation has to invoke the
     * <code>setValid(boolean)</code> method to change the data value valid state.
     *
     * @param newValue the data new value as a string
     *
     * @see SensibleDataType#setValid(boolean)
     */
    protected abstract void changeValue(String newValue);

    /**
     * Clears the data value. Actually it changes the data value to an empty string. Derived classes
     * can override this method.
     */
    public void clear() {

        setValue(SensibleContext.BLANK);
    }

    /**
     * Compares this <code>SensibleDataType</code> object with the given object and returns an
     * integer value as established in the <code>Comparable</code> interface. The method compares
     * the <code>toStringForSort()</code> string representation for both objects.
     *
     * @param target the target object
     *
     * @return a negative integer value if this object is less than the given object, zero if both
     *         objects represent the same value, and a positive integer value if this object is
     *         greater than the given object
     *
     * @see Comparable#compareTo(Object)
     * @see SensibleDataType#toStringForSort()
     */
    public int compareTo(Object target) {

        if (target instanceof SensibleDataType) {
            return toStringForSort().compareTo(((SensibleDataType) target).toStringForSort());
        } else if (target instanceof String) {
            return value.compareTo((String) target);
        }

        return 1;
    }

    /**
     * Compares the <code>value</code> property with the given object and returns whether the
     * <code>value</code> property and the given object represent the same value.
     *
     * @param target the target object
     *
     * @return whether the <code>value</code> property and the given object represent the same
     *         value
     *
     * @see java.lang.Object#equals(Object)
     * @see SensibleDataType#value
     */
    public boolean equals(Object target) {

        if (this == target) {
            return true;
        }

        if (!(target instanceof SensibleDataType)) {
            return false;
        }

        SensibleDataType sensibleTarget = (SensibleDataType) target;

        return this.value.equals(sensibleTarget.value);
    }

    /**
     * Fires property change events for each data type property. This method should be used
     * in visual controls when a new data bean is setted. Subtypes should override this
     * method to add new events as necessary.
     */
    void firePropertyChangeEvents() {

        firePropertyChange(JAVA_BEAN_PROPERTY_KEY, null, key);
        firePropertyChange(JAVA_BEAN_PROPERTY_READ_ONLY, null, readOnly);
        firePropertyChange(JAVA_BEAN_PROPERTY_REQUIRED, null, required);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALID, null, valid);
        firePropertyChange(JAVA_BEAN_PROPERTY_VALUE, null, value);
    }

    /**
     * Returns a hash code value for the object.
     *
     * Actually the method returns the hash code of the <code>value</code> property using the
     * <code>intern()</code> method to ensure hash code equality in strings.
     *
     * @return a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     * @see java.lang.String#intern()
     * @see SensibleDataType#value
     */
    public int hashCode() {

        return value.intern().hashCode();
    }

    /**
     * Returns the <code>value</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDataType#value
     * @see SensibleDataType#setValue(String)
     */
    protected final String getValue() {

        return value;
    }

    /**
     * Used by the document model in <code>SensibleTextField</code> to insert characters and
     * selections. It uses the <code>changeValue(String)</code> method to set the new value.
     *
     * @param offset the insert point
     * @param s the string to be inserted
     *
     * @see SensibleDataType#changeValue(String)
     */
    protected final void insertString(int offset, String s) {

        StringBuilder sb = new StringBuilder();

        if (offset == 0) {
            sb.append(s);
            sb.append(value);
        } else {
            sb.append(value.substring(0, offset));
            sb.append(s);
            sb.append(value.substring(offset));
        }

        changeValue(sb.toString());
    }

    /**
     * Returns <code>true</code> if the data value is clear. Actually it checks if the
     * <code>value</code> property is an empty string.
     *
     * @return whether the data value is clear
     */
    public boolean isClear() {

        return value.length() == 0;
    }

    /**
     * Returns the <code>key</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDataType#key
     * @see SensibleDataType#setKey(boolean)
     */
    public final boolean isKey() {

        return key;
    }

    /**
     * Returns the <code>readOnly</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDataType#readOnly
     * @see SensibleDataType#setReadOnly(boolean)
     */
    public final boolean isReadOnly() {

        return readOnly;
    }

    /**
     * Returns the <code>required</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDataType#required
     * @see SensibleDataType#setRequired(boolean)
     */
    public final boolean isRequired() {

        return required;
    }

    /**
     * Returns the <code>valid</code> property value.
     *
     * @return the property value
     *
     * @see SensibleDataType#valid
     * @see SensibleDataType#setValid(boolean)
     */
    public final boolean isValid() {
        return valid;
    }

    /**
     * Used by the document model in <code>SensibleTextField</code> to remove characters and
     * selections. It uses the <code>changeValue(String)</code> method to set the new value.
     *
     * @param offset the remove starting point
     * @param length length to be removed
     *
     * @see SensibleDataType#changeValue(String)
     */
    protected final void remove(int offset, int length) {

        StringBuilder sb = new StringBuilder();

        sb.append(value.substring(0, offset));
        sb.append(value.substring(offset + length));

        changeValue(sb.toString());
    }

    /**
     * Returns a copy of this object.
     *
     * @return a copy of this object or <code>null</code> if the copy could not be created
     */
    public SensibleDataType returnCopy() {

        SensibleDataType obj = returnNew();

        if (obj != null) {

            obj.setKey(isKey());
            obj.setReadOnly(isReadOnly());
            obj.setRequired(isRequired());
            obj.changeValue(new String(value));
        }

        return obj;
    }

    /**
     * Returns a new object of this class or <code>null</code> if an exception is thrown while
     * creating the new instance.
     *
     * @return a new instance of this object class or <code>null</code> if the new instance could not be created
     */
    public SensibleDataType returnNew() {

        try {
            return getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Changes the <code>key</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDataType#key
     * @see SensibleDataType#isKey()
     */
    public final void setKey(boolean newValue) {

        boolean oldValue = key;
        key = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_KEY, oldValue, newValue);
    }

    /**
     * Changes the <code>readOnly</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDataType#readOnly
     * @see SensibleDataType#isReadOnly()
     */
    public final void setReadOnly(boolean newValue) {

        boolean oldValue = readOnly;
        readOnly = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_READ_ONLY, oldValue, newValue);
    }

    /**
     * Changes the <code>required</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleDataType#required
     * @see SensibleDataType#isRequired()
     */
    public final void setRequired(boolean newValue) {

        boolean oldValue = required;
        required = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_REQUIRED, oldValue, newValue);

        changeValue(value);
    }

    /**
     * Changes the <code>valid</code> property value and fires the property change event. This
     * property cannot be <code>true</code> if the data is required and it is clear. This method
     * is declared <code>public</code> so external data validation methods can change the property
     * value.
     *
     * @param newValue the property new value
     *
     * @see SensibleDataType#valid
     * @see SensibleDataType#isValid()
     * @see SensibleDataType#required
     */
    public final void setValid(boolean newValue) {

        boolean oldValue = valid;
        boolean proposedValue = required ? (value.length() > 0 && newValue) : newValue;
        valid = proposedValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_VALID, oldValue, proposedValue);
    }

    /**
     * Changes the <code>value</code> property value using the <code>valueChangingInSet</code>
     * flag and the <code>changeValue(String)</code> method. This method is declared
     * <code>protected</code> so types that extends this abstract class can use it to change the
     * data value using a generic string independently of the particular data type.
     *
     * @param newValue the property new value
     *
     * @see SensibleDataType#value
     * @see SensibleDataType#getValue()
     * @see SensibleDataType#valueChangingInSet
     * @see SensibleDataType#changeValue(String)
     */
    protected final void setValue(String newValue) {

        valueChangingInSet = true;
        changeValue(newValue);
        valueChangingInSet = false;
    }

    /**
     * Returns a new object cloned from this object which value
     * is this object value minus the given value.
     *
     * @param subtraend the value to be subtracted
     *
     * @return the object with the new value
     */
    public SensibleDataType subtract(int subtraend) {
        return returnCopy();
    }

    /**
     * Returns a string representation of the data value. Actually it returns the <code>value</code>
     * property value.
     *
     * @return a string representation of the data value
     */
    public String toString() {

        return value;
    }

    /**
     * Returns a string representation valid for sorting data. For numeric and date data types, the
     * string returned when this method is invoked must ensure the internal data type order sequence
     * (i.e., <code>"100"</code> is greater than <code>"&nbsp;11"</code>).
     *
     * @return a string representation valid for sorting data
     */
    public abstract String toStringForSort();

    /**
     * Returns a string representation valid for sql language operations. Normally it is the value
     * as a string and for string and date data types it is the value as a string surrounded by
     * simple quotes.
     *
     * @return a string representation valid for sql language operations
     */
    public abstract String toStringForSQL();
}
