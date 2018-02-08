package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.ActivityManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

public class LockScreenWhileWaitingService extends Service {
    public LockScreenWhileWaitingService() {
    }

    int Awaiting = 2000;

    SharedPreferences Settings;

    SensorManager MySensorManager;
    Sensor MyGyroscope;

    SensorEventListener mySEL;

    float xPos;
    float yPos;
    float zPos;

    boolean isUp, isCount = false;
    public long systemTime;

    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        Toast.makeText(getApplicationContext(), "Locked service activated", Toast.LENGTH_SHORT).show();

        deviceManger = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);

        Settings = Settings_Preferences.MyShPrefs;

        Context context = getApplicationContext();

        //Settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //Sensor initialization
        MySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        MyGyroscope = MySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        mySEL = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                xPos = event.values[0];
                yPos = event.values[1];
                zPos = event.values[2];

                //Power mode control
                if ((yPos <= -8) && isCount && systemTime < System.currentTimeMillis()) {
                    systemTime = System.currentTimeMillis() + Awaiting;
                    isCount = false;
                }
                if ((yPos >= -8) && (yPos <= 2) && (systemTime <= System.currentTimeMillis()) && isUp) {
                    isUp = false;
                    deviceManger.lockNow();
                }
                if (yPos >= -180 && yPos < -8) {
                    isUp = true;
                    isCount = true;
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        MySensorManager.registerListener(mySEL, MyGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onDestroy() {
        MySensorManager.unregisterListener(mySEL);
        Toast.makeText(getApplicationContext(),"Locked service disabled",Toast.LENGTH_SHORT).show();
    }
}