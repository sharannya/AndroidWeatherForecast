package com.forecast.weather.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.forecast.weather.R;
import com.forecast.weather.adapter.WeatherDailyForecastAdapter;
import com.forecast.weather.adapter.WeatherHourlyForecastAdapter;
import com.forecast.weather.api.HttpHelper;
import com.forecast.weather.model.beans.Currently;
import com.forecast.weather.model.beans.Daily;
import com.forecast.weather.model.beans.Hourly;
import com.forecast.weather.model.beans.WeatherForecastModel;
import com.forecast.weather.presenter.WeatherPresenterImpl;
import com.forecast.weather.utility.AppConstants;
import com.forecast.weather.utility.DateUtil;
import com.forecast.weather.utility.LocationHelper;
import com.forecast.weather.utility.Logger;
import com.forecast.weather.utility.Response;
import com.forecast.weather.utility.WeatherUtil;
import com.forecast.weather.view.IWeatherView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static com.forecast.weather.utility.AppConstants.REQUEST_LOCATION_SETTINGS;

/**
 * Created by sharannya nair
 * Main Fragment display weather forecast summary
 */
public class WeatherFragment extends Fragment implements IWeatherView, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    private WeatherDailyForecastAdapter recyclerViewWeatherAdapter = null;
    private WeatherHourlyForecastAdapter recyclerViewHourlyWeatherAdapter = null;
    private RecyclerView recyclerViewWeather = null;
    private RecyclerView recyclerViewHourlyWeather = null;

    private TextView tvMessage;
    private TextView tvLocation;
    private TextView tvHumidity;
    private TextView tvPressure;
    private TextView tvLocalTime;
    private TextView tvWeatherSummary, tvTemperature;
    private ImageView ivIcon;
    private WeatherPresenterImpl presenter;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WeatherPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, null);
        initViews(view);
        if (WeatherUtil.checkPermissions(getContext())) {
            initiateLocationUpdates();
        } else
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.PERMISSIONS_REQUEST_LOCATION);

        return view;
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    /**
     * Method is used to initialize views of the fragment
     * @param view view instance set to ui components
     */
    private void initViews(View view) {

        tvHumidity = (TextView) view.findViewById(R.id.tv_humidity);
        tvPressure = (TextView) view.findViewById(R.id.tv_pressure);
        tvLocalTime = (TextView) view.findViewById(R.id.tv_time);
        tvWeatherSummary = (TextView) view.findViewById(R.id.tv_currentDesc);
        tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        ivIcon = (ImageView) view.findViewById(R.id.icon);
        recyclerViewWeather = (RecyclerView) view.findViewById(R.id.recyclerViewWeather);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvMessage = (TextView) view.findViewById(R.id.tv_msg);
        recyclerViewHourlyWeather = (RecyclerView) view.findViewById(R.id.recyclerViewHourlyWeather);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHourlyWeather.setLayoutManager(horizontalLayoutManager);

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewWeather.setLayoutManager(verticalLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleApiClient != null && !googleApiClient.isConnected())
            googleApiClient.connect();
    }

    /**
     * Method used to initiate location
     */
    private void initiateLocationUpdates() {
        initLoader();
        initializeLocationRequest();
    }

    /**
     * Method used to initialize Location Api
     */
    private void initializeLocationRequest() {
        if (googleApiClient == null) {
            buildGoogleAPI();
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            locationRequest.setInterval(AppConstants.LOCATION_INTERVAL);
            locationRequest.setFastestInterval(AppConstants.LOCATION_MIN_INTERVAL);
        }
        if (googleApiClient != null && !googleApiClient.isConnected())
            googleApiClient.connect();

    }

    /**
     * Method creates LocationSettingsRequest object
     */
    private void createLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    /**
     * Method used to set data to view
     * @param result WeatherForecastModel object to set data to view
     */
    private void setDataToView(WeatherForecastModel result) {
        if (result != null) {
            //Formatter class to format dat received from server
            WeatherUtil weatherUtil = new WeatherUtil();
            DateUtil dateUtil = new DateUtil();
            LocationHelper locationHelper = new LocationHelper(getContext());
            Currently currentData = result.getCurrently();
            Daily dailyData = result.getDaily();
            Hourly hourlyData = result.getHourly();

            if (currentData != null && dailyData != null && hourlyData != null) {
                //data binding  can be preferred to bind data to view
                Resources resources = getResources();
                String humidity = String.valueOf(weatherUtil.convertHumidity(currentData.getHumidity()));
                String pressure = String.valueOf(weatherUtil.convertPressure(currentData.getPressure()));
                String temp = String.valueOf(weatherUtil.convertFarheniteToCelsius(currentData.getTemperature()));
                String time = dateUtil.formatDayTime(currentData.getTime(), result.getTimezone(), AppConstants.DAYTIMEFORMAT);

                if (!TextUtils.isEmpty(humidity))
                    tvHumidity.setText(String.format(resources.getString(R.string.humidity_format), humidity, resources.getString(R.string.percentage)));
                if (!TextUtils.isEmpty(pressure))
                    tvPressure.setText(String.format(resources.getString(R.string.pressure_format), pressure));
                if (!TextUtils.isEmpty(temp))
                    tvTemperature.setText(String.format(resources.getString(R.string.temp_format), temp));
                if (!TextUtils.isEmpty(time))
                    tvLocalTime.setText(time);

                tvWeatherSummary.setText(currentData.getSummary());
                tvLocation.setText(locationHelper.getAddress(result.getLatitude(), result.getLongitude()));
                tvLocation.setText(locationHelper.getAddress(result.getLatitude(), result.getLongitude()));
                weatherUtil.setWeatherIcon(currentData.getIcon(), ivIcon);

                if (recyclerViewWeatherAdapter == null) {
                    recyclerViewWeatherAdapter = new WeatherDailyForecastAdapter(getContext(), dailyData.getData(), result.getTimezone());
                    recyclerViewWeather.setAdapter(recyclerViewWeatherAdapter);
                } else {
                    recyclerViewWeatherAdapter.notifyDataChanged(dailyData.getData());
                }

                if (recyclerViewHourlyWeatherAdapter == null) {
                    recyclerViewHourlyWeatherAdapter = new WeatherHourlyForecastAdapter(getContext(), hourlyData.getData(), result.getTimezone());
                    recyclerViewHourlyWeather.setAdapter(recyclerViewHourlyWeatherAdapter);
                } else {
                    recyclerViewHourlyWeatherAdapter.notifyDataChanged(hourlyData.getData());
                }
            }
        }
    }

    @Override
    public void onShowToast(String msg, int duration) {
        Toast.makeText(getContext(), msg, duration).show();
    }

    @Override
    public void onShowDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .create();
        dialog.setTitle(getString(R.string.alert));
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResponseSuccess(WeatherForecastModel model) {
        recyclerViewWeather.setVisibility(View.VISIBLE);
        disableLoader();
        tvMessage.setVisibility(View.GONE);
        setDataToView(model);
    }

    @Override
    public void onResponseFailure(Response resultCode) {
        if (resultCode != null) {
            setFailureResponseView();
            onShowDialog(resultCode.getResponseMsg());
        }
    }

    private void setFailureResponseView() {
        recyclerViewWeather.setVisibility(View.GONE);
        disableLoader();
        tvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == AppConstants.PERMISSIONS_REQUEST_LOCATION) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiateLocationUpdates();
            } else {
                Response response = new Response();
                response.setResponseCode(AppConstants.PERMISSION_DENIED);
                response.setResponseMsg(getString(R.string.permission_denied));
                onResponseFailure(response);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationSettingsRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null && !googleApiClient.isConnected())
            googleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (HttpHelper.checkInternetConnection(getActivity()))
                presenter.getDetails(location, getActivity());
            else {
                tvMessage.setVisibility(View.VISIBLE);
                disableLoader();
                onShowDialog(getString(R.string.no_internet));
            }
            //remove listener after location change
            locationRequest = null;
            if (googleApiClient != null && googleApiClient.isConnected())
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        googleApiClient, this);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            disableLoader();
            onShowToast(getString(R.string.location_null), Toast.LENGTH_SHORT);
        }

    }

    /**
     * Method disables loader
     */
    private void disableLoader() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    /**
     * Starts location services
     */
    private void requestLocationUpdates() {
        try {
            if (googleApiClient != null && googleApiClient.isConnected() && locationRequest != null)
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                        locationRequest, this);
        } catch (SecurityException e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    /**
     * Method is used to create GoogleApiClient instance for location
     */
    private void buildGoogleAPI() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * Initiate loader
     */
    private void initLoader() {
        // progress bar is visible till the data loads
        recyclerViewWeather.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //check result code for location api
            case REQUEST_LOCATION_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        initLoader();
                        requestLocationUpdates();
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        setFailureResponseView();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                requestLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(
                            getActivity(),
                            REQUEST_LOCATION_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Logger.e(e.getMessage());
                }
                break;
        }
    }
}
