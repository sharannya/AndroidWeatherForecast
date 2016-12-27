
package com.forecast.weather.api;

import android.content.Context;
import android.net.ConnectivityManager;

import com.forecast.weather.utility.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http helper class
 */

public class HttpHelper {

    /**
     * Method to check internet connectivity
     *
     * @param context context of the activity
     * @return boolean true if connection alive
     * false if not connected
     */
    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
    }

    /**
     * Method to open http connections
     *
     * @param urlPath path for network calls
     * @return httpURLConnection object for network connectivity
     */
    protected static HttpURLConnection openHttpConnection(String urlPath) throws IOException {

        HttpURLConnection httpURLConnection ;
        URL url = new URL(urlPath);
        httpURLConnection = (HttpURLConnection) url.openConnection();

        return httpURLConnection;
    }

    /**
     * Method to execute GET method for network connection
     *
     * @param httpURLConnection object for network connectivity
     * @return response string object
     */
    protected static String executeGet(HttpURLConnection httpURLConnection){
        InputStream inputStream = null;
        String response = "";


        try {
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int resCode = httpURLConnection.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK)
                inputStream = httpURLConnection.getInputStream();

            response = convertStreamToString(inputStream);

        } catch (IOException e) {
            Logger.e(e.getMessage());
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return response;
    }

    /**
     * Method converts inputstream to string
     * @param inputStream inputstream received from server
     * @return response string object
     * @throws IOException
     */
    private static String convertStreamToString(InputStream inputStream) throws IOException {
        String inputStr;
        String responseString="";
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        responseString = responseStrBuilder.toString();

        return responseString;
    }
}

