package deors.core.sensible;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Implements common property change support methods.
 *
 * @author deors
 * @version 1.0
 */
public class BasicPropertyChangeSupport {

    /**
     * Property change support property.
     */
    protected transient java.beans.PropertyChangeSupport propertyChange = new java.beans.PropertyChangeSupport(this);

    /**
     * Default constructor. This class is not designed to be
     * instantiated, but to add basic property change support
     * to JavaBeans.
     */
    protected BasicPropertyChangeSupport() {

        super();
    }

    /**
     * Adds a property change listener.
     *
     * @param listener the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        synchronized (propertyChange) {
            getPropertyChange().addPropertyChangeListener(listener);
        }
    }

    /**
     * Adds a property change listener linked to the given property.
     *
     * @param propertyName the property name
     * @param listener the listener
     */
    public void addPropertyChangeListener(String propertyName,
                                          PropertyChangeListener listener) {
        synchronized (propertyChange) {
            getPropertyChange().addPropertyChangeListener(propertyName, listener);
        }
    }

    /**
     * Fires a property change event.
     *
     * @param event the property change event
     */
    public void firePropertyChange(PropertyChangeEvent event) {

        getPropertyChange().firePropertyChange(event);
    }

    /**
     * Fires a property change event linked to the given property.
     *
     * @param propertyName the property name
     * @param oldValue the property old value
     * @param newValue the property new value
     */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {

        getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Fires a property change event linked to the given property.
     *
     * @param propertyName the property name
     * @param oldValue the property old value
     * @param newValue the property new value
     */
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {

        getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Fires a property change event linked to the given property.
     *
     * @param propertyName the property name
     * @param oldValue the property old value
     * @param newValue the property new value
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {

        getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Returns the <code>propertyChange</code> property value.
     *
     * @return the property value
     */
    protected PropertyChangeSupport getPropertyChange() {

        return propertyChange;
    }

    /**
     * Returns whether the given property has a listener.
     *
     * @param propertyName the property name
     *
     * @return whether the given property has a listener
     */
    public boolean hasListeners(String propertyName) {

        synchronized (propertyChange) {
            return getPropertyChange().hasListeners(propertyName);
        }
    }

    /**
     * Removes a property change listener.
     *
     * @param listener the listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {

        synchronized (propertyChange) {
            getPropertyChange().removePropertyChangeListener(listener);
        }
    }

    /**
     * Removes a property change listener linked to the given property.
     *
     * @param propertyName the property name
     * @param listener the listener
     */
    public void removePropertyChangeListener(String propertyName,
                                                          PropertyChangeListener listener) {
        synchronized (propertyChange) {
            getPropertyChange().removePropertyChangeListener(propertyName, listener);
        }
    }
}
