package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static String Operator, formDoubleInch, formDoubleHeight, formDoubleWidth;

    public static long BackButtonPressed;

    static double x, y, xPix, yPix;

    //SharedPreferences
    public static final String  WakeUpPreferences = "wakeup_preferences";
    SharedPreferences wakeup_preferences = getSharedPreferences(WakeUpPreferences, Context.MODE_PRIVATE);


    //Administrator
    SharedPreferences MyShPrefs;
    static final int RESULT_ENABLE = 1;
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;

    //Alert
    AlertDialog.Builder MyBuilder;
    String Message, Title, ButtonOK, ButtonCancel, ButtonDisturb;
    private LinearLayout MyLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mysettings_settings);
        setTitle("My Settings");


        //ButtonOK = "Ok";
//        ButtonCancel = "Close";
//
//        MyShPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        compName = new ComponentName(MainActivity.this, MyAdmin.class);
//
//        if(MyShPrefs.getBoolean(getString(R.string.FirstStart), true)){
//            MyBuilder = new AlertDialog.Builder(MainActivity.this);
//            MyBuilder.setTitle("Warning");
//            MyBuilder.setMessage("Some smartphones restrict background activity of the application. For great user " +
//                    "experience highly recommended " +
//                    "to disable any restrictions in \"Settings\" menu. Otherwise some functions may not working properly");
//            MyBuilder.setCancelable(false);
//
//            MyBuilder.setPositiveButton(ButtonOK, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(), "You have pressed the Positive button", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(MainActivity.this, Settings.class);
//                    startActivity(intent);
//                }
//            });
//            MyBuilder.setNegativeButton(ButtonCancel, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//            MyBuilder.show();
//        }
//
//        View.OnClickListener FloatingButtonClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Button ONE pressed", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, Settings_Preferences.class));
//            }
//        };
//        View.OnClickListener FloatingButtonClickListener2 = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Button TWO pressed", Toast.LENGTH_SHORT).show();
//                //Not for APP
//                SharedPreferences.Editor MyEditor = MyShPrefs.edit();
//                MyEditor.putBoolean(getString(R.string.ShowDialog), true);
//                MyEditor.putBoolean(getString(R.string.FirstStart), true);
//                MyEditor.apply();
//                //End
//                startActivity(new Intent(MainActivity.this, Info.class));
//            }
//        };
//        View.OnClickListener FloatingButtonClickListener3 = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Button THREE pressed", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, AboutApp.class));
//            }
//        };

        TelephonyManager telephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Operator = telephoneManager.getNetworkOperatorName();
        if (Operator == "") {
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


        //NEW
//        MyButton = (MagicButton) findViewById(R.id.Floating_Button);
//        MyButton.setMagicButtonClickListener(FloatingButtonClickListener);
//        MyButton2 = (MagicButton) findViewById(R.id.Floating_Button2);
//        MyButton2.setMagicButtonClickListener(FloatingButtonClickListener2);
//        MyButton3 = (MagicButton) findViewById(R.id.Floating_Button3);
//        MyButton3.setMagicButtonClickListener(FloatingButtonClickListener3);
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
                startActivity(new Intent(MainActivity.this, Info.class));
                return true;

            case R.id.about:
                startActivity(new Intent(MainActivity.this, AboutApp.class));
                return true;

            case R.id.exit:
                super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

//    public void onDestroy(){
//        super.onDestroy();
//        SharedPreferences.Editor MyEditor = MyShPrefs.edit();
//        MyEditor.putBoolean(getString(R.string.FirstStart), false);
//        MyEditor.apply();
//    }

//    public void onResume(){
//        super.onResume();
//        if(MyShPrefs.getBoolean(getString(R.string.FirstStart), true)){
//            MyBuilder = new AlertDialog.Builder(MainActivity.this);
//            MyBuilder.setTitle("Warning");
//            MyBuilder.setMessage("To prevent failures highly recommended " +
//                    "to disable any restrictions in \"Settings\" menu. Otherwise some functions may not working properly");
//            MyBuilder.setCancelable(false);
//
//            MyBuilder.setPositiveButton(ButtonOK, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(), "You have pressed the Positive button", Toast.LENGTH_SHORT).show();
//                }
//            });
//            MyBuilder.setNegativeButton(ButtonCancel, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//            MyBuilder.show();
//        }
//    }
}