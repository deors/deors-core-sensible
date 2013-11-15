package deors.core.sensible.it;

import java.beans.PropertyChangeEvent;

import deors.core.commons.StringToolkit;
import deors.core.sensible.SensibleDataType;
import deors.core.sensible.SensibleInteger;
import deors.core.sensible.SensibleObject;
import deors.core.sensible.SensibleString;

public class NamePhoneObject
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

        changeField(0, newId);
        changeField(1, newName);
        changeField(2, newPhone);
        changeField(3, newEmail);
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
