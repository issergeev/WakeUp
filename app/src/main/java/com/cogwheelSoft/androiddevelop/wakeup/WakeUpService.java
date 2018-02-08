package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class WakeUpService extends Service {

    PowerManager MyPower;
    PowerManager.WakeLock MyWakeLock;

    SensorManager sensorManager;
    Sensor proximity;
    SensorEventListener sensorEventListener;
    float x;

    SharedPreferences sharedPreferences = Settings_Preferences.MyShPrefs;

    public WakeUpService(){
    }

    @Override
    public IBinder onBind(Intent intent){
       return null;
    }

    public void onCreate() {
            MyPower = (PowerManager) getSystemService(Context.POWER_SERVICE);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                MyWakeLock = MyPower.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "WakeLock Activated");
            }
        }else {
        Toast.makeText(getApplicationContext(), "Please, update your device to android Lollipop or later versions",
                Toast.LENGTH_SHORT).show();
        }

            sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                x = event.values[0];

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && !MyWakeLock.isHeld()) {
                    MyWakeLock.acquire();
                }

                if ((getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) &&
                        MyWakeLock.isHeld() &&
                        sharedPreferences.getBoolean("DisableCheckBox", false)){
                    MyWakeLock.release();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorEventListener, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void onDestroy(){
        sensorManager.unregisterListener(sensorEventListener);

        if (MyWakeLock.isHeld()){
            MyWakeLock.release();
        }
    }
}