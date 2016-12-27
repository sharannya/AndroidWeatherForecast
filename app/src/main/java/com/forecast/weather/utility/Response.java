package com.forecast.weather.utility;

/**
 * Created by sharannya nair
 * Response status for data received from model
 */

public class Response {

    private String responseMsg = "";
    private int responseCode = 0;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
