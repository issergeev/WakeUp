package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

public class AlwaysOnDisplayService extends Service {

    SensorManager MySensorManager;
    Sensor MyGyroscope;

    protected PowerManager.WakeLock MyWLock;
    PowerManager pm;

    SensorEventListener mySEL;

    float xPos;
    float yPos;
    float zPos;


    public AlwaysOnDisplayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public void onCreate () {
        Toast.makeText(getApplicationContext(), "Always on display activated", Toast.LENGTH_SHORT).show();

        //Wake up when device is in air
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.MyWLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Acquire Wake Lock");

        //Sensor initialization
        MySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        MyGyroscope = MySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        mySEL = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                xPos = event.values[0];
                yPos = event.values[1];
                zPos = event.values[2];

                //Wake up Screen control
                if ((yPos >= -180) && (yPos <= 2) && !MyWLock.isHeld()) {
                    MyWLock.acquire();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        MySensorManager.registerListener(mySEL, MyGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        MyWLock.acquire();
    }

    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"Always on display disabled",Toast.LENGTH_SHORT).show();

        if (MyWLock.isHeld()){
            MyWLock.release();
        }
        MySensorManager.unregisterListener(mySEL);
    }
}