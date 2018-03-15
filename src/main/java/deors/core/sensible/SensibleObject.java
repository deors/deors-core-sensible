package deors.core.sensible;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Definition for an abstract object.<br>
 *
 * This class provides the abstract framework to create objects. Objects has 'key' properties that
 * determines when an object is 'complete'.<br>
 *
 * An object implemented extending this class can match database tables and if it is used with a
 * <code>deors.commons.ConnectionManager</code> object tables and views can be read and updated with
 * a single method invocation.<br>
 *
 * A <code>SensibleObject</code> object can also be used to customize the presentation of a
 * <code>SensibleTable</code> object.<br>
 *
 * The steps needed to create a new <code>SensibleObject</code> are:
 *
 * <ol>
 * <li>1. Create a new class that extends <code>SensibleObject</code>.</li>
 * <li>2. Implement the default constructor. Define local variables, one for each field in the object
 * definition and initialize each with the appropriate settings as maximum lengths for strings or
 * ranges for integers. Define key and required properties. Initialize the object definition arrays
 * <code>fields</code> and <code>fieldNames</code>. The first will be an array of
 * <code>SensibleDataType</code> objects referencing the fields in the object definition (the local
 * variables defined and initialized in this constructor) and the second will be an array of
 * <code>String</code> objects containing the field names. Each field name must be equal to the name
 * of the property and, if the object is going to be used to read and update tables, equal to the
 * table or view column name or alias. The constructor ends invoking the <code>addListeners()</code>
 * method.</li>
 * <li>3. Implement the desired constructors. All of them begin calling the default constructor and
 * change field values invoking the <code>changeField()</code> methods so the property change
 * events in the object are not raised or <code>setField()</code> methods if you desire to rise
 * the property change events.</li>
 * <li>4. Define accessors and mutators for the properties if desired. Accessors must use the
 * <code>getField()</code> methods and mutators must use the <code>setField()</code> methods so the
 * property change events in the object are raised.</li>
 * </ol>
 *
 * @author deors
 * @version 1.0
 */
public abstract class SensibleObject
    implements java.beans.PropertyChangeListener, Cloneable, Comparable<SensibleObject>, Serializable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -7497389273764001953L;

    /**
     * The <code>dataComplete</code> property is <code>true</code> when all object data is
     * valid.
     *
     * @see SensibleObject#isDataComplete()
     * @see SensibleObject#setDataComplete(boolean)
     */
    private boolean dataComplete;

    /**
     * Array of <code>SensibleDataType</code> objects that contains references to the fields in
     * the object definition.
     *
     * @see SensibleObject#getFields()
     * @see SensibleObject#getField(int)
     * @see SensibleObject#getField(String)
     * @see SensibleObject#getFieldCount()
     * @see SensibleObject#getFieldIndex(String)
     */
    protected SensibleDataType[] fields;

    /**
     * Array of <code>String</code> objects that contains the fields names.
     *
     * @see SensibleObject#getFieldNames()
     * @see SensibleObject#getFieldName(int)
     * @see SensibleObject#getFieldCount()
     */
    protected String[] fieldNames;

    /**
     * Separator.
     */
    private static final String SEPARATOR = "//"; //$NON-NLS-1$

    /**
     * The "dataComplete" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATA_COMPLETE = "dataComplete"; //$NON-NLS-1$

    /**
     * Default constructor. This is an abstract class and therefore it cannot be instantiated. The
     * constructor adds the property change listener to the class instance.
     */
    protected SensibleObject() {

        super();

        addPropertyChangeListener(this);
    }

    /**
     * Adds listeners for all the fields.
     */
    public final void addListeners() {

        synchronized (fields) {
            for (int i = 0, n = fields.length; i < n; i++) {
                fields[i].addPropertyChangeListener(this);
            }
        }
    }

    /**
     * Changes the value of a field given by index.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field index is
     * not defined.
     *
     * @param index the field index
     * @param newValue the new value
     */
    @SuppressWarnings("PMD.StringInstantiation")
    protected final void changeField(int index, String newValue) {

        SensibleDataType field = null;

        try {
            field = fields[index];
        } catch (ArrayIndexOutOfBoundsException aobe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_FIELD_NOT_DEFINED", Integer.toString(index)), aobe); //$NON-NLS-1$
        }

        field.setValue(new String(newValue));
    }

    /**
     * Changes the value of a field given by index.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field index is
     * not defined.
     *
     * @param index the field index
     * @param newValue the new value
     */
    protected final void changeField(int index, SensibleDataType newValue) {

        SensibleDataType field = null;

        try {
            field = fields[index];
        } catch (ArrayIndexOutOfBoundsException aobe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_FIELD_NOT_DEFINED", Integer.toString(index)), aobe); //$NON-NLS-1$
        }

        if (!field.getClass().equals(newValue.getClass())) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_INVALID_OBJECT")); //$NON-NLS-1$
        }

        field.setValue(new String(newValue.getValue()));
    }

    /**
     * Changes the value of a field given by name.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field name is
     * not defined.
     *
     * @param name the field name
     * @param newValue the new value
     */
    protected final void changeField(String name, String newValue) {

        changeField(getFieldIndex(name), newValue);
    }

    /**
     * Changes the value of a field given by name.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field name is
     * not defined.
     *
     * @param name the field name
     * @param newValue the new value
     */
    protected final void changeField(String name, SensibleDataType newValue) {

        changeField(getFieldIndex(name), newValue);
    }

    /**
     * Changes the value of all the fields.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given object
     * definition does not match this object definition.
     *
     * @param newValue the new value
     *
     * @see SensibleObject#changeField(int, SensibleDataType)
     */
    protected final void changeValue(SensibleObject newValue) {

        int n = fields.length;

        if (n != newValue.fields.length) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_INVALID_OBJECT")); //$NON-NLS-1$
        }

        for (int i = 0; i < n; i++) {
            changeField(i, newValue.fields[i]);
        }
    }

    /**
     * Checks the completion of object data and sets the <code>dataComplete</code> property.
     *
     * @see SensibleObject#setDataComplete(boolean)
     */
    private void checkDataComplete() {

        boolean newValue = true;

        for (int i = 0, n = fields.length; i < n; i++) {
            if (!fields[i].isValid()) {
                newValue = false;
                break;
            }
        }

        setDataComplete(newValue);
    }

    /**
     * Clears the value of all fields.
     *
     * @see SensibleDataType#clear()
     */
    public final void clear() {

        for (int i = 0, n = fields.length; i < n; i++) {
            fields[i].clear();
        }
    }

    /**
     * Clears the value of all key fields.
     *
     * @see SensibleDataType#clear()
     */
    public final void clearKey() {

        for (int i = 0, n = fields.length; i < n; i++) {
            if (fields[i].isKey()) {
                fields[i].clear();
            }
        }
    }

    /**
     * Clears the value of all no-key fields.
     *
     * @see SensibleDataType#clear()
     */
    public final void clearNoKey() {

        for (int i = 0, n = fields.length; i < n; i++) {
            if (!fields[i].isKey()) {
                fields[i].clear();
            }
        }
    }

    /**
     * Returns a clone of this object.
     *
     * @return a clone of this object
     *
     * @throws CloneNotSupportedException if the clone could not be done
     *
     * @see java.lang.Cloneable
     * @see java.lang.Object#clone()
     * @see SensibleObject#returnCopy()
     * @see SensibleDataType#clone()
     */
    public final SensibleObject clone()
        throws CloneNotSupportedException {

        SensibleObject obj = (SensibleObject) super.clone();

        obj.fields = new SensibleDataType[fields.length];
        obj.fieldNames = new String[fieldNames.length];

        for (int i = 0, n = fields.length; i < n; i++) {
            obj.fields[i] = (SensibleDataType) fields[i].clone();
            obj.fieldNames[i] = fieldNames[i];
        }

        return obj;
    }

    /**
     * Compares this <code>SensibleObject</code> object with the given object and returns an
     * integer value as established in the <code>Comparable</code> interface. The method compares
     * the <code>toStringForSort()</code> string representation for both objects if the target
     * object is a <code>SensibleObject</code> object while other target objects are always
     * greater than this object.
     *
     * @param target the target object
     *
     * @return a negative integer value if this object is less than the given object, zero if both
     *         objects represent the same value, and a positive integer value if this object is
     *         greater than the given object
     *
     * @see Comparable#compareTo(Object)
     * @see SensibleObject#toStringForSort()
     */
    public final int compareTo(SensibleObject target) {

        return this.toStringForSort().compareTo(target.toStringForSort());
    }

    /**
     * Compares this <code>SensibleObject</code> object with the given object and returns whether
     * both objects have the same field values.
     *
     * @param target the target object
     *
     * @return whether this and the given object have the same field values
     *
     * @see java.lang.Object#equals(Object)
     */
    public final boolean equals(Object target) {

        if (this == target) {
            return true;
        }

        if (!(target instanceof SensibleObject)) {
            return false;
        }

        SensibleObject sensibleTarget = (SensibleObject) target;

        int n = this.fields.length;

        if (n != sensibleTarget.fields.length) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (!this.fields[i].getValue().equals(sensibleTarget.fields[i].getValue())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the array with the fields in the object definition.
     *
     * @return the fields in the object definition
     *
     * @see SensibleObject#fields
     */
    public final SensibleDataType[] getFields() {

        return Arrays.copyOf(fields, fields.length);
    }

    /**
     * Returns a field from the object definition given by index.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field index is
     * not defined.
     *
     * @param index the field index
     *
     * @return the field
     *
     * @see SensibleObject#fields
     */
    public final SensibleDataType getField(int index) {

        try {
            return fields[index];
        } catch (ArrayIndexOutOfBoundsException aobe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_FIELD_NOT_DEFINED", Integer.toString(index)), aobe); //$NON-NLS-1$
        }
    }

    /**
     * Returns a field from the object definition given by name.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field name is
     * not defined.
     *
     * @param name the field name
     *
     * @return the field
     *
     * @see SensibleObject#fields
     */
    public final SensibleDataType getField(String name) {

        for (int i = 0, n = fieldNames.length; i < n; i++) {
            if (name.equals(fieldNames[i])) {
                return fields[i];
            }
        }

        throw new IllegalArgumentException(
            SensibleContext.getMessage(
                "DTYPE_ERR_FIELD_NOT_DEFINED", name)); //$NON-NLS-1$
    }

    /**
     * Returns the number of fields in the object definition.
     *
     * @return the number of fields in the object definition
     *
     * @see SensibleObject#fields
     */
    public final int getFieldCount() {

        return fields.length;
    }

    /**
     * Returns the index of a field from the object definition.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field name is
     * not defined.
     *
     * @param name the field name
     *
     * @return the field index
     *
     * @see SensibleObject#fields
     * @see SensibleObject#fieldNames
     */
    public final int getFieldIndex(String name) {

        for (int i = 0, n = fieldNames.length; i < n; i++) {
            if (name.equals(fieldNames[i])) {
                return i;
            }
        }

        throw new IllegalArgumentException(
            SensibleContext.getMessage(
                "DTYPE_ERR_FIELD_NOT_DEFINED", name)); //$NON-NLS-1$
    }

    /**
     * Returns the array with the field names.
     *
     * @return the field names
     *
     * @see SensibleObject#fieldNames
     */
    public final String[] getFieldNames() {

        return Arrays.copyOf(fieldNames, fieldNames.length);
    }

    /**
     * Returns the name of a field from the object definition.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field index is
     * not defined.
     *
     * @param index the field index
     *
     * @return the name the field
     *
     * @see SensibleObject#fieldNames
     */
    public final String getFieldName(int index) {

        try {
            return fieldNames[index];
        } catch (ArrayIndexOutOfBoundsException aobe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_FIELD_NOT_DEFINED", Integer.toString(index)), aobe); //$NON-NLS-1$
        }
    }

    /**
     * Returns a hash code value for the object.<br>
     *
     * Actually the method returns the hash code from the object's interned string representation.
     *
     * @return a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     * @see java.lang.String#hashCode()
     * @see java.lang.String#intern()
     * @see SensibleObject#toString()
     */
    public int hashCode() {

        return toString().intern().hashCode();
    }

    /**
     * Returns <code>true</code> if all object properties are clear.
     *
     * @return whether all object properties are clear
     *
     * @see SensibleDataType#isClear()
     */
    public final boolean isClear() {

        for (int i = 0, n = fields.length; i < n; i++) {
            if (!fields[i].isClear()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if all object key properties are clear.
     *
     * @return whether all object key properties are clear
     *
     * @see SensibleDataType#isClear()
     */
    public final boolean isClearKey() {

        for (int i = 0, n = fields.length; i < n; i++) {
            if (fields[i].isKey() && !fields[i].isClear()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if all object no-key properties are clear.
     *
     * @return whether all object no-key properties are clear
     *
     * @see SensibleDataType#isClear()
     */
    public final boolean isClearNoKey() {

        for (int i = 0, n = fields.length; i < n; i++) {
            if (!fields[i].isKey() && !fields[i].isClear()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the <code>dataComplete</code> property value.
     *
     * @return the property value
     *
     * @see SensibleObject#dataComplete
     * @see SensibleObject#setDataComplete(boolean)
     */
    public final boolean isDataComplete() {

        return dataComplete;
    }

    /**
     * Property change event handler. It monitorizes changes in any of the object fields and checks
     * whether the object is complete invoking the <code>checkDataComplete()</code> method.
     * Derived classes can override this method to include additional field validations.
     *
     * @param event the event
     *
     * @see SensibleObject#checkDataComplete()
     */
    public void propertyChange(java.beans.PropertyChangeEvent event) {

        checkDataComplete();
    }

    /**
     * Removes the listeners of all the fields.
     */
    public final void removeListeners() {

        synchronized (fields) {
            for (int i = 0, n = fields.length; i < n; i++) {
                fields[i].removePropertyChangeListener(this);
            }
        }
    }

    /**
     * Returns a clone of this object.
     *
     * @return a clone of this object
     *
     * @see SensibleObject#clone()
     */
    public final SensibleObject returnCopy() {

        try {
            return (SensibleObject) this.clone();
        } catch (CloneNotSupportedException cnse) {
            return null;
        }
    }

    /**
     * Returns a new object of this class or <code>null</code> if an exception is thrown while
     * creating the new instance.
     *
     * @return a new instance of this object class or <code>null</code> if an exception is thrown
     */
    public final SensibleObject returnNew() {

        try {
            SensibleObject obj = getClass().newInstance();

            return obj;
        } catch (InstantiationException ie) {
            return null;
        } catch (IllegalAccessException iae) {
            return null;
        }
    }

    /**
     * Changes the <code>dataComplete</code> property value and fires the property change event.
     *
     * @param newValue the new value
     *
     * @see SensibleObject#dataComplete
     * @see SensibleObject#isDataComplete()
     */
    private void setDataComplete(boolean newValue) {

        boolean oldValue = dataComplete;
        dataComplete = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATA_COMPLETE, oldValue, newValue);
    }

    /**
     * Changes the value of a field given by index and fires the property change event in the
     * object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field index is
     * not defined.
     *
     * @param index the field index
     * @param newValue the new value
     */
    @SuppressWarnings("PMD.StringInstantiation")
    public final void setField(int index, String newValue) {

        SensibleDataType field = null;

        try {
            field = fields[index];
        } catch (ArrayIndexOutOfBoundsException aobe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_FIELD_NOT_DEFINED", Integer.toString(index)), aobe); //$NON-NLS-1$
        }

        // a copy of the current field is created so the
        // type parameters conserve their values
        SensibleDataType oldField = field.returnCopy();

        // the value of the field is changed and then
        // then property change event in the object is fired
        field.setValue(new String(newValue));
        firePropertyChange(fieldNames[index], oldField, field);
    }

    /**
     * Changes the value of a field given by index and fires the property change event in the
     * object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field index is
     * not defined or the given object type is not the same as the field type.
     *
     * @param index the field index
     * @param newValue the new value
     */
    public final void setField(int index, SensibleDataType newValue) {

        SensibleDataType field = null;

        try {
            field = fields[index];
        } catch (ArrayIndexOutOfBoundsException aobe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_FIELD_NOT_DEFINED", Integer.toString(index)), aobe); //$NON-NLS-1$
        }

        if (!field.getClass().equals(newValue.getClass())) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_INVALID_OBJECT")); //$NON-NLS-1$
        }

        // a copy of the current field is created so the
        // type parameters conserve their values
        SensibleDataType oldField = field.returnCopy();

        // the value of the field is changed and then
        // then property change event in the object is fired
        field.setValue(new String(newValue.getValue()));
        firePropertyChange(fieldNames[index], oldField, field);
    }

    /**
     * Changes the value of a field given by name and fires the property change event in the
     * object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field name is
     * not defined.
     *
     * @param name the field name
     * @param newValue the new value
     */
    public final void setField(String name, String newValue) {

        setField(getFieldIndex(name), newValue);
    }

    /**
     * Changes the value of a field given by name and fires the property change event in the
     * object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the field name is
     * not defined or the given object type is not the same as the field type.
     *
     * @param name the field name
     * @param newValue the new value
     */
    public final void setField(String name, SensibleDataType newValue) {

        setField(getFieldIndex(name), newValue);
    }

    /**
     * Changes the value of all the fields and fires the property change events in the object.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the given object
     * definition does not match this object definition.
     *
     * @param newValue the new value
     */
    public final void setValue(SensibleObject newValue) {

        int n = fields.length;

        if (n != newValue.fields.length) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage(
                    "DTYPE_ERR_INVALID_OBJECT")); //$NON-NLS-1$
        }

        for (int i = 0; i < n; i++) {
            setField(i, newValue.fields[i]);
        }
    }

    /**
     * Returns the value of all fields separated by two slashes.
     *
     * @return a string representation of this object
     */
    public final String toString() {

        StringBuilder buf = new StringBuilder();

        for (int i = 0, n = fields.length; i < n; i++) {
            buf.append(fields[i]);
            buf.append(SEPARATOR);
        }

        return buf.toString();
    }

    /**
     * Returns a string representation valid for sorting data. Actually it returns the object key
     * field values separated by two slashes.
     *
     * @return a string representation valid for sorting data
     */
    public final String toStringForSort() {

        StringBuilder buf = new StringBuilder();

        for (int i = 0, n = fields.length; i < n; i++) {
            if (fields[i].isKey()) {
                buf.append(fields[i].toStringForSort());
                buf.append(SEPARATOR);
            }
        }

        return buf.toString();
    }

    /**
     * Property change support property.
     */
    protected transient java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    /**
     * Adds a property change listener.
     *
     * @param listener the listener
     */
    public final void addPropertyChangeListener(
        java.beans.PropertyChangeListener listener) {

        synchronized (propertyChangeSupport) {
            getPropertyChange().addPropertyChangeListener(listener);
        }

        // notify the listener the current object state
        // unless the method is invoked from the default constructor
        if (fields != null) {
            firePropertyChange(JAVA_BEAN_PROPERTY_DATA_COMPLETE, null, dataComplete);
        }
    }

    /**
     * Adds a property change listener linked to the given property.
     *
     * @param propertyName the property name
     * @param listener the listener
     */
    public final void addPropertyChangeListener(String propertyName,
        java.beans.PropertyChangeListener listener) {

        synchronized (propertyChangeSupport) {
            getPropertyChange().addPropertyChangeListener(propertyName, listener);
        }

        // notify the listener the current object state
        // unless the method is invoked from the default constructor
        if (fields != null) {
            firePropertyChange(JAVA_BEAN_PROPERTY_DATA_COMPLETE, null, dataComplete);
        }
    }

    /**
     * Fires a property change event.
     *
     * @param event the property change event
     */
    public final void firePropertyChange(java.beans.PropertyChangeEvent event) {

        getPropertyChange().firePropertyChange(event);
    }

    /**
     * Fires a property change event linked to the given property.
     *
     * @param propertyName the property name
     * @param oldValue the property old value
     * @param newValue the property new value
     */
    public final void firePropertyChange(String propertyName, int oldValue, int newValue) {

        getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Fires a property change event linked to the given property.
     *
     * @param propertyName the property name
     * @param oldValue the property old value
     * @param newValue the property new value
     */
    public final void firePropertyChange(String propertyName, Object oldValue, Object newValue) {

        getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Fires a property change event linked to the given property.
     *
     * @param propertyName the property name
     * @param oldValue the property old value
     * @param newValue the property new value
     */
    public final void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {

        getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Returns the <code>propertyChange</code> property value.
     *
     * @return the property value
     */
    protected final java.beans.PropertyChangeSupport getPropertyChange() {

        return propertyChangeSupport;
    }

    /**
     * Returns whether the given property has a listener.
     *
     * @param propertyName the property name
     *
     * @return whether the given property has a listener
     */
    public final boolean hasListeners(String propertyName) {

        synchronized (propertyChangeSupport) {
            return getPropertyChange().hasListeners(propertyName);
        }
    }

    /**
     * Removes a property change listener.
     *
     * @param listener the listener
     */
    public final void removePropertyChangeListener(
        java.beans.PropertyChangeListener listener) {

        synchronized (propertyChangeSupport) {
            getPropertyChange().removePropertyChangeListener(listener);
        }
    }

    /**
     * Removes a property change listener linked to the given property.
     *
     * @param propertyName the property name
     * @param listener the listener
     */
    public final void removePropertyChangeListener(String propertyName,
        java.beans.PropertyChangeListener listener) {

        synchronized (propertyChangeSupport) {
            getPropertyChange().removePropertyChangeListener(propertyName, listener);
        }
    }
}
