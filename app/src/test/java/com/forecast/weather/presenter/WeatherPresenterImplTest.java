package com.forecast.weather.presenter;

import com.forecast.weather.fragments.WeatherFragment;
import com.forecast.weather.model.beans.WeatherForecastModel;
import com.forecast.weather.utility.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterImplTest {
    @Mock
    private WeatherFragment view;
    @Mock
    private WeatherForecastModel forecastModel;
    private WeatherPresenterImpl weatherPresenterImpl;

    @Before
    public void setUp() throws Exception {
        weatherPresenterImpl = new WeatherPresenterImpl(view);
    }

    @After
    public void tearDown() throws Exception {
        weatherPresenterImpl = null;
    }

    @Test
    public void testOnWeatherTaskSuccess() throws Exception {
        Response response = new Response();
        response.setResponseCode(200);
        assertNotNull("Response for weather task success is null", response);
        weatherPresenterImpl.onWeatherTaskCompleted(forecastModel, response);
        verify(view).onResponseSuccess(forecastModel);
    }

    @Test
    public void testOnWeatherTaskFailure() throws Exception {
        Response response = new Response();
        response.setResponseCode(300);
        assertNotNull("Response for weather task failure is null", response);
        weatherPresenterImpl.onWeatherTaskCompleted(forecastModel, response);
        verify(view).onResponseFailure(response);
    }

}