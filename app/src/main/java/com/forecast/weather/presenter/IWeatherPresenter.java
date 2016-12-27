package com.forecast.weather.presenter;

import android.content.Context;
import android.location.Location;

import com.forecast.weather.model.beans.WeatherForecastModel;
import com.forecast.weather.utility.Response;

/*
Base Interface to PRESENTER to communicate with layers
*
*/
interface IWeatherPresenter {

    //used to fetch details
    void getDetails(Location location, Context context);

    //called when weather service call task is completed
    void onWeatherTaskCompleted(WeatherForecastModel result, Response resultCode);

}