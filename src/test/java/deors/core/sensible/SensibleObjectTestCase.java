package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;

import deors.core.commons.StringToolkit;
import deors.core.sensible.SensibleDataType;
import deors.core.sensible.SensibleInteger;
import deors.core.sensible.SensibleObject;
import deors.core.sensible.SensibleString;

public class SensibleObjectTestCase implements PropertyChangeListener {

    public SensibleObjectTestCase() {

        super();
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
    public void testReturnCopy() {

        NamePhoneObject o1 = new NamePhoneObject();
        o1.setField(0, new SensibleInteger(100));
        o1.setField(1, new SensibleString("name1"));
        o1.setField(2, new SensibleString("phone1"));
        o1.setField(3, new SensibleString("email1"));

        NamePhoneObject o2 = (NamePhoneObject) o1.returnCopy();
        assertNotNull("the object copy should not be null", o2);
        assertFalse(o1 == o2);
        assertEquals("phone1", o1.getPhone().getString());
        assertEquals("phone1", o2.getPhone().getString());
        assertEquals("email1", o1.getEmail().getString());
        assertEquals("email1", o2.getEmail().getString());

        o1.setField(2, new SensibleString("phone1new"));
        o1.setField(3, new SensibleString("email1new"));
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
        assertEquals("phone1", o1.getPhone().getString());
        assertEquals("", o2.getPhone().getString());
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
}
