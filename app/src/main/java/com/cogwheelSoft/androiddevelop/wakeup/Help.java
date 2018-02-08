package com.cogwheelSoft.androiddevelop.wakeup;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Help extends ListActivity{
    final String[] functionList = new String[] {"Lock on proximity", "Always on display", "Mute on rotation", "Display Lock",
    "Administrator"};
    ArrayAdapter<String> arrayAdapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Help");

        arrayAdapter = new ArrayAdapter<>(Help.this, android.R.layout.simple_list_item_1, functionList);
        setListAdapter(arrayAdapter);
    }

//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l , v, position, id);
//        if (position == 0){
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setTitle("Lock on proximity");
//            alertDialogBuilder.setMessage("This function turns the phone's screen off when proximity sensor activates");
//            alertDialogBuilder.setCancelable(true);
//            alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }else if (position == 1){
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setTitle("Always on display");
//            alertDialogBuilder.setMessage("This function keeps the phone's screen on");
//            alertDialogBuilder.setCancelable(true);
//            alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }else if (position == 2){
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setTitle("Mute on rotation");
//            alertDialogBuilder.setMessage("This function turns the sound off when your phone lays on the screen");
//            alertDialogBuilder.setCancelable(true);
//            alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }else if (position == 3){
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setTitle("Display lock");
//            alertDialogBuilder.setMessage("This function locks the phone's screen when it is put away (requires " +
//                    "administrator rights)");
//            alertDialogBuilder.setCancelable(true);
//            alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }else if (position == 4){
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setTitle("Administrator");
//            alertDialogBuilder.setMessage("This function adds the application to phone's administrators (is required to use " +
//                    "some functions)");
//            alertDialogBuilder.setCancelable(true);
//            alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }
}