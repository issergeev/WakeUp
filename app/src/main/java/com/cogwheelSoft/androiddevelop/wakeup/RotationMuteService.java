package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

public class RotationMuteService extends Service {


    SensorManager MySensorManager;
    Sensor MyGyroscope;

    PowerManager.WakeLock MyWLock;
    PowerManager pm;

    AudioManager MyAudioManager;
    SensorEventListener mySEL;

    float xPos;
    float yPos;
    float zPos;


    public RotationMuteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate () {
            Toast.makeText(getApplicationContext(),"Rotation mute activated",Toast.LENGTH_SHORT).show();

//            //Wake up when device is in air
//            pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
//            MyWLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
//                    "Acquire Wake Lock");


            //Sensor initialization
            MySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
            MyGyroscope = MySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

            //Audio initialization
            MyAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        mySEL = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                xPos = event.values[0];
                yPos = event.values[1];
                zPos = event.values[2];

                //Sound mode control
                if ((MyAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL && yPos >= -180 && yPos <= -150)) {
                    MyAudioManager.setRingerMode(AudioManager.VIBRATE_SETTING_ON);
                    //Toast.makeText(getApplicationContext(), "Silent Mode Enabled", Toast.LENGTH_SHORT).show();
                }
                if ((yPos > -149) && (yPos < 0) && (MyAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)) {
                    MyAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    //Toast.makeText(getApplicationContext(), "Silent Mode Disabled", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(),"Rotation mute disabled",Toast.LENGTH_SHORT).show();
    }
}