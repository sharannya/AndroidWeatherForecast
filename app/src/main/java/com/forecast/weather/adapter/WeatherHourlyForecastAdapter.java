package com.forecast.weather.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forecast.weather.R;
import com.forecast.weather.model.beans.HourlySource;
import com.forecast.weather.utility.AppConstants;
import com.forecast.weather.utility.DateUtil;
import com.forecast.weather.utility.WeatherUtil;

import java.util.List;

/**
 * Created by sharannya nair.
 * <p/>
 * Provide views to RecyclerView with hourly forecast of Weather.
 */
public class WeatherHourlyForecastAdapter extends RecyclerView.Adapter<WeatherHourlyForecastAdapter.ViewHolder> {

    private List<HourlySource> mDataModel;
    private Context context;
    private String timeZone;

    /**
     * Initialize the data of the Adapter.
     *
     * @param details model object containing the data to populate views to be used by RecyclerView.
     */
    public WeatherHourlyForecastAdapter(Context context, List<HourlySource> details, String timeZone) {
        mDataModel = details;
        this.context = context;
        this.timeZone = timeZone;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_horizontal, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        WeatherUtil weatherUtil = new WeatherUtil();
        DateUtil dateUtil = new DateUtil();
        HourlySource hourlySourceData = mDataModel.get(position);
        if (hourlySourceData != null) {
            //data binding  can be preferred to bind data to view
            String temperature = Long.toString(weatherUtil.convertFarheniteToCelsius(hourlySourceData.getTemperature()));
            if (!TextUtils.isEmpty(temperature))
                viewHolder.tvTemp.setText(String.format(context.getString(R.string.temp_format), temperature));
            viewHolder.tvTime.setText(dateUtil.formatDayTime(hourlySourceData.getTime(), timeZone, AppConstants.TIME));
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
    public void notifyDataChanged(List<HourlySource> model) {
        this.mDataModel = model;
        notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTemp;
        private final TextView tvTime;


        ViewHolder(View view) {
            super(view);

            tvTemp = (TextView) view.findViewById(R.id.tvHourlyTemp);
            tvTime = (TextView) view.findViewById(R.id.tvHourlyTime);
        }
    }

}