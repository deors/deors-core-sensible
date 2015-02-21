package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SensibleBigDecimalTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public SensibleBigDecimalTestCase() {

        super();
    }

    @Test
    public void testConstructors() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal();
        bd1.setNumber("12345,68");
        SensibleBigDecimal bd2 = new SensibleBigDecimal();
        bd2.setNumber(new BigDecimal("12345.68"));
        SensibleBigDecimal bd3 = new SensibleBigDecimal();
        bd3.setNumber(bd1);
        SensibleBigDecimal bd4 = new SensibleBigDecimal("12345,68");
        SensibleBigDecimal bd5 = new SensibleBigDecimal(new BigDecimal("12345.68"));
        SensibleBigDecimal bd6 = new SensibleBigDecimal(bd1);

        assertEquals(bd1, bd2);
        assertEquals(bd1, bd3);
        assertEquals(bd1, bd4);
        assertEquals(bd1, bd5);
        assertEquals(bd1, bd6);
    }

    @Test
    public void testConstructorsMore() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal(5, 2);
        bd1.setNumber("12345,68");
        SensibleBigDecimal bd2 = new SensibleBigDecimal(5, 2, false);
        bd2.setNumber(new BigDecimal("12345.68"));
        SensibleBigDecimal bd3 = new SensibleBigDecimal(5, 2, true);
        bd3.setNumber(bd1);
        SensibleBigDecimal bd4 = new SensibleBigDecimal(5, 2, "12345,68");
        SensibleBigDecimal bd5 = new SensibleBigDecimal(5, 2, new BigDecimal("12345.68"));
        SensibleBigDecimal bd6 = new SensibleBigDecimal(5, 2, bd1);
        SensibleBigDecimal bd7 = new SensibleBigDecimal(5, 2, true, "12345,68");
        SensibleBigDecimal bd8 = new SensibleBigDecimal(5, 2, true, new BigDecimal("12345.68"));
        SensibleBigDecimal bd9 = new SensibleBigDecimal(5, 2, true, bd1);

        assertEquals(bd1, bd2);
        assertEquals(bd1, bd3);
        assertEquals(bd1, bd4);
        assertEquals(bd1, bd5);
        assertEquals(bd1, bd6);
        assertEquals(bd1, bd7);
        assertEquals(bd1, bd8);
        assertEquals(bd1, bd9);
        assertEquals(5, bd1.getMaxIntegerDigits());
        assertEquals(2, bd1.getMaxFractionalDigits());
        assertFalse(bd2.isNegativeValuesAllowed());
        assertTrue(bd3.isNegativeValuesAllowed());
    }

    @Test
    public void testConstructorInvalid1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("BIGD_ERR_INVALID_STRING"));

        SensibleBigDecimal bd = new SensibleBigDecimal(5, 2);
        bd.setNumber("123456");
    }

    @Test
    public void testConstructorInvalid2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("BIGD_ERR_INVALID_STRING"));

        SensibleBigDecimal bd = new SensibleBigDecimal(5, 2);
        bd.setNumber("12345,678");
    }

    @Test
    public void testConstructorInvalid3() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("BIGD_ERR_INVALID_STRING"));

        SensibleBigDecimal bd = new SensibleBigDecimal(5, 2, false);
        bd.setNumber("-123");
    }

    @Test
    public void testAdd() {

        assertEquals(new SensibleBigDecimal("87"), new SensibleBigDecimal("4").add(83));
    }

    @Test
    public void testSubtract() {

        assertEquals(new SensibleBigDecimal("63"), new SensibleBigDecimal("87").subtract(24));
    }

    @Test
    public void testNumberFormat() {

        // big decimal 0
        SensibleBigDecimal bd1 = new SensibleBigDecimal();
        assertEquals("", bd1.toString());
        assertEquals(new java.math.BigDecimal(0), bd1.getNumber());

        // checking formats from strings
        String[] sources = {
            "10.512,23",
            "9.725",
            "123,4",
            "982",
            ",0976",
            "0,0976",
            "99.910.512,83981646155",
            "9.87.654.3,2837",
            "10512,334455",
            "961,36.21",
            "0128,287",
            "018.238,198",
            "12,",
            ",",
            "0,",
            ",0",
            "0,0",
            "-",
            "-0,",
            "-,0",
            "-0,0",
            "-10.512,23",
            "-9.725",
            "-123,4",
            "-982",
            "-,0976",
            "-0,0976",
            "-99.910.512,83981646155",
            "-9.87.654.3,2837",
            "-10512,334455",
            "-961,36.21",
            "-0128,287",
            "-018.238,198",
        };
        java.math.BigDecimal[] expecteds = {
            new java.math.BigDecimal("10512.23"),
            new java.math.BigDecimal("9725"),
            new java.math.BigDecimal("123.4"),
            new java.math.BigDecimal("982"),
            new java.math.BigDecimal("0.0976"),
            new java.math.BigDecimal("0.0976"),
            new java.math.BigDecimal("99910512.83981646155"),
            null,
            new java.math.BigDecimal("10512.334455"),
            null,
            new java.math.BigDecimal("128.287"),
            new java.math.BigDecimal("18238.198"),
            new java.math.BigDecimal("12"),
            new java.math.BigDecimal("0"),
            new java.math.BigDecimal("0"),
            new java.math.BigDecimal("0.0"),
            new java.math.BigDecimal("0.0"),
            new java.math.BigDecimal("0"),
            new java.math.BigDecimal("0"),
            new java.math.BigDecimal("0.0"),
            new java.math.BigDecimal("0.0"),
            new java.math.BigDecimal("-10512.23"),
            new java.math.BigDecimal("-9725"),
            new java.math.BigDecimal("-123.4"),
            new java.math.BigDecimal("-982"),
            new java.math.BigDecimal("-0.0976"),
            new java.math.BigDecimal("-0.0976"),
            new java.math.BigDecimal("-99910512.83981646155"),
            null,
            new java.math.BigDecimal("-10512.334455"),
            null,
            new java.math.BigDecimal("-128.287"),
            new java.math.BigDecimal("-18238.198"),
        };
        for (int i = 0; i < sources.length; i++) {
            if (expecteds[i] == null) {
                assertNull(bd1.checkFormat(sources[i]));
            } else {
                assertEquals(expecteds[i], bd1.checkFormat(sources[i]));
            }
        }

        // checking formats from numbers
        java.math.BigDecimal[] sources2 = {
            new java.math.BigDecimal("10512.23"),
            new java.math.BigDecimal("9725"),
            new java.math.BigDecimal("123.4"),
            new java.math.BigDecimal("982"),
            new java.math.BigDecimal(".0976"),
            new java.math.BigDecimal("99910512.83981646155"),
            new java.math.BigDecimal("9876543.2837"),
            new java.math.BigDecimal("10512.334455"),
            new java.math.BigDecimal("-10512.23"),
            new java.math.BigDecimal("-9725"),
            new java.math.BigDecimal("-123.4"),
            new java.math.BigDecimal("-982"),
            new java.math.BigDecimal("-.0976"),
            new java.math.BigDecimal("-99910512.83981646155"),
            new java.math.BigDecimal("-9876543.2837"),
            new java.math.BigDecimal("-10512.334455"),
        };
        String[] expecteds2 = {
            "10.512,23",
            "9.725",
            "123,4",
            "982",
            "0,0976",
            "99.910.512,83981646155",
            "9.876.543,2837",
            "10.512,334455",
            "-10.512,23",
            "-9.725",
            "-123,4",
            "-982",
            "-0,0976",
            "-99.910.512,83981646155",
            "-9.876.543,2837",
            "-10.512,334455",
        };
        for (int i = 0; i < sources2.length; i++) {
            assertEquals(expecteds2[i], bd1.checkFormat(sources2[i]));
        }

        // setNumber 10.512,23
        bd1.setNumber("10.512,23");
        assertEquals("10.512,23", bd1.toString());

        // different decimal and group separators
        bd1.setDecimalSeparator('.');
        bd1.setGroupSeparator('_');
        assertEquals("10_512.23", bd1.toString());

        bd1.setDecimalSeparator(',');
        bd1.setGroupSeparator('\'');
        assertEquals("10'512,23", bd1.toString());

        bd1.setDecimalSeparator('$');
        assertEquals("10'512$23", bd1.toString());

        bd1.setDecimalSeparator(',');
        bd1.setGroupSeparator('.');
        assertEquals("10.512,23", bd1.toString());
    }

    @Test
    public void testToStringForSort() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal(-1, -1, "254");

        assertEquals("254", bd1.toStringForSort());

        SensibleBigDecimal bd2 = new SensibleBigDecimal(5, 2, "121,32");

        assertEquals("   121,32", bd2.toStringForSort());

        SensibleBigDecimal bd3 = new SensibleBigDecimal(5, 2, "121");

        assertEquals("   121", bd3.toStringForSort());

        SensibleBigDecimal bd4 = new SensibleBigDecimal(5, 2, false, "121,32");

        assertEquals("  121,32", bd4.toStringForSort());

        SensibleBigDecimal bd5 = new SensibleBigDecimal(5, 2, false, "121");

        assertEquals("  121", bd5.toStringForSort());
    }

    @Test
    public void testEqualsString() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        String sbd1 = "254";
        String sbd2 = "255";

        assertFalse(bd1.equals((String) null));
        assertTrue(bd1.equals(sbd1));
        assertFalse(bd1.equals(sbd2));
    }

    @Test
    public void testEqualsBigDecimal() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        BigDecimal mbd1 = new BigDecimal("254");
        BigDecimal mbd2 = new BigDecimal("255");

        assertFalse(bd1.equals((BigDecimal) null));
        assertTrue(bd1.equals(mbd1));
        assertFalse(bd1.equals(mbd2));
    }

    @Test
    public void testEqualsObject() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        SensibleBigDecimal bd2 = new SensibleBigDecimal("254");
        SensibleBigDecimal bd3 = new SensibleBigDecimal("255");

        assertFalse(bd1.equals((Object) null));
        assertTrue(bd1.equals(bd1));
        assertTrue(bd1.equals(bd2));
        assertFalse(bd1.equals(bd3));
    }

    @Test
    public void testFirePropertyChangeEvents() {

        SensibleBigDecimal bd = new SensibleBigDecimal("254");
        final List<String> props = new ArrayList<>();
        bd.addPropertyChangeListener(event -> {
            props.add(event.getPropertyName());
        });
        bd.setValue("123");
        bd.firePropertyChangeEvents();

        assertTrue(props.contains("number"));
        assertTrue(props.contains("maxIntegerDigits"));
        assertTrue(props.contains("maxFractionalDigits"));
        assertTrue(props.contains("negativeValuesAllowed"));
        assertTrue(props.contains("decimalSeparator"));
        assertTrue(props.contains("groupSeparator"));
    }

    @Test
    public void testCompareTo() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        SensibleBigDecimal bd2 = new SensibleBigDecimal("255");

        BigDecimal mbd1 = new BigDecimal("254");
        BigDecimal mbd2 = new BigDecimal("255");

        String sbd1 = "254";
        String sbd2 = "255";

        assertTrue(bd1.compareTo(bd1) == 0);
        assertTrue(bd1.compareTo(bd2) < 0);
        assertTrue(bd2.compareTo(bd1) > 0);

        assertTrue(bd1.compareTo(mbd1) == 0);
        assertTrue(bd1.compareTo(mbd2) < 0);
        assertTrue(bd2.compareTo(mbd1) > 0);

        assertTrue(bd1.compareTo(sbd1) == 0);
        assertTrue(bd1.compareTo(sbd2) < 0);
        assertTrue(bd2.compareTo(sbd1) > 0);
    }

    @Test
    public void testToStringForSQL() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        SensibleBigDecimal bd2 = new SensibleBigDecimal("-255");

        assertEquals(bd1.toString(), bd1.toStringForSQL());
        assertEquals(bd2.toString(), bd2.toStringForSQL());
    }

    @Test
    public void testToStringFormatted() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        SensibleBigDecimal bd2 = new SensibleBigDecimal("-255");
        SensibleBigDecimal bd3 = new SensibleBigDecimal("-1255,12");

        assertEquals("254", bd1.toStringFormatted());
        assertEquals("-255", bd2.toStringFormatted());
        assertEquals("-1.255,12", bd3.toStringFormatted());
    }

    @Test
    public void testBigDecimalValue() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");

        assertTrue(new BigDecimal("254").equals(bd1.bigDecimalValue()));
    }

    @Test
    public void testHashCode() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("254");
        SensibleBigDecimal bd2 = new SensibleBigDecimal("254");

        assertEquals(bd1.hashCode(), bd2.hashCode());
    }

    @Test
    public void testAllowInsert() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("123");
        SensibleTextField stf = new SensibleTextField(bd1);

        assertTrue(bd1.allowInsert(0, "1", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertTrue(bd1.allowInsert(0, "-", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertFalse(bd1.allowInsert(0, "a", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertTrue(bd1.allowInsert(2, "1", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertFalse(bd1.allowInsert(2, "-", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertFalse(bd1.allowInsert(2, "a", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

        assertFalse(bd1.allowInsert(0, "1"));
    }

    @Test
    public void testAllowRemove() {

        SensibleBigDecimal bd1 = new SensibleBigDecimal("123");
        SensibleTextField stf = new SensibleTextField(bd1);

        assertTrue(bd1.allowRemove(0, 1, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertTrue(bd1.allowRemove(0, 2, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

        assertFalse(bd1.allowRemove(0, 1));

        bd1 = new SensibleBigDecimal("1234");
        stf = new SensibleTextField(bd1);

        assertTrue(bd1.allowRemove(0, 1, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
        assertTrue(bd1.allowRemove(0, 2, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

        assertFalse(bd1.allowRemove(0, 1));
    }
}
