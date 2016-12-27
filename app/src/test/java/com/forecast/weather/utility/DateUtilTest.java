package com.forecast.weather.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sharannya nair on 11/21/16.
 */
public class DateUtilTest {
    private DateUtil dateUtil;

    @Before
    public void setUp() throws Exception {
        dateUtil = new DateUtil();
    }

    @After
    public void tearDown() throws Exception {
        dateUtil = null;
    }

    @Test
    public void testDayFormat() throws Exception {
        String actual = dateUtil.formatDayTime("1479021757", "Asia/Kolkata", "Day");
        String expected = "Sun ,13 Nov";
        assertEquals("Day formatter is successful ", expected, actual);
    }

    @Test
    public void testDayTimeFormat() throws Exception {
        String actual = dateUtil.formatDayTime("1479021757", "Asia/Kolkata", "DayTime");
        String expected = "Sun ,13 Nov 12:52 PM";
        assertEquals("Time or Day formatter is successful ", expected, actual);
    }

    @Test
    public void testSunRiseTimeFormat() throws Exception {
        String actual = dateUtil.formatDayTime("1479021757", "Asia/Kolkata", "suntime");
        String expected = "12:52 PM";
        assertEquals("Sunrise or Sunset time formatter is successful ", expected, actual);
    }

}