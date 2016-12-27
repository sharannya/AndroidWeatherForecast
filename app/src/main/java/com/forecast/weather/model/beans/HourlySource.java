
package com.forecast.weather.model.beans;

/**
 * Model class for saving hourly weather array object
 */
public class HourlySource {

    private String time;
    private String temperature;
    private String apparentTemperature;

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return The temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @param temperature The temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * @return The apparentTemperature
     */
    public String getApparentTemperature() {
        return apparentTemperature;
    }

    /**
     * @param apparentTemperature The apparentTemperature
     */
    public void setApparentTemperature(String apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }
}
