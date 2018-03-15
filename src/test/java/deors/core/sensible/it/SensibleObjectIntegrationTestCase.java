package deors.core.sensible.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SensibleObjectIntegrationTestCase {

    private FrameFixture testFrame;
    private AllTypesFrame frame;

    @BeforeClass
    public static void setUpOnce() {

        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {

        frame = GuiActionRunner.execute(new GuiQuery<AllTypesFrame>() {
            protected AllTypesFrame executeInEDT() {
                return new AllTypesFrame();
            }
        });
        testFrame = new FrameFixture(frame);
        testFrame.show();
    }

    @After
    public void tearDown () {

        testFrame.cleanUp();
    }

    @Test
    public void testWindowAndObject() {

        frame.setSize(410, 465);

        assertFalse(frame.getAllTypesObject1().isDataComplete());

        testFrame.button("JButton3").requireDisabled();
        testFrame.textBox("SensibleTextField1").enterText("test string");

        assertTrue(frame.getAllTypesObject1().getSstring().isValid());
        assertEquals("test string", frame.getAllTypesObject1().getSstring().getString());

        testFrame.textBox("SensibleTextField2").enterText("A");
        testFrame.textBox("SensibleTextField2").enterText("10000");

        assertFalse(frame.getAllTypesObject1().getSinteger().isValid());

        testFrame.textBox("SensibleTextField2").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);

        assertTrue(frame.getAllTypesObject1().getSinteger().isValid());

        testFrame.textBox("SensibleTextField3").enterText("A");
        testFrame.textBox("SensibleTextField3").enterText("10000000");

        assertFalse(frame.getAllTypesObject1().getSlong().isValid());

        testFrame.textBox("SensibleTextField3").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);

        assertTrue(frame.getAllTypesObject1().getSlong().isValid());

        testFrame.checkBox("SensibleCheckBox1").check();

        // small sleep to allow the click event to propagate
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        assertTrue(frame.getAllTypesObject1().getSboolean().booleanValue());

        testFrame.textBox("SensibleTextField5").enterText("A");
        testFrame.textBox("SensibleTextField5").enterText("12345,67");
        testFrame.textBox("SensibleTextField5").requireText("12.345,67");
        testFrame.textBox("SensibleTextField5").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);
        testFrame.textBox("SensibleTextField5").requireText("12.345,6");
        testFrame.textBox("SensibleTextField5").pressAndReleaseKeys(KeyEvent.VK_9);
        testFrame.textBox("SensibleTextField5").requireText("12.345,69");

        // converting chars because of fest bug with spanish kb layouts
        frame.getAllTypesObject1().getSdate().setDateSeparator('-');
        frame.getAllTypesObject1().getStime1().setTimeSeparator('.');
        frame.getAllTypesObject1().getStime2().setTimeSeparator('.');
        frame.getAllTypesObject1().getSdatetime1().setDateSeparator('-');
        frame.getAllTypesObject1().getSdatetime1().setTimeSeparator('.');
        frame.getAllTypesObject1().getSdatetime2().setDateSeparator('-');
        frame.getAllTypesObject1().getSdatetime2().setTimeSeparator('.');

        testFrame.textBox("SensibleTextField6").enterText("A");
        testFrame.textBox("SensibleTextField6").enterText("1-1-2011");
        testFrame.textBox("SensibleTextField6").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);
        testFrame.textBox("SensibleTextField6").pressAndReleaseKeys(KeyEvent.VK_0);
        testFrame.textBox("SensibleTextField6").requireText("1-1-2010");

        testFrame.textBox("SensibleTextField7").enterText("A");
        testFrame.textBox("SensibleTextField7").enterText("10.25.55");
        testFrame.textBox("SensibleTextField7").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);
        testFrame.textBox("SensibleTextField7").pressAndReleaseKeys(KeyEvent.VK_0);
        testFrame.textBox("SensibleTextField7").requireText("10.25.50");

        testFrame.textBox("SensibleTextField8").enterText("A");
        testFrame.textBox("SensibleTextField8").enterText("13.45");
        testFrame.textBox("SensibleTextField8").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);
        testFrame.textBox("SensibleTextField8").pressAndReleaseKeys(KeyEvent.VK_4);
        testFrame.textBox("SensibleTextField8").requireText("13.44");

        testFrame.textBox("SensibleTextField9").enterText("A");
        testFrame.textBox("SensibleTextField9").enterText("1-1-2010 10.25.55");
        testFrame.textBox("SensibleTextField9").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);
        testFrame.textBox("SensibleTextField9").pressAndReleaseKeys(KeyEvent.VK_0);
        testFrame.textBox("SensibleTextField9").requireText("1-1-2010 10.25.50");

        testFrame.textBox("SensibleTextField10").enterText("A");
        testFrame.textBox("SensibleTextField10").enterText("1-1-2010 13.45");
        testFrame.textBox("SensibleTextField10").pressAndReleaseKeys(KeyEvent.VK_BACK_SPACE);
        testFrame.textBox("SensibleTextField10").pressAndReleaseKeys(KeyEvent.VK_4);
        testFrame.textBox("SensibleTextField10").requireText("1-1-2010 13.44");

        testFrame.textBox("SensibleTextField11").enterText("password");

        assertTrue(frame.getAllTypesObject1().isDataComplete());
    }
}
