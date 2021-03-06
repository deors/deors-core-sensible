package deors.core.sensible;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import deors.core.commons.inifile.INIFileManager;

/**
 * Definition for a combo box control with values loaded from a history file.<br>
 *
 * The history file is a configuration file managed using an instance of the
 * <code>INIFileManager</code> class. The format of the keys in this file is as follows:
 *
 * <ol>
 * <li>First, a <code>maxEntries</code> key with the maximum number of entries to be remembered
 * in the history file.</li>
 * <li>Next, a list of entries with the names <code>0</code>, <code>1</code>, ...,
 * <code><i>maxEntries - 1</i></code>. The corresponding values are the history entries that
 * will be loaded in the combo box list. The combo is loaded when the property
 * <code>historyFileName</code> changes or when the <code>updateHistory()</code> method is
 * called.</li>
 * </ol>
 *
 * If the history file does not exist it is created with an empty history list and using the
 * <code>DEFAULT_MAX_ENTRIES</code> property for the value of the <code>maxEntries</code> key.<br>
 *
 * If the history file name is not defined, the combo has no history and items can be added as a
 * normal <code>JComboBox</code> control.<br>
 *
 * The history entries are used as the auto-completion list for the <code>SensibleTextField</code>
 * object used as the combo editor.<br>
 *
 * The control has two history modes:
 *
 * <ol>
 * <li>The default one, <code>FULL_HISTORY</code>, updates the history file with the user
 * selections and positions first in the history the last selected entry. If the control is
 * not editable, then it is not possible to add new entries, but the existing ones will be
 * reordered.</li>
 * <li>The second mode, <code>REMEMBER_AND_SELECT</code>, uses the <code>lastSelected</code>
 * key in the history file to remember what was the last selection made by the user. When the
 * combo is loaded this selection is maintained. In this mode the combo is always not
 * editable.</li>
 * </ol>
 *
 * @param <T> the <code>SensibleDataType</code> type that will be held in the combo box
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleComboBox<T extends SensibleDataType>
    extends javax.swing.JComboBox<T>
    implements java.beans.PropertyChangeListener {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -4534598093937060854L;

    /**
     * The <code>SensibleDataType</code> object that defines the editor and is also used to store
     * the selection.
     *
     * @see SensibleComboBox#getData()
     * @see SensibleComboBox#setData(SensibleDataType)
     */
    private T data;

    /**
     * The name of the file that contains the history entries.
     *
     * @see SensibleComboBox#getHistoryFileName()
     * @see SensibleComboBox#setHistoryFileName(String)
     */
    private String historyFileName;

    /**
     * The operational history mode for the combo.
     *
     * @see SensibleComboBox#getHistoryMode()
     * @see SensibleComboBox#setHistoryMode(int)
     */
    private int historyMode = FULL_HISTORY;

    /**
     * The entry list contained in the history file.
     */
    private List<String> historyEntries;

    /**
     * The default history mode for the combo.
     */
    public static final int FULL_HISTORY = 0;

    /**
     * This mode remembers the last selected item.
     */
    public static final int REMEMBER_AND_SELECT = 1;

    /**
     * The default maximum number of entries.
     */
    private static final int DEFAULT_MAX_ENTRIES = 5;

    /**
     * Key name for the maximum number of entries property.
     */
    private static final String KEY_MAX_ENTRIES = "maxEntries"; //$NON-NLS-1$

    /**
     * Key name for the last selected entry property.
     */
    private static final String KEY_LAST_SELECTED = "lastSelected"; //$NON-NLS-1$

    /**
     * The "data" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_DATA = "data"; //$NON-NLS-1$

    /**
     * The "historyFileName" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_HISTORY_FILE_NAME = "historyFileName"; //$NON-NLS-1$

    /**
     * The "historyMode" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_HISTORY_MODE = "historyMode"; //$NON-NLS-1$

    /**
     * Definition for the combo box editor model.<br>
     *
     * @author deors
     * @version 1.0
     */
    public class SensibleComboBoxEditor
        extends javax.swing.plaf.basic.BasicComboBoxEditor {

        /**
         * Default constructor.
         */
        public SensibleComboBoxEditor() {

            super();
            editor = new SensibleTextField(data);
        }

        /**
         * Returns the edited item. Actually it returns the <code>data</code> property value.
         *
         * @return the edited item
         */
        public Object getItem() {

            return data;
        }

        /**
         * Sets the item that should be edited. The object type should be the parameterized
         * type <code>T</code>, otherwise, it is ignored and the method does nothing.
         *
         * @param o the object to be edited
         */
        public void setItem(Object o) {

            if (o == null) {
                data.clear();
            } else if (o instanceof SensibleDataType) {
                data.setValue(((SensibleDataType) o).getValue());
            }
        }
    }

    /**
     * Default constructor. The constructor initializes the property change listener.
     */
    public SensibleComboBox() {

        super();

        addPropertyChangeListener(this);
    }

    /**
     * Constructor that sets the history mode.
     *
     * @param mode the history mode
     */
    public SensibleComboBox(int mode) {

        this();

        setHistoryMode(mode);
    }

    /**
     * Constructor that sets the history mode and data type.
     *
     * @param mode the history mode
     * @param data the data type
     */
    public SensibleComboBox(int mode, T data) {

        this();

        setHistoryMode(mode);
        setData(data);
    }

    /**
     * Constructor that sets the history mode, file name and data type.
     *
     * @param mode the history mode
     * @param data the data type
     * @param fileName the history file name
     */
    public SensibleComboBox(int mode, T data, String fileName) {

        this();

        setHistoryMode(mode);
        setData(data);
        setHistoryFileName(fileName);

        loadValues();
    }

    /**
     * Constructor that sets the data type.
     *
     * @param data the data type
     */
    public SensibleComboBox(T data) {

        this();

        setData(data);
    }

    /**
     * Constructor that sets the history file name and data type.
     *
     * @param data the data type
     * @param fileName the history file name
     */
    public SensibleComboBox(T data, String fileName) {

        this();

        setData(data);
        setHistoryFileName(fileName);

        loadValues();
    }

    /**
     * Returns the <code>data</code> property value.
     *
     * @return the property value
     *
     * @see SensibleComboBox#data
     * @see SensibleComboBox#setData(SensibleDataType)
     */
    public SensibleDataType getData() {

        return data;
    }

    /**
     * Returns the <code>historyFileName</code> property value.
     *
     * @return the property value
     *
     * @see SensibleComboBox#historyFileName
     * @see SensibleComboBox#setHistoryFileName(String)
     */
    public String getHistoryFileName() {

        return historyFileName;
    }

    /**
     * Returns the <code>historyMode</code> property value.
     *
     * @return the property value
     *
     * @see SensibleComboBox#historyMode
     * @see SensibleComboBox#setHistoryMode(int)
     */
    public int getHistoryMode() {

        return historyMode;
    }

    /**
     * This method loads the combo with the values in the history file.
     *
     * @throws IllegalArgumentException the history file does not exist,
     *         could not be created, could not be read or it is not valid
     */
    private void loadValues() {

        removeAllItems();

        if (historyFileName == null) {
            return;
        }

        File f = new File(historyFileName);
        INIFileManager ini = null;

        if (!f.exists()) {
            try {
                if (!f.createNewFile()) {
                    throw new IllegalArgumentException(
                        SensibleContext.getMessage(
                            "CMBOX_ERR_CREATE_HISTORY_FILE", historyFileName)); //$NON-NLS-1$
                }

                ini = new INIFileManager(historyFileName);
                ini.addEntry(KEY_MAX_ENTRIES, Integer.toString(DEFAULT_MAX_ENTRIES));
                ini.updateFile();

            } catch (IOException ioe) {
                throw new IllegalArgumentException(
                    SensibleContext.getMessage(
                        "CMBOX_ERR_CREATE_HISTORY_FILE", historyFileName), ioe); //$NON-NLS-1$
            }
        }

        try {
            if (ini == null) {
                ini = new INIFileManager(historyFileName);
            }

            String maxEntriesValue = ini.getValue(KEY_MAX_ENTRIES);
            int maxEntries = maxEntriesValue == null ?
                DEFAULT_MAX_ENTRIES : Integer.parseInt(maxEntriesValue);

            historyEntries = new ArrayList<>();

            for (int i = 0; i < maxEntries; i++) {
                String entry = ini.getValue(Integer.toString(i));

                if (entry != null && entry.length() != 0) {
                    @SuppressWarnings("unchecked")
                    T clone = (T) data.returnCopy();
                    clone.setValue(entry);
                    addItem(clone);
                    historyEntries.add(entry);
                }
            }

            // takes into account missing or blank entries
            maxEntries = historyEntries.size();
            setMaximumRowCount(maxEntries);

            switch (historyMode) {
                case FULL_HISTORY:

                    setSelectedIndex(-1);

                    break;

                case REMEMBER_AND_SELECT:

                    setEditable(false);

                    String lastSelected = ini.getValue(KEY_LAST_SELECTED);
                    if (lastSelected == null || lastSelected.length() == 0) {
                        setSelectedIndex(-1);
                    } else {
                        @SuppressWarnings("unchecked")
                        T clone = (T) data.returnCopy();
                        clone.setValue(lastSelected);
                        setSelectedItem(clone);
                    }

                    break;

                default:

                    break;
            }

            // the history entries are used for the auto-completion
            // feature in the text field used as editor
            if (data != null) {
                ((SensibleTextField) getEditor().getEditorComponent()).
                    setAutoCompletionEntries(historyEntries);
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("CMBOX_ERR_INVALID_HISTORY_FILE"), ioe); //$NON-NLS-1$
        }
    }

    /**
     * This method monitors changes in the history properties values and loads the combo when
     * needed.
     *
     * @param event the event
     */
    public void propertyChange(java.beans.PropertyChangeEvent event) {

        if (event.getSource() == this
            && event.getPropertyName().equals(JAVA_BEAN_PROPERTY_HISTORY_FILE_NAME)) {
            loadValues();
        }

        if (event.getSource() == this
            && event.getPropertyName().equals(JAVA_BEAN_PROPERTY_HISTORY_MODE)) {
            loadValues();
        }
    }

    /**
     * Changes the <code>data</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleComboBox#data
     * @see SensibleComboBox#getData()
     */
    public void setData(T newValue) {

        SensibleDataType oldValue = data;

        data = newValue;
        data.addPropertyChangeListener(this);

        firePropertyChange(JAVA_BEAN_PROPERTY_DATA, oldValue, newValue);

        newValue.firePropertyChangeEvents();

        setEditor(new SensibleComboBoxEditor());

        // the history entries are used for the auto-completion
        // feature in the text field used as editor
        if (historyEntries != null) {
            ((SensibleTextField) getEditor().getEditorComponent()).
                setAutoCompletionEntries(historyEntries);
        }
    }

    /**
     * Changes the <code>historyFileName</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleComboBox#historyFileName
     * @see SensibleComboBox#getHistoryFileName()
     */
    public void setHistoryFileName(String newValue) {

        String oldValue = historyFileName;
        historyFileName = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_HISTORY_FILE_NAME, oldValue, newValue);
    }

    /**
     * Changes the <code>historyMode</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleComboBox#historyMode
     * @see SensibleComboBox#getHistoryMode()
     */
    public void setHistoryMode(int newValue) {

        int oldValue = historyMode;
        historyMode = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_HISTORY_MODE, oldValue, newValue);
    }

    /**
     * Changes the element selected in the control.
     *
     * @param index the element index
     */
    public void setSelectedIndex(int index) {

        if (index == -1 && data != null) {
            ((SensibleTextField) getEditor().getEditorComponent()).getData().clear();
        }

        super.setSelectedIndex(index);
    }

    /**
     * Updates the history file adding the current selected item in the combo. If the entry exists
     * the method reorders the existing entries and positions the current selected item in the
     * combo as the last (most recent) entry.
     *
     * @throws IllegalArgumentException the history file could not be updated
     */
    public void updateHistory() {

        String newValue = null;

        @SuppressWarnings("unchecked")
        T editorItem = (T) getEditor().getItem();
        @SuppressWarnings("unchecked")
        T selectedItem = (T) getSelectedItem();

        if (editorItem == null || editorItem.getValue().length() == 0) {
            if (selectedItem == null || selectedItem.getValue().length() == 0) {
                return;
            } else {
                newValue = selectedItem.toString();
            }
        } else {
            newValue = editorItem.toString();
        }

        try {
            INIFileManager ini = new INIFileManager(historyFileName);

            if (historyMode == FULL_HISTORY) {
                String maxEntriesValue = ini.getValue(KEY_MAX_ENTRIES);
                int maxEntries = maxEntriesValue == null ?
                    DEFAULT_MAX_ENTRIES : Integer.parseInt(maxEntriesValue);

                if (ini.hasValue(newValue)) {

                    int whereToAdd = 0;
                    for (int i = 0; i < getItemCount(); i++) {
                        String temp = ini.getValue(Integer.toString(i));
                        if (!temp.equals(newValue)) {
                            ini.updateEntry(Integer.toString(whereToAdd), temp);
                            whereToAdd++;
                        }
                    }
                    ini.updateEntry(Integer.toString(getItemCount() - 1), newValue);

                } else {

                    if (getItemCount() < maxEntries) {
                        ini.addEntry(Integer.toString(getItemCount()), newValue);
                    } else {
                        for (int i = 0; i < maxEntries - 1; i++) {
                            ini.updateEntry(Integer.toString(i),
                                ini.getValue(Integer.toString(i + 1)));
                        }

                        ini.updateEntry(Integer.toString(maxEntries - 1), newValue);
                    }
                }
            } else if (historyMode == REMEMBER_AND_SELECT
                && !ini.updateEntry(KEY_LAST_SELECTED, newValue)) {

                ini.addEntry(KEY_LAST_SELECTED, newValue);
            }

            ini.updateFile();

        } catch (IOException ioe) {
            throw new IllegalArgumentException(
                SensibleContext.getMessage("CMBOX_ERR_CANNOT_UPDATE"), ioe); //$NON-NLS-1$
        }

        loadValues();
    }
}
