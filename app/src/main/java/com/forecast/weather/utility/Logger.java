package com.forecast.weather.utility;

import android.util.Log;

import com.forecast.weather.BuildConfig;

/**
 * Created by sharannya nair.
 * Wrapper class for logging. Logs are disabled on release versions
 */

public class Logger {
    private static final String TAG = "WEATHER_FORECAST";

    public static void d(String string) {
        if (BuildConfig.LOG) {
            Log.d(TAG, string);
        }
    }

    public static void v(String string) {
        if (BuildConfig.LOG) {
            Log.v(TAG, string);
        }
    }

    public static void i(String string) {
        if (BuildConfig.LOG) {
            Log.i(TAG, string);
        }
    }

    public static void e(String string) {
        if (BuildConfig.LOG) {
            Log.e(TAG, string);
        }
    }

    public static void w(String string) {
        if (BuildConfig.LOG) {
            Log.w(TAG, string);
        }
    }

}

