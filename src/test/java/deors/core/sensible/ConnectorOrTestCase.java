package deors.core.sensible;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

        or.setIn1(true);
        assertTrue(or.isOut());

        or.setIn1(false);
        assertFalse(or.isOut());

        or.setIn2(true);
        assertTrue(or.isOut());
    }
}
