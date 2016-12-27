package com.forecast.weather.utility;

import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;

import com.forecast.weather.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sharannya nair
 * Helper class for location services
 */

public class LocationHelper {
    private Context context;

    public LocationHelper(Context context) {
        this.context = context;
    }

    /**
     * Method is used to get corresponding address from location coordinates
     *
     * @param latitude  latitude value to get address
     * @param longitude longitude value to get address
     */
    public String getAddress(String latitude, String longitude) {
        String locationAddress = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            double lat = Double.parseDouble(latitude);
            double longitudeCoord = Double.parseDouble(longitude);
            List addresses = geocoder.getFromLocation(lat, longitudeCoord, 1);
            if (addresses.size() > 0) {
                Address address = (Address) addresses.get(0);

                String subLocality = address.getSubLocality();
                String locality = address.getLocality();
                String country = address.getCountryName();
                Resources resources = context.getResources();

                if (!TextUtils.isEmpty(subLocality) && !TextUtils.isEmpty(locality) && !TextUtils.isEmpty(country))
                    locationAddress = String.format(resources.getString(R.string.address_format), subLocality, locality, country);
                else if (TextUtils.isEmpty(subLocality) && !TextUtils.isEmpty(locality) && !TextUtils.isEmpty(country))
                    locationAddress = String.format(resources.getString(R.string.city_format), locality, country);
                else if (TextUtils.isEmpty(subLocality) && TextUtils.isEmpty(locality) && !TextUtils.isEmpty(country))
                    locationAddress = country;
            }
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        return locationAddress;
    }
}
