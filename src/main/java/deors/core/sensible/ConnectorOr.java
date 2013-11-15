package deors.core.sensible;

/**
 * Connector using the OR logic operator.<br>
 *
 * The connector has two boolean bounded input properties and one boolean bounded output property
 * which value will be the result of evaluating both input values with the OR logic operator.<br>
 *
 * @author deors
 * @version 1.0
 */
public class ConnectorOr
    extends BasicPropertyChangeSupport {

    /**
     * Input 1 property.
     */
    private boolean in1;

    /**
     * Input 2 property.
     */
    private boolean in2;

    /**
     * Output property.
     */
    private boolean out;

    /**
     * The "in1" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_IN1 = "in1"; //$NON-NLS-1$

    /**
     * The "in2" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_IN2 = "in2"; //$NON-NLS-1$

    /**
     * The "out" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_OUT = "out"; //$NON-NLS-1$

    /**
     * Default constructor.
     */
    public ConnectorOr() {

        super();
    }

    /**
     * Returns the <code>in1</code> property value.
     *
     * @return the property value
     */
    public boolean isIn1() {

        return in1;
    }

    /**
     * Returns the <code>in2</code> property value.
     *
     * @return the property value
     */
    public boolean isIn2() {

        return in2;
    }

    /**
     * Returns the <code>out</code> property value.
     *
     * @return the property value
     */
    public boolean isOut() {

        return out;
    }

    /**
     * Changes the <code>in1</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     */
    public void setIn1(boolean newValue) {

        boolean oldValue = in1;
        in1 = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_IN1, oldValue, newValue);

        setOut(in1 || in2);
    }

    /**
     * Changes the <code>in2</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     */
    public void setIn2(boolean newValue) {

        boolean oldValue = in2;
        in2 = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_IN2, oldValue, newValue);

        setOut(in1 || in2);
    }

    /**
     * Changes the <code>out</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     */
    private void setOut(boolean newValue) {

        boolean oldValue = out;
        out = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_OUT, oldValue, newValue);
    }
}
