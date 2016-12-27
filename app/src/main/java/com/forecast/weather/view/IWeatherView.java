package com.forecast.weather.view;

import com.forecast.weather.model.beans.WeatherForecastModel;
import com.forecast.weather.utility.Response;

/**
 * Created by sharannya nair.
 * <p/>
 * Weather view interface
 */

public interface IWeatherView {

    /**
     * notify ui on response success
     *
     * @param model Model object for View layer
     */
    void onResponseSuccess(WeatherForecastModel model);


    /**
     * notify ui on response failure
     *
     * @param responseObject response passed to view
     */
    void onResponseFailure(Response responseObject);

    /**
     * Shows a Toast in the Activity with custom time
     *
     * @param msg      Message to show
     * @param duration Time Length
     *                 {@link android.widget.Toast#LENGTH_SHORT}
     *                 {@link android.widget.Toast#LENGTH_LONG}
     */

    void onShowToast(String msg, int duration);

    /**
     * Shows a dialog in the Activity
     */

    void onShowDialog(String msg);
}
