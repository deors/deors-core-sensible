package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import deors.core.commons.io.IOToolkit;

public class SensibleComboBoxTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDefaultConstructor() {

        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>();

        assertNull(scb.getData());
    }

    @Test
    public void testConstructorFromData() {

        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(s);

        assertTrue(scb.getData() instanceof SensibleString);
    }

    @Test
    public void testConstructorFromMode() {

        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(SensibleComboBox.FULL_HISTORY);

        assertNull(scb.getData());
        assertEquals(SensibleComboBox.FULL_HISTORY, scb.getHistoryMode());
    }

    @Test
    public void testConstructorFromModeAndData() {

        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            SensibleComboBox.FULL_HISTORY, s);

        assertTrue(scb.getData() instanceof SensibleString);
        assertEquals(SensibleComboBox.FULL_HISTORY, scb.getHistoryMode());
    }

    @Test
    public void testConstructorFromDataAndFile() throws IOException {

        File tempHistory = IOToolkit.createTempFile(true);
        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            s, tempHistory.getAbsolutePath());

        assertTrue(scb.getData() instanceof SensibleString);
        assertEquals(tempHistory.getAbsolutePath(), scb.getHistoryFileName());
    }

    @Test
    public void testConstructorFromFileNull() {

        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(s, (String) null);

        assertTrue(scb.getData() instanceof SensibleString);
        assertNull(scb.getHistoryFileName());
    }

    @Test
    public void testConstructorFromFileTemp() throws IOException {

        File tempHistory = IOToolkit.createTempFile(true);
        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            s, tempHistory.getAbsolutePath());

        assertTrue(scb.getData() instanceof SensibleString);
        assertEquals(tempHistory.getAbsolutePath(), scb.getHistoryFileName());
    }

    @Test
    public void testConstructorFromFileNotExists() {

        File tempHistory = new File("notexists");
        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            s, tempHistory.getAbsolutePath());

        assertTrue(scb.getData() instanceof SensibleString);
        assertEquals(tempHistory.getAbsolutePath(), scb.getHistoryFileName());

        tempHistory.delete();
    }

    @Test
    public void testFullHistory() throws IOException, URISyntaxException {

        File history = new File(this.getClass().getResource("/history.ini").toURI());
        File tempHistory = IOToolkit.createTempFile(true);
        IOToolkit.copyStream(
            new FileInputStream(history),
            new FileOutputStream(tempHistory));
        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            SensibleComboBox.FULL_HISTORY, s,
            tempHistory.getAbsolutePath());

        scb.setSelectedIndex(1);

        assertEquals("entry 2", scb.getSelectedItem().toString());

        scb.setSelectedIndex(2);

        assertEquals("entry 3", scb.getSelectedItem().toString());

        scb.updateHistory();

        List<String> historyContents = IOToolkit.readTextFile(tempHistory);

        assertTrue(historyContents.contains("0=entry 1"));
        assertTrue(historyContents.contains("1=entry 2"));
        assertTrue(historyContents.contains("2=entry 3"));
        assertFalse(historyContents.contains("lastSelected=entry 3"));
    }

    @Test
    public void testRememberAndSelect() throws IOException, URISyntaxException {

        File history = new File(this.getClass().getResource("/history.ini").toURI());
        File tempHistory = IOToolkit.createTempFile(true);
        IOToolkit.copyStream(
            new FileInputStream(history),
            new FileOutputStream(tempHistory));
        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            SensibleComboBox.REMEMBER_AND_SELECT, s,
            tempHistory.getAbsolutePath());

        assertFalse(scb.isEditable());
        assertNull(scb.getSelectedItem());

        scb.setSelectedIndex(0);

        assertEquals("entry 1", scb.getSelectedItem().toString());

        scb.setSelectedIndex(2);

        assertEquals("entry 3", scb.getSelectedItem().toString());

        scb.updateHistory();

        List<String> historyContents = IOToolkit.readTextFile(tempHistory);

        assertTrue(historyContents.contains("0=entry 1"));
        assertTrue(historyContents.contains("1=entry 2"));
        assertTrue(historyContents.contains("2=entry 3"));
        assertTrue(historyContents.contains("lastSelected=entry 3"));
    }

    @Test
    public void testRememberAndSelectWithLast() throws IOException, URISyntaxException {

        File history = new File(this.getClass().getResource("/historyWithLast.ini").toURI());
        File tempHistory = IOToolkit.createTempFile(true);
        IOToolkit.copyStream(
            new FileInputStream(history),
            new FileOutputStream(tempHistory));
        SensibleString s = new SensibleString();
        SensibleComboBox<SensibleString> scb = new SensibleComboBox<>(
            SensibleComboBox.REMEMBER_AND_SELECT, s,
            tempHistory.getAbsolutePath());

        assertFalse(scb.isEditable());
        assertEquals("entry 2", scb.getSelectedItem().toString());

        scb.setSelectedIndex(0);

        assertEquals("entry 1", scb.getSelectedItem().toString());

        scb.setSelectedIndex(2);

        assertEquals("entry 3", scb.getSelectedItem().toString());

        scb.updateHistory();

        List<String> historyContents = IOToolkit.readTextFile(tempHistory);

        assertTrue(historyContents.contains("0=entry 1"));
        assertTrue(historyContents.contains("1=entry 2"));
        assertTrue(historyContents.contains("2=entry 3"));
        assertTrue(historyContents.contains("lastSelected=entry 3"));
    }
}
