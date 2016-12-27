
package com.forecast.weather.model.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for saving daily weather object details
 */
public class Daily {

    private List<DailySource> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<DailySource> getData() {
        return data;
    }
    /**
     * @param data The data
     */
    public void setData(List<DailySource> data) {
        this.data = data;
    }


}
