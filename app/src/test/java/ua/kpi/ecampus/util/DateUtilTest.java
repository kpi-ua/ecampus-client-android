package ua.kpi.ecampus.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class DateUtilTest {

    @Test
    public void getCurrentDate() {
        String actual = DateUtil.getCurrentDate();
        String expected = (new SimpleDateFormat(DateUtil.FORMAT).format(new
                Date()));
        assertEquals(expected, actual);
    }

    @Test
    public void getCurrentDateWithFormat() {
        String format = "dd.MM.yyyy";
        String actual = DateUtil.getCurrentDate(format);
        String expected = (new SimpleDateFormat(format).format(new Date()));
        assertEquals(expected, actual);
    }

    @Test
    public void convert() {
        String date = "2016-05-19";
        try {
            Date actual = DateUtil.convert(date);
            assertNotNull(actual);
        } catch (Exception e) {
            fail();
        }
    }
}
