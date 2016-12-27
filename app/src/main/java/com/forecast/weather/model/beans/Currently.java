
package com.forecast.weather.model.beans;

/**
 * Model class for saving current weather object details
 */

public class Currently {

    private String time;
    private String summary;
    private String icon;
    private String temperature;
    private String humidity;
    private String pressure;

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
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
     * @return The humidity
     */
    public String getHumidity() {
        return humidity;
    }

    /**
     * @param humidity The humidity
     */
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /**
     * @return The pressure
     */
    public String getPressure() {
        return pressure;
    }

    /**
     * @param pressure The pressure
     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

}
