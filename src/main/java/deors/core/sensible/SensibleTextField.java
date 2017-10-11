package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import deors.core.commons.StringToolkit;

/**
 * Definition for a data type dependent behavior text field.<br>
 *
 * The behavior of the field depends on the <code>SensibleDataType</code> object attached to the
 * <code>data</code> property.<br>
 *
 * The class uses a <code>PlainDocument</code> that calls the <code>insertString()</code> and
 * <code>remove()</code> methods from the <code>SensibleDataType</code> object. This allows to
 * customize the data validations for every data type.<br>
 *
 * The property <code>autoCompletionEntries</code> may contain a list of suggestions to be
 * auto-inserted as the user writes in the field. This list is checked only when the user writes a
 * single character at the end of the string.<br>
 *
 * If the property <code>passwordField</code> is set to <code>true</code>, the field acts as
 * a password field using the character <code>echoCharacter</code> to mask the user input.<br>
 *
 * @author deors
 * @version 1.0
 */
public class SensibleTextField
    extends javax.swing.JTextField
    implements java.beans.PropertyChangeListener {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -4083652945679588176L;

    /**
     * The <code>SensibleDataType</code> object that defines the field behaviour and is also used
     * to store the information.
     *
     * @see SensibleTextField#getData()
     * @see SensibleTextField#setData(SensibleDataType)
     */
    protected SensibleDataType data;

    /**
     * Vector that contains the list of auto-completion entries for the field.
     *
     * @see SensibleTextField#getAutoCompletionEntries()
     * @see SensibleTextField#setAutoCompletionEntries(List)
     */
    private List<String> autoCompletionEntries;

    /**
     * Whether the field is used as a password field.
     *
     * @see SensibleTextField#isPasswordField()
     * @see SensibleTextField#setPasswordField(boolean)
     */
    private boolean passwordField;

    /**
     * The echo character used in password fields.
     *
     * @see SensibleTextField#getEchoCharacter()
     * @see SensibleTextField#setEchoCharacter(char)
     */
    protected char echoCharacter = '*';

    /**
     * The "autoCompletionEntries" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_AUTO_COMPLETION_ENTRIES = "autoCompletionEntries"; //$NON-NLS-1$

    /**
     * The "data" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATA = "data"; //$NON-NLS-1$

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
     * Color used as background for required data values. Configurable in the properties file
     * <code>textField.requiredColor</code>. Default value is <code>0xFAF082</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    private static final String TEXTFIELD_REQUIRED_COLOR =
        getConfigurationProperty("textField.requiredColor", "0xFAF082"); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * Color used as background for required data values. Configurable in the properties file
     * using the key <code>textField.invalidColor</code> and <code>0xF29785</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    private static final String TEXTFIELD_INVALID_COLOR =
        getConfigurationProperty("textField.invalidColor", "0xF29785"); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * Definition for the document model.<br>
     *
     * It overrides <code>insertString()</code> and <code>remove()</code> methods allowing the
     * use of the <code>SensibleDataType</code> defined methods to change the field and the data
     * synchronously.<br>
     *
     * @author deors
     * @version 1.0
     */
    public class SensibleTextFieldDocument
        extends javax.swing.text.PlainDocument {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = -8472775342027782803L;

        /**
         * Default constructor.
         */
        public SensibleTextFieldDocument() {

            super();
        }

        /**
         * The insert method.
         *
         * @param offset the insert point
         * @param s the string to be inserted
         * @param a the attribute set
         *
         * @throws javax.swing.text.BadLocationException the insert point is not valid
         *
         * @see SensibleDataType#insertString(int, String)
         */
        public void insertString(int offset, String s, javax.swing.text.AttributeSet a)
            throws javax.swing.text.BadLocationException {

            if (data.valueChangingInSet) {
                if (isPasswordField()) {
                    String echos = StringToolkit.repeatCharacter(echoCharacter, s.length());
                    super.insertString(offset, echos, a);
                } else {
                    super.insertString(offset, s, a);
                }
            } else if (data.controlsDocument) {
                data.allowInsert(offset, s, SensibleTextField.this, this);
            } else if (data.allowInsert(offset, s)) {
                if (isPasswordField()) {
                    String echos = StringToolkit.repeatCharacter(echoCharacter, s.length());
                    super.insertString(offset, echos, a);
                } else {
                    super.insertString(offset, s, a);
                }

                data.insertString(offset, s);
            }

            // if the insertion point was at the end of the string
            // in the field, the auto-completion list is checked
            if (s.length() == 1 && offset == data.value.length() - 1) {
                checkAutoCompletion();
            }
        }

        /**
         * Calls the insert method in the parent class.
         *
         * @param offset the insert point
         * @param s the string to be inserted
         * @param a the attribute set
         *
         * @throws javax.swing.text.BadLocationException the insert point is not valid
         *
         * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String,
         *                                                  javax.swing.text.AttributeSet)
         */
        void insertStringFromParent(int offset, String s, javax.swing.text.AttributeSet a)
            throws javax.swing.text.BadLocationException {

            super.insertString(offset, s, a);
        }

        /**
         * The remove method.
         *
         * @param offset the remove point
         * @param length the length to be removed
         *
         * @throws javax.swing.text.BadLocationException the remove point is not valid
         *
         * @see SensibleDataType#remove(int, int)
         */
        public void remove(int offset, int length)
            throws javax.swing.text.BadLocationException {

            if (data.valueChangingInSet) {
                super.remove(offset, length);
            } else if (data.controlsDocument) {
                data.allowRemove(offset, length, SensibleTextField.this, this);
            } else if (data.allowRemove(offset, length)) {
                super.remove(offset, length);
                data.remove(offset, length);
            }
        }

        /**
         * Calls the remove method in the parent class.
         *
         * @param offset the remove point
         * @param length the length to be removed
         *
         * @throws javax.swing.text.BadLocationException the remove point is not valid
         *
         * @see javax.swing.text.AbstractDocument#remove(int, int)
         */
        void removeFromParent(int offset, int length)
            throws javax.swing.text.BadLocationException {

            super.remove(offset, length);
        }

        /**
         * Used to repaint the field if we change the <code>data</code> property.
         *
         * @param s the text to be repainted
         *
         * @throws javax.swing.text.BadLocationException error occurred while repainting text
         */
        public void repaint(String s)
            throws javax.swing.text.BadLocationException {

            super.remove(0, getLength());
            super.insertString(0, s, null);
        }
    }

    /**
     * Default constructor. The constructor initializes the property change listener and the
     * document model.
     */
    public SensibleTextField() {

        super();

        addPropertyChangeListener(this);
        setDocument(new SensibleTextFieldDocument());
    }

    /**
     * Constructor that sets the data type.
     *
     * @param data the data type
     */
    public SensibleTextField(SensibleDataType data) {

        this();

        setData(data);
    }

    /**
     * Checks the auto-completion list and writes the suggestion to the field.
     */
    protected void checkAutoCompletion() {

        if (autoCompletionEntries == null || autoCompletionEntries.isEmpty()) {
            return;
        }

        for (String entry : autoCompletionEntries) {
            if (entry.startsWith(getText())) {
                int selStart = getText().length();
                int selEnd = entry.length();
                setText(entry);
                setCaretPosition(selEnd);
                moveCaretPosition(selStart);
            }
        }
    }

    /**
     * Checks the <code>readOnly</code> property to see whether the data showed in the field can
     * be edited.
     */
    private void checkEditable() {

        setEditable(!data.readOnly);
    }

    /**
     * Sets the field background and foreground colors as defined by the required
     * and valid properties values.
     */
    private void checkColors() {

        if (data.valid) {
            setForeground(java.awt.Color.black);
            setBackground(java.awt.Color.white);
        } else {
            setForeground(java.awt.Color.red);
            if (data.required) {
                setBackground(Color.decode(TEXTFIELD_REQUIRED_COLOR));
            } else {
                setBackground(Color.decode(TEXTFIELD_INVALID_COLOR));
            }
        }
    }

    /**
     * Checks the <code>required</code> property and changes the field colors if needed.
     */
    private void checkRequired() {

        checkColors();
    }

    /**
     * Checks the <code>valid</code> property and changes the field colors if needed.
     */
    private void checkValid() {

        checkColors();
    }

    /**
     * Returns the <code>autoCompletionEntries</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTextField#autoCompletionEntries
     * @see SensibleTextField#setAutoCompletionEntries(List)
     */
    public List<String> getAutoCompletionEntries() {

        return Collections.unmodifiableList(autoCompletionEntries);
    }

    /**
     * Returns the <code>data</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTextField#data
     * @see SensibleTextField#setData(SensibleDataType)
     */
    public SensibleDataType getData() {

        return data;
    }

    /**
     * Returns the <code>echoCharacter</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTextField#echoCharacter
     * @see SensibleTextField#setEchoCharacter(char)
     */
    public char getEchoCharacter() {

        return echoCharacter;
    }

    /**
     * Returns the <code>passwordField</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTextField#passwordField
     * @see SensibleTextField#setPasswordField(boolean)
     */
    public boolean isPasswordField() {

        return passwordField;
    }

    /**
     * Property change event handler. It monitorizes changes in the <code>data</code> property to
     * check for insertion and/or remove actions, changes on its <code>readOnly</code>,
     * <code>required</code> and <code>valid</code> properties and also changes on the whole
     * <code>data</code> object.
     *
     * @param event the event
     *
     * @see SensibleTextField#checkValid()
     * @see SensibleTextField#checkRequired()
     * @see SensibleTextField#checkEditable()
     */
    public void propertyChange(java.beans.PropertyChangeEvent event) {

        if (event.getSource() == this && event.getPropertyName().equals(JAVA_BEAN_PROPERTY_DATA)) {
            try {
                ((SensibleTextFieldDocument) getDocument()).repaint(data.value);
            } catch (javax.swing.text.BadLocationException e) {
                e = null;
            }

            checkValid();
            checkRequired();
            checkEditable();
        } else if (event.getSource() == getData()) {
            if (event.getPropertyName().equals(JAVA_BEAN_PROPERTY_VALUE)) {
                if (data.valueChangingInSet) {
                    try {
                        javax.swing.text.Document doc = getDocument();
                        doc.remove(0, doc.getLength());
                        doc.insertString(0, data.value, null);
                    } catch (javax.swing.text.BadLocationException e) {
                        e = null;
                    }
                }

                checkValid();
                checkRequired();
            } else if (event.getPropertyName().equals(JAVA_BEAN_PROPERTY_VALID)) {
                checkValid();
                checkRequired();
            } else if (event.getPropertyName().equals(JAVA_BEAN_PROPERTY_REQUIRED)) {
                checkValid();
                checkRequired();
            } else if (event.getPropertyName().equals(JAVA_BEAN_PROPERTY_READ_ONLY)) {
                checkEditable();
            }
        }
    }

    /**
     * Changes the <code>autoCompletionEntries</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTextField#autoCompletionEntries
     * @see SensibleTextField#getAutoCompletionEntries()
     */
    public void setAutoCompletionEntries(List<String> newValue) {

        List<String> oldValue = autoCompletionEntries;

        autoCompletionEntries = new ArrayList<>(newValue);

        firePropertyChange(JAVA_BEAN_PROPERTY_AUTO_COMPLETION_ENTRIES, oldValue, newValue);
    }

    /**
     * Changes the <code>data</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTextField#data
     * @see SensibleTextField#getData()
     */
    public final void setData(SensibleDataType newValue) {

        SensibleDataType oldValue = data;

        data = newValue;
        data.addPropertyChangeListener(this);

        firePropertyChange(JAVA_BEAN_PROPERTY_DATA, oldValue, newValue);

        newValue.firePropertyChangeEvents();
    }

    /**
     * Changes the <code>echoCharacter</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTextField#echoCharacter
     * @see SensibleTextField#getEchoCharacter()
     */
    public void setEchoCharacter(char newValue) {

        echoCharacter = newValue;
    }

    /**
     * Changes the <code>editable</code> property value. If the field data is read only, the field
     * can not be editable.
     *
     * @param newValue the property new value
     */
    public void setEditable(boolean newValue) {

        if (data != null && data.isReadOnly()) {
            super.setEditable(false);
        } else {
            super.setEditable(newValue);
        }
    }

    /**
     * Changes the <code>passwordField</code> property value.
     *
     * @param newValue the property new value
     *
     * @see SensibleTextField#passwordField
     * @see SensibleTextField#isPasswordField()
     */
    public void setPasswordField(boolean newValue) {

        passwordField = newValue;
    }

    /**
     * Changes the text showed in the field by changing the data value.
     *
     * @param newText the new text
     */
    public void setText(String newText) {

        data.setValue(newText);
    }
}
