package deors.core.sensible;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiTask;
import org.junit.jupiter.api.Test;

public class SensibleDateTestCase {

    public SensibleDateTestCase() {

        super();
    }

    @Test
    public void testConstructors() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2008);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 12);
        Date d = c.getTime();
        SensibleDate d1 = new SensibleDate();
        d1.setDate(c);
        SensibleDate d2 = new SensibleDate();
        d2.setDate(d);
        SensibleDate d3 = new SensibleDate();
        d3.setDate(d1);
        SensibleDate d4 = new SensibleDate();
        d4.setDate("12/1/2008");
        SensibleDate d5 = new SensibleDate();
        d5.setDate(2008, 1, 12);
        SensibleDate d6 = new SensibleDate(c);
        SensibleDate d7 = new SensibleDate(d);
        SensibleDate d8 = new SensibleDate(d1);
        SensibleDate d9 = new SensibleDate("12/1/2008");
        SensibleDate d10 = new SensibleDate(2008, 1, 12);

        assertEquals(d1, d2);
        assertEquals(d1, d3);
        assertEquals(d1, d4);
        assertEquals(d1, d5);
        assertEquals(d1, d6);
        assertEquals(d1, d7);
        assertEquals(d1, d8);
        assertEquals(d1, d9);
        assertEquals(d1, d10);
    }

    @Test
    public void testDateFormat() {

        // dates in different formats
        SensibleDate d1 = new SensibleDate();
        assertTrue(d1.isClear());
        assertFalse(d1.isComplete());

        d1.setDate(2005, 5, 23);
        assertFalse(d1.isClear());
        assertTrue(d1.isComplete());
        assertEquals("23/5/2005", d1.toString());
        assertEquals('/', d1.getDateSeparator());

        SensibleDate d2 = new SensibleDate();
        d2.setDateFormat(SensibleDate.YMD_DATE_FORMAT);
        d2.setDate(2005, 5, 23);
        assertEquals("2005/5/23", d2.toString());
        assertEquals(SensibleDate.YMD_DATE_FORMAT, d2.getDateFormat());

        SensibleDate d3 = new SensibleDate();
        d3.setDate(2005, 5, 23);
        d3.setDateFormat(SensibleDate.MDY_DATE_FORMAT);
        assertEquals("5/23/2005", d3.toString());
        assertEquals(SensibleDate.MDY_DATE_FORMAT, d3.getDateFormat());

        // the same dates forcing DMY format
        assertEquals("23/05/2005", d1.toStringDMY());
        assertEquals("23/05/2005", d2.toStringDMY());
        assertEquals("23/05/2005", d3.toStringDMY());

        // different date separator
        d2.setDateSeparator('.');
        assertEquals("2005.5.23", d2.toString());
        assertEquals('.', d2.getDateSeparator());
    }

    @Test
    public void testDateFormatInvalid1() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat("invalid");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid2() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.MDY_DATE_FORMAT);
            d.setDate("15/1/2009");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid3() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.YMD_DATE_FORMAT);
            d.setDate("2009/15/1");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid4() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/15/2009");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid5() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid6() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid7() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/12");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid8() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/12/");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid9() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/12/2010/");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid10() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("111/12/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid11() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/121/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid12() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("1/12/20101");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid13() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("A/12/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid14() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.MDY_DATE_FORMAT);
            d.setDate("111/12/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid15() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.MDY_DATE_FORMAT);
            d.setDate("1/121/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid16() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.MDY_DATE_FORMAT);
            d.setDate("1/12/20101");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid17() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.MDY_DATE_FORMAT);
            d.setDate("A/12/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid18() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.YMD_DATE_FORMAT);
            d.setDate("20101/15/1");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid19() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.YMD_DATE_FORMAT);
            d.setDate("2010/151/1");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid20() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.YMD_DATE_FORMAT);
            d.setDate("2010/15/111");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid21() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDateFormat(SensibleDate.YMD_DATE_FORMAT);
            d.setDate("A/15/1");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid22() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("31/4/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid23() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("29/2/2010");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testDateFormatInvalid24() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate("30/2/2012");

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE_FORMAT"), ex.getMessage());
    }

    @Test
    public void testInvalidDate1() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2009, 1, 32);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate2() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2009, 2, 31);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate3() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2007, 2, 29);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate4() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2009, 2, -1);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate5() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2009, -1, 28);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate6() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(-1, 2, 28);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate7() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2009, 4, 31);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate8() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2008, 2, 30);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testInvalidDate9() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleDate d = new SensibleDate();
            d.setDate(2008, 13, 30);

        });
        assertEquals(SensibleContext.getMessage("DATE_ERR_INVALID_DATE"), ex.getMessage());
    }

    @Test
    public void testEquals() {

        SensibleDate d = new SensibleDate(2009, 1, 15);

        assertTrue(d.equals(d));
        assertFalse(d.equals((Object) null));
        assertFalse(d.equals(SensibleContext.BLANK));
    }

    @Test
    public void testEqualsCalendar() {

        SensibleDate d = new SensibleDate(2009, 1, 15);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2009);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 15);

        assertTrue(d.equals(c));
        assertFalse(d.equals((Calendar) null));

        c.add(Calendar.DAY_OF_MONTH, -1);
        assertFalse(d.equals(c));

        c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, -1);
        assertFalse(d.equals(c));

        c.add(Calendar.MONTH, 1);
        c.add(Calendar.YEAR, -1);
        assertFalse(d.equals(c));
    }

    @Test
    public void testEqualsInt() {

        SensibleDate d = new SensibleDate();
        d.setDate(2009, 1, 15);

        assertTrue(d.equals(2009, 1, 15));
        assertFalse(d.equals(2009, 1, 14));
        assertFalse(d.equals(2009, 2, 15));
        assertFalse(d.equals(2008, 1, 15));
    }

    @Test
    public void testGetSet() {

        SensibleDate d = new SensibleDate(Calendar.getInstance());
        d.setYear(2005);
        d.setMonth(5);
        d.setDay(23);

        assertEquals(2005, d.getYear());
        assertEquals(5, d.getMonth());
        assertEquals(23, d.getDay());
    }

    @Test
    public void testGetSetLeapYear() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2012);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 29);
        SensibleDate d = new SensibleDate(c);

        d.setYear(2005);
        d.setMonth(5);
        d.setDay(23);

        assertEquals(2005, d.getYear());
        assertEquals(5, d.getMonth());
        assertEquals(23, d.getDay());
    }

    @Test
    public void testCalendarValue() {

        SensibleDate d1 = new SensibleDate(2008, 2, 28);
        Calendar c = d1.calendarValue();

        SensibleDate d2 = new SensibleDate(c);

        assertEquals(d1, d2);
    }

    @Test
    public void testDateValue() {

        SensibleDate d1 = new SensibleDate(2008, 2, 28);
        long l = d1.dateValue().getTime();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);

        SensibleDate d2 = new SensibleDate(c);

        assertEquals(d1, d2);
    }

    @Test
    public void testGetDateTime() {

        SensibleDate d = new SensibleDate(2008, 2, 28);
        int[] i = d.getDate();

        assertEquals(i[0], 2008);
        assertEquals(i[1], 2);
        assertEquals(i[2], 28);
    }

    @Test
    public void testClear() {

        SensibleDate d = new SensibleDate(Calendar.getInstance());
        d.clear();

        assertEquals(0, d.getYear());
        assertEquals(0, d.getMonth());
        assertEquals(0, d.getDay());
    }

    @Test
    public void testLeapYear() {

        assertFalse(SensibleDate.isLeapYear(1500));
        assertTrue(SensibleDate.isLeapYear(1504));
        assertFalse(SensibleDate.isLeapYear(1509));
        assertFalse(SensibleDate.isLeapYear(1800));
        assertTrue(SensibleDate.isLeapYear(1804));
        assertFalse(SensibleDate.isLeapYear(1809));
        assertTrue(SensibleDate.isLeapYear(2000));
    }

    @Test
    public void testToString() {

        SensibleDate d = new SensibleDate();
        d.setDate("15/1/2009");

        assertEquals("15/1/2009", d.toString());
        assertEquals("15/01/2009", d.toStringDMY());
        assertEquals("01/15/2009", d.toStringMDY());
        assertEquals("2009/01/15", d.toStringYMD());
        assertEquals("2009/01/15", d.toStringForSort());
        assertEquals("'15/1/2009'", d.toStringForSQL());
    }

    @Test
    public void testAllowInsert() {

        SensibleDate d = new SensibleDate();

        assertFalse(d.allowInsert(0, "a"));
        assertFalse(d.allowInsert(0, "*"));
        assertTrue(d.allowInsert(0, "1"));
        assertTrue(d.allowInsert(0, "1/"));

        d.changeValue("1/");

        assertTrue(d.allowInsert(2, "1/"));

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleTextField stf = new SensibleTextField(d);

                assertFalse(d.allowInsert(0, "", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
            }
        });
    }

    @Test
    public void testAllowRemove() {

        SensibleDate d = new SensibleDate();

        d.changeValue("1/1/2015");

        assertTrue(d.allowRemove(4, 4));

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleTextField stf = new SensibleTextField(d);

                assertFalse(d.allowRemove(0, 0, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
            }
        });
    }
}
