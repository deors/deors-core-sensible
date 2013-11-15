package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import deors.core.sensible.SensibleBigDecimal;
import deors.core.sensible.SensibleContext;

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
}
