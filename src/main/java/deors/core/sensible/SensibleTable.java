package deors.core.sensible;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * Definition for a record-dependent behavior table.<br>
 *
 * The behavior of the field depends on the <code>SensibleObject</code> object attached to the
 * <code>record</code> property.<br>
 *
 * The class uses an <code>AbstractTableModel</code> that invokes the abstract methods on
 * <code>SensibleObject</code> class to handle the data. The <code>record</code> property is
 * also used to store the row selected from the table.<br>
 *
 * Cell values can be edited over the table using as renderer and editor
 * <code>SensibleTableCellRendererAndEditor</code> objects.<br>
 *
 * @param <R> a SensibleObject type that models the table behavior and data
 *
 * @author deors
 * @version 1.0
 */
public class SensibleTable<R extends SensibleObject>
    extends javax.swing.JTable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -3945876310313816753L;

    /**
     * The <code>SensibleObject</code> object that defines the table model.
     *
     * @see SensibleTable#getRecord()
     * @see SensibleTable#setRecord(SensibleObject)
     */
    protected R record;

    /**
     * Array with the width of all columns.
     *
     * @see SensibleTable#getColumnWidth()
     * @see SensibleTable#getColumnWidth(int)
     * @see SensibleTable#setColumnWidth(int[])
     * @see SensibleTable#setColumnWidth(int, int)
     */
    private int[] columnWidth;

    /**
     * List that contains the table data.
     *
     * @see SensibleTable#getTableData()
     * @see SensibleTable#setTableData(List)
     * @see SensibleTable#setTableData(SensibleObject[])
     */
    protected List<R> tableData = new ArrayList<R>();

    /**
     * Whether cell values are editable in the table.
     *
     * @see SensibleTable#areCellsEditableInTable()
     * @see SensibleTable#setCellsEditableInTable(boolean)
     */
    protected boolean cellsEditableInTable;

    /**
     * This property is <code>true</code> if there is a row selected.
     *
     * @see SensibleTable#hasRowSelected()
     */
    private boolean rowSelected;

    /**
     * The last row selected.
     */
    private int lastSelectedRow = -1;

    /**
     * Constant to select object cloning while adding and updating records in the table.
     */
    public static final boolean CLONE = true;

    /**
     * Constant to select not cloning while adding and updating records in the table.
     */
    public static final boolean DONT_CLONE = false;

    /**
     * The "cellsEditableInTable" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_CELLS_EDITABLE_IN_TABLE = "cellsEditableInTable"; //$NON-NLS-1$

    /**
     * The "columnWidth" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_COLUMN_WIDTH = "columnWidth"; //$NON-NLS-1$

    /**
     * The "record" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_RECORD = "record"; //$NON-NLS-1$

    /**
     * The "rowSelected" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_ROW_SELECTED = "rowSelected"; //$NON-NLS-1$

    /**
     * The "tableData" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_TABLE_DATA = "tableData"; //$NON-NLS-1$

    /**
     * Definition for the table model.<br>
     *
     * It overrides the methods in <code>AbstractTableModel</code> to use the record property.<br>
     *
     * @author deors
     * @version 1.0
     */
    public class SensibleTableModel
        extends javax.swing.table.AbstractTableModel {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = 2916640147809846957L;

        /**
         * Default constructor.
         */
        public SensibleTableModel() {

            super();
        }

        /**
         * Finds a column by name.
         *
         * @param name the column name
         *
         * @return the column index
         *
         * @see javax.swing.table.AbstractTableModel#findColumn(String)
         */
        @Override
        public int findColumn(String name) {

            try {
                return record.getFieldIndex(name);
            } catch (IllegalArgumentException iae) {
                return -1;
            }
        }

        /**
         * Returns the number of columns. Actually it returns the number of fields in the record
         * definition.
         *
         * @return the number of columns
         *
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        @Override
        public int getColumnCount() {

            return record.getFieldCount();
        }

        /**
         * Returns the class for the given column.
         *
         * @param column the column index
         *
         * @return the class for the given column
         *
         * @see javax.swing.table.TableModel#getColumnClass(int)
         */
        @Override
        public Class<?> getColumnClass(int column) {

            return getValueAt(0, column).getClass();
        }

        /**
         * Returns the name for the given column.
         *
         * @param column the column index
         *
         * @return the column name
         *
         * @see javax.swing.table.TableModel#getColumnName(int)
         */
        @Override
        public String getColumnName(int column) {

            return record.getFieldName(column);
        }

        /**
         * Returns the number of rows currently on the table. Actually it returns the number of
         * records in the <code>tableData</code> property.
         *
         * @return the number of rows
         *
         * @see javax.swing.table.TableModel#getRowCount()
         */
        @Override
        public int getRowCount() {

            if (tableData == null) {
                return 0;
            }

            return tableData.size();
        }

        /**
         * Returns the object at the given position.
         *
         * @param row the row index
         * @param column the column index
         *
         * @return the object
         *
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        @Override
        public Object getValueAt(int row, int column) {

            if (tableData == null) {
                return null;
            }

            return tableData.get(row).getField(column);
        }

        /**
         * Returns whether a cell is editable. Actually it returns the
         * <code>cellsEditableInTable</code> property value.
         *
         * @param row the row index
         * @param column the column index
         *
         * @return the <code>cellsEditableInTable</code> property value
         *
         * @see SensibleTable.SensibleTableModel#setValueAt(Object, int, int)
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        @Override
        public boolean isCellEditable(int row, int column) {

            return cellsEditableInTable;
        }

        /**
         * Changes a cell value and fires the table cell updated event.
         *
         * @param newValue the new value
         * @param row the row index
         * @param column the column index
         *
         * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
         */
        @Override
        public void setValueAt(Object newValue, int row, int column) {

            if (!tableData.get(row).getField(column).equals(newValue)) {
                tableData.get(row).setField(column, newValue.toString());
                fireTableCellUpdated(row, column);
            }
        }
    }

    /**
     * Definition for the cell renderer and editor.<br>
     *
     * It overrides the methods in <code>AbstractCellEditor</code> and implements the interfaces
     * <code>TableCellRenderer</code> and <code>TableCellEditor</code> to use
     * <code>SensibleTextField</code> and <code>SensibleCheckBox</code> objects as renderers and
     * editors.<br>
     *
     * @param <R> a SensibleObject type that models the table behavior and data
     *
     * @author deors
     * @version 1.0
     */
    public static class SensibleTableCellRendererAndEditor<R extends SensibleObject>
        extends javax.swing.AbstractCellEditor
        implements javax.swing.table.TableCellRenderer,
                   javax.swing.table.TableCellEditor,
                   java.awt.event.ActionListener,
                   java.awt.event.ItemListener {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = 1167074440063705872L;

        /**
         * The text field used as editor.
         */
        private final SensibleTextField fieldEditor = new SensibleTextField();

        /**
         * The check box used as renderer and editor for boolean values.
         */
        private final SensibleCheckBox checkEditor = new SensibleCheckBox();

        /**
         * The number of clicks needed to start editing a cell.
         *
         * @see SensibleTable.SensibleTableCellRendererAndEditor#getClickCountToStart()
         * @see SensibleTable.SensibleTableCellRendererAndEditor#setClickCountToStart(int)
         */
        private int clickCountToStart = 2;

        /**
         * The column that is being edited.
         */
        private int columnBeingEdited = -1;

        /**
         * The table that is being edited.
         */
        private SensibleTable<R> tableBeingEdited;

        /**
         * The value that is being edited.
         */
        private SensibleDataType valueBeingEdited;

        /**
         * Default constructor. It adds an action listener to both editor components.
         */
        public SensibleTableCellRendererAndEditor() {

            super();

            fieldEditor.addActionListener(this);
            checkEditor.addActionListener(this);
        }

        /**
         * Action performed event handler. This method stops the cell edition when the event is
         * fired.
         *
         * @param event the event
         */
        @Override
        public void actionPerformed(java.awt.event.ActionEvent event) {

            stopCellEditing();
        }

        /**
         * Cancels the cell edition. This method calls <code>fireEditingCanceled()</code>.
         *
         * @see SensibleTable.SensibleTableCellRendererAndEditor#fireEditingCanceled()
         */
        @Override
        public void cancelCellEditing() {

            fireEditingCanceled();
        }

        /**
         * Returns the cell being edited value.
         *
         * @return the cell being edited value
         */
        @Override
        public Object getCellEditorValue() {

            return valueBeingEdited;
        }

        /**
         * Returns the <code>clickCountToStart</code> property value.
         *
         * @return the property value
         *
         * @see SensibleTable.SensibleTableCellRendererAndEditor#clickCountToStart
         * @see SensibleTable.SensibleTableCellRendererAndEditor#setClickCountToStart(int)
         */
        public int getClickCountToStart() {

            return clickCountToStart;
        }

        /**
         * Returns the component used to edit the cell referenced by the given parameters.
         *
         * @param table the table being edited
         * @param value the value being edited
         * @param isSelected whether the cell is selected
         * @param row the row being edited
         * @param column the column being edited
         *
         * @return the editor component or null if the table is not a <code>SensibleTable</code>
         *         object or the value is not a <code>SensibleDataType</code> object
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            if (table instanceof SensibleTable<?>) {
                @SuppressWarnings("unchecked")
                SensibleTable<R> castedTable = (SensibleTable<R>) table;
                tableBeingEdited = castedTable;
            } else {
                return null;
            }

            columnBeingEdited = column;

            if (value instanceof SensibleBoolean) {
                valueBeingEdited = (SensibleBoolean) value;
                checkEditor.setData((SensibleBoolean) value);
                return checkEditor;
            } else if (value instanceof SensibleDataType) {
                valueBeingEdited = (SensibleDataType) value;
                fieldEditor.setData((SensibleDataType) value);
                return fieldEditor;
            } else {
                return null;
            }
        }

        /**
         * Returns the component used to render the cell referenced by the given parameters.
         *
         * @param table the table being edited
         * @param value the value being edited
         * @param isSelected whether the cell is selected
         * @param hasFocus whether the cell has the focus
         * @param row the row being edited
         * @param column the column being edited
         *
         * @return the renderer component or null if the value is not a <code>SensibleBoolean</code>
         *         object
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            if (value instanceof SensibleBoolean) {
                SensibleCheckBox renderer = new SensibleCheckBox((SensibleBoolean) value);

                if (isSelected) {
                    renderer.setForeground(table.getSelectionForeground());
                    renderer.setBackground(table.getSelectionBackground());
                } else {
                    renderer.setForeground(table.getForeground());
                    renderer.setBackground(table.getBackground());
                }

                return renderer;
            }

            return null;
        }

        /**
         * Returns <code>true</code> if the event is not a mouse event, <code>true</code> if the
         * necessary number of clicks have occurred, and <code>false</code> otherwise.
         *
         * @param event the event
         *
         * @return <code>true</code> if the cell is ready for editing; <code>false</code>
         *         otherwise
         *
         * @see SensibleTable.SensibleTableCellRendererAndEditor#clickCountToStart
         * @see SensibleTable.SensibleTableCellRendererAndEditor#shouldSelectCell(java.util.EventObject)
         */
        @Override
        public boolean isCellEditable(java.util.EventObject event) {

            if (event instanceof java.awt.event.MouseEvent) {
                return ((java.awt.event.MouseEvent) event).getClickCount() >= clickCountToStart;
            }

            return true;
        }

        /**
         * Item state changed event listener. This method stops the cell edition when the event is
         * fired.
         *
         * @param event the event
         */
        @Override
        public void itemStateChanged(java.awt.event.ItemEvent event) {

            stopCellEditing();
        }

        /**
         * Changes the <code>clickCountToStart</code> property value.
         *
         * @param newValue the property new value
         *
         * @see SensibleTable.SensibleTableCellRendererAndEditor#clickCountToStart
         * @see SensibleTable.SensibleTableCellRendererAndEditor#getClickCountToStart()
         */
        public void setClickCountToStart(int newValue) {

            clickCountToStart = newValue;
        }

        /**
         * Returns <code>true</code> to indicate that the editing cell may be selected.
         *
         * @param event the event
         *
         * @return <code>true</code>
         *
         * @see SensibleTable.SensibleTableCellRendererAndEditor#isCellEditable(java.util.EventObject)
         */
        @Override
        public boolean shouldSelectCell(java.util.EventObject event) {

            return true;
        }

        /**
         * Returns <code>true</code> to indicate that editing has begun.
         *
         * @param event the event
         *
         * @return <code>true</code>
         */
        public boolean startCellEditing(java.util.EventObject event) {

            return true;
        }

        /**
         * Stops editing and returns whether editing has stopped or editing has been cancelled
         * because the new value is not valid.
         *
         * @return whether the new value is a valid value
         */
        @Override
        public boolean stopCellEditing() {

            if (valueBeingEdited != null && !valueBeingEdited.isValid()) {
                cancelCellEditing();

                if (tableBeingEdited != null) {
                    valueBeingEdited.setValue(
                        tableBeingEdited.getRecord().getField(columnBeingEdited).getValue());
                }

                return false;
            }

            fireEditingStopped();

            if (tableBeingEdited != null) {
                if (valueBeingEdited instanceof SensibleBoolean) {
                    ((SensibleBoolean) valueBeingEdited).setFlag(checkEditor.isSelected());
                    tableBeingEdited.getRecord().setField(
                        columnBeingEdited, valueBeingEdited.getValue());
                } else {
                    tableBeingEdited.getRecord().setField(
                        columnBeingEdited, valueBeingEdited.getValue());
                }
            }

            return true;
        }
    }

    /**
     * Default constructor. The constructor initializes the table models.
     */
    public SensibleTable() {

        super();

        initializeModels();
    }

    /**
     * Constructor that also sets the record for the model.
     *
     * @param record the <code>SensibleObject</code> object that defines the model
     */
    public SensibleTable(R record) {

        this();

        setRecord(record);
    }

    /**
     * Constructor that sets the record and the data from an array.
     *
     * @param record the <code>SensibleObject</code> object that defines the model
     * @param tableData the data to be loaded into the table
     */
    public SensibleTable(R record, R[] tableData) {

        this();

        setRecord(record);
        setTableData(tableData);
    }

    /**
     * Constructor that sets the record and the data from a list.
     *
     * @param record the <code>SensibleObject</code> object that defines the model
     * @param tableData the data to be loaded into the table
     */
    public SensibleTable(R record, List<R> tableData) {

        this();

        setRecord(record);
        setTableData(tableData);
    }

    /**
     * Adds a record to the end of the table cloning the source object.
     *
     * @param newRecord the record to be added
     *
     * @see SensibleTable#insertRecord(SensibleObject, int, boolean)
     */
    public void addRecord(R newRecord) {

        insertRecord(newRecord, tableData.size(), CLONE);
    }

    /**
     * Adds a record to the end of the table choosing whether to clone the source object.
     *
     * @param newRecord the record to be added
     * @param clone whether to clone the source object
     *
     * @see SensibleTable#insertRecord(SensibleObject, int, boolean)
     * @see SensibleTable#CLONE
     * @see SensibleTable#DONT_CLONE
     */
    public void addRecord(R newRecord, boolean clone) {

        insertRecord(newRecord, tableData.size(), clone);
    }

    /**
     * Returns the <code>cellsEditableInTable</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTable#cellsEditableInTable
     * @see SensibleTable#setCellsEditableInTable(boolean)
     */
    public boolean areCellsEditableInTable() {

        return cellsEditableInTable;
    }

    /**
     * Deletes a record from the table and fires the table rows inserted event.
     *
     * @param existingRecord the record to be deleted
     *
     * @return whether the given record existed and was deleted
     */
    public boolean deleteRecord(R existingRecord) {

        int row = tableData.indexOf(existingRecord);

        return deleteRecord(row);
    }

    /**
     * Deletes a record from the table and fires the table rows inserted event.
     *
     * @param row the row to be deleted
     *
     * @return whether the given row does not equal to <code>-1</code>
     */
    public boolean deleteRecord(int row) {

        if (row == -1) {
            return false;
        }

        tableData.remove(row);

        getModel().fireTableRowsDeleted(row, row);

        clearSelection();

        return true;
    }

    /**
     * Returns a copy of the <code>columnWidth</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTable#columnWidth
     * @see SensibleTable#getColumnWidth(int)
     * @see SensibleTable#setColumnWidth(int[])
     * @see SensibleTable#setColumnWidth(int, int)
     */
    public int[] getColumnWidth() {

        return Arrays.copyOf(columnWidth, columnWidth.length);
    }

    /**
     * Returns the width of the given column.
     *
     * @param column the column index
     *
     * @return the column width
     *
     * @see SensibleTable#columnWidth
     * @see SensibleTable#getColumnWidth()
     * @see SensibleTable#setColumnWidth(int[])
     * @see SensibleTable#setColumnWidth(int, int)
     */
    public int getColumnWidth(int column) {

        return columnWidth[column];
    }

    /**
     * Returns the table model.
     *
     * @return the table model
     */
    @Override
    public SensibleTableModel getModel() {

        if (super.getModel() instanceof SensibleTable<?>.SensibleTableModel) {
            @SuppressWarnings("unchecked")
            SensibleTable<R>.SensibleTableModel castedModel =
                (SensibleTable<R>.SensibleTableModel) super.getModel();
            return castedModel;
        }

        return null;
    }

    /**
     * Returns the <code>record</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTable#record
     * @see SensibleTable#setRecord(SensibleObject)
     */
    public R getRecord() {

        return record;
    }

    /**
     * Returns the <code>tableData</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTable#tableData
     * @see SensibleTable#setTableData(List)
     * @see SensibleTable#setTableData(SensibleObject[])
     */
    public List<R> getTableData() {

        return tableData;
    }

    /**
     * Returns the <code>rowSelected</code> property value.
     *
     * @return the property value
     *
     * @see SensibleTable#rowSelected
     */
    public boolean hasRowSelected() {

        return rowSelected;
    }

    /**
     * Initializes the models. Actually it sets the selection mode to allow only one row at a time
     * to be selected.
     */
    public final void initializeModels() {

        getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Inserts a record in a given position in the table cloning the source object.
     *
     * @param newRecord the record to be inserted
     * @param row the insert position
     *
     * @see SensibleTable#insertRecord(SensibleObject, int, boolean)
     */
    public void insertRecord(R newRecord, int row) {

        insertRecord(newRecord, row, CLONE);
    }

    /**
     * Inserts a record in a given position in the table choosing whether to clone the source object
     * and fires the table rows inserted event.
     *
     * @param newRecord the record to be inserted
     * @param row the insert position
     * @param clone whether to clone the source object
     *
     * @see SensibleTable#CLONE
     * @see SensibleTable#DONT_CLONE
     */
    public void insertRecord(R newRecord, int row, boolean clone) {

        if (clone) {
            @SuppressWarnings("unchecked")
            R clonedRecord = (R) newRecord.returnCopy();
            tableData.add(row, clonedRecord);
        } else {
            tableData.add(row, newRecord);
        }

        getModel().fireTableRowsInserted(row, row);

        setRowSelectionInterval(row, row);
    }

    /**
     * Prepares the cell renderers and editors. The renderer and editor for
     * <code>SensibleBoolean</code> objects is a <code>SensibleCheckBox</code> component, and
     * the editor for the rest of <code>SensibleDataType</code> objects is a
     * <code>SensibleTextField</code> component (the renderer is not changed and an standard Swing
     * label is used).
     */
    private void prepareCellRenderersAndEditors() {

        if (record != null) {
            TableColumnModel colModel = getColumnModel();

            SensibleTableCellRendererAndEditor<R> editor = new SensibleTableCellRendererAndEditor<R>();

            for (int i = 0, n = getModel().getColumnCount(); i < n; i++) {
                SensibleDataType field = record.getField(i);

                if (field instanceof SensibleBoolean) {
                    colModel.getColumn(i).setCellRenderer(editor);
                }

                colModel.getColumn(i).setCellEditor(editor);
            }
        }
    }

    /**
     * Changes the <code>cellsEditableInTable</code> property value and fires the property change
     * event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTable#cellsEditableInTable
     * @see SensibleTable#areCellsEditableInTable()
     */
    public void setCellsEditableInTable(boolean newValue) {

        boolean oldValue = cellsEditableInTable;
        cellsEditableInTable = newValue;
        firePropertyChange(JAVA_BEAN_PROPERTY_CELLS_EDITABLE_IN_TABLE, oldValue, newValue);
    }

    /**
     * Changes the <code>columnWidth</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTable#columnWidth
     * @see SensibleTable#getColumnWidth()
     * @see SensibleTable#getColumnWidth(int)
     * @see SensibleTable#setColumnWidth(int, int)
     */
    public final void setColumnWidth(int[] newValue) {

        int[] oldValue = columnWidth;

        columnWidth = Arrays.copyOf(newValue, newValue.length);

        if (record != null) {
            javax.swing.table.TableColumnModel colModel = getColumnModel();
            for (int i = 0; i < newValue.length; i++) {
                colModel.getColumn(i).setWidth(newValue[i]);
                colModel.getColumn(i).setPreferredWidth(newValue[i]);
            }
        }

        firePropertyChange(JAVA_BEAN_PROPERTY_COLUMN_WIDTH, oldValue, newValue);
    }

    /**
     * Changes the width of the given column and fires the property change event.
     *
     * @param c the column index
     * @param newWidth the column new width
     *
     * @see SensibleTable#columnWidth
     * @see SensibleTable#getColumnWidth()
     * @see SensibleTable#getColumnWidth(int)
     * @see SensibleTable#setColumnWidth(int[])
     */
    public final void setColumnWidth(int c, int newWidth) {

        int[] oldValue = columnWidth;

        columnWidth = new int[oldValue.length];
        for (int i = 0; i < oldValue.length; i++) {
            if (i == c) {
                columnWidth[i] = newWidth;
            } else {
                columnWidth[i] = oldValue[i];
            }
        }

        if (record != null) {
            javax.swing.table.TableColumnModel colModel = getColumnModel();
            colModel.getColumn(c).setWidth(newWidth);
        }

        firePropertyChange(JAVA_BEAN_PROPERTY_COLUMN_WIDTH, oldValue, columnWidth);
    }

    /**
     * Changes the <code>record</code> property value and fires the property change event. The
     * method also prepares the model, the column widths and the cell renderers and editors.
     *
     * @param newValue the property new value
     *
     * @see SensibleTable#record
     * @see SensibleTable#getRecord()
     */
    public final void setRecord(R newValue) {

        R oldValue = record;

        record = newValue;

        setModel(new SensibleTableModel());

        if (columnWidth != null) {
            setColumnWidth(columnWidth);
        }

        prepareCellRenderersAndEditors();

        firePropertyChange(JAVA_BEAN_PROPERTY_RECORD, oldValue, newValue);
    }

    /**
     * Changes the <code>tableData</code> property value and fires the property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTable#tableData
     * @see SensibleTable#getTableData()
     * @see SensibleTable#setTableData(SensibleObject[])
     */
    public final void setTableData(List<R> newValue) {

        List<R> oldValue = tableData;

        tableData = newValue;

        getModel().fireTableDataChanged();

        firePropertyChange(JAVA_BEAN_PROPERTY_TABLE_DATA, oldValue, newValue);
    }

    /**
     * Changes the <code>tableData</code> property value using the given array and fires the
     * property change event.
     *
     * @param newValue the property new value
     *
     * @see SensibleTable#tableData
     * @see SensibleTable#getTableData()
     * @see SensibleTable#setTableData(List)
     */
    public final void setTableData(R[] newValue) {

        List<R> oldValue = tableData;

        tableData = new ArrayList<R>();

        for (int i = 0; i < newValue.length; i++) {
            tableData.add(newValue[i]);
        }

        getModel().fireTableDataChanged();

        firePropertyChange(JAVA_BEAN_PROPERTY_TABLE_DATA, oldValue, tableData);
    }

    /**
     * Updates a record from the given new value cloning the source object.
     *
     * @param existingRecord the record to be updated
     * @param newValue the new value for the record
     *
     * @return whether the given record existed and was deleted
     *
     * @see SensibleTable#updateRecord(int, SensibleObject, boolean)
     */
    public boolean updateRecord(R existingRecord, R newValue) {

        int row = tableData.indexOf(existingRecord);

        return updateRecord(row, newValue, CLONE);
    }

    /**
     * Updates a record from the given new value choosing whether to clone the source object.
     *
     * @param existingRecord the record to be updated
     * @param newValue the new value for the record
     * @param clone whether to clone the source object
     *
     * @return whether the given record existed and was deleted
     *
     * @see SensibleTable#updateRecord(int, SensibleObject, boolean)
     * @see SensibleTable#CLONE
     * @see SensibleTable#DONT_CLONE
     */
    public boolean updateRecord(R existingRecord, R newValue, boolean clone) {

        int row = tableData.indexOf(existingRecord);

        return updateRecord(row, newValue, clone);
    }

    /**
     * Updates a record from the given new value cloning the source object.
     *
     * @param row the row to be updated
     * @param newValue the new value for the record
     *
     * @return whether the given row does not equal to <code>-1</code>
     *
     * @see SensibleTable#updateRecord(int, SensibleObject, boolean)
     */
    public boolean updateRecord(int row, R newValue) {

        return updateRecord(row, newValue, CLONE);
    }

    /**
     * Updates a record from the given new value choosing whether to clone the source object and
     * fires the table rows updated event.
     *
     * @param row the row to be updated
     * @param newValue the new value for the record
     * @param clone whether to clone the source object
     *
     * @return whether the given row does not equal to <code>-1</code>
     *
     * @see SensibleTable#CLONE
     * @see SensibleTable#DONT_CLONE
     */
    public boolean updateRecord(int row, R newValue, boolean clone) {

        if (row == -1) {
            return false;
        }

        if (clone) {
            tableData.remove(row);
            @SuppressWarnings("unchecked")
            R clonedValue = (R) newValue.returnCopy();
            tableData.add(row, clonedValue);
        } else {
            tableData.remove(row);
            tableData.add(row, newValue);
        }

        getModel().fireTableRowsUpdated(row, row);

        setRowSelectionInterval(row, row);

        return true;
    }

    /**
     * List selection event handler. This method changes the <code>record</code> property with the
     * row selected and fires the property change events for the <code>rowSelected</code> and
     * <code>record</code> properties.
     *
     * @param event the event
     *
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @Override
    public void valueChanged(javax.swing.event.ListSelectionEvent event) {

        super.valueChanged(event);

        int row = getSelectionModel().getMinSelectionIndex();

        if (lastSelectedRow != row) {
            lastSelectedRow = row;

            @SuppressWarnings("unchecked")
            R oldRecord = (R) record.returnCopy();
            if (row == -1) {
                rowSelected = false;
                record.clear();
            } else {
                rowSelected = true;
                record.setValue(tableData.get(row));
            }

            firePropertyChange(JAVA_BEAN_PROPERTY_ROW_SELECTED, !rowSelected, rowSelected);
            firePropertyChange(JAVA_BEAN_PROPERTY_RECORD, oldRecord, record);
        }
    }
}
