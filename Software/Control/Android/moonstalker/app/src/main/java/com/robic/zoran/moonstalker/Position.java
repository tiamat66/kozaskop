package com.robic.zoran.moonstalker;

import android.util.Log;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by zoran on 7.3.2016.
 */
public class Position {
    GregorianCalendar calendar;

    private static final String TAG = "Position";

    //Equatorial coordinates
    double ra;
    double dec;

    //Telescope coordinates
    double azimuth; //[deg]
    double height;  //[deg]

    private GPSService gpsService;

    public Position(GPSService myGpsService) {

        gpsService = myGpsService;
        calendar = new GregorianCalendar(2000, Calendar.JANUARY, 1, 0, 0);
    }

    public void setDec(double dec) {
        this.dec = dec;
    }

    public void setRa(double ra) {
        this.ra = ra;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getHeight() {
        return height;
    }

    public void RaDec2AltAz() {

        Log.d(TAG, "RA=" + ra * 15.0);
        double RA = ra * 15.0;
        double DEC = dec;

        double LAT = gpsService.getLatitude();
        double daysFromY2k = getTime() / 1000.0 / 86400.0;

        double LST = 100.46 +
                0.985647 * daysFromY2k +
                gpsService.getLongitude() +
                15.0 * getUTC();

        Log.d(TAG, "DaysFromY2k = " + daysFromY2k);
        Log.d(TAG, "UTC =" + getUTC());
        Log.d(TAG, "LST=" + LST);
        long a = (long) LST / 360;
        LST = LST -
                a * 360;
        Log.d(TAG, "LST[deg]=" + LST);
        getUTC();

        double HA = LST - RA;
        if (HA < 0.0) HA += 360.0;
        Log.d(TAG, "HA=" + HA);

        HA = Math.toRadians(HA);
        DEC = Math.toRadians(DEC);
        LAT = Math.toRadians(LAT);

        double sinALT = Math.sin(DEC) * Math.sin(LAT) + Math.cos(DEC) * Math.cos(LAT) * Math.cos(HA);
        double ALT = Math.asin(sinALT);

        double b1 = Math.sin(DEC) - Math.sin(ALT) * Math.sin(LAT);
        double b2 = Math.cos(ALT) * Math.cos(LAT);

        double cosA = b1 / b2;
        double A = Math.acos(cosA);

        height =  Math.toDegrees(ALT);
        azimuth = Math.toDegrees(A);

        if (Math.sin(HA) < 0.0) azimuth = 360.0 - azimuth;
        azimuth = 360.0 - azimuth;

        Log.d(TAG, "sin(HA)="+ Math.sin(HA));

        Log.d(TAG, "Altitude=" + convertDec2Hour(height));
        Log.d(TAG, "Azimuth=" + convertDec2Hour(azimuth));


    }

    private long getCurrentTime() {
        return (System.currentTimeMillis());
    }

    private long getVernalEquinoxTime() {
        return (calendar.getTimeInMillis());
    }

    public long getTime() {
        return (getCurrentTime() - getVernalEquinoxTime());
    }

    public double getUTC() {

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        hour -= 2;
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        minute -= 4;
        int second = Calendar.getInstance().get(Calendar.SECOND);
        second -= 8;
        return hour +
                minute / 60.0 +
                second / 3600.0;
    }

    private String convertDec2Hour(double num)
    {

        long hours;
        long minutes;
        double seconds;
        double fPart;
        String hour;

        hours = (long) num;
        fPart = num - hours;
        fPart *= 60;
        minutes = (long) fPart;
        seconds = fPart - minutes;
        seconds *= 60;

        hour = String.format("%d %d\' %.2f\"", hours, minutes, seconds);
        return hour;
    }
}
