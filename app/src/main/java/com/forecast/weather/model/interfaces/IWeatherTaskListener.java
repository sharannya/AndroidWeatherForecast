package com.forecast.weather.model.interfaces;

import com.forecast.weather.utility.Response;

/**
 * Created by sharannya nair
 * <p>
 * Callback interface to be used for WeatherScheduler Task
 */
public interface IWeatherTaskListener {
    void onTaskFinished(String result, Response response);
}
