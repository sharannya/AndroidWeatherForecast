package com.forecast.weather.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sharannya nair on 11/20/2016.
 */
public class WeatherUtilTest {

    private WeatherUtil weatherUtil;

    @Before
    public void setUp() throws Exception {
        weatherUtil = new WeatherUtil();
    }

    @After
    public void tearDown() throws Exception {
        weatherUtil = null;
    }

    @Test
    public void testConvertFarheniteToCelcius() throws Exception {
        long actual = weatherUtil.convertFarheniteToCelsius("86");
        long expected = 30;
        assertEquals("Farhenite to Celcius conversion is successful ", expected,
                actual, 0.001);
    }

    @Test
    public void testConvertHumidity() throws Exception {
        long actual = weatherUtil.convertHumidity("0.50");
        long expected = 50;
        assertEquals("Humidity conversion is successful ", expected,
                actual, 0.001);
    }

    @Test
    public void testConvertPressure() throws Exception {
        long actual = weatherUtil.convertPressure("1011.3");
        long expected = 1011;
        assertEquals("Pressure conversion is successful ", expected,
                actual, 0.001);
    }


}
