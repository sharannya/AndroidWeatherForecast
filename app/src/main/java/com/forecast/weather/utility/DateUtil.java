package com.forecast.weather.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by sharannya nair
 * Class handles operation related to Date formats
 */

public class DateUtil {
    /**
     * Method is used to convert current unixSeconds to yyyy-MM-dd HH:mm:ss z format
     *
     * @param unixSec   time in current unixSeconds
     * @param timezone  time t converted according to timezone
     * @param caseValue convert value for DayFormat,DayTime Format and time format
     */
    public String formatDayTime(String unixSec, String timezone, String caseValue) {
        SimpleDateFormat simpleDateFormat = null;
        String formattedDate = "";
        try {

            long unixSeconds = Long.parseLong(unixSec);
            Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
            switch (caseValue) {
                case AppConstants.DAYFORMAT:
                    simpleDateFormat = new SimpleDateFormat("EEE ,dd MMM",Locale.US);
                    break;
                case AppConstants.DAYTIMEFORMAT:
                    simpleDateFormat = new SimpleDateFormat("EEE ,dd MMM hh:mm aa",Locale.US);
                    break;
                case AppConstants.SUNTIME:
                case AppConstants.TIME:
                    simpleDateFormat = new SimpleDateFormat("hh:mm aa",Locale.US);
                    break;
            }
            //Timezone is set as per response obtained by the server
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
            // give a timezone reference for formatting
            formattedDate = simpleDateFormat.format(date);
        } catch (NullPointerException e) {
            Logger.e(e.getMessage());
        }
        return formattedDate;

    }
}
