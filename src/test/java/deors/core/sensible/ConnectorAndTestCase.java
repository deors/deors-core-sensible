package deors.core.sensible;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConnectorAndTestCase {

    @Test
    public void testConstructor() {

        ConnectorAnd and = new ConnectorAnd();
        assertFalse(and.isIn1());
        assertFalse(and.isIn2());
        assertFalse(and.isOut());
    }

    @Test
    public void testEvents() {

        ConnectorAnd and = new ConnectorAnd();

        and.setIn1(true);
        assertFalse(and.isOut());

        and.setIn2(true);
        assertTrue(and.isOut());

        and.setIn1(false);
        assertFalse(and.isOut());

        and.setIn2(false);
        assertFalse(and.isOut());

        and.setIn1(true);
        assertFalse(and.isOut());

        and.setIn2(false);
        assertFalse(and.isOut());

        and.setIn1(false);
        assertFalse(and.isOut());

        and.setIn2(true);
        assertFalse(and.isOut());

        and.setIn1(true);
        assertTrue(and.isOut());
    }
}
