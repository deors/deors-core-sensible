package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SensibleLongTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public SensibleLongTestCase() {

        super();
    }

    @Test
    public void testConstructors() {

        SensibleLong l1 = new SensibleLong();

        assertTrue(l1.isClear());

        l1.setNumber(234);

        assertFalse(l1.isClear());

        SensibleLong l2 = new SensibleLong();
        l2.setNumber(Long.valueOf(234));
        SensibleLong l3 = new SensibleLong();
        l3.setNumber(l1);
        SensibleLong l4 = new SensibleLong();
        l4.setNumber("234");
        SensibleLong l5 = new SensibleLong(234);
        SensibleLong l6 = new SensibleLong(Long.valueOf(234));
        SensibleLong l7 = new SensibleLong(l1);
        SensibleLong l8 = new SensibleLong("234");

        assertEquals(l1, l2);
        assertEquals(l1, l3);
        assertEquals(l1, l4);
        assertEquals(l1, l5);
        assertEquals(l1, l6);
        assertEquals(l1, l7);
        assertEquals(l1, l8);
    }

    @Test
    public void testConstructorsMore() {

        SensibleLong l1 = new SensibleLong(0, 300);
        l1.setNumber(234);
        SensibleLong l2 = new SensibleLong(0, 300);
        l2.setNumber(Long.valueOf(234));
        SensibleLong l3 = new SensibleLong(0, 300);
        l3.setNumber(l1);
        SensibleLong l4 = new SensibleLong(0, 300);
        l4.setNumber("234");
        SensibleLong l5 = new SensibleLong(0, 300, 234);
        SensibleLong l6 = new SensibleLong(0, 300, Long.valueOf(234));
        SensibleLong l7 = new SensibleLong(0, 300, l1);
        SensibleLong l8 = new SensibleLong(0, 300, "234");

        assertEquals(l1, l2);
        assertEquals(l1, l3);
        assertEquals(l1, l4);
        assertEquals(l1, l5);
        assertEquals(l1, l6);
        assertEquals(l1, l7);
        assertEquals(l1, l8);
        assertEquals(0, l1.getMinValue());
        assertEquals(300, l1.getMaxValue());
    }

    @Test
    public void testConstructorValueNotInRange() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_VALUE_NOT_IN_RANGE"));

        SensibleLong l = new SensibleLong(0, 300);
        l.setNumber(301);
    }

    @Test
    public void testAdd() {

        assertEquals(new SensibleLong(87), new SensibleLong(4).add(83));

        SensibleLong l = new SensibleLong(0, 1000, 900);
        assertEquals(new SensibleLong(1000), l.add(100));

        l.setRange(0, 1200);
        assertEquals(new SensibleLong(1200), l.add(300));
    }

    @Test
    public void testAddValueNotInRange() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_VALUE_NOT_IN_RANGE"));

        SensibleLong l = new SensibleLong(0, 1000, 900);
        l.add(500);
    }

    @Test
    public void testSubtract() {

        assertEquals(new SensibleLong(63), new SensibleLong(87).subtract(24));

        SensibleLong l = new SensibleLong(500, 1000, 600);
        assertEquals(new SensibleLong(590), l.subtract(10));

        l.setRange(400, 1000);
        assertEquals(new SensibleLong(490), l.subtract(110));
    }

    @Test
    public void testSubtractValueNotInRange() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("INT_ERR_VALUE_NOT_IN_RANGE"));

        SensibleLong l = new SensibleLong(500, 1000, 600);
        l.subtract(200);
    }

    @Test
    public void testOutOfRange1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_VALUE_NOT_IN_RANGE"));

        SensibleLong l = new SensibleLong(0, 1000);
        l.setNumber("1200");
    }

    @Test
    public void testOutOfRange1ChangeValue() {

        SensibleLong l = new SensibleLong(0, 1000);
        l.changeValue("1200");

        assertFalse(l.isValid());
    }

    @Test
    public void testOutOfRange2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_VALUE_NOT_IN_RANGE"));

        SensibleLong l = new SensibleLong(0, 1000);
        l.setNumber("-100");
    }

    @Test
    public void testOutOfRange2ChangeValue() {

        SensibleLong l = new SensibleLong(0, 1000);
        l.changeValue("-100");

        assertFalse(l.isValid());
    }

    @Test
    public void testInvalidRange1() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_INVALID_LIMIT"));

        SensibleLong l = new SensibleLong(0, 1000);
        l.setMinValue(1200);
    }

    @Test
    public void testZeroOutOfRange() {

        SensibleLong l = new SensibleLong(100, 1000);
        l.changeValue("");

        assertFalse(l.isValid());

        l.changeValue("-");

        assertFalse(l.isValid());

        l.changeValue("100");

        assertTrue(l.isValid());

        l = new SensibleLong(-10, 10);
        l.changeValue("");

        assertTrue(l.isValid());

        l.changeValue("-");

        assertTrue(l.isValid());

        l.changeValue("100");

        assertFalse(l.isValid());

        l = new SensibleLong(-1000, -10);
        l.changeValue("");

        assertFalse(l.isValid());

        l.changeValue("-");

        assertFalse(l.isValid());

        l.changeValue("-100");

        assertTrue(l.isValid());
    }

    @Test
    public void testInvalidRange2() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_INVALID_LIMIT"));

        SensibleLong l = new SensibleLong(0, 1000);
        l.setMaxValue(-100);
    }

    @Test
    public void testInvalidRange3() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_INVALID_LIMIT"));

        new SensibleLong(100, 0);
    }

    @Test
    public void testInvalidString() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("LONG_ERR_INVALID_STRING"));

        SensibleLong l = new SensibleLong(0, 1000);
        l.setNumber("A");
    }

    @Test
    public void testGetSet() {

        SensibleLong l = new SensibleLong();
        l.setNumber("100");

        assertEquals(100, l.longValue());
        assertEquals(100, l.getNumber());
    }

    @Test
    public void testCompareTo() {

        SensibleLong l1 = new SensibleLong(1);
        SensibleLong l2 = new SensibleLong(2);
        SensibleLong l3 = new SensibleLong(3);

        Long ll1 = 1L;
        Long ll3 = 3L;

        String s1 = "1";
        String s3 = "3";

        assertEquals(-1, l2.compareTo(l3));
        assertEquals(-1, l2.compareTo(new SensibleLong(ll3)));
        assertEquals(-1, l2.compareTo(new SensibleLong(s3)));

        assertEquals(1, l2.compareTo(l1));
        assertEquals(1, l2.compareTo(new SensibleLong(ll1)));
        assertEquals(1, l2.compareTo(new SensibleLong(s1)));
    }

    @Test
    public void testEquals() {

        SensibleLong l1 = new SensibleLong(5);
        Long ll1 = 5L;
        long lll1 = 5;
        String s1 = "5";
        SensibleLong l2 = new SensibleLong(3);
        Long ll2 = 3L;
        long lll2 = 3;
        String s2 = "3";
        SensibleString ss = new SensibleString("");

        assertTrue(l1.equals(l1));
        assertTrue(l1.equals(ll1));
        assertTrue(l1.equals(lll1));
        assertTrue(l1.equals(s1));
        assertFalse(l1.equals(l2));
        assertFalse(l1.equals(ll2));
        assertFalse(l1.equals(lll2));
        assertFalse(l1.equals(s2));

        assertFalse(l1.equals(ss));

        assertFalse(l1.equals((Long) null));
        assertFalse(l1.equals((SensibleLong) null));
        assertFalse(l1.equals((String) null));
    }

    @Test
    public void testHashCode() {

        SensibleLong l1 = new SensibleLong(5);
        SensibleLong l2 = new SensibleLong(5);

        assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    public void testToString() {

        SensibleLong l = new SensibleLong(123);

        assertEquals("123", l.toString());
        assertEquals("123", l.toStringForSQL());
        assertEquals("                 123", l.toStringForSort());

        l.setNumber(1000000000000000000L);

        assertEquals(" 1000000000000000000", l.toStringForSort());

        l.setNumber(-1000000000000000000L);

        assertEquals("-1000000000000000000", l.toStringForSort());
    }

    @Test
    public void testAllowInsert() {

        SensibleLong l = new SensibleLong();

        assertTrue(l.allowInsert(0, "1"));
        assertTrue(l.allowInsert(0, "0"));
        assertTrue(l.allowInsert(0, "-"));
        assertFalse(l.allowInsert(0, "a"));
        assertFalse(l.allowInsert(0, " "));
        assertTrue(l.allowInsert(0, "10"));
        assertTrue(l.allowInsert(0, "01"));
        assertTrue(l.allowInsert(0, "-1"));

        l.setValue("0");

        assertFalse(l.allowInsert(0, "0"));
        assertFalse(l.allowInsert(1, "0"));
        assertFalse(l.allowInsert(1, "-"));
        assertFalse(l.allowInsert(1, "-1"));

        l.setValue("1");

        assertFalse(l.allowInsert(0, "0"));
        assertTrue(l.allowInsert(1, "0"));

        l.setValue("-");

        assertFalse(l.allowInsert(0, "0"));
        assertFalse(l.allowInsert(1, "0"));
        assertFalse(l.allowInsert(1, "-"));

        l.clear();
        l.setMinValue(0);

        assertFalse(l.allowInsert(0, "-"));

        SensibleTextField stf = new SensibleTextField(l);

        assertFalse(l.allowInsert(0, "-", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
    }

    @Test
    public void testAllowRemove() {

        SensibleLong l = new SensibleLong("10");

        assertTrue(l.allowRemove(0, 0));
        assertTrue(l.allowRemove(0, 1));
        assertTrue(l.allowRemove(1, 1));

        SensibleTextField stf = new SensibleTextField(l);

        assertFalse(l.allowRemove(0, 0, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
    }

    @Test
    public void testFirePropertyChangeEvents() {

        SensibleLong l = new SensibleLong();
        final List<String> props = new ArrayList<>();
        l.addPropertyChangeListener(event -> {
            props.add(event.getPropertyName());
        });
        l.setValue("0");
        l.firePropertyChangeEvents();

        assertTrue(props.contains("number"));
        assertTrue(props.contains("minValue"));
        assertTrue(props.contains("maxValue"));
    }
}
