package me.aungkooo.geologist;


import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;

import java.util.Calendar;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utility
{
    private Utility() {
    }

    public static double sec(double angle)
    {
        return 1 / Math.cos(angle);
    }

    public static int getYear()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDate()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return String.valueOf(day + "/" + (month + 1) + "/" + year);
    }

    public static int getHour()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static String getTime()
    {
        String time;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour > 12)
        {
            hour -= 12;

            if(minute < 10 && minute >= 0) {
                time = String.valueOf(hour + ":0" + minute + " PM");
            }
            else {
                time = String.valueOf(hour + ":" + minute + " PM");
            }
        }
        else if(hour == 0)
        {
            hour += 12;

            if(minute < 10 && minute >= 0) {
                time = String.valueOf(hour + ":0" + minute + " AM");
            }
            else {
                time = String.valueOf(hour + ":" + minute + " AM");
            }
        }
        else {
            if(minute < 10 && minute >= 0) {
                time = String.valueOf(hour + ":0" + minute + " AM");
            }
            else {
                time = String.valueOf(hour + ":" + minute + " AM");
            }
        }

        return time;
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static float convertToDegree(float value) {
        return (180.0f * value) / 3.14159265358979323846f;
    }

    public static float normalizeAxis(float value, float range)
    {
        if(value > range)
        {
            value = range - (value - range);
        }

        return value;
    }

    public static float normalizeDegree(float value, float range) {
        return (value + range) % range;
    }

    public static float[] updateOrientationAngles(float[] rotationMatrix, float[] acceleration, float[] geomagnetic)
    {
        float[] I = new float[9];
        float[] orientationAngles = new float[3];
        SensorManager.getRotationMatrix(rotationMatrix, I, acceleration, geomagnetic);

        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Y, rotationMatrix);

        return orientationAngles;
    }

    public static float[] withLowPassFilter(float[] values) {
        float alpha = 0.8f;
        int length = values.length;
        float[] gravity = new float[length];
        for (int i = 0; i < length; i++) {
            gravity[i] = alpha * gravity[i] + (1 - alpha) * values[i];
        }

        return gravity;
    }

    public static float[] withHighPassFilter(float[] values, float[] gravity) {
        int length = values.length;
        float[] linearAcceleration = new float[length];
        for (int i = 0; i < length; i++) {
            linearAcceleration[i] = values[i] - gravity[i];
        }

        return linearAcceleration;
    }

    public static SharedPreferences getPref(Context context)
    {
        String prefKey = context.getString(R.string.pref_key);
        return context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
    }
}
