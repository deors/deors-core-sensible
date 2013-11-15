package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import deors.core.sensible.SensibleContext;
import deors.core.sensible.SensibleTime;

public class SensibleTimeTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public SensibleTimeTestCase() {

        super();
    }

    @Test
    public void testConstructors() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 35);
        SensibleTime t1 = new SensibleTime();
        t1.setTime(c);
        SensibleTime t2 = new SensibleTime();
        t2.setTime(t1);
        SensibleTime t3 = new SensibleTime();
        t3.setTime("12:25:35");
        SensibleTime t4 = new SensibleTime();
        t4.setTime(12, 25, 35);
        SensibleTime t5 = new SensibleTime(c);
        SensibleTime t6 = new SensibleTime(t1);
        SensibleTime t7 = new SensibleTime("12:25:35");
        SensibleTime t8 = new SensibleTime(12, 25, 35);

        assertEquals(t1, t2);
        assertEquals(t1, t3);
        assertEquals(t1, t4);
        assertEquals(t1, t5);
        assertEquals(t1, t6);
        assertEquals(t1, t7);
        assertEquals(t1, t8);
    }

    @Test
    public void testConstructorsMore() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 35);
        SensibleTime t1 = new SensibleTime(false);
        t1.setTime(c);
        SensibleTime t2 = new SensibleTime(false);
        t2.setTime(t1);
        SensibleTime t3 = new SensibleTime(false);
        t3.setTime("12:25");
        SensibleTime t4 = new SensibleTime(false);
        t4.setTime(12, 25, 0);
        SensibleTime t5 = new SensibleTime(false, c);
        SensibleTime t6 = new SensibleTime(false, t1);
        SensibleTime t7 = new SensibleTime(false, "12:25");
        SensibleTime t8 = new SensibleTime(false, 12, 25, 0);

        assertEquals(t1, t2);
        assertEquals(t1, t3);
        assertEquals(t1, t4);
        assertEquals(t1, t5);
        assertEquals(t1, t6);
        assertEquals(t1, t7);
        assertEquals(t1, t8);
    }

    @Test
    public void testTimeFormat() {

        // a time, with different time separator, without seconds and again with seconds
        SensibleTime t1 = new SensibleTime();
        assertTrue(t1.isClear());
        assertFalse(t1.isComplete());

        t1.setTime(16, 18, 23);
        assertFalse(t1.isClear());
        assertTrue(t1.isComplete());
        assertEquals("16:18:23", t1.toString());
        assertEquals(':', t1.getTimeSeparator());

        t1.setTimeSeparator('-');
        assertEquals("16-18-23", t1.toString());
        assertEquals('-', t1.getTimeSeparator());

        t1.setTimeWithSeconds(false);
        assertEquals("16-18", t1.toString());
        assertFalse(t1.isTimeWithSeconds());

        t1.setTimeWithSeconds(true);
        assertEquals("16-18-00", t1.toString());
        assertTrue(t1.isTimeWithSeconds());
    }

    @Test
    public void testTimeFormatInvalid1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12");
    }

    @Test
    public void testTimeFormatInvalid2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:");
    }

    @Test
    public void testTimeFormatInvalid3() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:25");
    }

    @Test
    public void testTimeFormatInvalid4() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:25:");
    }

    @Test
    public void testTimeFormatInvalid5() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:25:52:");
    }

    @Test
    public void testTimeFormatInvalid6() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime(false);
        t.setTime("12:25:52");
    }

    @Test
    public void testTimeFormatInvalid7() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("121:25:52");
    }

    @Test
    public void testTimeFormatInvalid8() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:251:52");
    }

    @Test
    public void testTimeFormatInvalid9() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:25:521");
    }

    @Test
    public void testTimeFormatInvalid10() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("A:25:52");
    }

    @Test
    public void testTimeFormatInvalid11() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("24:25:52");
    }

    @Test
    public void testTimeFormatInvalid12() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:60:52");
    }

    @Test
    public void testTimeFormatInvalid13() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME_FORMAT"));

        SensibleTime t = new SensibleTime();
        t.setTime("12:25:60");
    }

    @Test
    public void testTimeInvalid1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME"));

        SensibleTime t = new SensibleTime();
        t.setTime(-1, 25, 35);
    }

    @Test
    public void testTimeInvalid2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME"));

        SensibleTime t = new SensibleTime();
        t.setTime(12, -1, 35);
    }

    @Test
    public void testTimeInvalid3() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME"));

        SensibleTime t = new SensibleTime();
        t.setTime(12, 25, -1);
    }

    @Test
    public void testTimeInvalid4() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME"));

        SensibleTime t = new SensibleTime();
        t.setTime(24, 25, 35);
    }

    @Test
    public void testTimeInvalid5() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME"));

        SensibleTime t = new SensibleTime();
        t.setTime(12, 60, 35);
    }

    @Test
    public void testTimeInvalid6() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("TIME_ERR_INVALID_TIME"));

        SensibleTime t = new SensibleTime();
        t.setTime(12, 25, 60);
    }

    @Test
    public void testEquals() {

        SensibleTime t = new SensibleTime(12, 1, 15);

        assertTrue(t.equals(t));
        assertFalse(t.equals((Object) null));
        assertFalse(t.equals(SensibleContext.BLANK));
    }

    @Test
    public void testEqualsCalendar() {

        SensibleTime t = new SensibleTime(12, 25, 54);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 54);

        assertTrue(t.equals(c));
        assertFalse(t.equals((Calendar) null));

        c.add(Calendar.SECOND, -1);
        assertFalse(t.equals(c));

        c.add(Calendar.SECOND, 1);
        c.add(Calendar.MINUTE, -1);
        assertFalse(t.equals(c));

        c.add(Calendar.MINUTE, 1);
        c.add(Calendar.HOUR_OF_DAY, -1);
        assertFalse(t.equals(c));
    }

    @Test
    public void testEqualsInt() {

        SensibleTime t = new SensibleTime();
        t.setTime(12, 25, 54);

        assertTrue(t.equals(12, 25, 54));
        assertFalse(t.equals(12, 25, 55));
        assertFalse(t.equals(12, 26, 54));
        assertFalse(t.equals(13, 25, 54));
    }

    @Test
    public void testGetSet() {

        SensibleTime t = new SensibleTime(Calendar.getInstance());
        t.setHour(16);
        t.setMinute(18);
        t.setSecond(23);

        assertEquals(16, t.getHour());
        assertEquals(18, t.getMinute());
        assertEquals(23, t.getSecond());
    }

    @Test
    public void testCalendarValue() {

        SensibleTime t1 = new SensibleTime(12, 2, 28);
        Calendar c = t1.calendarValue();

        SensibleTime t2 = new SensibleTime(c);

        assertEquals(t1, t2);
    }

    @Test
    public void testDateValue() {

        SensibleTime t1 = new SensibleTime(12, 2, 28);
        long l = t1.dateValue().getTime();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);

        SensibleTime t2 = new SensibleTime(c);

        assertEquals(t1, t2);
    }

    @Test
    public void testGetDateTime() {

        SensibleTime d = new SensibleTime(12, 2, 28);
        int[] i = d.getTime();

        assertEquals(i[0], 12);
        assertEquals(i[1], 2);
        assertEquals(i[2], 28);
    }

    @Test
    public void testClear() {

        SensibleTime t = new SensibleTime(Calendar.getInstance());
        t.clear();

        assertEquals(0, t.getHour());
        assertEquals(0, t.getMinute());
        assertEquals(0, t.getSecond());
    }

    @Test
    public void testToString() {

        SensibleTime t = new SensibleTime(12, 25, 35);

        assertEquals("12:25:35", t.toString());
        assertEquals("12:25:35", t.toStringHMS());
        assertEquals("12:25:35", t.toStringForSort());
        assertEquals("'12:25:35'", t.toStringForSQL());
    }

    @Test
    public void testToString2() {

        SensibleTime t = new SensibleTime(12, 25, 5);

        assertEquals("12:25:05", t.toString());
        assertEquals("12:25:05", t.toStringHMS());
        assertEquals("12:25:05", t.toStringForSort());
        assertEquals("'12:25:05'", t.toStringForSQL());
    }
}
