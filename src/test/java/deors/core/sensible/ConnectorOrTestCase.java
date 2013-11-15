package deors.core.sensible;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import deors.core.sensible.ConnectorOr;

public class ConnectorOrTestCase {

    @Test
    public void testConstructor() {

        ConnectorOr or = new ConnectorOr();
        assertFalse(or.isIn1());
        assertFalse(or.isIn2());
        assertFalse(or.isOut());
    }

    @Test
    public void testEvents() {

        ConnectorOr or = new ConnectorOr();

        or.setIn1(true);
        assertTrue(or.isOut());

        or.setIn2(true);
        assertTrue(or.isOut());

        or.setIn1(false);
        assertTrue(or.isOut());

        or.setIn2(false);
        assertFalse(or.isOut());
    }
}
