package com.cogwheelSoft.androiddevelop.wakeup;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class all_sensors extends ListActivity {

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> listSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);

            List<String> listSensorType = new ArrayList<>();
            for (int i = 0; i < listSensor.size(); i++) {
                listSensorType.add(listSensor.get(i).getName());
            }

            setListAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, listSensorType));
            getListView().setTextFilterEnabled(true);
        }

        public void onBackPressed(){
            startActivity(new Intent(all_sensors.this, Settings_Preferences.class));
            overridePendingTransition(R.anim.settings_anim_reverce, R.anim.options_anim_reverce);
            finish();
        }
}