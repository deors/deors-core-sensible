package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SensibleIntegerTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public SensibleIntegerTestCase() {

        super();
    }

    @Test
    public void testConstructors() {

        SensibleInteger i1 = new SensibleInteger();

        assertTrue(i1.isClear());

        i1.setNumber(234);

        assertFalse(i1.isClear());

        SensibleInteger i2 = new SensibleInteger();
        i2.setNumber(Integer.valueOf(234));
        SensibleInteger i3 = new SensibleInteger();
        i3.setNumber(i1);
        SensibleInteger i4 = new SensibleInteger();
        i4.setNumber("234");
        SensibleInteger i5 = new SensibleInteger(234);
        SensibleInteger i6 = new SensibleInteger(Integer.valueOf(234));
        SensibleInteger i7 = new SensibleInteger(i1);
        SensibleInteger i8 = new SensibleInteger("234");

        assertEquals(i1, i2);
        assertEquals(i1, i3);
        assertEquals(i1, i4);
        assertEquals(i1, i5);
        assertEquals(i1, i6);
        assertEquals(i1, i7);
        assertEquals(i1, i8);
    }

    @Test
    public void testConstructorsMore() {

        SensibleInteger i1 = new SensibleInteger(0, 300);
        i1.setNumber(234);
        SensibleInteger i2 = new SensibleInteger(0, 300);
        i2.setNumber(Integer.valueOf(234));
        SensibleInteger i3 = new SensibleInteger(0, 300);
        i3.setNumber(i1);
        SensibleInteger i4 = new SensibleInteger(0, 300);
        i4.setNumber("234");
        SensibleInteger i5 = new SensibleInteger(0, 300, 234);
        SensibleInteger i6 = new SensibleInteger(0, 300, Integer.valueOf(234));
        SensibleInteger i7 = new SensibleInteger(0, 300, i1);
        SensibleInteger i8 = new SensibleInteger(0, 300, "234");

        assertEquals(i1, i2);
        assertEquals(i1, i3);
        assertEquals(i1, i4);
        assertEquals(i1, i5);
        assertEquals(i1, i6);
        assertEquals(i1, i7);
        assertEquals(i1, i8);
        assertEquals(0, i1.getMinValue());
        assertEquals(300, i1.getMaxValue());
    }

    @Test
    public void testConstructorInvalid1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_VALUE_NOT_IN_RANGE"));

        SensibleInteger i = new SensibleInteger(0, 300);
        i.setNumber(301);
    }

    @Test
    public void testAdd() {

        assertEquals(new SensibleInteger(87), new SensibleInteger(4).add(83));

        SensibleInteger i = new SensibleInteger(0, 1000, 900);
        i.setRange(0, 500);
        assertEquals(new SensibleInteger(500), i.add(100));

        i.setRange(1200, 1500);
        assertEquals(new SensibleInteger(1200), i.add(100));
    }

    @Test
    public void testSubtract() {

        assertEquals(new SensibleInteger(63), new SensibleInteger(87).subtract(24));

        SensibleInteger i = new SensibleInteger(0, 1000, 600);
        i.setRange(0, 500);
        assertEquals(new SensibleInteger(500), i.subtract(10));

        i.setRange(1200, 1500);
        assertEquals(new SensibleInteger(1200), i.subtract(10));
    }

    @Test
    public void testOutOfRange1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_VALUE_NOT_IN_RANGE"));

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.setNumber("1200");
    }

    @Test
    public void testOutOfRange1ChangeValue() {

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.changeValue("1200");

        assertFalse(i.isValid());
    }

    @Test
    public void testOutOfRange2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_VALUE_NOT_IN_RANGE"));

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.setNumber("-100");
    }

    @Test
    public void testOutOfRange2ChangeValue() {

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.changeValue("-100");

        assertFalse(i.isValid());
    }

    @Test
    public void testZeroOutOfRange() {

        SensibleInteger i = new SensibleInteger(100, 1000);
        i.changeValue("");

        assertFalse(i.isValid());

        i.changeValue("-");

        assertFalse(i.isValid());

        i.changeValue("100");

        assertTrue(i.isValid());

        i = new SensibleInteger(-10, 10);
        i.changeValue("");

        assertTrue(i.isValid());

        i.changeValue("-");

        assertTrue(i.isValid());

        i.changeValue("100");

        assertFalse(i.isValid());

        i = new SensibleInteger(-1000, -10);
        i.changeValue("");

        assertFalse(i.isValid());

        i.changeValue("-");

        assertFalse(i.isValid());

        i.changeValue("-100");

        assertTrue(i.isValid());
    }

    @Test
    public void testInvalidRange1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_INVALID_LIMIT"));

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.setMinValue(1200);
    }

    @Test
    public void testInvalidRange2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_INVALID_LIMIT"));

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.setMaxValue(-100);
    }

    @Test
    public void testInvalidRange3() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_INVALID_LIMIT"));

        new SensibleInteger(100, 0);
    }

    @Test
    public void testInvalidString() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_INVALID_STRING"));

        SensibleInteger i = new SensibleInteger(0, 1000);
        i.setNumber("A");
    }

    @Test
    public void testGetSet() {

        SensibleInteger i = new SensibleInteger();
        i.setNumber("100");

        assertEquals(100, i.intValue());
        assertEquals(100, i.integerValue());
        assertEquals(100, i.getNumber());
    }

    @Test
    public void testCompareTo() {

        SensibleInteger i1 = new SensibleInteger(1);
        SensibleInteger i2 = new SensibleInteger(2);
        SensibleInteger i3 = new SensibleInteger(3);

        Integer ii1 = 1;
        Integer ii3 = 3;

        String s1 = "1";
        String s3 = "3";

        assertEquals(-1, i2.compareTo(i3));
        assertEquals(-1, i2.compareTo(new SensibleInteger(ii3)));
        assertEquals(-1, i2.compareTo(new SensibleInteger(s3)));

        assertEquals(1, i2.compareTo(i1));
        assertEquals(1, i2.compareTo(new SensibleInteger(ii1)));
        assertEquals(1, i2.compareTo(new SensibleInteger(s1)));
    }

    @Test
    public void testEquals() {

        SensibleInteger i1 = new SensibleInteger(5);
        Integer ii1 = 5;
        int iii1 = 5;
        String s1 = "5";
        SensibleInteger i2 = new SensibleInteger(3);
        Integer ii2 = 3;
        int iii2 = 3;
        String s2 = "3";
        SensibleString ss = new SensibleString("");

        assertTrue(i1.equals(i1));
        assertTrue(i1.equals(ii1));
        assertTrue(i1.equals(iii1));
        assertTrue(i1.equals(s1));
        assertFalse(i1.equals(i2));
        assertFalse(i1.equals(ii2));
        assertFalse(i1.equals(iii2));
        assertFalse(i1.equals(s2));

        assertFalse(i1.equals(ss));

        assertFalse(i1.equals((Integer) null));
        assertFalse(i1.equals((SensibleInteger) null));
        assertFalse(i1.equals((String) null));
    }

    @Test
    public void testHashCode() {

        SensibleInteger i1 = new SensibleInteger(5);
        SensibleInteger i2 = new SensibleInteger(5);

        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    public void testToString() {

        SensibleInteger i = new SensibleInteger(123);

        assertEquals("123", i.toString());
        assertEquals("123", i.toStringForSQL());
        assertEquals("        123", i.toStringForSort());

        i.setNumber(1000000000);

        assertEquals(" 1000000000", i.toStringForSort());

        i.setNumber(-1000000000);

        assertEquals("-1000000000", i.toStringForSort());
    }

    @Test
    public void testAllowInsert() {

        SensibleInteger i = new SensibleInteger();

        assertTrue(i.allowInsert(0, "1"));
        assertTrue(i.allowInsert(0, "0"));
        assertTrue(i.allowInsert(0, "-"));
        assertFalse(i.allowInsert(0, "a"));
        assertFalse(i.allowInsert(0, " "));
        assertTrue(i.allowInsert(0, "10"));
        assertTrue(i.allowInsert(0, "01"));
        assertTrue(i.allowInsert(0, "-1"));

        i.setValue("0");

        assertFalse(i.allowInsert(0, "0"));
        assertFalse(i.allowInsert(1, "0"));
        assertFalse(i.allowInsert(1, "-"));
        assertFalse(i.allowInsert(1, "-1"));

        i.setValue("1");

        assertFalse(i.allowInsert(0, "0"));
        assertTrue(i.allowInsert(1, "0"));

        i.setValue("-");

        assertFalse(i.allowInsert(0, "0"));
        assertFalse(i.allowInsert(1, "0"));
        assertFalse(i.allowInsert(1, "-"));

        i.clear();
        i.setMinValue(0);

        assertFalse(i.allowInsert(0, "-"));

        SensibleTextField stf = new SensibleTextField(i);

        assertFalse(i.allowInsert(0, "-", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
    }

    @Test
    public void testAllowRemove() {

        SensibleInteger i = new SensibleInteger("10");

        assertTrue(i.allowRemove(0, 0));
        assertTrue(i.allowRemove(0, 1));
        assertTrue(i.allowRemove(1, 1));

        SensibleTextField stf = new SensibleTextField(i);

        assertFalse(i.allowRemove(0, 0, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
    }

    @Test
    public void testFirePropertyChangeEvents() {

        SensibleInteger i = new SensibleInteger();
        final List<String> props = new ArrayList<>();
        i.addPropertyChangeListener(event -> {
            props.add(event.getPropertyName());
        });
        i.setValue("0");
        i.firePropertyChangeEvents();

        assertTrue(props.contains("number"));
        assertTrue(props.contains("minValue"));
        assertTrue(props.contains("maxValue"));
    }
}
