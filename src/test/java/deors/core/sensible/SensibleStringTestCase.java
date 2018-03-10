package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

public class SensibleStringTestCase {

    @Test
    public void testDefaultConstructorAndSetters() {

        SensibleString ss = new SensibleString();

        assertEquals(0, ss.length());

        ss.setString("a");

        assertEquals("a", ss.toString());

        ss.setValue("b");

        assertEquals("b", ss.toString());

        SensibleString ss2 = new SensibleString("d");

        ss.setString(ss2);

        assertEquals(ss2, ss);
        assertFalse(ss == ss2);
    }

    @Test
    public void testConstructors() {

        SensibleString ss1 = new SensibleString(3);
        ss1.setString("abc");

        assertEquals("abc", ss1.stringValue());
        assertEquals(3, ss1.getMaxLength());

        SensibleString ss2 = new SensibleString(new SensibleString("abc"));

        assertEquals("abc", ss2.stringValue());

        SensibleString ss3 = new SensibleString(3, new SensibleString("abc"));

        assertEquals("abc", ss3.stringValue());
        assertEquals(3, ss3.getMaxLength());
    }

    @Test
    public void testAllowInsert() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString("abc");
                SensibleTextField stf = new SensibleTextField(ss);

                assertTrue(ss.allowInsert(0, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertTrue(ss.allowInsert(2, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

                assertFalse(ss.allowInsert(0, ""));
            }
        });
    }

    @Test
    public void testAllowInsertWithMaxLength() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString(5, "abc");
                SensibleTextField stf = new SensibleTextField(ss);

                assertTrue(ss.allowInsert(0, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertFalse(ss.allowInsert(2, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

                ss.setString("abc");

                assertTrue(ss.allowInsert(2, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

                ss.setString("abcde");

                assertFalse(ss.allowInsert(0, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertFalse(ss.allowInsert(2, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
            }
        });
    }

    @Test
    public void testAllowInsertWithAllowedCharacters() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString("abc");
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setAllowedCharacters("abcdefgh");

                assertEquals("abcdefgh", ss.getAllowedCharacters());
                assertTrue(ss.allowInsert(0, "de", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertTrue(ss.allowInsert(2, "gh", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertFalse(ss.allowInsert(0, "ablm", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
            }
        });
    }

    @Test
    public void testAllowInsertWithUpperCase() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString();
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setCasingMode(SensibleString.TO_UPPER_CASE);

                assertTrue(ss.allowInsert(0, "abc", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("ABC", ss.getString());
                assertTrue(ss.allowInsert(2, "gh", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("ABGHC", ss.getString());
                assertTrue(ss.allowInsert(0, "ablm", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("ABLMABGHC", ss.getString());
            }
        });
    }

    @Test
    public void testAllowInsertWithLowerCase() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString();
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setCasingMode(SensibleString.TO_LOWER_CASE);

                assertTrue(ss.allowInsert(0, "ABC", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("abc", ss.getString());
                assertTrue(ss.allowInsert(2, "GH", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("abghc", ss.getString());
                assertTrue(ss.allowInsert(0, "ABLM", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("ablmabghc", ss.getString());
            }
        });
    }

    @Test
    public void testAllowInsertWithCapitalization() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString();
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setCasingMode(SensibleString.CAPITALIZE);

                assertTrue(ss.allowInsert(0, "abc", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("Abc", ss.getString());
                assertTrue(ss.allowInsert(3, " gh", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("Abc Gh", ss.getString());
                assertTrue(ss.allowInsert(0, "ABLM ", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("Ablm Abc Gh", ss.getString());
            }
        });
    }

    @Test
    public void testAllowInsertWithPassword() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString();
                SensibleTextField stf = new SensibleTextField(ss);
                stf.setPasswordField(true);
                stf.setEchoCharacter('-');

                assertTrue(ss.allowInsert(0, "abc", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("---", stf.getText());
                assertEquals("abc", ss.getString());
                assertTrue(ss.allowInsert(2, "gh", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("-----", stf.getText());
                assertEquals("abghc", ss.getString());
                assertTrue(ss.allowInsert(0, "ablm", stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("---------", stf.getText());
                assertEquals("ablmabghc", ss.getString());
            }
        });
    }

    @Test
    public void testAllowRemove() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString("abcdefgh");
                SensibleTextField stf = new SensibleTextField(ss);

                assertTrue(ss.allowRemove(0, 2, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertTrue(ss.allowRemove(2, 4, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));

                assertFalse(ss.allowRemove(0,  0));
            }
        });
    }

    @Test
    public void testAllowRemoveWithUpperCase() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString("ABCDEFGH");
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setCasingMode(SensibleString.TO_UPPER_CASE);

                assertTrue(ss.allowRemove(0, 3, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("DEFGH", ss.getString());
            }
        });
    }

    @Test
    public void testAllowRemoveWithCapitalization() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString("Abc De Fgh");
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setCasingMode(SensibleString.CAPITALIZE);

                assertTrue(ss.allowRemove(4, 4, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("Abc Gh", ss.getString());
            }
        });
    }

    @Test
    public void testAllowRemoveWithLowerCase() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString("abcdefgh");
                SensibleTextField stf = new SensibleTextField(ss);
                ss.setCasingMode(SensibleString.TO_LOWER_CASE);

                assertTrue(ss.allowRemove(2, 3, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("abfgh", ss.getString());
            }
        });
    }

    @Test
    public void testAllowRemoveWithPassword() {

        GuiActionRunner.execute(new GuiTask() {
            protected void executeInEDT() throws Throwable {
                SensibleString ss = new SensibleString();
                SensibleTextField stf = new SensibleTextField(ss);
                stf.setPasswordField(true);
                stf.setEchoCharacter('-');

                ss.setString("abcdefgh");

                assertTrue(ss.allowRemove(0, 2, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("------", stf.getText());
                assertEquals("cdefgh", ss.getString());
                assertTrue(ss.allowRemove(2, 2, stf, (SensibleTextField.SensibleTextFieldDocument) stf.getDocument()));
                assertEquals("----", stf.getText());
                assertEquals("cdgh", ss.getString());
            }
        });
    }

    @Test
    public void testAdd() {

        SensibleString ss = new SensibleString("123");

        assertEquals(new SensibleString("125"), ss.add(2));

        ss = new SensibleString(3, "890");

        assertEquals(new SensibleString("990"), ss.add(100));
        assertEquals(new SensibleString("890"), ss.add(200));

        ss = new SensibleString("abc");

        assertEquals(new SensibleString("abc"), ss.add(1));
    }

    @Test
    public void testSubtract() {

        SensibleString ss = new SensibleString("123");

        assertEquals(new SensibleString("23"), ss.subtract(100));
        assertEquals(new SensibleString("123"), ss.subtract(200));

        ss = new SensibleString("abc");

        assertEquals(new SensibleString("abc"), ss.subtract(1));
    }

    @Test
    public void testSubstring() {

        SensibleString ss = new SensibleString("abcdefghijklm");

        assertEquals("abc", ss.substring(0, 3).stringValue());
        assertEquals("defghijklm", ss.substring(3).stringValue());
    }

    @Test
    public void testToStringForSort() {

        SensibleString ss = new SensibleString("abcdefghijklm");

        assertEquals("abcdefghijklm", ss.toStringForSort());
    }

    @Test
    public void testToStringForSQL() {

        SensibleString ss = new SensibleString("abcdefghijklm");

        assertEquals("'abcdefghijklm'", ss.toStringForSQL());
    }

    @Test
    public void testEqualsString() {

        SensibleString ss = new SensibleString("abcdefghijklm");

        assertTrue(ss.equals("abcdefghijklm"));
        assertFalse(ss.equals("abc"));
    }

    @Test
    public void testEqualsObject() {

        SensibleString ss = new SensibleString("abcdefghijklm");

        assertFalse(ss.equals(null));
        assertTrue(ss.equals(ss));
        assertFalse(ss.equals(Integer.parseInt("1")));
        assertTrue(ss.equals(new SensibleString("abcdefghijklm")));
    }

    @Test
    public void testEqualsIgnoreCase() {

        SensibleString ss = new SensibleString("abcdefghijklm");

        assertTrue(ss.equalsIgnoreCase("abcdefGHijklm"));
        assertTrue(ss.equalsIgnoreCase("abcdefghijklm"));
        assertTrue(ss.equalsIgnoreCase("ABCDEFGHIJKLM"));

        assertTrue(ss.equalsIgnoreCase(new SensibleString("abcdefGHijklm")));
        assertTrue(ss.equalsIgnoreCase(new SensibleString("abcdefghijklm")));
        assertTrue(ss.equalsIgnoreCase(new SensibleString("ABCDEFGHIJKLM")));
    }

    @Test
    public void testHashCode() {

        SensibleString ss1 = new SensibleString("abc");
        SensibleString ss2 = new SensibleString("abc");

        assertEquals(ss1.hashCode(), ss2.hashCode());
    }
}
