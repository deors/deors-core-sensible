/**
 * Sensible extension to Swing. Sensible extends UI controls with data type specific
 * behavior: if a Sensible text field implementation is bound to a Date data type,
 * then the text field only allows valid Date values; if it is bound to a String data
 * type instead, all characters are allowed up to a defined maximum length; and
 * analogously to other defined data types. A data type is any subclass of
 * <code>SensibleDataType</code>. Data types can be combined in instances of
 * <code>SensibleObject</code>. Each data type can be key or not, required or not,
 * and the object will react to changes on values firing an event when the object is
 * "complete": all required properties are informed and valid, and all non required
 * properties are either empty or valid.
 */
package deors.core.sensible;
