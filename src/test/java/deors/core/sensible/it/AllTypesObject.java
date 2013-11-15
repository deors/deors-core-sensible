package deors.core.sensible.it;

import deors.core.sensible.SensibleBigDecimal;
import deors.core.sensible.SensibleBoolean;
import deors.core.sensible.SensibleDataType;
import deors.core.sensible.SensibleDate;
import deors.core.sensible.SensibleDateTime;
import deors.core.sensible.SensibleInteger;
import deors.core.sensible.SensibleLong;
import deors.core.sensible.SensibleObject;
import deors.core.sensible.SensibleString;
import deors.core.sensible.SensibleTime;

public class AllTypesObject
    extends SensibleObject {

    private static final long serialVersionUID = 5352088964855045594L;

    public AllTypesObject() {

        super();

        SensibleString sstring = new SensibleString(25);
        SensibleInteger sinteger = new SensibleInteger(0, 1000);
        SensibleLong slong = new SensibleLong(0, 1000000);
        SensibleBoolean sboolean = new SensibleBoolean();
        SensibleBigDecimal sbigdecimal = new SensibleBigDecimal(10, 2, true);
        SensibleDate sdate = new SensibleDate();
        SensibleTime stime1 = new SensibleTime(true);
        SensibleTime stime2 = new SensibleTime(false);
        SensibleDateTime sdatetime1 = new SensibleDateTime(true);
        SensibleDateTime sdatetime2 = new SensibleDateTime(false);
        SensibleString spassword = new SensibleString(25);

        sstring.setKey(true);
        sinteger.setKey(true);

        sstring.setRequired(true);
        sinteger.setRequired(true);
        slong.setRequired(true);
        sboolean.setRequired(true);
        sbigdecimal.setRequired(true);
        sdate.setRequired(true);
        stime1.setRequired(true);
        stime2.setRequired(true);
        sdatetime1.setRequired(true);
        sdatetime2.setRequired(true);
        spassword.setRequired(true);

        fields =
            new SensibleDataType[] {sstring, sinteger, slong, sboolean, sbigdecimal, sdate,
                stime1, stime2, sdatetime1, sdatetime2, spassword};
        fieldNames =
            new String[] {"sstring", "sinteger", "slong", "sboolean", "sbigdecimal", "sdate",
                "stime1", "stime2", "sfatetime1", "sdatetime2", "spassword"};

        // the object listens itself
        addListeners();
    }

    public AllTypesObject(SensibleString newSstring, SensibleInteger newSinteger,
                          SensibleLong newSlong, SensibleBoolean newSboolean,
                          SensibleBigDecimal newSbigdecimal, SensibleDate newSdate,
                          SensibleTime newStime1, SensibleTime newStime2,
                          SensibleDateTime newSdatetime1, SensibleDateTime newSdatetime2,
                          SensibleString newSpassword) {

        this();

        setField(0, newSstring);
        setField(1, newSinteger);
        setField(2, newSlong);
        setField(3, newSboolean);
        setField(4, newSbigdecimal);
        setField(5, newSdate);
        setField(6, newStime1);
        setField(7, newStime2);
        setField(8, newSdatetime1);
        setField(9, newSdatetime2);
        setField(10, newSpassword);
    }

    public SensibleBigDecimal getSbigdecimal() {
        return (SensibleBigDecimal) getField(4);
    }

    public SensibleBoolean getSboolean() {
        return (SensibleBoolean) getField(3);
    }

    public SensibleDate getSdate() {
        return (SensibleDate) getField(5);
    }

    public SensibleDateTime getSdatetime1() {
        return (SensibleDateTime) getField(8);
    }

    public SensibleDateTime getSdatetime2() {
        return (SensibleDateTime) getField(9);
    }

    public SensibleInteger getSinteger() {
        return (SensibleInteger) getField(1);
    }

    public SensibleLong getSlong() {
        return (SensibleLong) getField(2);
    }

    public SensibleString getSpassword() {
        return (SensibleString) getField(10);
    }

    public SensibleString getSstring() {
        return (SensibleString) getField(0);
    }

    public SensibleTime getStime1() {
        return (SensibleTime) getField(6);
    }

    public SensibleTime getStime2() {
        return (SensibleTime) getField(7);
    }

    public void setSbigdecimal(SensibleBigDecimal newSbigdecimal) {
        setField(4, newSbigdecimal);
    }

    public void setSboolean(SensibleBoolean newSboolean) {
        setField(3, newSboolean);
    }

    public void setSdate(SensibleDate newSdate) {
        setField(5, newSdate);
    }

    public void setSdatetime1(SensibleDateTime newSdatetime1) {
        setField(8, newSdatetime1);
    }

    public void setSdatetime2(SensibleDateTime newSdatetime2) {
        setField(9, newSdatetime2);
    }

    public void setSinteger(SensibleInteger newSinteger) {
        setField(1, newSinteger);
    }

    public void setSlong(SensibleLong newSlong) {
        setField(2, newSlong);
    }

    public void setSpassword(SensibleString newSpassword) {
        setField(10, newSpassword);
    }

    public void setSstring(SensibleString newSstring) {
        setField(0, newSstring);
    }

    public void setStime1(SensibleTime newStime1) {
        setField(6, newStime1);
    }

    public void setStime2(SensibleTime newStime2) {
        setField(7, newStime2);
    }
}
