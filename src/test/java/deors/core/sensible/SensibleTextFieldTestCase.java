package deors.core.sensible;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import deors.core.sensible.SensibleBigDecimal;
import deors.core.sensible.SensibleDate;
import deors.core.sensible.SensibleDateTime;
import deors.core.sensible.SensibleInteger;
import deors.core.sensible.SensibleLong;
import deors.core.sensible.SensibleString;
import deors.core.sensible.SensibleTextField;
import deors.core.sensible.SensibleTime;

public class SensibleTextFieldTestCase {

    public SensibleTextFieldTestCase() {

        super();
    }

    @Test
    public void testConstructorDefault() {

        SensibleTextField field = new SensibleTextField();

        assertTrue(field.isEditable());
        assertTrue(field.isEnabled());
        assertFalse(field.isPasswordField());
        assertEquals('*', field.getEchoCharacter());
        assertEquals("", field.getText());
        assertNull(field.getData());
        assertNull(field.getAutoCompletionEntries());
    }

    @Test
    public void testWriteString() {

        SensibleTextField field = new SensibleTextField();
        SensibleString data = new SensibleString(100);
        field.setData(data);
        field.setText("new text");

        assertEquals(data, field.getData());
        assertEquals("new text", ((SensibleString) field.getData()).getString());
    }

    @Test
    public void testWriteInteger() {

        SensibleTextField field = new SensibleTextField();
        SensibleInteger data = new SensibleInteger(0, 100);
        field.setData(data);
        field.setText("99");

        assertEquals(data, field.getData());
        assertEquals(99, ((SensibleInteger) field.getData()).getNumber());
    }

    @Test
    public void testWriteLong() {

        SensibleTextField field = new SensibleTextField();
        SensibleLong data = new SensibleLong(0, 1000000000);
        field.setData(data);
        field.setText("999999999");

        assertEquals(data, field.getData());
        assertEquals(999999999, ((SensibleLong) field.getData()).getNumber());
    }

    @Test
    public void testWriteBigDecimal() {

        SensibleTextField field = new SensibleTextField();
        SensibleBigDecimal data = new SensibleBigDecimal(5, 2, true);
        field.setData(data);
        field.setText("12345,68");

        assertEquals(data, field.getData());
        assertEquals(new BigDecimal("12345.68"), ((SensibleBigDecimal) field.getData()).getNumber());
    }

    @Test
    public void testWriteDate() {

        SensibleTextField field = new SensibleTextField();
        SensibleDate data = new SensibleDate();
        field.setData(data);
        field.setText("12/1/2009");

        assertEquals(data, field.getData());
        assertArrayEquals(new int[] {2009, 1, 12}, ((SensibleDate) field.getData()).getDate());
    }

    @Test
    public void testWriteTime() {

        SensibleTextField field = new SensibleTextField();
        SensibleTime data = new SensibleTime();
        field.setData(data);
        field.setText("13:58:23");

        assertEquals(data, field.getData());
        assertArrayEquals(new int[] {13, 58, 23}, ((SensibleTime) field.getData()).getTime());
    }

    @Test
    public void testWriteDateTime() {

        SensibleTextField field = new SensibleTextField();
        SensibleDateTime data = new SensibleDateTime();
        field.setData(data);
        field.setText("12/1/2009 13:58:23");

        assertEquals(data, field.getData());
        assertArrayEquals(new int[] {2009, 1, 12, 13, 58, 23}, ((SensibleDateTime) field.getData()).getDateTime());
    }
}
