package com.forecast.weather.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forecast.weather.R;
import com.forecast.weather.model.beans.DailySource;
import com.forecast.weather.utility.AppConstants;
import com.forecast.weather.utility.DateUtil;
import com.forecast.weather.utility.WeatherUtil;

import java.util.List;

/**
 * Created by sharannya nair.
 * <p/>
 * Provide views to RecyclerView with daily forecast details.
 */
public class WeatherDailyForecastAdapter extends RecyclerView.Adapter<WeatherDailyForecastAdapter.WeatherDailyViewHolder> {

    private List<DailySource> mDataModel;
    private Context context;
    private String timeZone;

    /**
     * Initialize the data of the Adapter.
     *
     * @param details model object containing the data to populate views to be used by RecyclerView.
     */
    public WeatherDailyForecastAdapter(Context context, List<DailySource> details, String timeZone) {
        mDataModel = details;
        this.context = context;
        this.timeZone = timeZone;
    }

    @Override
    public WeatherDailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_list_row, viewGroup, false);

        return new WeatherDailyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherDailyViewHolder viewHolder, int position) {
        WeatherUtil weatherUtil = new WeatherUtil();
        DateUtil dateUtil = new DateUtil();
        DailySource dailySourceData = mDataModel.get(position);
        if (dailySourceData != null) {
            //data binding can be used to bind data to view
            viewHolder.tvDesc.setText(dailySourceData.getSummary());
            viewHolder.tvSunTime.setText(setSunTime(dateUtil, dailySourceData));
            viewHolder.tvTemp.setText(setMinMaxTemperatures(weatherUtil, dailySourceData));
            viewHolder.tvDate.setText(dateUtil.formatDayTime(dailySourceData.getTime(), timeZone, AppConstants.DAYFORMAT));
            weatherUtil.setWeatherIcon((dailySourceData.getIcon()),viewHolder.ivWeatherIcon);
        }

    }

    @Override
    public int getItemCount() {
        if (mDataModel != null)
            return mDataModel.size();
        else
            return 0;
    }


    /*
      Method used to notify adapter for changes
     */
    public void notifyDataChanged(List<DailySource> dailySourceList) {
        this.mDataModel = dailySourceList;
        notifyDataSetChanged();
    }

    /**
     * Method used to set sunrise and sunset time
     * @param dateUtil object for date formatting
     * @param dailySourceData object for time params
     * @return time sunrise and sunset time format
     */
    private String setSunTime(DateUtil dateUtil, DailySource dailySourceData) {
        String time = "";
        Resources resource = context.getResources();
        String sunRiseTime = dateUtil.formatDayTime(dailySourceData.getSunriseTime(), timeZone, AppConstants.SUNTIME);
        String sunSetTime = dateUtil.formatDayTime(dailySourceData.getSunsetTime(), timeZone, AppConstants.SUNTIME);
        if(!TextUtils.isEmpty(sunRiseTime) && !TextUtils.isEmpty(sunSetTime))
        time = String.format(resource.getString(R.string.sunrise_sunset_format), sunRiseTime, sunSetTime);
        return time;
    }

    /**
     * Method used to set minimum and maximum temperatures
     * @param weatherUtil object for temp conversion
     * @param dailySourceData object for time params
     * @return temperature minimum and maximum temperature format
     */
    private String setMinMaxTemperatures(WeatherUtil weatherUtil, DailySource dailySourceData) {
        String temperature = "";
        Resources resource = context.getResources();
        String maxTemp = Long.toString(weatherUtil.convertFarheniteToCelsius(dailySourceData.getTemperatureMax()));
        String minTemp = Long.toString(weatherUtil.convertFarheniteToCelsius(dailySourceData.getTemperatureMin()));
        if (!TextUtils.isEmpty(maxTemp) && !TextUtils.isEmpty(minTemp))
            temperature = String.format(resource.getString(R.string.daily_temp_format), maxTemp, minTemp);

        return temperature;
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    static class WeatherDailyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTemp;
        private final TextView tvSunTime;
        private final TextView tvDesc;
        private final TextView tvDate;
        private final ImageView ivWeatherIcon;


        WeatherDailyViewHolder(View view) {
            super(view);

            tvTemp = (TextView) view.findViewById(R.id.tvTemp);
            tvSunTime = (TextView) view.findViewById(R.id.tvSunTime);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvDate = (TextView) view.findViewById(R.id.tvDateTime);
            ivWeatherIcon = (ImageView) view.findViewById(R.id.climateIcon);
        }
    }

}