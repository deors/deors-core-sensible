package deors.core.sensible;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import deors.core.sensible.ConnectorNot;

public class ConnectorNotTestCase {

    @Test
    public void testConstructor() {

        ConnectorNot not = new ConnectorNot();
        assertFalse(not.isIn());
        assertTrue(not.isOut());
    }

    @Test
    public void testEvents() {

        ConnectorNot not = new ConnectorNot();

        not.setIn(true);
        assertFalse(not.isOut());

        not.setIn(false);
        assertTrue(not.isOut());

        not.setIn(true);
        assertFalse(not.isOut());
    }
}
