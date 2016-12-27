package com.forecast.weather.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.forecast.weather.R;

/**
 * Weather util class for weather objects.
 */
public class WeatherUtil {


    /**
     * To convert temperature from Farhenite to Celsius
     *
     * @param value temperature in string to be converted to celsius(long)
     */
    public long convertFarheniteToCelsius(String value) {
        long celcius;
        double x;
        x = Double.parseDouble(value);
        x = ((x - 32) * 5) / 9;
        celcius = Math.round(x);
        return celcius;
    }

    /**
     * To convert humidity in percentage
     *
     * @param value humidity in string to be converted to long
     */
    public long convertHumidity(String value) {
        long x = 0;
        double percentHumidity;
        try {
            percentHumidity = Double.parseDouble(value);
            percentHumidity = percentHumidity * 100;
            x = Math.round(percentHumidity);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return x;
    }

    /**
     * To round off atmospheric pressure
     *
     * @param value pressure in string to be converted to long
     */
    public long convertPressure(String value) {
        long pressure;
        double x;
        x = Double.parseDouble(value);
        pressure = Math.round(x);
        return pressure;
    }

    /**
     * Method used to set weather drawable according to weather description string
     * @param iconType weather description string
     * @param ivIcon ImageView resource to set drawable
     */
    public void setWeatherIcon(String iconType, ImageView ivIcon) {
        switch (iconType) {
            case AppConstants.RAIN:
            case AppConstants.HAIL:
            case AppConstants.THUNDERSTORM:
            case AppConstants.SNOW:
            case AppConstants.SLEET:
                ivIcon.setImageResource(R.drawable.rain);
                break;
            case AppConstants.CLOUDY:
            case AppConstants.CLOUDY_DAY:
            case AppConstants.WIND:
                ivIcon.setImageResource(R.drawable.cloud);
                break;

            case AppConstants.PARTLY_CLOUDY_DAY:
                ivIcon.setImageResource(R.drawable.partly_cloudy_day);
                break;
            case AppConstants.PARTLY_CLOUDY_NIGHT:
            case AppConstants.CLOUDY_NIGHT:
                ivIcon.setImageResource(R.drawable.partly_cloudy_night);
                break;

            case AppConstants.CLEAR_NIGHT:
                ivIcon.setImageResource(R.drawable.night);
                break;
            case AppConstants.CLEAR_DAY:
            default:
                ivIcon.setImageResource(R.drawable.sun);
                break;
        }
    }

    /**
     * Method checks permissions for marshmellow
     *
     * @param context Context object of the view
     * @return true if permission granted
     */
    public static boolean checkPermissions(Context context) {
        //permissions for marshmellow
        int hasFineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED
                && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED;

    }

}
