package com.cogwheelSoft.androiddevelop.wakeup;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class info_all extends Fragment {

    static final String Page_Number = "page_number";
    int Page_Page;

    View view;
    TextView MyModel, MyVersion, MyOperator, MyManufacture, MyApi, MyBrand, MyDevice, MyWidth, MyHeight, MyInches,
        MyResolution;

    static info_all newInstance(int Page) {
        info_all info = new info_all();
        Bundle arguments = new Bundle();
        arguments.putInt(Page_Number, Page);
        info.setArguments(arguments);
        return info;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Page_Page = getArguments().getInt(Page_Number);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        if (Page_Page == 0) {
            view = layoutInflater.inflate(R.layout.info_summary, null);
            MyModel = (TextView) view.findViewById(R.id.MyModel);
            String PhoneModel = Build.MODEL;
            MyModel.setText("Model : " + PhoneModel);

            MyBrand = (TextView) view.findViewById(R.id.MyBrand);
            String Brand = Build.BRAND;
            MyBrand.setText("Brand : " + Brand);

            MyDevice = (TextView) view.findViewById(R.id.MyDevice);
            String Device = Build.DEVICE;
            MyDevice.setText("Device : " + Device);

            MyVersion = (TextView) view.findViewById(R.id.MyVersion);
            String Version = Build.VERSION.RELEASE;
            MyVersion.setText("Android version : " + Version);

            MyApi = (TextView) view.findViewById(R.id.MyApi);
            String Api = Build.VERSION.SDK;
            MyApi.setText("API version : " + Api);

            MyOperator = (TextView) view.findViewById(R.id.MyOperator);
            MyOperator.setText("Operator : " + Settings_Preferences.Operator);

            MyManufacture = (TextView) view.findViewById(R.id.MyManufacture);
            String Manufacture = Build.MANUFACTURER;
            MyManufacture.setText("Manufacture : " + Manufacture);
        }
        if(Page_Page == 1) {
            view = layoutInflater.inflate(R.layout.info_screen, null);

            MyWidth = (TextView) view.findViewById(R.id.WidthScr);
            MyWidth.setText("Screen width : "+Settings_Preferences.formDoubleWidth+" pix");

            MyHeight = (TextView) view.findViewById(R.id.HeightScr);
            MyHeight.setText("Screen height : "+Settings_Preferences.formDoubleHeight+" pix");

            MyInches = (TextView) view.findViewById(R.id.InchesScr);
            MyInches.setText("Screen inches : "+Settings_Preferences.formDoubleInch+" inches");

            MyResolution = (TextView) view.findViewById(R.id.ResolutionScr);
            MyResolution.setText("Screen resolution : "+ Settings_Preferences.formDoubleHeight+" x "+
                    Settings_Preferences.formDoubleWidth+" pix");
        }return view;
    }
}