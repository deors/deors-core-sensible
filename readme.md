# deors-core-sensible

highly productive framework for desktop UIs built on top of Java Swing

## bill of materials (main components)

* SensibleDataType: base data type uses databean with common functionality; data type beans encapsulate data values from Java immutable types in a way that can work associated with UI controls and propagate value change events

* SensibleObject: a 'record' object composed of multiple data types, each of them optionally flagged as part of the key, required or not required

* SensibleSting: a String-based data type, optionally with a maximum length

* SensibleInteger: an Integer-based data type, optionally with a valid range

* SensibleLong: a Long-based data type, optionally with a valid range

* SensibleBigDecimal: a BigDecimal-based data type, optionally with fixed precision

* SensibleBoolean: a Boolean-based data type

* SensibleDate: data type to work with dates

* SensibleTime: data type to work with times

* SensibleDateTime: data type to work with dates and times

* SensibleTextField: extension to Swing text field that uses data types to adjust its behaviour

* SensibleCalendarPicker: UI control to visually select dates from a calendar view

* SensibleCheckBox: extension to Swing check box that uses SensibleBoolean to hold its value

* SensibleComboBox: extension to Swing combo box that uses a list of data types to hold its possible values and selected value, optionally remembering history of previous selections

* SensibleSpinner: UI control to select integer values with 'spinning' buttons

* SensibleTable: extension to Swing table that uses a SensibleObject to dynamically adjust its behaviour, the table documents and the cell editors
