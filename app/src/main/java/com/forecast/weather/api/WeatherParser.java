package com.forecast.weather.api;

import com.forecast.weather.model.beans.WeatherForecastModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Created by sharannya nair
 * Parser class for Weather object
 */
public class WeatherParser {

    /**
     * Converts response and stores in WeatherForecastModel object
     *
     * @param response responseString from the server to be parsed
     */
    public WeatherForecastModel setWeatherJsonModel(String response) throws JsonParseException {
        WeatherForecastModel model;

        Gson gson = new GsonBuilder().create();
        model = gson.fromJson(response, WeatherForecastModel.class);


        return model;


    }

}
