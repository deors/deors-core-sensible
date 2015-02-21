package deors.core.sensible;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

public class SensibleCalendarPickerTestCase {

    @Test
    public void testDefaultConstructor() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SensibleDate expected = new SensibleDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH));

        assertEquals(expected, scp.getDate());
    }

    @Test
    public void testConstructorFromDate() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker(new SensibleDate());

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SensibleDate expected = new SensibleDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH));

        assertEquals(expected, scp.getDate());
    }

    @Test
    public void testConstructorFromLocale() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker(Locale.getDefault());

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SensibleDate expected = new SensibleDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH));

        assertEquals(expected, scp.getDate());
    }

    @Test
    public void testGetters() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        assertEquals(calendar.get(Calendar.YEAR), scp.getYear());
        assertEquals(calendar.get(Calendar.MONTH) + 1, scp.getMonth());
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), scp.getDay());
    }

    @Test
    public void testSetDate() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker();
        SensibleDate sd = new SensibleDate(2015, 2, 25);

        scp.setDate(sd);

        assertEquals(2015, scp.getYear());
        assertEquals(2, scp.getMonth());
        assertEquals(25, scp.getDay());
    }

    @Test
    public void testSetYear() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker();
        SensibleDate sd = new SensibleDate(2016, 2, 29);

        scp.setDate(sd);
        scp.setYear(2015);

        assertEquals(28, scp.getDay());
    }

    @Test
    public void testSetMonth() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker();
        SensibleDate sd = new SensibleDate(2015, 3, 31);

        scp.setDate(sd);
        scp.setMonth(2);

        assertEquals(28, scp.getDay());
    }

    @Test
    public void testSetDay() {

        SensibleCalendarPicker scp = new SensibleCalendarPicker();
        SensibleDate sd = new SensibleDate(2015, 3, 31);

        scp.setDate(sd);
        scp.setDay(25);

        assertEquals(25, scp.getDay());
    }
}
