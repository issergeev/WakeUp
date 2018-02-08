package com.cogwheelSoft.androiddevelop.wakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyBootReceiver extends BroadcastReceiver {

    SharedPreferences Settings;

    @Override
    public void onReceive(Context context, Intent intent) {
        Settings = PreferenceManager.getDefaultSharedPreferences(context);
        if (Settings.getBoolean(context.getString(R.string.SwitchPrefsOne), true)) {
            Intent startIntent = new Intent(context, WakeUpService.class);
            context.startService(startIntent);
        }
        if (Settings.getBoolean(context.getString(R.string.SwitchPrefsTwo), true)) {
            Intent startIntent = new Intent(context, AlwaysOnDisplayService.class);
            context.startService(startIntent);
        }
        if (Settings.getBoolean(context.getString(R.string.SwitchPrefsThree), true)) {
            Intent startIntent = new Intent(context, RotationMuteService.class);
            context.startService(startIntent);
        }
        if (Settings.getBoolean(context.getString(R.string.SwitchPrefsFour), true)) {
            Intent startIntent = new Intent(context, LockScreenWhileWaitingService.class);
            context.startService(startIntent);
        }
    }
}