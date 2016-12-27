package com.forecast.weather.presenter;

import android.content.Context;
import android.location.Location;

import com.forecast.weather.R;
import com.forecast.weather.model.beans.WeatherForecastModel;
import com.forecast.weather.utility.AppConstants;
import com.forecast.weather.utility.Response;
import com.forecast.weather.view.IWeatherView;

/**
 * Created by sharannya nair on 11/8/2016.
 * <p/>
 * Class is offered to VIEW,MODEL to communicate with PRESENTER
 */

public class WeatherPresenterImpl implements IWeatherPresenter {
    private IWeatherView view;
    private WeatherForecastModel model;

    public WeatherPresenterImpl(IWeatherView view) {
        this.view = view;
        this.model = new WeatherForecastModel(this);
    }

    @Override
    public void getDetails(Location location, Context context) {
        // current location coordinates is passed for fetching data
        model.getWeatherForecast(context.getResources().getString(R.string.url,
                AppConstants.API_KEY, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())), context);
    }

    @Override
    public void onWeatherTaskCompleted(WeatherForecastModel result, Response resultCode) {
        switch (resultCode.getResponseCode()) {
            case AppConstants.SUCCESS:
                if (view != null)
                    view.onResponseSuccess(result);
                break;
            case AppConstants.ERROR:
                if (view != null)
                    view.onResponseFailure(resultCode);
                break;
        }
    }
}
