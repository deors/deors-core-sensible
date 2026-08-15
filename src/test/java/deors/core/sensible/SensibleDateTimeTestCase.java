package deors.core.sensible;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class SensibleDateTimeTestCase {

    public SensibleDateTimeTestCase() {

        super();
    }

    @Test
    public void testConstructors() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2008);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 12);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 35);
        SensibleDateTime dt1 = new SensibleDateTime();
        dt1.setDateTime(c);
        SensibleDateTime dt2 = new SensibleDateTime();
        dt2.setDateTime(dt1);
        SensibleDateTime dt3 = new SensibleDateTime();
        dt3.setDateTime("12/1/2008 12:25:35");
        SensibleDateTime dt4 = new SensibleDateTime();
        dt4.setDateTime(new SensibleDate(2008, 1, 12), new SensibleTime(12, 25, 35));
        SensibleDateTime dt5 = new SensibleDateTime();
        dt5.setDateTime(2008, 1, 12, 12, 25, 35);
        SensibleDateTime dt6 = new SensibleDateTime(c);
        SensibleDateTime dt7 = new SensibleDateTime(dt1);
        SensibleDateTime dt8 = new SensibleDateTime("12/1/2008 12:25:35");
        SensibleDateTime dt9 = new SensibleDateTime(new SensibleDate(2008, 1, 12), new SensibleTime(12, 25, 35));
        SensibleDateTime dt10 = new SensibleDateTime(2008, 1, 12, 12, 25, 35);

        assertEquals(dt1, dt2);
        assertEquals(dt1, dt3);
        assertEquals(dt1, dt4);
        assertEquals(dt1, dt5);
        assertEquals(dt1, dt6);
        assertEquals(dt1, dt7);
        assertEquals(dt1, dt8);
        assertEquals(dt1, dt9);
        assertEquals(dt1, dt10);
    }

    @Test
    public void testConstructorsFlag1() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2008);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 12);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 35);
        SensibleDateTime dt1 = new SensibleDateTime(false);
        dt1.setDateTime(c);
        SensibleDateTime dt2 = new SensibleDateTime(false);
        dt2.setDateTime(dt1);
        SensibleDateTime dt3 = new SensibleDateTime(false);
        dt3.setDateTime("12/1/2008 12:25");
        SensibleDateTime dt4 = new SensibleDateTime(false);
        dt4.setDateTime(new SensibleDate(2008, 1, 12), new SensibleTime(12, 25, 35));
        SensibleDateTime dt5 = new SensibleDateTime(false);
        dt5.setDateTime(2008, 1, 12, 12, 25, 0);
        SensibleDateTime dt6 = new SensibleDateTime(false, c);
        SensibleDateTime dt7 = new SensibleDateTime(false, dt1);
        SensibleDateTime dt8 = new SensibleDateTime(false, "12/1/2008 12:25");
        SensibleDateTime dt9 = new SensibleDateTime(false, new SensibleDate(2008, 1, 12), new SensibleTime(12, 25, 35));
        SensibleDateTime dt10 = new SensibleDateTime(false, 2008, 1, 12, 12, 25, 0);

        assertEquals(dt1, dt2);
        assertEquals(dt1, dt3);
        assertEquals(dt1, dt4);
        assertEquals(dt1, dt5);
        assertEquals(dt1, dt6);
        assertEquals(dt1, dt7);
        assertEquals(dt1, dt8);
        assertEquals(dt1, dt9);
        assertEquals(dt1, dt10);
    }

    @Test
    public void testConstructorsFlag2() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2008);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 12);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        SensibleDateTime dt1 = new SensibleDateTime(true, true);
        dt1.setDateTime(c);
        SensibleDateTime dt2 = new SensibleDateTime(true, true);
        dt2.setDateTime(dt1);
        SensibleDateTime dt3 = new SensibleDateTime(true, true);
        dt3.setDateTime("12/1/2008");
        SensibleDateTime dt4 = new SensibleDateTime(true, true);
        dt4.setDateTime(new SensibleDate(2008, 1, 12), new SensibleTime(0, 0, 0));
        SensibleDateTime dt5 = new SensibleDateTime(true, true);
        dt5.setDateTime(2008, 1, 12, 0, 0, 0);
        SensibleDateTime dt6 = new SensibleDateTime(true, true, c);
        SensibleDateTime dt7 = new SensibleDateTime(true, true, dt1);
        SensibleDateTime dt8 = new SensibleDateTime(true, true, "12/1/2008");
        SensibleDateTime dt9 = new SensibleDateTime(true, true, new SensibleDate(2008, 1, 12), new SensibleTime(0, 0, 0));
        SensibleDateTime dt10 = new SensibleDateTime(true, true, 2008, 1, 12, 0, 0, 0);

        assertEquals(dt1, dt2);
        assertEquals(dt1, dt3);
        assertEquals(dt1, dt4);
        assertEquals(dt1, dt5);
        assertEquals(dt1, dt6);
        assertEquals(dt1, dt7);
        assertEquals(dt1, dt8);
        assertEquals(dt1, dt9);
        assertEquals(dt1, dt10);
    }

    @Test
    public void testDateTimeFormat() {

        SensibleDateTime dt1 = new SensibleDateTime();

        assertEquals(' ', dt1.getDateTimeSeparator());
        assertEquals('/', dt1.getDateSeparator());
        assertEquals(':', dt1.getTimeSeparator());
        assertTrue(dt1.isTimeWithSeconds());

        assertTrue(dt1.isClear());
        assertFalse(dt1.isComplete());

        dt1.setDateTime(2009, 11, 9, 17, 32, 30);

        assertFalse(dt1.isClear());
        assertTrue(dt1.isComplete());

        assertEquals(SensibleDateTime.DMY_DATE_FORMAT, dt1.getDateFormat());
        assertEquals("9/11/2009 17:32:30", dt1.toString());

        dt1.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);

        assertEquals("11/9/2009 17:32:30", dt1.toString());

        dt1.setDateFormat(SensibleDateTime.YMD_DATE_FORMAT);
        dt1.setDateSeparator('-');
        dt1.setDateTimeSeparator('@');

        assertEquals("2009-11-9@17:32:30", dt1.toString());

        dt1.setTimeSeparator('.');
        dt1.setTimeWithSeconds(false);

        assertEquals("2009-11-9@17.32", dt1.toString());

        dt1 = new SensibleDateTime();
        dt1.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);
        dt1.setDateTime("12/25/2010 12:10:50");

        assertEquals("12/25/2010 12:10:50", dt1.toString());

        dt1 = new SensibleDateTime();
        dt1.setDateFormat(SensibleDateTime.YMD_DATE_FORMAT);
        dt1.setDateTime("2010/12/25 12:10:50");

        assertEquals("2010/12/25 12:10:50", dt1.toString());
    }

    @Test
    public void testDateTimeFormatInvalid1() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat("invalid");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid2() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);
            dt.setDateTime("15/1/2009 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid3() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);
            dt.setDateTime("151/1/2009 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid4() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);
            dt.setDateTime("15/151/2009 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid5() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);
            dt.setDateTime("15/1/20091 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid6() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("151/1/2009 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid7() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/151/2009 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid8() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/20091 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid9() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.YMD_DATE_FORMAT);
            dt.setDateTime("20091/1/1 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid10() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.YMD_DATE_FORMAT);
            dt.setDateTime("2009/151/1 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid11() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.YMD_DATE_FORMAT);
            dt.setDateTime("2009/1/151 12:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid12() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/2009 121:25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid13() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/2009 12:251:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid14() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/2009 12:25:351");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid15() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/2009 :25:35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid16() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/2009 12::35");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid17() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/1/2009 12:25:");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid18() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime(false);
            dt.setDateTime("1/1/2009 12:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid19() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("0/1/2009 12:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid20() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/0/2009 12:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid21() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("31/4/2009 12:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid22() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("29/2/2009 12:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid23() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/2/2008 12:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid24() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008 24:25:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid25() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008 12:60:15");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid26() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008 12:25:60");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid27() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid28() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid29() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008 12");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid30() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008 12:25");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid31() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008 :");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid32() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("/:");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid33() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(":");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid34() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("/");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid35() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("30/1/2008/10");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid36() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("12:15:24:12");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid37() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("A/1/2001 12:12:12");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid38() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.MDY_DATE_FORMAT);
            dt.setDateTime("A/1/2001 12:12:12");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid39() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateFormat(SensibleDateTime.YMD_DATE_FORMAT);
            dt.setDateTime("A/15/1 12:12:12");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeFormatInvalid40() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime("1/15/2001 A:12:12");

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid1() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(-1, 1, 15, 12, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid2() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, -1, 15, 12, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid3() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, -1, 12, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid4() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 15, -1, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid5() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 15, 12, -1, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid6() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 15, 12, 25, -1);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid7() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 2, 29, 12, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid8() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 13, 15, 12, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid9() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 32, 12, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid10() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 15, 24, 25, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid11() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 15, 12, 60, 35);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid12() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 1, 15, 12, 25, 60);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid13() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2009, 4, 31, 12, 25, 59);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testDateTimeInvalid14() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDateTime dt = new SensibleDateTime();
            dt.setDateTime(2008, 2, 30, 12, 25, 59);

        });
        assertEquals(SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"), ex.getMessage());
    }

    @Test
    public void testEquals() {

        SensibleDateTime dt = new SensibleDateTime(2009, 1, 15, 12, 25, 59);

        assertTrue(dt.equals(dt));
        assertFalse(dt.equals((Object) null));
        assertFalse(dt.equals(SensibleContext.BLANK));
    }

    @Test
    public void testEqualsCalendar() {

        SensibleDateTime dt = new SensibleDateTime(2009, 1, 15, 12, 25, 59);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2009);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 15);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 25);
        c.set(Calendar.SECOND, 59);

        assertTrue(dt.equals(c));
        assertFalse(dt.equals((Calendar) null));

        c.add(Calendar.SECOND, -1);
        assertFalse(dt.equals(c));

        c.add(Calendar.SECOND, 1);
        c.add(Calendar.MINUTE, -1);
        assertFalse(dt.equals(c));

        c.add(Calendar.MINUTE, 1);
        c.add(Calendar.HOUR_OF_DAY, -1);
        assertFalse(dt.equals(c));

        c.add(Calendar.HOUR_OF_DAY, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        assertFalse(dt.equals(c));

        c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, -1);
        assertFalse(dt.equals(c));

        c.add(Calendar.MONTH, 1);
        c.add(Calendar.YEAR, -1);
        assertFalse(dt.equals(c));
    }

    @Test
    public void testEqualsInt() {

        SensibleDateTime dt = new SensibleDateTime();
        dt.setDateTime(2009, 1, 15, 12, 25, 59);

        assertTrue(dt.equals(2009, 1, 15, 12, 25, 59));
        assertFalse(dt.equals(2009, 1, 15, 12, 25, 58));
        assertFalse(dt.equals(2009, 1, 15, 12, 24, 59));
        assertFalse(dt.equals(2009, 1, 15, 11, 25, 59));
        assertFalse(dt.equals(2009, 1, 14, 12, 25, 59));
        assertFalse(dt.equals(2009, 2, 15, 12, 25, 59));
        assertFalse(dt.equals(2008, 1, 15, 12, 25, 59));
    }

    @Test
    public void testGetSet() {

        SensibleDateTime dt = new SensibleDateTime(Calendar.getInstance());
        dt.setYear(2009);
        dt.setMonth(10);
        dt.setDay(9);
        dt.setHour(17);
        dt.setMinute(32);
        dt.setSecond(30);

        assertEquals(2009, dt.getYear());
        assertEquals(10, dt.getMonth());
        assertEquals(9, dt.getDay());
        assertEquals(17, dt.getHour());
        assertEquals(32, dt.getMinute());
        assertEquals(30, dt.getSecond());
    }

    @Test
    public void testGetSetLeapYear() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2012);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 29);
        SensibleDateTime dt = new SensibleDateTime(c);
        dt.setYear(2009);
        dt.setMonth(10);
        dt.setDay(9);
        dt.setHour(17);
        dt.setMinute(32);
        dt.setSecond(30);

        assertEquals(2009, dt.getYear());
        assertEquals(10, dt.getMonth());
        assertEquals(9, dt.getDay());
        assertEquals(17, dt.getHour());
        assertEquals(32, dt.getMinute());
        assertEquals(30, dt.getSecond());
    }

    @Test
    public void testCalendarValue() {

        SensibleDateTime dt1 = new SensibleDateTime(2008, 2, 28, 12, 25, 59);
        Calendar c = dt1.calendarValue();

        SensibleDateTime dt2 = new SensibleDateTime(c);

        assertEquals(dt1, dt2);
    }

    @Test
    public void testDateValue() {

        SensibleDateTime dt1 = new SensibleDateTime(2008, 2, 28, 12, 25, 59);
        long l = dt1.dateValue().getTime();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);

        SensibleDateTime dt2 = new SensibleDateTime(c);

        assertEquals(dt1, dt2);
    }

    @Test
    public void testGetDateTime() {

        SensibleDateTime dt = new SensibleDateTime(2008, 2, 28, 12, 25, 59);
        int[] i = dt.getDateTime();

        assertEquals(i[0], 2008);
        assertEquals(i[1], 2);
        assertEquals(i[2], 28);
        assertEquals(i[3], 12);
        assertEquals(i[4], 25);
        assertEquals(i[5], 59);
    }

    @Test
    public void testClear() {

        SensibleDateTime dt = new SensibleDateTime(Calendar.getInstance());
        dt.clear();

        assertEquals(0, dt.getYear());
        assertEquals(0, dt.getMonth());
        assertEquals(0, dt.getDay());
        assertEquals(0, dt.getHour());
        assertEquals(0, dt.getMinute());
        assertEquals(0, dt.getSecond());
    }

    @Test
    public void testLeapYear() {

        assertFalse(SensibleDateTime.isLeapYear(1500));
        assertTrue(SensibleDateTime.isLeapYear(1504));
        assertFalse(SensibleDateTime.isLeapYear(1509));
        assertFalse(SensibleDateTime.isLeapYear(1800));
        assertTrue(SensibleDateTime.isLeapYear(1804));
        assertFalse(SensibleDateTime.isLeapYear(1809));
        assertTrue(SensibleDateTime.isLeapYear(2000));
    }

    @Test
    public void testToString() {

        SensibleDateTime dt = new SensibleDateTime();
        dt.setDateTime(2008, 2, 29, 12, 25, 35);

        assertEquals("29/2/2008 12:25:35", dt.toString());
        assertEquals("29/02/2008 12:25:35", dt.toStringDMYHMS());
        assertEquals("02/29/2008 12:25:35", dt.toStringMDYHMS());
        assertEquals("2008/02/29 12:25:35", dt.toStringYMDHMS());
        assertEquals("2008/02/29 12:25:35", dt.toStringForSort());
        assertEquals("'29/2/2008 12:25:35'", dt.toStringForSQL());
    }

    @Test
    public void testToString2() {

        SensibleDateTime dt = new SensibleDateTime();
        dt.setDateTime(2008, 2, 29, 12, 25, 2);

        assertEquals("29/2/2008 12:25:02", dt.toString());
        assertEquals("29/02/2008 12:25:02", dt.toStringDMYHMS());
        assertEquals("02/29/2008 12:25:02", dt.toStringMDYHMS());
        assertEquals("2008/02/29 12:25:02", dt.toStringYMDHMS());
        assertEquals("2008/02/29 12:25:02", dt.toStringForSort());
        assertEquals("'29/2/2008 12:25:02'", dt.toStringForSQL());
    }

    @Test
    public void testAllowInsert() {

        SensibleDateTime dt = new SensibleDateTime();

        assertFalse(dt.allowInsert(0, "a"));
        assertFalse(dt.allowInsert(0, "*"));
        assertTrue(dt.allowInsert(0, "1"));
        assertTrue(dt.allowInsert(0, "1/"));
        assertTrue(dt.allowInsert(0, "1/1/2015 12:"));

        dt.changeValue("1/");

        assertTrue(dt.allowInsert(2, "1/"));

        SensibleTextField stf = new SensibleTextField(dt);

        assertFalse(dt.allowInsert(0, "", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
    }

    @Test
    public void testAllowRemove() {

        SensibleDateTime dt = new SensibleDateTime();

        dt.changeValue("1/1/2015");

        assertTrue(dt.allowRemove(4, 4));

        SensibleTextField stf = new SensibleTextField(dt);

        assertFalse(dt.allowRemove(0, 0, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
    }
}
