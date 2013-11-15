package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import deors.core.sensible.SensibleBoolean;
import deors.core.sensible.SensibleContext;
import deors.core.sensible.SensibleInteger;

public class SensibleBooleanTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public SensibleBooleanTestCase() {

        super();
    }

    @Test
    public void testCreateDefault() {

        SensibleBoolean b = new SensibleBoolean();

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());

        b.clear();

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());
    }

    @Test
    public void testCreateFalse() {

        SensibleBoolean b = new SensibleBoolean(false);

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());

        b.clear();

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());
    }

    @Test
    public void testCreateTrue() {

        SensibleBoolean b = new SensibleBoolean(true);

        assertTrue(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(-1, b.hashCode());

        b.clear();

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());
    }

    @Test
    public void testCreateBooleanTrue() {

        SensibleBoolean b = new SensibleBoolean(Boolean.TRUE);

        assertTrue(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(-1, b.hashCode());
    }

    @Test
    public void testCreateBooleanFalse() {

        SensibleBoolean b = new SensibleBoolean(Boolean.FALSE);

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());
    }

    @Test
    public void testCreateIntTrue() {

        SensibleBoolean b = new SensibleBoolean(-1);

        assertTrue(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(-1, b.hashCode());
    }

    @Test
    public void testCreateIntFalse() {

        SensibleBoolean b = new SensibleBoolean(0);

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());
    }

    @Test
    public void testCreateStringTrue() {

        SensibleBoolean b = new SensibleBoolean(SensibleBoolean.BOOLEAN_TRUE_AS_STRING);

        assertTrue(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(-1, b.hashCode());
    }

    @Test
    public void testCreateStringFalse() {

        SensibleBoolean b = new SensibleBoolean(SensibleBoolean.BOOLEAN_FALSE_AS_STRING);

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());
    }

    @Test
    public void testCreateStringInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("BOOL_ERR_INVALID_STRING"));

        new SensibleBoolean("invalid");
    }

    @Test
    public void testCreateSensibleTrue() {

        SensibleBoolean b0 = new SensibleBoolean(true);
        SensibleBoolean b = new SensibleBoolean(b0);

        assertTrue(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(-1, b.hashCode());

        assertEquals(b.isFlag(), b0.isFlag());

        b0.setFlag(false);

        assertTrue(b.isFlag());
        assertFalse(b0.isFlag());
    }

    @Test
    public void testCreateSensibleFalse() {

        SensibleBoolean b0 = new SensibleBoolean(false);
        SensibleBoolean b = new SensibleBoolean(b0);

        assertFalse(b.isFlag());
        assertFalse(b.isClear());
        assertEquals(b.booleanValue(), b.isFlag());
        assertEquals(0, b.hashCode());

        assertEquals(b.isFlag(), b0.isFlag());

        b0.setFlag(true);

        assertFalse(b.isFlag());
        assertTrue(b0.isFlag());
    }

    @Test
    public void testEquals() {

        SensibleBoolean bt = new SensibleBoolean(true);
        SensibleBoolean bf = new SensibleBoolean(false);

        assertTrue(bt.equals(bt));
        assertTrue(bf.equals(bf));

        assertFalse(bt.equals(bf));
        assertFalse(bf.equals(bt));

        assertFalse(bt.equals((Object) null));
        assertFalse(bt.equals((Boolean) null));
        assertFalse(bt.equals((Integer) null));
        assertFalse(bt.equals((SensibleInteger) null));
        assertFalse(bt.equals((String) null));
        assertTrue(bt.equals(true));
        assertFalse(bt.equals(false));
        assertTrue(bt.equals(-1));
        assertTrue(bt.equals(1));
        assertFalse(bt.equals(0));
        assertTrue(bt.equals(Integer.valueOf(-1)));
        assertFalse(bt.equals(Integer.valueOf(0)));
        assertTrue(bt.equals(new SensibleInteger(-1)));
        assertFalse(bt.equals(new SensibleInteger(0)));
        assertTrue(bt.equals(Boolean.TRUE));
        assertFalse(bt.equals(Boolean.FALSE));
        assertTrue(bt.equals(new SensibleBoolean(true)));
        assertFalse(bt.equals(new SensibleBoolean(false)));
        assertTrue(bt.equals(SensibleBoolean.BOOLEAN_TRUE_AS_STRING));
        assertFalse(bt.equals(SensibleBoolean.BOOLEAN_FALSE_AS_STRING));
        assertFalse(bt.equals("invalid"));

        assertFalse(bf.equals((Object) null));
        assertFalse(bf.equals((Boolean) null));
        assertFalse(bf.equals((Integer) null));
        assertFalse(bf.equals((SensibleInteger) null));
        assertFalse(bf.equals((String) null));
        assertFalse(bf.equals(true));
        assertTrue(bf.equals(false));
        assertFalse(bf.equals(-1));
        assertFalse(bf.equals(1));
        assertTrue(bf.equals(0));
        assertFalse(bf.equals(Integer.valueOf(-1)));
        assertTrue(bf.equals(Integer.valueOf(0)));
        assertFalse(bf.equals(new SensibleInteger(-1)));
        assertTrue(bf.equals(new SensibleInteger(0)));
        assertFalse(bf.equals(Boolean.TRUE));
        assertTrue(bf.equals(Boolean.FALSE));
        assertFalse(bf.equals(new SensibleBoolean(true)));
        assertTrue(bf.equals(new SensibleBoolean(false)));
        assertFalse(bf.equals(SensibleBoolean.BOOLEAN_TRUE_AS_STRING));
        assertTrue(bf.equals(SensibleBoolean.BOOLEAN_FALSE_AS_STRING));
        assertFalse(bf.equals("invalid"));
    }

    @Test
    public void testToString() {

        SensibleBoolean bt = new SensibleBoolean(true);
        SensibleBoolean bf = new SensibleBoolean(false);

        assertEquals("true", bt.toString());
        assertEquals("false", bf.toString());

        assertEquals("true", bt.toStringForSort());
        assertEquals("false", bf.toStringForSort());

        assertEquals("true", bt.toStringForSQL());
        assertEquals("false", bf.toStringForSQL());
    }
}
