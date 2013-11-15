package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;

import org.junit.Test;

import deors.core.commons.AbstractContext;
import deors.core.commons.CommonsContext;
import deors.core.sensible.SensibleContext;

public class SensibleContextTestCase {

    public SensibleContextTestCase() {

        super();
    }

    @Test
    public void testInit() {

        assertNotNull(SensibleContext.getContext());
    }

    @Test
    public void testNoBundle() throws NoSuchFieldException, IllegalAccessException {

        AbstractContext noBundle = new AbstractContext("nobundle", null) {
            @SuppressWarnings("unused")
            private static final long serialVersionUID = 2689951325683362420L;
        };

        Field bundleField = AbstractContext.class.getDeclaredField("bundle");
        bundleField.setAccessible(true);

        assertNull(bundleField.get(noBundle));
    }

    @Test
    public void testGetConfigurationProperty() {

        assertEquals(4096, SensibleContext.getConfigurationProperty("commons.defaultBufferSize", 1));

        assertEquals("yyyy/MM/dd", SensibleContext.getConfigurationProperty("commons.defaultDateFormat", ""));

        assertEquals(';', SensibleContext.getConfigurationProperty("inimgr.commentsStartsWith", '0'));

        assertEquals(";", CommonsContext.getConfigurationProperty("inimgr.commentsStartsWith", ";", new String[] {";", "#"}));
    }

    @Test
    public void testGetConfigurationPropertyDefaultValue() {

        assertEquals(200, SensibleContext.getConfigurationProperty("nokey", 200));

        assertEquals("xxx", SensibleContext.getConfigurationProperty("nokey", "xxx"));

        assertEquals(true, SensibleContext.getConfigurationProperty("nokey", true));

        assertEquals('0', SensibleContext.getConfigurationProperty("nokey", '0'));

        assertEquals(";", CommonsContext.getConfigurationProperty("nokey", ";", new String[] {";", "#"}));
    }

    @Test
    public void testGetConfigurationPropertyIntegerError() {

        assertEquals(1, SensibleContext.getConfigurationProperty("commons.defaultDateFormat", 1));
    }

    @Test
    public void testGetConfigurationPropertyOtherContext() {

        assertEquals("DMY", SensibleContext.getConfigurationProperty("format.defaultDateFormat", "", new String[] {"DMY", "YMD", "MDY"}));
    }

    @Test
    public void testGetMessage() {

        assertEquals("error reading base 64 encoded data",
            SensibleContext.getMessage("B64TK_ERR_READING_DATA"));

        assertEquals("file F not found",
            SensibleContext.getMessage("INIMGR_ERR_FILE_NOT_FOUND", "F"));
    }

    @Test
    public void testGetMessageDefaultValue() {

        assertEquals("nokey",
            SensibleContext.getMessage("nokey"));

        assertEquals("nokey",
            SensibleContext.getMessage("nokey", "F"));

        assertEquals("nokey",
            SensibleContext.getMessage("nokey", "A", "B"));

        assertEquals("nokey",
            SensibleContext.getMessage("nokey", new String[] {"P", "A", "B"}));

        assertEquals("nokey",
            SensibleContext.getMessage("nokey", new String[] {"{1}", "{0}"}, new String[] {"A", "B"}));
    }

    @Test
    public void testGetMessageBaseContext() {

        assertEquals("error reading base 64 encoded data",
            SensibleContext.getMessage("B64TK_ERR_READING_DATA"));

        assertEquals("file F not found",
            SensibleContext.getMessage("INIMGR_ERR_FILE_NOT_FOUND", "F"));
    }

    @Test
    public void testGetMessageOtherContext() {

        assertEquals("the parameters do not define a valid date/time",
            SensibleContext.getMessage("DTTM_ERR_INVALID_DTTM"));
    }
}
