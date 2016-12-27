package com.forecast.weather.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.forecast.weather.R;
import com.forecast.weather.fragments.WeatherFragment;
import com.forecast.weather.utility.AppConstants;

import static com.forecast.weather.utility.AppConstants.REQUEST_LOCATION_SETTINGS;

/**
 * Class to represent WeatherForecast to users
 */
public class WeatherActivity extends AppCompatActivity {

    private WeatherFragment weatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherFragment = WeatherFragment.newInstance();
        if (savedInstanceState == null)
            //fragment added
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, weatherFragment, AppConstants.WEATHER_FORECAST)
                    .commit();
        else
            // find fragment by tag name
            getSupportFragmentManager().findFragmentByTag(AppConstants.WEATHER_FORECAST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        weatherFragment.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //callback from fragment for location enabling for the server
            case REQUEST_LOCATION_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(WeatherActivity.this, getString(R.string.location_service_denied), Toast.LENGTH_SHORT).show();
                        break;

                }

        }
    }
}


