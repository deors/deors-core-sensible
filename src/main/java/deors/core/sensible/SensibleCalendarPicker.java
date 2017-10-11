package deors.core.sensible;

import static deors.core.sensible.SensibleContext.getConfigurationProperty;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.accessibility.Accessible;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 * Definition of a Calendar Picker component binded to a <code>SensibleDate</code>
 * object.<br>
 *
 * The Calendar Picker uses a combo-box to select the month, an editable spinner
 * to select the year, buttons to navigate to the previous and next month, and
 * individual buttons to select the day.<br>
 *
 * The Calendar layout is defined by the locale passed out as a parameter in the
 * bean constructor. When no locale is passed, the default locale is used.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleCalendarPicker
    extends JComponent
    implements Accessible, ActionListener, PropertyChangeListener {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 5308441783980534108L;

    /**
     * The locale used to configure the Calendar.
     */
    private Locale locale;

    /**
     * <code>SensibleDate</code> object that represents the current selected date.
     */
    private SensibleDate date;

    /**
     * <code>java.util.Calendar</code> object that represents the current selected date.
     */
    private Calendar calendar;

    /**
     * Button used to select the next month in the Calendar.
     */
    private JButton buttonNextMonth;

    /**
     * Button used to select the previous month in the Calendar.
     */
    private JButton buttonPrevMonth;

    /**
     * ComboBox used to select the month.
     */
    private SensibleComboBox<SensibleString> comboCurrentMonth;

    /**
     * Spinner used to select the year.
     */
    private SensibleSpinner spinCurrentYear;

    /**
     * Buttons used to select the day.
     */
    private JButton[] buttonDays;

    /**
     * The font family.
     */
    private static final String FONT_FAMILY = "Arial"; //$NON-NLS-1$

    /**
     * The font style.
     */
    private static final int FONT_STYLE = Font.PLAIN;

    /**
     * The font size.
     */
    private static final int FONT_SIZE = 10;

    /**
     * Days per week.
     */
    private static final int DAYS_PER_WEEK = 7;

    /**
     * Months in a year.
     */
    private static final int MONTHS_PER_YEAR = 12;

    /**
     * The year string maximum size.
     */
    private static final int YEAR_STRING_MAX_SIZE = 4;

    /**
     * The year string allowed characters.
     */
    private static final String YEAR_STRING_ALLOWED_CHARACTERS = "0123456789"; //$NON-NLS-1$

    /**
     * The "value" JavaBean property name.
     */
    private static final String JAVA_BEAN_PROPERTY_VALUE = "value"; //$NON-NLS-1$

    /**
     * Icon image used for the left arrow. Configurable in the properties file
     * <code>icon.left</code>. Default value is <code>left.png</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    public static final String LEFT_ICON = getConfigurationProperty("icon.left", "left.png"); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * Icon image used for the right arrow. Configurable in the properties file
     * <code>icon.right</code>. Default value is <code>right.png</code>.
     *
     * @see SensibleContext#getConfigurationProperty(String, String)
     */
    public static final String RIGHT_ICON = getConfigurationProperty("icon.right", "right.png"); //$NON-NLS-1$ $NON-NLS-2$

    /**
     * Default constructor. No <code>SensibleDate</code> object is binded and
     * the default locale is used.
     *
     * @see Locale#getDefault()
     */
    public SensibleCalendarPicker() {

        this(null, Locale.getDefault());
    }

    /**
     * Constructor that sets the <code>SensibleDate</code> object binded to the
     * Calendar Picker. The default locale is used.
     *
     * @param date the <code>SensibleDate</code> object binded to the component
     *
     * @see Locale#getDefault()
     */
    public SensibleCalendarPicker(SensibleDate date) {

        this(date, Locale.getDefault());
    }

    /**
     * Constructor that sets the locale used to define the Calendar Picker layout.
     * No <code>SensibleDate</code> object is binded to the component.
     *
     * @param locale the locale used to define the component internal layout
     */
    public SensibleCalendarPicker(Locale locale) {

        this(null, locale);
    }

    /**
     * Constructor that sets the <code>SensibleDate</code> object binded to the
     * Calendar Picker and the locale used to define the Calendar Picker layout.
     *
     * @param date the <code>SensibleDate</code> object binded to the component
     * @param locale the locale used to define the component internal layout
     *
     * @see #initCalendarLayout()
     * @see #initButtonDaysLayout()
     */
    public SensibleCalendarPicker(SensibleDate date, Locale locale) {

        super();
        this.date = date;
        this.locale = locale;
        initCalendarLayout();
        initButtonDaysLayout();
    }

    /**
     * Initializes the Calendar layout.
     */
    private void initCalendarLayout() {

        final int prevMonthX = 0;
        final int comboX = 16;
        final int spinnerX = 100;
        final int nextMonthX = 147;
        final int firstRowY = 0;
        final int buttonWidth = 16;
        final int comboWidth = 80;
        final int spinnerWidth = 47;
        final int firstRowHeight = 18;
        final int borderSize = 0;
        final int brickSize = 23;

        setLayout(null);

        ImageIcon icon = null;
        Font font = new Font(FONT_FAMILY, FONT_STYLE, FONT_SIZE);

        buttonPrevMonth = new JButton();
        buttonPrevMonth.setBounds(prevMonthX, firstRowY, buttonWidth, firstRowHeight);
        buttonPrevMonth.setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
        buttonPrevMonth.setContentAreaFilled(false);
        icon = SensibleToolkit.createImageIcon(LEFT_ICON);
        if (icon != null) {
            buttonPrevMonth.setIcon(icon);
        }
        add(buttonPrevMonth);

        comboCurrentMonth = new SensibleComboBox();
        comboCurrentMonth.setBounds(comboX, firstRowY, comboWidth, firstRowHeight);
        comboCurrentMonth.setFont(font);
        add(comboCurrentMonth);

        SensibleString data = new SensibleString(YEAR_STRING_MAX_SIZE);
        data.setAllowedCharacters(YEAR_STRING_ALLOWED_CHARACTERS);

        spinCurrentYear = new SensibleSpinner(data);
        spinCurrentYear.setBounds(spinnerX, firstRowY, spinnerWidth, firstRowHeight);
        spinCurrentYear.setFont(font);
        add(spinCurrentYear);

        buttonNextMonth = new JButton();
        buttonNextMonth.setBounds(nextMonthX, firstRowY, buttonWidth, firstRowHeight);
        buttonNextMonth.setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
        buttonNextMonth.setContentAreaFilled(false);
        icon = SensibleToolkit.createImageIcon(RIGHT_ICON);
        if (icon != null) {
            buttonNextMonth.setIcon(icon);
        }
        add(buttonNextMonth);

        calendar = Calendar.getInstance(locale);

        if (date == null) {
            date = new SensibleDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            if (date.isComplete() && date.isValid()) {
                calendar.set(Calendar.YEAR, date.getYear());
                calendar.set(Calendar.MONTH, date.getMonth() - 1);
                calendar.set(Calendar.DAY_OF_MONTH, date.getMonth());
            } else {
                date.setDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));
            }
        }

        int firstWeekDayOfCalendar = calendar.getFirstDayOfWeek();

        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] shortWeekdays = symbols.getShortWeekdays();
        for (int count = 0; count < DAYS_PER_WEEK; count++) {
            shortWeekdays[count + 1] =
                shortWeekdays[count + 1].toUpperCase(Locale.getDefault()).substring(0, 1);
        }

        for (int count = 0; count < DAYS_PER_WEEK; count++) {
            JLabel labelDay = new JLabel(
                shortWeekdays[1 + ((count + firstWeekDayOfCalendar - 1) % DAYS_PER_WEEK)]);
            labelDay.setBounds(count * brickSize, brickSize, brickSize + 1, brickSize + 1);
            labelDay.setHorizontalAlignment(SwingConstants.CENTER);
            labelDay.setForeground(
                (count + firstWeekDayOfCalendar) % DAYS_PER_WEEK == Calendar.SUNDAY ? Color.RED : Color.BLACK);
            add(labelDay);
        }

        String[] monthNames = symbols.getMonths();
        for (int count = 0; count < MONTHS_PER_YEAR; count++) {
            comboCurrentMonth.addItem(new SensibleString(monthNames[count]));
        }

        buttonPrevMonth.addActionListener(this);
        comboCurrentMonth.addActionListener(this);
        spinCurrentYear.getData().addPropertyChangeListener(this);
        buttonNextMonth.addActionListener(this);
    }

    /**
     * Initializes the buttons used to select the day.
     */
    private void initButtonDaysLayout() {

        final int brickSize = 23;

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstWeekDayOfCalendar = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstWeekDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());

        spinCurrentYear.getData().setValue(Integer.toString(date.getYear()));
        comboCurrentMonth.setSelectedIndex(date.getMonth() - 1);

        if (buttonDays != null) {
            for (int monthDay = 0; monthDay < buttonDays.length; monthDay++) {
                if (buttonDays[monthDay] != null) {
                    remove(buttonDays[monthDay]);
                }
            }
            validate();
            repaint();
        }

        buttonDays = new JButton[daysInMonth];

        int firstWeekDay = firstWeekDayOfMonth - firstWeekDayOfCalendar;
        if (firstWeekDay < 0) {
            firstWeekDay += DAYS_PER_WEEK;
        }
        for (int week = 0, monthDay = 0; monthDay < daysInMonth; week++) {
            for (int weekDay = 0; weekDay < DAYS_PER_WEEK && monthDay < daysInMonth; weekDay++) {
                if (week == 0 && weekDay < firstWeekDay) {
                    continue;
                }
                buttonDays[monthDay] = new JButton(Integer.toString(monthDay + 1));
                buttonDays[monthDay].setActionCommand(Integer.toString(monthDay + 1));
                buttonDays[monthDay].setBounds(
                    weekDay * brickSize, (week + 2) * brickSize, brickSize + 1, brickSize + 1);
                buttonDays[monthDay].setBorder(new SensibleCalendarPickerDashedBorder(Color.GRAY));
                buttonDays[monthDay].setContentAreaFilled(false);
                buttonDays[monthDay].setForeground(
                    (weekDay + firstWeekDayOfCalendar) % DAYS_PER_WEEK == Calendar.SUNDAY ? Color.RED : Color.BLACK);
                buttonDays[monthDay].addActionListener(this);
                add(buttonDays[monthDay]);

                // CHECKSTYLE:OFF
                monthDay++;
                // CHECKSTYLE:ON
            }
        }
    }

    /**
     * Returns the <code>SensibleDate</code> object binded to the component.
     *
     * @return the date object
     */
    public SensibleDate getDate() {
        return date;
    }

    /**
     * Returns the year portion of the date.
     *
     * @return the year currently selected
     */
    public int getYear() {
        return date.getYear();
    }

    /**
     * Returns the month portion of the date.
     *
     * @return the month currently selected
     */
    public int getMonth() {
        return date.getMonth();
    }

    /**
     * Returns the day portion of the date.
     *
     * @return the day currently selected
     */
    public int getDay() {
        return date.getDay();
    }

    /**
     * Changes the <code>SensibleDate</code> object binded to the component.
     *
     * @param newValue the new value for the date object
     */
    public void setDate(SensibleDate newValue) {

        date.setDate(newValue);
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        initButtonDaysLayout();
    }

    /**
     * Changes the year portion of the date.
     *
     * @param newValue the year new value
     */
    public void setYear(int newValue) {

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, newValue);
        if (date.getDay() > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            date.setDay(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        }
        date.setYear(newValue);
        initButtonDaysLayout();
    }

    /**
     * Changes the month portion of the date.
     *
     * @param newValue the month new value
     */
    public void setMonth(int newValue) {

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, newValue - 1);
        if (date.getDay() > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            date.setDay(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        }
        date.setMonth(newValue);
        initButtonDaysLayout();
    }

    /**
     * Changes the day portion of the date.
     *
     * @param newValue the day new value
     */
    public void setDay(int newValue) {

        calendar.set(Calendar.DAY_OF_MONTH, newValue);
        date.setDay(newValue);
    }

    /**
     * Callback executed when a button is pressed.
     *
     * @param event the action event object
     */
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == buttonPrevMonth) {
            int newMonth = date.getMonth() - 1;
            if (newMonth == 0) {
                newMonth = MONTHS_PER_YEAR;
                setYear(date.getYear() - 1);
            }
            setMonth(newMonth);
        } else if (event.getSource() == buttonNextMonth) {
            int newMonth = date.getMonth() + 1;
            if (newMonth == MONTHS_PER_YEAR + 1) {
                newMonth = 1;
                setYear(date.getYear() + 1);
            }
            setMonth(newMonth);
        } else if (event.getSource() == comboCurrentMonth) {
            int newMonth = comboCurrentMonth.getSelectedIndex() + 1;
            setMonth(newMonth);
        } else {
            try {
                int newDay = Integer.parseInt(event.getActionCommand());
                setDay(newDay);
            } catch (NumberFormatException nfe) {
                return;
            }
        }
    }

    /**
     * Callback executed when a bean property changes.
     *
     * @param event the property change event object
     */
    public void propertyChange(PropertyChangeEvent event) {

        if (event.getSource() == spinCurrentYear.getData()
            && event.getPropertyName().equals(JAVA_BEAN_PROPERTY_VALUE)) {
            String newValue = spinCurrentYear.getData().getValue();

            if (newValue.length() != 0) {
                setYear(Integer.parseInt(newValue));
            }
        }
    }

    /**
     * Implementation of a Swing border used to decorate buttons with a
     * dashed border.<br>
     *
     * @author deors
     * @version 1.0
     */
    private static class SensibleCalendarPickerDashedBorder
        extends AbstractBorder {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = 3335102905178079489L;

        /**
         * The color used to paint the border.
         */
        private final Color color;

        /**
         * The border thickness.
         */
        private static final int THICKNESS = 1;

        /**
         * The border miter limit.
         */
        private static final int MITER_LIMIT = 0;

        /**
         * The border dash phase.
         */
        private static final int DASH_PHASE = 0;

//        /**
//         * Default constructor. The color used to paint the border is black.
//         *
//         * @see Color#black
//         */
//        public SensibleCalendarPickerDashedBorder() {
//
//            this(Color.black);
//        }

        /**
         * Constructor that sets the border color.
         *
         * @param color the color used to paint the border
         */
        public SensibleCalendarPickerDashedBorder(Color color) {

            super();
            this.color = color;
        }

        /**
         * Paints the border.
         *
         * @param c the component where the border is to be painted
         * @param g the graphics object where the border is to be painted
         * @param x the component horizontal axis start position
         * @param y the component vertical axis start position
         * @param width the component width
         * @param height the component height
         */
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            if (g instanceof Graphics2D) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(
                    THICKNESS, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, MITER_LIMIT,
                    new float[] {THICKNESS, THICKNESS}, DASH_PHASE));
                g2d.setColor(color);
                g2d.drawLine(x, y, x + width - 1, y);
                g2d.drawLine(x, y + width - 1, x + width - 1, y + width - 1);
                g2d.drawLine(x, y, x, y + width - 1);
                g2d.drawLine(x + width - 1, y, x + width - 1, y + width - 1);
            } else {
                g.setColor(color);
                g.drawLine(x, y, x + width - 1, y);
                g.drawLine(x, y + width - 1, x + width - 1, y + width - 1);
                g.drawLine(x, y, x, y + width - 1);
                g.drawLine(x + width - 1, y, x + width - 1, y + width - 1);
            }
        }

        /**
         * Returns the border insets.
         *
         * @param c the component where the border is to be painted
         *
         * @return the border insets
         */
        public Insets getBorderInsets(Component c) {

            return new Insets(THICKNESS, THICKNESS, THICKNESS, THICKNESS);
        }

        /**
         * Returns the border insets.
         *
         * @param c the component where the border is to be painted
         * @param insets insets ignored in this implementation
         *
         * @return the border insets
         */
        public Insets getBorderInsets(Component c, Insets insets) {

            return new Insets(THICKNESS, THICKNESS, THICKNESS, THICKNESS);
        }

        /**
         * Returns whether the border is opaque. This implementation always
         * return <code>false</code>.
         *
         * @return the boolean <code>false</code> value
         */
        public boolean isBorderOpaque() {

            return false;
        }
    }
}
