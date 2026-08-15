package deors.core.sensible;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
