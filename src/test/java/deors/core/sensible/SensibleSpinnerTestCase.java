package deors.core.sensible;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;


public class SensibleSpinnerTestCase {

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

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new SensibleSpinner(new SensibleBoolean());

        });
        assertEquals("the spinner data is not valid; only SensibleBigDecimal, SensibleInteger, SensibleLong and SensibleString objects are allowed", ex.getMessage());
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

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleSpinner ss = new SensibleSpinner();

            ss.setEditor(null);

        });
        assertEquals("the spinner editor can not be set if data bean has not been set yet", ex.getMessage());
    }

    @Test
    public void testModelNoData() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleSpinner ss = new SensibleSpinner();

            ss.setModel(null);

        });
        assertEquals("the spinner model can not be set if data bean has not been set yet", ex.getMessage());
    }

    @Test
    public void testEditorAgain() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleSpinner ss = new SensibleSpinner(new SensibleString("5"));

            ss.setEditor(null);

        });
        assertEquals("the spinner editor can not be set once it is initialized", ex.getMessage());
    }

    @Test
    public void testModelAgain() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            SensibleSpinner ss = new SensibleSpinner(new SensibleString("5"));

            ss.setModel(null);

        });
        assertEquals("the spinner model can not be set once it is initialized", ex.getMessage());
    }

    @Test
    public void testChangeValue() {

        SensibleInteger si = new SensibleInteger(0, 10, 5);
        SensibleSpinner ss = new SensibleSpinner(si);

        si.setNumber(6);

        assertEquals("6", ss.getValue());
    }
}
