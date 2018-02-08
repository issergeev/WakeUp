package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Settings_Preferences extends AppCompatActivity{
    public static long BackButtonPressed;

    static String Operator, formDoubleInch, formDoubleHeight, formDoubleWidth;
    static double x, y, xPix, yPix;

    //Switches
    SwitchCompat MySwitch1;
    SwitchCompat MySwitch2;
    SwitchCompat MySwitch3;
    SwitchCompat MySwitch4;
    SwitchCompat MySwitchAdmin;

    //CheckBoxes
    CheckBox DisableCheckBox, LogoCheckBox;

    ImageView LogoView;

    static SharedPreferences MyShPrefs;
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    SharedPreferences.Editor MyEditor;

    //Alert
    AlertDialog.Builder MyBuilder;
    static final int RESULT_ENABLE = 1;
    String Message, Title, ButtonOK, ButtonCancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysettings_settings);
        setTitle("WakeUp");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Switches initialization
        MySwitch1 = (SwitchCompat) findViewById(R.id.MySwitchOne);
        MySwitch2 = (SwitchCompat) findViewById(R.id.MySwitchTwo);
        MySwitch3 = (SwitchCompat) findViewById(R.id.MySwitchThree);
        MySwitch4 = (SwitchCompat) findViewById(R.id.MySwitchFour);
        MySwitchAdmin = (SwitchCompat) findViewById(R.id.AdminActivator);

        //CheckBoxes initialization
        DisableCheckBox = (CheckBox) findViewById(R.id.proximityCheckBox);
        LogoCheckBox = (CheckBox) findViewById(R.id.logoCheckBox);

        LogoView = (ImageView) findViewById(R.id.LogoView);

        Title = getString(R.string.Title);
        Message = getString(R.string.Message);
        ButtonOK = getString(R.string.ButtonOK);
        ButtonCancel = getString(R.string.ButtonCancel);

        deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        compName = new ComponentName(Settings_Preferences.this, MyAdmin.class);

        MyShPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (!MyShPrefs.getBoolean("isFirstStart", false)){
            MyBuilder = new AlertDialog.Builder(Settings_Preferences.this);
            MyBuilder.setTitle("Help");
            MyBuilder.setMessage("Welcome to \"WakeUp\"! \n" +
            "Would you like to read information about application's functions?");
            MyBuilder.setCancelable(false);
            MyBuilder.setPositiveButton("Read", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("isFirstStart", true);
                    MyEditor.apply();
                    startActivity(new Intent(Settings_Preferences.this, RecyclerHelper.class));
                }
            });
            MyBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("isFirstStart", true);
                    MyEditor.apply();
                }
            });
            MyBuilder.show();
        }

        //Switches onCheckedChangeListener
        MySwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySwitch1.isChecked()){
                    startService(new Intent(Settings_Preferences.this, WakeUpService.class));
                    Toast.makeText(getApplicationContext(), "Proximity lock activated", Toast.LENGTH_SHORT).show();
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("isProximityEnabled", true);
                    MyEditor.apply();
                    DisableCheckBox.setEnabled(true);
                }else{
                    stopService(new Intent(Settings_Preferences.this, WakeUpService.class));
                    Toast.makeText(getApplicationContext(), "Proximity lock disabled", Toast.LENGTH_SHORT).show();
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("isProximityEnabled", false);
                    MyEditor.apply();
                    DisableCheckBox.setEnabled(false);
                }
            }
        });


        MySwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySwitch2.isChecked()){
                    startService(new Intent(Settings_Preferences.this, AlwaysOnDisplayService.class));
                }else{
                    stopService(new Intent(Settings_Preferences.this, AlwaysOnDisplayService.class));
                }
            }
        });

        MySwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySwitch3.isChecked()){
                    startService(new Intent(Settings_Preferences.this, RotationMuteService.class));
                }else{
                    stopService(new Intent(Settings_Preferences.this, RotationMuteService.class));
                }
            }
        });

        MySwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySwitch4.isChecked()){
                    startService(new Intent(Settings_Preferences.this, LockScreenWhileWaitingService.class));
                }else{
                    stopService(new Intent(Settings_Preferences.this, LockScreenWhileWaitingService.class));
                }
            }
        });

        MySwitchAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySwitchAdmin.isChecked() && !deviceManger.isAdminActive(compName)){
                    ShowDialogMessage();
                }if(!MySwitchAdmin.isChecked()){
                    //Function
                    deviceManger.removeActiveAdmin(compName);
                    MySwitch4.setChecked(false);
                    MySwitch4.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Administrator rights disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //CheckBox onCheckedChangeListener
        DisableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (DisableCheckBox.isChecked()){
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("DisableCheckBox", true);
                    MyEditor.apply();
                }else if (!DisableCheckBox.isChecked()){
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("DisableCheckBox", false);
                    MyEditor.apply();
                }
            }
        });
        LogoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (LogoCheckBox.isChecked()){
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("LogotypeCheckBox", true);
                    MyEditor.apply();

                    LogoView.setVisibility(View.VISIBLE);
                    LogoView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_anim));
                }else if (!LogoCheckBox.isChecked()){
                    MyEditor = MyShPrefs.edit();
                    MyEditor.putBoolean("LogotypeCheckBox", false);
                    MyEditor.apply();

                    LogoView.setVisibility(View.GONE);
                    LogoView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_gone));
                }
            }
        });

        TelephonyManager telephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Operator = telephoneManager.getNetworkOperatorName();
        if (Operator.equals("")) {
            Operator = "No carrier";
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        x = Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 1);
        y = Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 1);
        double inc = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        xPix = displayMetrics.widthPixels;
        yPix = displayMetrics.heightPixels;
        formDoubleInch = String.format("%.2f", inc);
        formDoubleHeight = String.format("%.0f", yPix);
        formDoubleWidth = String.format("%.0f", xPix);
    }

    public void ShowDialogMessage() {
        MyBuilder = new AlertDialog.Builder(this);
        MyBuilder.setTitle(Title);
        MyBuilder.setMessage(Message);
        MyBuilder.setCancelable(false);
        MyBuilder.setPositiveButton(ButtonOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Administrator rights activated successfully", Toast.LENGTH_SHORT).show();
                //LOCKSCREEN
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        compName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Access to administration rights to lock the screen");
                startActivityForResult(intent, RESULT_ENABLE);

                MyEditor = MyShPrefs.edit();
                MyEditor.putBoolean(getString(R.string.CheckboxOne), true);
                MyEditor.apply();
                MySwitch4.setEnabled(true);
            }
        });
        MyBuilder.setNegativeButton(ButtonCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Administrator rights disabled", Toast.LENGTH_SHORT).show();
                MySwitchAdmin.setChecked(false);
                MySwitch4.setChecked(false);
            }
        });
        MyBuilder.show();
    }

    public void onPause(){
        super.onPause();
        MyEditor = MyShPrefs.edit();
        MyEditor.putBoolean(getString(R.string.SwitchPrefsOne), MySwitch1.isChecked());
        MyEditor.putBoolean(getString(R.string.SwitchPrefsTwo), MySwitch2.isChecked());
        MyEditor.putBoolean(getString(R.string.SwitchPrefsThree), MySwitch3.isChecked());
        MyEditor.putBoolean(getString(R.string.SwitchPrefsFour), MySwitch4.isChecked());
        MyEditor.putBoolean(getString(R.string.AdminEnabled), MySwitchAdmin.isChecked());
        MyEditor.apply();
    }

    public void onResume(){
        super.onResume();
        if (!MyShPrefs.getBoolean(getString(R.string.AdminEnabled), false)) {
            MySwitch4.setEnabled(false);
        }
        if (MyShPrefs.getBoolean(getString(R.string.AdminEnabled), false)) {
            MySwitch4.setEnabled(true);
        }

        if(MySwitchAdmin.isChecked() && !deviceManger.isAdminActive(compName)){
            MySwitchAdmin.setChecked(false);
            //MySwitch4.setChecked(false);
        }

        //Preferences saves
        MySwitch1.setChecked(MyShPrefs.getBoolean(getString(R.string.SwitchPrefsOne), false));
        MySwitch2.setChecked(MyShPrefs.getBoolean(getString(R.string.SwitchPrefsTwo), false));
        MySwitch3.setChecked(MyShPrefs.getBoolean(getString(R.string.SwitchPrefsThree), false));
        MySwitch4.setChecked(MyShPrefs.getBoolean(getString(R.string.SwitchPrefsFour), false));
        MySwitchAdmin.setChecked(MyShPrefs.getBoolean(getString(R.string.AdminEnabled), false));

        DisableCheckBox.setChecked(MyShPrefs.getBoolean("DisableCheckBox", false));
        LogoCheckBox.setChecked(MyShPrefs.getBoolean("LogotypeCheckBox", true));

        if (MyShPrefs.getBoolean("LogotypeCheckBox", true)){
            LogoView.setVisibility(View.VISIBLE);
        }else {
            LogoView.setVisibility(View.GONE);
        }

        if (MyShPrefs.getBoolean("isProximityEnabled", false)) {
            DisableCheckBox.setEnabled(true);
        }else{
            DisableCheckBox.setEnabled(false);
        }
    }

    public void onBackPressed() {
        if (BackButtonPressed + 2000 >= System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(), "Press 'Back' button to exit", Toast.LENGTH_SHORT).show();
            BackButtonPressed = System.currentTimeMillis();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(!menuItem.isChecked()) {
            menuItem.setChecked(true);
        }

        switch (id){
            case R.id.phone_info:
                startActivity(new Intent(Settings_Preferences.this, Info.class));
                overridePendingTransition(R.anim.options_anim_start, R.anim.settings_anim_start);
                finish();
                return true;

            case R.id.allsensors:
                startActivity(new Intent(Settings_Preferences.this, all_sensors.class));
                overridePendingTransition(R.anim.options_anim_start, R.anim.settings_anim_start);
                finish();
                return true;

            case R.id.about:
                startActivity(new Intent(Settings_Preferences.this, AboutApp.class));
                overridePendingTransition(R.anim.options_anim_start, R.anim.settings_anim_start);
                finish();
                return true;

            case R.id.help:
                startActivity(new Intent(Settings_Preferences.this, RecyclerHelper.class));
                overridePendingTransition(R.anim.options_anim_start, R.anim.settings_anim_start);
                finish();
                return true;

            case R.id.exit:
                finish();
                //super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}