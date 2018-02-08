package com.cogwheelSoft.androiddevelop.wakeup;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;


public class Info extends FragmentActivity {

    public static int Page_Count = 2;
    ViewPager MyPager;
    PagerAdapter MyAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.holder_pageview);
        setTitle("Information");

        //NEW
        MyPager = (ViewPager) findViewById(R.id.MyViewPager);
        MyAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        MyPager.setAdapter(MyAdapter);
        MyPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        public void onPageSelected(int Position){
//            MyModel = (TextView) findViewById(R.id.MyModel);
//            String PhoneModel = android.os.Build.MODEL;
//            MyModel.setText(PhoneModel);
        }
        public void onPageScrolled(int Position, float Position_Offset, int Position_Pixels){
        }
        public void onPageScrollStateChanged(int State){
        }
        });
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter{

        public MyFragmentAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        public CharSequence getPageTitle(int Position){
            if(Position == 0) {
                return "Summary";
            }else
                return "Display";
        }

        @Override
        public Fragment getItem(int Position) {
            return info_all.newInstance(Position);
        }

        @Override
        public int getCount() {
            return Page_Count;
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(Info.this, Settings_Preferences.class));
        overridePendingTransition(R.anim.settings_anim_reverce, R.anim.options_anim_reverce);
        finish();
    }
}