package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import deors.core.commons.StringToolkit;

public class SensibleObjectTestCase implements PropertyChangeListener {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public SensibleObjectTestCase() {

        super();
    }

    @Test
    public void testChangeField() {

        NamePhoneObject o1 = new NamePhoneObject();
        assertEquals("////////", o1.toString());

        o1.changeField(0, new SensibleInteger(100));
        assertEquals("100////////", o1.toString());

        o1.changeField(1, new SensibleString("name1"));
        assertEquals("100//name1//////", o1.toString());

        o1.changeField(2, new SensibleString("phone1"));
        assertEquals("100//name1//phone1////", o1.toString());

        o1.changeField(3, new SensibleString("email1"));
        assertEquals("100//name1//phone1//email1//", o1.toString());
        assertFalse(o1.getEmail().isValid());
        assertFalse(o1.isDataComplete());

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));
        assertEquals("192//name2//phone2//email2//", o2.toString());

        o2.changeField(0, new SensibleInteger(160));
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.changeField(1, new SensibleString("otherName"));
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.changeField(2, new SensibleString("otherPhone"));
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.changeField(3, new SensibleString("otherEmail"));
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testChangeFieldByName() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField("id", new SensibleInteger(160));
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.changeField("name", new SensibleString("otherName"));
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.changeField("phone", new SensibleString("otherPhone"));
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.changeField("email", new SensibleString("otherEmail"));
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testChangeFieldFromString() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField(0, "160");
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.changeField(1, "otherName");
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.changeField(2, "otherPhone");
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.changeField(3, "otherEmail");
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testChangeFieldFromStringByName() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField("id", "160");
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.changeField("name", "otherName");
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.changeField("phone", "otherPhone");
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.changeField("email", "otherEmail");
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testChangeFieldInvalidType() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_INVALID_OBJECT"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField("id", new SensibleString("invalid"));
    }

    @Test
    public void testChangeFieldOutOfBounds() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "10"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField(10, new SensibleInteger(100));
    }

    @Test
    public void testChangeFieldOutOfBoundsFromString() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "10"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField(10, "160");
    }

    @Test
    public void testChangeFieldInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "invalid"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.changeField("invalid", "160");
    }

    @Test
    public void testSetField() {

        NamePhoneObject o1 = new NamePhoneObject();
        assertEquals("////////", o1.toString());

        o1.setField(0, new SensibleInteger(100));
        assertEquals("100////////", o1.toString());

        o1.setField(1, new SensibleString("name1"));
        assertEquals("100//name1//////", o1.toString());

        o1.setField(2, new SensibleString("phone1"));
        assertEquals("100//name1//phone1////", o1.toString());

        o1.setField(3, new SensibleString("email1"));
        assertEquals("100//name1//phone1//email1//", o1.toString());
        assertFalse(o1.getEmail().isValid());
        assertFalse(o1.isDataComplete());

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));
        assertEquals("192//name2//phone2//email2//", o2.toString());

        o2.setField(0, new SensibleInteger(160));
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.setField(1, new SensibleString("otherName"));
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.setField(2, new SensibleString("otherPhone"));
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.setField(3, new SensibleString("otherEmail"));
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testSetFieldByName() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField("id", new SensibleInteger(160));
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.setField("name", new SensibleString("otherName"));
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.setField("phone", new SensibleString("otherPhone"));
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.setField("email", new SensibleString("otherEmail"));
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testSetFieldFromString() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField(0, "160");
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.setField(1, "otherName");
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.setField(2, "otherPhone");
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.setField(3, "otherEmail");
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testSetFieldFromStringByName() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField("id", "160");
        assertEquals("160//name2//phone2//email2//", o2.toString());

        o2.setField("name", "otherName");
        assertEquals("160//otherName//phone2//email2//", o2.toString());

        o2.setField("phone", "otherPhone");
        assertEquals("160//otherName//otherPhone//email2//", o2.toString());

        o2.setField("email", "otherEmail");
        assertEquals("160//otherName//otherPhone//otherEmail//", o2.toString());
    }

    @Test
    public void testSetFieldInvalidType() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_INVALID_OBJECT"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField("id", new SensibleString("invalid"));
    }

    @Test
    public void testSetFieldOutOfBounds() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "10"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField(10, new SensibleInteger(100));
    }

    @Test
    public void testSetFieldOutOfBoundsFromString() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "10"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField(10, "160");
    }

    @Test
    public void testSetFieldInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "invalid"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.setField("invalid", "160");
    }

    @Test
    public void testGetField() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals(new SensibleInteger(192), o2.getField(0));
        assertEquals(new SensibleString("name2"), o2.getField(1));
        assertEquals(new SensibleString("phone2"), o2.getField(2));
        assertEquals(new SensibleString("email2"), o2.getField(3));
    }

    @Test
    public void testGetFieldByName() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals(new SensibleInteger(192), o2.getField("id"));
        assertEquals(new SensibleString("name2"), o2.getField("name"));
        assertEquals(new SensibleString("phone2"), o2.getField("phone"));
        assertEquals(new SensibleString("email2"), o2.getField("email"));
    }

    @Test
    public void testGetFieldOutOfBounds() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "10"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.getField(10);
    }

    @Test
    public void testGetFieldInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "invalid"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        o2.getField("invalid");
    }

    @Test
    public void testGetFields() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals(4, o2.getFields().length);
        assertEquals(new SensibleInteger(192), o2.getFields()[0]);
        assertEquals(new SensibleString("name2"), o2.getFields()[1]);
        assertEquals(new SensibleString("phone2"), o2.getFields()[2]);
        assertEquals(new SensibleString("email2"), o2.getFields()[3]);
    }

    @Test
    public void testGetFieldCount() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals(4, o2.getFieldCount());
    }

    @Test
    public void testGetFieldName() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals("id", o2.getFieldName(0));
        assertEquals("name", o2.getFieldName(1));
        assertEquals("phone", o2.getFieldName(2));
        assertEquals("email", o2.getFieldName(3));
    }

    @Test
    public void testGetFieldNameOutOfBounds() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_FIELD_NOT_DEFINED", "10"));

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals("notexists", o2.getFieldName(10));
    }

    @Test
    public void testGetFieldNames() {

        NamePhoneObject o2 = new NamePhoneObject(
            new SensibleInteger(192), new SensibleString("name2"),
            new SensibleString("phone2"), new SensibleString("email2"));

        assertEquals(4, o2.getFieldNames().length);
        assertEquals("id", o2.getFieldNames()[0]);
        assertEquals("name", o2.getFieldNames()[1]);
        assertEquals("phone", o2.getFieldNames()[2]);
        assertEquals("email", o2.getFieldNames()[3]);
    }

    @Test
    public void testDataComplete() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        assertFalse(o1.isDataComplete());

        o1.setField(3, new SensibleString("email1"));
        assertFalse(o1.isDataComplete());

        o1.setField(3, new SensibleString("email@valido.com"));
        assertTrue(o1.isDataComplete());
    }

    @Test
    public void testClear() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        o1.setField(3, new SensibleString("email1"));

        assertFalse(o1.isClear());

        o1.clear();

        assertTrue(o1.isClear());
        assertEquals(new SensibleInteger(), o1.getField(0));
        assertEquals(new SensibleString(), o1.getField(1));
        assertEquals(new SensibleString(), o1.getField(2));
        assertEquals(new SensibleString(), o1.getField(3));
    }

    @Test
    public void testClearKey() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        o1.setField(3, new SensibleString("email1"));

        assertFalse(o1.isClearKey());

        o1.clearKey();

        assertTrue(o1.isClearKey());

        assertEquals(new SensibleInteger(), o1.getField("id"));
        assertEquals(new SensibleString("name1"), o1.getField("name"));
        assertEquals(new SensibleString("phone1"), o1.getField("phone"));
        assertEquals(new SensibleString("email1"), o1.getField("email"));
    }

    @Test
    public void testClearNoKey() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        o1.setField(3, new SensibleString("email1"));

        assertFalse(o1.isClearNoKey());

        o1.clearNoKey();

        assertTrue(o1.isClearNoKey());

        assertEquals(new SensibleInteger(100), o1.getField("id"));
        assertEquals(new SensibleString(), o1.getField("name"));
        assertEquals(new SensibleString(), o1.getField("phone"));
        assertEquals(new SensibleString(), o1.getField("email"));
    }

    @Test
    public void testReturnCopy() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        o1.setField(3, new SensibleString("email1"));
        NamePhoneObject o2 = (NamePhoneObject) o1.returnCopy();

        assertNotNull("the object copy should not be null", o2);
        assertFalse(o1 == o2);
        assertEquals(100, o1.getId().integerValue());
        assertEquals(100, o2.getId().integerValue());
        assertEquals("name1", o1.getName().stringValue());
        assertEquals("name1", o2.getName().stringValue());
        assertEquals("phone1", o1.getPhone().stringValue());
        assertEquals("phone1", o2.getPhone().stringValue());
        assertEquals("email1", o1.getEmail().stringValue());
        assertEquals("email1", o2.getEmail().stringValue());

        o1.setField(2, new SensibleString("phone1new"));
        o1.setField(3, new SensibleString("email1new"));

        assertEquals(100, o1.getId().integerValue());
        assertEquals(100, o2.getId().integerValue());
        assertEquals("name1", o1.getName().stringValue());
        assertEquals("name1", o2.getName().stringValue());
        assertEquals("phone1new", o1.getPhone().getString());
        assertEquals("phone1", o2.getPhone().getString());
        assertEquals("email1new", o1.getEmail().getString());
        assertEquals("email1", o2.getEmail().getString());
    }

    @Test
    public void testReturnNew() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        o1.setField(3, new SensibleString("email1"));

        NamePhoneObject o2 = (NamePhoneObject) o1.returnNew();
        assertNotNull("the object new should not be null", o2);
        assertFalse(o1 == o2);
        assertTrue(o2.isClear());
        assertEquals(100, o1.getId().integerValue());
        assertEquals(0, o2.getId().integerValue());
        assertEquals("name1", o1.getName().stringValue());
        assertEquals("", o2.getName().stringValue());
        assertEquals("phone1", o1.getPhone().stringValue());
        assertEquals("", o2.getPhone().stringValue());
        assertEquals("email1", o1.getEmail().stringValue());
        assertEquals("", o2.getEmail().stringValue());
    }

    @Test
    public void testEquals() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        NamePhoneObject o2 = new NamePhoneObject();
        o2.setId(new SensibleInteger(100));
        o2.setName(new SensibleString("name1"));
        o2.setPhone(new SensibleString("phone1"));
        o2.setEmail(new SensibleString("email1"));

        NamePhoneObject o3 = new NamePhoneObject();
        o3.setId(new SensibleInteger(101));
        o3.setName(new SensibleString("name1"));
        o3.setPhone(new SensibleString("phone1"));
        o3.setEmail(new SensibleString("email1"));

        IdNameObject o4 = new IdNameObject();

        assertFalse(o1.equals(false));
        assertFalse(o1.equals(""));
        assertFalse(o1.equals(o4));
        assertTrue(o1.equals(o1));
        assertTrue(o1.equals(o2));
        assertFalse(o1.equals(o3));
    }

    @Test
    public void testHashCode() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        NamePhoneObject o2 = new NamePhoneObject();
        o2.setId(new SensibleInteger(100));
        o2.setName(new SensibleString("name1"));
        o2.setPhone(new SensibleString("phone1"));
        o2.setEmail(new SensibleString("email1"));

        assertEquals(o1.hashCode(), o2.hashCode());
    }

    @Test
    public void testToStringForSort() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        assertEquals("        100//", o1.toStringForSort());
    }

    @Test
    public void testCompareTo() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        NamePhoneObject o2 = new NamePhoneObject();
        o2.setId(new SensibleInteger(101));
        o2.setName(new SensibleString("name1"));
        o2.setPhone(new SensibleString("phone1"));
        o2.setEmail(new SensibleString("email1"));

        NamePhoneObject o3 = new NamePhoneObject();
        o3.setId(new SensibleInteger(100));
        o3.setName(new SensibleString("name1"));
        o3.setPhone(new SensibleString("phone1"));
        o3.setEmail(new SensibleString("email1"));

        assertTrue(o1.compareTo(o2) < 0);
        assertTrue(o1.compareTo(o3) == 0);
        assertTrue(o2.compareTo(o1) > 0);
    }

    @Test
    public void testChangeValueObject() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        NamePhoneObject o2 = new NamePhoneObject();
        o2.setId(new SensibleInteger(101));
        o2.setName(new SensibleString("name1"));
        o2.setPhone(new SensibleString("phone1"));
        o2.setEmail(new SensibleString("email1"));

        o1.changeValue(o2);

        assertEquals(101, o2.getId().integerValue());
        assertEquals("name1", o2.getName().stringValue());
        assertEquals("phone1", o2.getPhone().stringValue());
        assertEquals("email1", o2.getEmail().stringValue());
    }

    @Test
    public void testChangeValueObjectInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_INVALID_OBJECT"));

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        IdNameObject o2 = new IdNameObject();

        o1.changeValue(o2);
    }

    @Test
    public void testSetValueObject() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        NamePhoneObject o2 = new NamePhoneObject();
        o2.setId(new SensibleInteger(101));
        o2.setName(new SensibleString("name1"));
        o2.setPhone(new SensibleString("phone1"));
        o2.setEmail(new SensibleString("email1"));

        o1.setValue(o2);

        assertEquals(101, o2.getId().integerValue());
        assertEquals("name1", o2.getName().stringValue());
        assertEquals("phone1", o2.getPhone().stringValue());
        assertEquals("email1", o2.getEmail().stringValue());
    }

    @Test
    public void testSetValueObjectInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(SensibleContext.getMessage("DTYPE_ERR_INVALID_OBJECT"));

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        IdNameObject o2 = new IdNameObject();

        o1.setValue(o2);
    }

    @Test
    public void testListeners() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setId(new SensibleInteger(100));
        o1.setName(new SensibleString("name1"));
        o1.setPhone(new SensibleString("phone1"));
        o1.setEmail(new SensibleString("email1"));

        assertTrue(o1.hasListeners("email"));

        o1.addPropertyChangeListener("email", o1);

        assertTrue(o1.hasListeners("email"));

        o1.removeListeners();

        assertTrue(o1.hasListeners("email"));

        o1.removePropertyChangeListener("email", o1);

        assertTrue(o1.hasListeners("email"));

        o1.removePropertyChangeListener(o1);

        o1.addPropertyChangeListener(o1);
    }

    public void propertyChange(java.beans.PropertyChangeEvent event) {

        System.out.println("a property value has been changed");
        System.out.println("  source class: " + event.getSource().getClass().getName());
        System.out.println("  property name: " + event.getPropertyName());
        System.out.println("  old value: " + event.getOldValue());
        System.out.println("  new value: " + event.getNewValue());
    }

    public static class NamePhoneObject
        extends SensibleObject {

        private static final long serialVersionUID = -6201352567121018212L;

        public NamePhoneObject() {

            super();

            SensibleInteger id = new SensibleInteger(0, 10000);
            SensibleString name = new SensibleString(100);
            SensibleString phone = new SensibleString(100);
            SensibleString email = new SensibleString(100);

            id.setKey(true);

            id.setRequired(true);
            name.setRequired(true);
            phone.setRequired(true);
            email.setRequired(true);

            fields = new SensibleDataType[] {id, name, phone, email};
            fieldNames = new String[] {"id", "name", "phone", "email"};

            // the object listens itself
            addListeners();
        }

        public NamePhoneObject(SensibleInteger newId, SensibleString newName,
                               SensibleString newPhone, SensibleString newEmail) {

            this();

            setField(0, newId);
            setField(1, newName);
            setField(2, newPhone);
            setField(3, newEmail);
        }

        public SensibleString getEmail() {

            return (SensibleString) getField(3);
        }

        public SensibleInteger getId() {

            return (SensibleInteger) getField(0);
        }

        public SensibleString getName() {

            return (SensibleString) getField(1);
        }

        public SensibleString getPhone() {

            return (SensibleString) getField(2);
        }

        public void setEmail(SensibleString newEmail) {

            setField(3, newEmail);
        }

        public void setId(SensibleInteger newId) {

            setField(0, newId);
        }

        public void setName(SensibleString newName) {

            setField(1, newName);
        }

        public void setPhone(SensibleString newPhone) {

            setField(2, newPhone);
        }

        public void propertyChange(PropertyChangeEvent event) {

            // the e-mail is checked
            getField(3).setValid(checkEMail(getField(3).toString()));
            super.propertyChange(event);
        }

        public boolean checkEMail(String eMail) {

            if (eMail.length() == 0
                || StringToolkit.hasInvalidCharacters(eMail, "'\\/,;:*?\"<>()[]{}| ")) {
                return false;
            }

            int pa = eMail.indexOf('@');
            int ua = eMail.lastIndexOf('@');
            int dot = eMail.lastIndexOf('.');
            String dom = (dot == -1) ? "" : eMail.substring(dot + 1);

            boolean validEMail = true;

            if (pa != ua || pa < 1 || dot < pa || dot == pa + 1 || dom.trim().length() == 0) {
                validEMail = false;
            }

            return validEMail;
        }
    }

    public static class IdNameObject
        extends SensibleObject {

        private static final long serialVersionUID = -2508032219705182284L;

        public IdNameObject() {

            super();

            SensibleInteger id = new SensibleInteger(0, 10000);
            SensibleString name = new SensibleString(100);

            id.setKey(true);

            id.setRequired(true);
            name.setRequired(true);

            fields = new SensibleDataType[] {id, name};
            fieldNames = new String[] {"id", "name"};

            // the object listens itself
            addListeners();
        }
    }
}
