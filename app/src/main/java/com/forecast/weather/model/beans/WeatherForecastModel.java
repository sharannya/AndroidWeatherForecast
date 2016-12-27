
package com.forecast.weather.model.beans;

import android.content.Context;

import com.forecast.weather.api.FetchWeatherTask;
import com.forecast.weather.api.WeatherParser;
import com.forecast.weather.model.interfaces.IWeatherTaskListener;
import com.forecast.weather.presenter.WeatherPresenterImpl;
import com.forecast.weather.utility.AppConstants;
import com.forecast.weather.utility.Logger;
import com.forecast.weather.utility.Response;
import com.google.gson.JsonParseException;

/**
 * Parent Model class for holding all weather parameters
 */

public class WeatherForecastModel implements IWeatherTaskListener {


    private String latitude;
    private String longitude;
    private String timezone;
    private Currently currently;
    private Hourly hourly;
    private Daily daily;
    private WeatherPresenterImpl presenter;

    public WeatherForecastModel(WeatherPresenterImpl weatherPresenter) {
        presenter = weatherPresenter;
    }

    /**
     * @return The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * @return The currently
     */
    public Currently getCurrently() {
        return currently;
    }

    /**
     * @param currently The currently
     */
    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    /**
     * @return The hourly
     */
    public Hourly getHourly() {
        return hourly;
    }

    /**
     * @param hourly The hourly
     */
    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    /**
     * @return The daily
     */
    public Daily getDaily() {
        return daily;
    }

    /**
     * @param daily The daily
     */
    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    /**
     * Method for fetching WeatherForecast
     *
     * @param urlPath urlof the web service
     */
    public void getWeatherForecast(String urlPath, Context context) {
        new FetchWeatherTask(WeatherForecastModel.this, context).execute(urlPath);
    }

    /**
     * Method is called when weather api task is completed
     *
     * @param result WeatherForecastModel object received
     * @param resultCode Response
     */
    @Override
    public void onTaskFinished(String result, Response resultCode) {
        WeatherForecastModel model = null;
        if (result != null)
            model = storeWeatherForecast(result, resultCode);
        if (presenter != null) {
            presenter.onWeatherTaskCompleted(model, resultCode);
        }
    }

    /**
     * Method is used to store data in model object.According to further requirement,data can be stored in model,database or files.
     * @param result string object received from api calls
     * @param responseCode response object received from api
     * @return model WeatherForecast object
     */
    private WeatherForecastModel storeWeatherForecast(String result, Response responseCode) {
        WeatherForecastModel model = null;
        try {
            WeatherParser modelParser = new WeatherParser();
            model = modelParser.setWeatherJsonModel(result);
        } catch (JsonParseException e) {
            Logger.e(e.getMessage());
            responseCode.setResponseCode(AppConstants.ERROR);
            responseCode.setResponseMsg("There was a error parsing data.Please try again after some time.");
        }
        return model;
    }
}
