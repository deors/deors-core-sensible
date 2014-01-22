package deors.core.sensible;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

import javax.swing.AbstractSpinnerModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Definition for a datatype-dependent behaviour spinner text field.<br>
 *
 * The behaviour of the field depends on the <code>SensibleDataType</code> object attached to the
 * <code>data</code> property. Only <code>SensibleBigDecimal</code>, <code>SensibleInteger</code>,
 * <code>SensibleLong</code> and <code>SensibleString</code> objects are allowed.<br>
 *
 * The class uses a <code>SensibleTextField</code> as the input component.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleSpinner
    extends JSpinner {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 6903749400963248665L;

    /**
     * The <code>SensibleDataType</code> object that defines the field behaviour and is also used
     * to store the information.
     *
     * @see SensibleSpinner#getData()
     * @see SensibleSpinner#setData(SensibleDataType)
     */
    private SensibleDataType data;

    /**
     * The editor used to control de spinner.
     */
    private SensibleSpinnerEditor editor;

    /**
     * The model used to manage the spinner value.
     */
    private SensibleSpinnerModel model;

    /**
     * The "data" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATA = "data"; //$NON-NLS-1$

    /**
     * Definition for the spinner editor.<br>
     *
     * The spinner uses a <code>SensibleTextField</code> component as the input component and
     * synchronizes changes in model back to the <code>SensibleDataType</code> object used to
     * store the information.<br>
     *
     * @author deors
     * @version 2.1
     */
    private class SensibleSpinnerEditor
        extends JPanel
        implements ChangeListener, LayoutManager {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = 126186748859114368L;

        /**
         * The text field component used to input text.
         */
        private SensibleTextField textField;

        /**
         * Default constructor.
         */
        public SensibleSpinnerEditor() {

            super(null);

            initLayout();
        }

        /**
         * Initializes the spinner layout.
         */
        private void initLayout() {

            setLayout(this);

            textField = new SensibleTextField(getData());
            textField.setEditable(true);
            textField.setInheritsPopupMenu(true);
            textField.setHorizontalAlignment(JTextField.RIGHT);

            // avoids confusion in what tool tip text we are using
            String toolTipText = SensibleSpinner.this.getToolTipText();
            if (toolTipText != null) {
                textField.setToolTipText(toolTipText);
            }

            add(textField);

            addChangeListener(this);
        }

        /**
         * New components are not allowed in the panel where the spinner is located, thus the method
         * does nothing.
         *
         * @param name the name of the component to be added
         * @param component the component to be added
         */
        public void addLayoutComponent(String name, Component component) {
            // new components are not allowed in the panel
            // thus the method does nothing
        }

        /**
         * Returns the spinner text field baseline.
         *
         * @param width the spinner text field width
         * @param height the spinner text field height
         *
         * @return the spinner text field baseline
         */
        public int getBaseline(int width, int height) {

            super.getBaseline(width, height);
            Insets insets = getInsets();
            int baseline = textField.getBaseline(
                width - insets.left - insets.right,
                height - insets.top - insets.bottom);
            if (baseline >= 0) {
                return baseline + insets.top;
            }
            return -1;
        }

        /**
         * Returns the spinner text field baseline resize behaviour.
         *
         * @return the spinner text field baseline resize behaviour
         */
        public BaselineResizeBehavior getBaselineResizeBehavior() {

            return textField.getBaselineResizeBehavior();
        }

        /**
         * Returns the spinner text field inset size.
         *
         * @param parent the spinner text field parent container
         *
         * @return the spinner text field inset size
         */
        private Dimension insetSize(Container parent) {

            Insets insets = parent.getInsets();
            int width = insets.left + insets.right;
            int height = insets.top + insets.bottom;
            return new Dimension(width, height);
        }

        /**
         * Sets the spinner text field bounds.
         *
         * @param parent the spinner text field parent container
         */
        public void layoutContainer(Container parent) {

            Insets insets = parent.getInsets();
            int width = parent.getWidth() - (insets.left + insets.right);
            int height = parent.getHeight() - (insets.top + insets.bottom);
            textField.setBounds(insets.left, insets.top, width, height);
        }

        /**
         * Returns the spinner text field minimum layout size.
         *
         * @param parent the spinner text field parent container
         *
         * @return the spinner text field minimum layer size
         */
        public Dimension minimumLayoutSize(Container parent) {

            Dimension minimumSize = insetSize(parent);
            Dimension childSize = textField.getMinimumSize();
            minimumSize.width += childSize.width;
            minimumSize.height += childSize.height;
            return minimumSize;
        }

        /**
         * Returns the spinner text field preferred layout size.
         *
         * @param parent the spinner text field parent container
         *
         * @return the spinner text field preferred layer size
         */
        public Dimension preferredLayoutSize(Container parent) {

            Dimension preferredSize = insetSize(parent);
            Dimension childSize = textField.getPreferredSize();
            preferredSize.width += childSize.width;
            preferredSize.height += childSize.height;
            return preferredSize;
        }

        /**
         * The text field component can not be removed, thus the method
         * does nothing.
         *
         * @param component the component to be removed
         */
        public void removeLayoutComponent(Component component) {
            // the text field component can not be removed
            // thus the method does nothing
        }

        /**
         * Method invoked when the spinner model change. The method changes spinner data value.
         *
         * @param event the change event
         */
        @SuppressWarnings("PMD.CompareObjectsWithEquals")
        public void stateChanged(ChangeEvent event) {

            Object source = event.getSource();
            if (source == SensibleSpinner.this) {
                getData().setValue((String) getModel().getValue());
            }
        }
    }

    /**
     * Definition for the spinner model.<br>
     *
     * The mode value is the same <code>SensibleDataType</code> object attached to the spinner
     * <code>data</code> property. Only <code>SensibleBigDecimal</code>, <code>SensibleInteger</code>,
     * <code>SensibleLong</code> and <code>SensibleString</code> objects are allowed.<br>
     *
     * @author deors
     * @version 1.0
     */
    private static class SensibleSpinnerModel
        extends AbstractSpinnerModel
        implements Serializable {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = 4442753122487950407L;

        /**
         * The <code>SensibleDataType</code> object used to store the information.
         */
        private final SensibleDataType data;

        /**
         * Constructor that sets the spinner data.<br>
         *
         * An <code>IllegalArgumentException</code> exception is thrown if the spinner
         * data is not an instance of <code>SensibleBigDecimal</code>, <code>SensibleInteger</code>,
         * <code>SensibleLong</code> or <code>SensibleString</code> classes.
         *
         * @param data the spinner data
         */
        public SensibleSpinnerModel(SensibleDataType data) {

            super();

            if (!(data instanceof SensibleInteger)
                && !(data instanceof SensibleLong)
                && !(data instanceof SensibleBigDecimal)
                && !(data instanceof SensibleString)) {
                throw new IllegalArgumentException(
                    SensibleContext.getMessage("SPIN_ERR_INVALID_DATA_TYPE")); //$NON-NLS-1$
            }

            this.data = data;
            fireStateChanged();
        }

        /**
         * Returns the model next value adding 1 to the current value.
         *
         * @return the model next value
         *
         * @see SensibleDataType#add(int)
         */
        public Object getNextValue() {

            SensibleDataType newValue = data.add(1);
            return newValue.getValue();
        }

        /**
         * Returns the model previous value subtracting 1 to the current value.
         *
         * @return the model previous value
         *
         * @see SensibleDataType#subtract(int)
         */
        public Object getPreviousValue() {

            SensibleDataType newValue = data.subtract(1);
            return newValue.getValue();
        }

        /**
         * Returns the model current value.
         *
         * @return the model current value
         */
        public Object getValue() {

            return data.getValue();
        }

        /**
         * Changes the model current value.
         *
         * @param newValue the model new value
         */
        public void setValue(Object newValue) {

            if (newValue instanceof String
                && !newValue.equals(data.getValue())) {

                data.setValue((String) newValue);
                fireStateChanged();
            }
        }
    }

    /**
     * Default constructor. The constructor does nothing, delegating initialization in the
     * <code>data</code> property setter.
     */
    @SuppressWarnings("PMD.CallSuperInConstructor")
    public SensibleSpinner() {
        // we can not call constructor in JSpinner because
        // we override the text field with the Sensible component
    }

    /**
     * Constructor that sets the data bean.
     *
     * @param data the data bean
     *
     * @see SensibleSpinner#data
     * @see SensibleSpinner#setData(SensibleDataType)
     */
    public SensibleSpinner(SensibleDataType data) {

        this();
        setData(data);
    }

    /**
     * Returns the <code>data</code> property value.
     *
     * @return the property value
     *
     * @see SensibleSpinner#data
     * @see SensibleSpinner#setData(SensibleDataType)
     */
    public SensibleDataType getData() {

        return data;
    }

    /**
     * Changes the <code>data</code> property value and fires the property change event.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the spinner
     * data is not an instance of <code>SensibleBigDecimal</code>, <code>SensibleInteger</code>,
     * <code>SensibleLong</code> or <code>SensibleString</code> classes.
     *
     * @param newValue the property new value
     *
     * @see SensibleSpinner#data
     * @see SensibleSpinner#getData()
     */
    public void setData(SensibleDataType newValue) {

        if (!(newValue instanceof SensibleInteger)
            && !(newValue instanceof SensibleLong)
            && !(newValue instanceof SensibleBigDecimal)
            && !(newValue instanceof SensibleString)) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_INVALID_DATA_TYPE")); //$NON-NLS-1$
        }

        SensibleDataType oldValue = data;
        data = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_DATA, oldValue, newValue);

        setEditor(new SensibleSpinnerEditor());
        setModel(new SensibleSpinnerModel(data));
    }

    /**
     * Changes the editor used by this spinner instance.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the data bean
     * is not set, the editor has already been set or the given editor is not a
     * <code>SensibleSpinnerEditor</code> object.
     *
     * @param newEditor the new spinner editor
     */
    public void setEditor(JComponent newEditor) {

        if (data == null) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_CANNOT_SET_EDITOR_DATA_NULL")); //$NON-NLS-1$
        }

        if (editor != null) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_CANNOT_SET_EDITOR_AGAIN")); //$NON-NLS-1$
        }

        if (!(newEditor instanceof SensibleSpinnerEditor)) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_INVALID_SPINNER_EDITOR")); //$NON-NLS-1$
        }

        editor = (SensibleSpinnerEditor) newEditor;
        super.setEditor(editor);
    }

    /**
     * Changes the model used by this spinner instance.<br>
     *
     * An <code>IllegalArgumentException</code> exception is thrown if the data bean
     * is not setted, the model has already been setted or the given model is not a
     * <code>SensibleSpinnerModel</code> object.
     *
     * @param newModel the new spinner model
     */
    public void setModel(SpinnerModel newModel) {

        if (data == null) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_CANNOT_SET_MODEL_DATA_NULL")); //$NON-NLS-1$
        }

        if (model != null) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_CANNOT_SET_MODEL_AGAIN")); //$NON-NLS-1$
        }

        if (!(newModel instanceof SensibleSpinnerModel)) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("SPIN_ERR_INVALID_SPINNER_MODEL")); //$NON-NLS-1$
        }

        model = (SensibleSpinnerModel) newModel;
        super.setModel(model);
    }
}
