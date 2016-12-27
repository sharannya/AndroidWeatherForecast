
package com.forecast.weather.model.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for saving hourly weather details
 */
public class Hourly {

    private List<HourlySource> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<HourlySource> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<HourlySource> data) {
        this.data = data;
    }


}
