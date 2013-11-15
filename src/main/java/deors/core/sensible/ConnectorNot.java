package deors.core.sensible;

/**
 * Connector using the NOT logic operator.<br>
 *
 * The connector has one boolean bounded input property and one boolean bounded output property
 * which value will be the result of evaluating the input value with the NOT logic operator.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class ConnectorNot
    extends BasicPropertyChangeSupport {

    /**
     * Input property.
     */
    private boolean in;

    /**
     * Output property.
     */
    private boolean out = true;

    /**
     * The "in" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_IN = "in"; //$NON-NLS-1$

    /**
     * The "out" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_OUT = "out"; //$NON-NLS-1$

    /**
     * Default constructor.
     */
    public ConnectorNot() {

        super();
    }

    /**
     * Returns the <code>in</code> property value.
     *
     * @return the property value
     */
    public boolean isIn() {

        return in;
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
     * Changes the <code>in</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     */
    public void setIn(boolean newValue) {

        boolean oldValue = in;
        in = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_IN, oldValue, newValue);

        setOut(!in);
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
