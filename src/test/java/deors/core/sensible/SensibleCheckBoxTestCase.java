package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SensibleCheckBoxTestCase {

    @Test
    public void testConstructorDefault() {

        SensibleCheckBox field = new SensibleCheckBox();

        assertTrue(field.isEnabled());
        assertFalse(field.isSelected());
        assertNull(field.getData());
    }

    @Test
    public void testConstructorData() {

        SensibleBoolean boo = new SensibleBoolean();
        SensibleCheckBox field = new SensibleCheckBox(boo);

        assertEquals(boo, field.getData());
    }

    @Test
    public void testSetData() {

        SensibleBoolean boo = new SensibleBoolean();
        SensibleCheckBox field = new SensibleCheckBox();
        field.setData(boo);

        assertEquals(boo, field.getData());
    }

    @Test
    public void testEvents() {

        SensibleBoolean boo = new SensibleBoolean();
        SensibleCheckBox field = new SensibleCheckBox(boo);

        assertTrue(field.isEnabled());
        assertFalse(field.isSelected());
        assertEquals(boo, field.getData());

        boo.setFlag(true);
        assertTrue(field.isSelected());

        boo.setFlag(false);
        assertFalse(field.isSelected());
    }

    @Test
    public void testReadOnly() {

        SensibleBoolean boo = new SensibleBoolean();
        SensibleCheckBox field = new SensibleCheckBox(boo);

        assertTrue(field.isEnabled());
        assertFalse(field.isSelected());
        assertEquals(boo, field.getData());

        boo.setReadOnly(true);
        assertFalse(field.isEnabled());
    }

    @Test
    public void testChangeData() {

        SensibleBoolean boo = new SensibleBoolean();
        SensibleCheckBox field = new SensibleCheckBox(boo);

        assertTrue(field.isEnabled());
        assertFalse(field.isSelected());
        assertEquals(boo, field.getData());

        SensibleBoolean boo2 = new SensibleBoolean();
        field.setData(boo2);

        assertEquals(boo2, field.getData());
    }

    @Test
    public void testClick() {

        SensibleBoolean boo = new SensibleBoolean();
        SensibleCheckBox field = new SensibleCheckBox(boo);

        assertTrue(field.isEnabled());
        assertFalse(field.isSelected());
        assertEquals(boo, field.getData());
        assertFalse(boo.isFlag());

        field.doClick();

        assertTrue(boo.isFlag());

        field.doClick();

        assertFalse(boo.isFlag());
    }
}
