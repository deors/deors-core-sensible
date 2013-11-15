package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import deors.core.sensible.SensibleBigDecimal;
import deors.core.sensible.SensibleBoolean;
import deors.core.sensible.SensibleInteger;
import deors.core.sensible.SensibleLong;
import deors.core.sensible.SensibleSpinner;
import deors.core.sensible.SensibleString;


public class SensibleSpinnerTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructorDefault() {

        SensibleSpinner ss = new SensibleSpinner();

        assertNull(ss.getData());
    }

    @Test
    public void testConstructorDataInteger() {

        SensibleInteger si = new SensibleInteger(0);
        SensibleSpinner ss = new SensibleSpinner(si);

        assertEquals(si, ss.getData());
    }

    @Test
    public void testConstructorDataLong() {

        SensibleLong sl = new SensibleLong(0L);
        SensibleSpinner ss = new SensibleSpinner(sl);

        assertEquals(sl, ss.getData());
    }

    @Test
    public void testConstructorDataBigDecimal() {

        SensibleBigDecimal sbd = new SensibleBigDecimal("0");
        SensibleSpinner ss = new SensibleSpinner(sbd);

        assertEquals(sbd, ss.getData());
    }

    @Test
    public void testConstructorDataString() {

        SensibleString sst = new SensibleString("0");
        SensibleSpinner ss = new SensibleSpinner(sst);

        assertEquals(sst, ss.getData());
    }

    @Test
    public void testConstructorDataInvalid() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("the spinner data is not valid; only SensibleBigDecimal, SensibleInteger, SensibleLong and SensibleString objects are allowed");

        new SensibleSpinner(new SensibleBoolean());
    }

    @Test
    public void testSpinUp() {

        SensibleInteger si = new SensibleInteger(0, 10, 5);
        SensibleSpinner ss = new SensibleSpinner(si);

        assertEquals("6", ss.getNextValue());
    }

    @Test
    public void testSpinDown() {

        SensibleInteger si = new SensibleInteger(0, 10, 5);
        SensibleSpinner ss = new SensibleSpinner(si);

        assertEquals("4", ss.getPreviousValue());
    }

    @Test
    public void testEditorNoData() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("the spinner editor can not be set if data bean has not been set yet");

        SensibleSpinner ss = new SensibleSpinner();

        ss.setEditor(null);
    }

    @Test
    public void testModelNoData() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("the spinner model can not be set if data bean has not been set yet");

        SensibleSpinner ss = new SensibleSpinner();

        ss.setModel(null);
    }

    @Test
    public void testEditorAgain() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("the spinner editor can not be set once it is initialized");

        SensibleSpinner ss = new SensibleSpinner(new SensibleString("5"));

        ss.setEditor(null);
    }

    @Test
    public void testModelAgain() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("the spinner model can not be set once it is initialized");

        SensibleSpinner ss = new SensibleSpinner(new SensibleString("5"));

        ss.setModel(null);
    }

    @Test
    public void testChangeValue() {

        SensibleInteger si = new SensibleInteger(0, 10, 5);
        SensibleSpinner ss = new SensibleSpinner(si);

        si.setNumber(6);

        assertEquals("6", ss.getValue());
    }
}
