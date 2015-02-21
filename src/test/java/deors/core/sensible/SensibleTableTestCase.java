package deors.core.sensible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class SensibleTableTestCase {

    @Test
    public void testConstructorDefault() {

        @SuppressWarnings("rawtypes")
        SensibleTable st = new SensibleTable();

        assertNull(st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(0, st.getTableData().size());
    }

    @Test
    public void testConstructorRecord() {

        AllTypesObject o = new AllTypesObject();
        @SuppressWarnings({"rawtypes", "unchecked"})
        SensibleTable st = new SensibleTable(o);

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(0, st.getTableData().size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetRecord() {

        AllTypesObject o = new AllTypesObject();
        @SuppressWarnings("rawtypes")
        SensibleTable st = new SensibleTable();
        st.setRecord(o);

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(0, st.getTableData().size());
    }

    @Test
    public void testSetInitialDataArray() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        AllTypesObject[] l = new AllTypesObject[] {o1, o2};
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
    }

    @Test
    public void testSetInitialDataList() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        @SuppressWarnings({"rawtypes", "unchecked"})
        SensibleTable st = new SensibleTable(o, l);

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
    }

    @Test
    public void testAddClone() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.addRecord(o3);

        assertEquals(3, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
        assertEquals(o3, st.getTableData().get(2));
        assertFalse(o3 == st.getTableData().get(2));
    }

    @Test
    public void testAddReference() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.addRecord(o3, SensibleTable.DONT_CLONE);

        assertEquals(3, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
        assertEquals(o3, st.getTableData().get(2));
        assertTrue(o3 == st.getTableData().get(2));
    }

    @Test
    public void testRemoveExisting() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.addRecord(o3, SensibleTable.DONT_CLONE);

        assertEquals(3, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
        assertEquals(o3, st.getTableData().get(2));
        assertTrue(o3 == st.getTableData().get(2));

        st.deleteRecord(o2);

        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o3, st.getTableData().get(1));
    }

    @Test
    public void testRemoveNotExisting() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.addRecord(o3, SensibleTable.DONT_CLONE);

        assertEquals(3, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
        assertEquals(o3, st.getTableData().get(2));
        assertTrue(o3 == st.getTableData().get(2));

        st.deleteRecord(new AllTypesObject());

        assertEquals(3, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
        assertEquals(o3, st.getTableData().get(2));
    }

    @Test
    public void testInsertClone() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.insertRecord(o3, 1);

        assertEquals(3, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o3, st.getTableData().get(1));
        assertEquals(o2, st.getTableData().get(2));
        assertFalse(o3 == st.getTableData().get(1));
    }

    @Test
    public void testUpdateClone() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.updateRecord(o2, o3);

        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o3, st.getTableData().get(1));
        assertFalse(o3 == st.getTableData().get(1));
    }

    @Test
    public void testUpdateReference() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.updateRecord(o2, o3, false);

        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o3, st.getTableData().get(1));
        assertTrue(o3 == st.getTableData().get(1));
    }

    @Test
    public void testUpdateCloneRow() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.updateRecord(1, o3);

        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o3, st.getTableData().get(1));
        assertFalse(o3 == st.getTableData().get(1));
    }

    @Test
    public void testUpdateNotExisting() {

        AllTypesObject o = new AllTypesObject();
        AllTypesObject o1 = new AllTypesObject(
            new SensibleString("s1"),
            new SensibleInteger(1),
            new SensibleLong(1L),
            new SensibleBoolean(true),
            new SensibleBigDecimal("1"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p1"));
        AllTypesObject o2 = new AllTypesObject(
            new SensibleString("s2"),
            new SensibleInteger(2),
            new SensibleLong(2L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("2"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p2"));
        List<AllTypesObject> l = new ArrayList<AllTypesObject>();
        l.add(o1);
        l.add(o2);
        SensibleTable<AllTypesObject> st = new SensibleTable<AllTypesObject>(o, l);
        AllTypesObject o3 = new AllTypesObject(
            new SensibleString("s3"),
            new SensibleInteger(3),
            new SensibleLong(3L),
            new SensibleBoolean(false),
            new SensibleBigDecimal("3"),
            new SensibleDate(Calendar.getInstance()),
            new SensibleTime(Calendar.getInstance()),
            new SensibleTime(false, Calendar.getInstance()),
            new SensibleDateTime(Calendar.getInstance()),
            new SensibleDateTime(false, Calendar.getInstance()),
            new SensibleString("p3"));

        assertEquals(o, st.getRecord());
        assertNotNull(st.getTableData());
        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));

        st.updateRecord(new AllTypesObject(), o3);

        assertEquals(2, st.getTableData().size());
        assertEquals(o1, st.getTableData().get(0));
        assertEquals(o2, st.getTableData().get(1));
    }

    public static class AllTypesObject
        extends SensibleObject {

        private static final long serialVersionUID = 1797945797595402760L;

        public AllTypesObject() {

            super();

            SensibleString sstring = new SensibleString(25);
            SensibleInteger sinteger = new SensibleInteger(0, 1000);
            SensibleLong slong = new SensibleLong(0, 1000000);
            SensibleBoolean sboolean = new SensibleBoolean();
            SensibleBigDecimal sbigdecimal = new SensibleBigDecimal(10, 2, true);
            SensibleDate sdate = new SensibleDate();
            SensibleTime stime1 = new SensibleTime(true);
            SensibleTime stime2 = new SensibleTime(false);
            SensibleDateTime sdatetime1 = new SensibleDateTime(true);
            SensibleDateTime sdatetime2 = new SensibleDateTime(false);
            SensibleString spassword = new SensibleString(25);

            sstring.setKey(true);
            sinteger.setKey(true);

            sstring.setRequired(true);
            sinteger.setRequired(true);
            slong.setRequired(true);
            sboolean.setRequired(true);
            sbigdecimal.setRequired(true);
            sdate.setRequired(true);
            stime1.setRequired(true);
            stime2.setRequired(true);
            sdatetime1.setRequired(true);
            sdatetime2.setRequired(true);
            spassword.setRequired(true);

            fields =
                new SensibleDataType[] {sstring, sinteger, slong, sboolean, sbigdecimal, sdate,
                    stime1, stime2, sdatetime1, sdatetime2, spassword};
            fieldNames =
                new String[] {"sstring", "sinteger", "slong", "sboolean", "sbigdecimal", "sdate",
                    "stime1", "stime2", "sfatetime1", "sdatetime2", "spassword"};

            // the object listens itself
            addListeners();
        }

        public AllTypesObject(SensibleString newSstring, SensibleInteger newSinteger,
                              SensibleLong newSlong, SensibleBoolean newSboolean,
                              SensibleBigDecimal newSbigdecimal, SensibleDate newSdate,
                              SensibleTime newStime1, SensibleTime newStime2,
                              SensibleDateTime newSdatetime1, SensibleDateTime newSdatetime2,
                              SensibleString newSpassword) {

            this();

            setField(0, newSstring);
            setField(1, newSinteger);
            setField(2, newSlong);
            setField(3, newSboolean);
            setField(4, newSbigdecimal);
            setField(5, newSdate);
            setField(6, newStime1);
            setField(7, newStime2);
            setField(8, newSdatetime1);
            setField(9, newSdatetime2);
            setField(10, newSpassword);
        }

        public SensibleBigDecimal getSbigdecimal() {
            return (SensibleBigDecimal) getField(4);
        }

        public SensibleBoolean getSboolean() {
            return (SensibleBoolean) getField(3);
        }

        public SensibleDate getSdate() {
            return (SensibleDate) getField(5);
        }

        public SensibleDateTime getSdatetime1() {
            return (SensibleDateTime) getField(8);
        }

        public SensibleDateTime getSdatetime2() {
            return (SensibleDateTime) getField(9);
        }

        public SensibleInteger getSinteger() {
            return (SensibleInteger) getField(1);
        }

        public SensibleLong getSlong() {
            return (SensibleLong) getField(2);
        }

        public SensibleString getSpassword() {
            return (SensibleString) getField(10);
        }

        public SensibleString getSstring() {
            return (SensibleString) getField(0);
        }

        public SensibleTime getStime1() {
            return (SensibleTime) getField(6);
        }

        public SensibleTime getStime2() {
            return (SensibleTime) getField(7);
        }

        public void setSbigdecimal(SensibleBigDecimal newSbigdecimal) {
            setField(4, newSbigdecimal);
        }

        public void setSboolean(SensibleBoolean newSboolean) {
            setField(3, newSboolean);
        }

        public void setSdate(SensibleDate newSdate) {
            setField(5, newSdate);
        }

        public void setSdatetime1(SensibleDateTime newSdatetime1) {
            setField(8, newSdatetime1);
        }

        public void setSdatetime2(SensibleDateTime newSdatetime2) {
            setField(9, newSdatetime2);
        }

        public void setSinteger(SensibleInteger newSinteger) {
            setField(1, newSinteger);
        }

        public void setSlong(SensibleLong newSlong) {
            setField(2, newSlong);
        }

        public void setSpassword(SensibleString newSpassword) {
            setField(10, newSpassword);
        }

        public void setSstring(SensibleString newSstring) {
            setField(0, newSstring);
        }

        public void setStime1(SensibleTime newStime1) {
            setField(6, newStime1);
        }

        public void setStime2(SensibleTime newStime2) {
            setField(7, newStime2);
        }
    }
}
