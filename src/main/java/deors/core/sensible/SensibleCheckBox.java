package deors.core.sensible;

import java.beans.PropertyChangeEvent;

import javax.swing.JCheckBox;

/**
 * Definition for a check box control that works with a <code>SensibleBoolean</code> object.<br>
 *
 * The check box will never be enabled if the attached boolean value is in read only mode.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleCheckBox
    extends JCheckBox
    implements java.beans.PropertyChangeListener, java.awt.event.ActionListener {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -9064008610263921727L;

    /**
     * The <code>SensibleBoolean</code> object that contains the check box selection status.
     *
     * @see SensibleCheckBox#getData()
     * @see SensibleCheckBox#setData(SensibleBoolean)
     */
    private SensibleBoolean data;

    /**
     * The "data" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATA = "data"; //$NON-NLS-1$

    /**
     * The "readOnly" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_READ_ONLY = "readOnly"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * Default constructor. The constructor initializes the property change listener and the action
     * listener.
     */
    public SensibleCheckBox() {

        super();

        addPropertyChangeListener(this);
        addActionListener(this);
    }

    /**
     * Constructor that sets the <code>SensibleBoolean</code> object that contains the check box
     * selection status.
     *
     * @param data the <code>SensibleBoolean</code> object that contains the selection status
     */
    public SensibleCheckBox(SensibleBoolean data) {

        this();

        setData(data);
    }

    /**
     * Process an <code>actionPerformed</code> event, changing the <code>data</code> property
     * value to the actual selection status.
     *
     * @param event the event
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {

        data.setFlag(isSelected());
    }

    /**
     * Checks the component <code>enable</code> property and the <code>readOnly</code> property
     * from the <code>data</code> property to see whether the user can edit the data showed in the
     * field.
     */
    private void checkEnabled() {

        setEnabled(!data.readOnly);
    }

    /**
     * Returns the <code>data</code> property value.
     *
     * @return the property value
     *
     * @see SensibleCheckBox#data
     * @see SensibleCheckBox#setData(SensibleBoolean)
     */
    public SensibleBoolean getData() {

        return data;
    }

    /**
     * Property change event handler. It checks the change of the <code>data</code> property and
     * changes in its value.
     *
     * @param event the event
     */
    public void propertyChange(PropertyChangeEvent event) {

        if (event.getSource() == this && event.getPropertyName().equals(JAVA_BEAN_PROPERTY_DATA)) {
            setSelected(data.booleanValue());
            checkEnabled();
        } else if (event.getSource() == getData()) {
            if (event.getPropertyName().equals(JAVA_BEAN_PROPERTY_VALUE)) {
                setSelected(data.booleanValue());
            } else if (event.getPropertyName().equals(JAVA_BEAN_PROPERTY_READ_ONLY)) {
                checkEnabled();
            }
        }
    }

    /**
     * Changes the <code>data</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleCheckBox#data
     * @see SensibleCheckBox#getData()
     */
    public void setData(SensibleBoolean newValue) {

        SensibleBoolean oldValue = data;

        if (data != null) {
            data.removePropertyChangeListener(this);
        }

        data = newValue;
        data.addPropertyChangeListener(this);

        firePropertyChange(JAVA_BEAN_PROPERTY_DATA, oldValue, newValue);

        newValue.firePropertyChangeEvents();
    }

    /**
     * Changes the <code>enabled</code> property value. If the field data is read only, the field
     * can not be enabled.
     *
     * @param newValue the property new value
     */
    public void setEnabled(boolean newValue) {

        if (data != null && data.isReadOnly()) {
            super.setEnabled(false);
        } else {
            super.setEnabled(newValue);
        }
    }
}
