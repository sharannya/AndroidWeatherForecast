
package com.forecast.weather;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;

import com.forecast.weather.activities.WeatherActivity;

/**
 * Created by sharannya nair on 8/25/2016.
 */

public class ActivityFunctionalTest extends
        ActivityInstrumentationTestCase2<WeatherActivity> {

    private static final String WEATHER_TEST = "Weather";

    public ActivityFunctionalTest() {
        super(WeatherActivity.class);
    }

    public void testSetText() throws Exception {

        WeatherActivity activity = getActivity();

        // search for the textView
        final TextView textViewLocation = (TextView) activity
                .findViewById(R.id.tv_location);
        final TextView textViewTime = (TextView) activity
                .findViewById(R.id.tv_time);
        final TextView textViewHumidity = (TextView) activity
                .findViewById(R.id.tv_humidity);
        final TextView textViewPressure = (TextView) activity
                .findViewById(R.id.tv_pressure);
        final TextView textViewCurrentDes = (TextView) activity
                .findViewById(R.id.tv_currentDesc);
        final TextView textViewTemp = (TextView) activity
                .findViewById(R.id.tv_temperature);
        // set text
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                textViewLocation.setText(WEATHER_TEST);
                textViewTime.setText(WEATHER_TEST);
                textViewHumidity.setText(WEATHER_TEST);
                textViewPressure.setText(WEATHER_TEST);
                textViewCurrentDes.setText(WEATHER_TEST);
                textViewTemp.setText(WEATHER_TEST);

            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals("Text incorrect", WEATHER_TEST, textViewLocation.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewTime.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewHumidity.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewPressure.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewCurrentDes.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewTemp.getText().toString());


    }

    @UiThreadTest
    public void testSetTextWithAnnotation() throws Exception {

        WeatherActivity activity = getActivity();

        // search for the textView
        final TextView textViewLocation = (TextView) activity
                .findViewById(R.id.tv_location);
        final TextView textViewTime = (TextView) activity
                .findViewById(R.id.tv_time);
        final TextView textViewHumidity = (TextView) activity
                .findViewById(R.id.tv_humidity);
        final TextView textViewPressure = (TextView) activity
                .findViewById(R.id.tv_pressure);
        final TextView textViewCurrentDes = (TextView) activity
                .findViewById(R.id.tv_currentDesc);
        final TextView textViewTemp = (TextView) activity
                .findViewById(R.id.tv_temperature);

        textViewLocation.setText(WEATHER_TEST);
        textViewTime.setText(WEATHER_TEST);
        textViewHumidity.setText(WEATHER_TEST);
        textViewPressure.setText(WEATHER_TEST);
        textViewCurrentDes.setText(WEATHER_TEST);
        textViewTemp.setText(WEATHER_TEST);

        assertEquals("Text incorrect", WEATHER_TEST, textViewLocation.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewTime.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewHumidity.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewPressure.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewCurrentDes.getText().toString());
        assertEquals("Text incorrect", WEATHER_TEST, textViewTemp.getText().toString());

    }

}
