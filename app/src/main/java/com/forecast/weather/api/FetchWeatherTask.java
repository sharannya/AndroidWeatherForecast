package com.forecast.weather.api;

import android.content.Context;
import android.os.AsyncTask;

import com.forecast.weather.R;
import com.forecast.weather.model.beans.WeatherForecastModel;
import com.forecast.weather.utility.AppConstants;
import com.forecast.weather.utility.Logger;
import com.forecast.weather.utility.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by sharannya nair.
 * FetchWeatherTask is used for getting weather details responseObject from the server. Web services can be consumed using Retrofit (http calls)for better performance
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String> {
    private WeatherForecastModel model = null;
    private Response responseObject = null;
    private Context context;

    /**
     * Initializing Task
     *
     * @param model object instance for communicating with model layer
     */
    public FetchWeatherTask(WeatherForecastModel model, Context context) {
        this.model = model;
        responseObject = new Response();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String httpResponse = null;
        String url = params[0];
        try {
            HttpURLConnection httpURLConnection = HttpHelper.openHttpConnection(url);
            httpResponse = HttpHelper.executeGet(httpURLConnection);
            responseObject.setResponseCode(AppConstants.SUCCESS);
            responseObject.setResponseMsg(context.getResources().getString(R.string.success_msg));
        } catch (IOException e) {
            Logger.e(e.getMessage());
            responseObject.setResponseCode(AppConstants.ERROR);
            responseObject.setResponseMsg(context.getResources().getString(R.string.error_retreiving_data));
        }
        return httpResponse;


    }

    @Override
    protected void onPostExecute(String response) {
        if (model != null) {
            model.onTaskFinished(response, this.responseObject);
        }
    }
}
