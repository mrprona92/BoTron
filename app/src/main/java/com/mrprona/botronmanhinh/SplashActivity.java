package com.mrprona.botronmanhinh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.ads.MobileAds;

import java.util.Locale;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences localPrefs = getSharedPreferences("locale", MODE_PRIVATE);
        String loc = localPrefs.getString("current", null);
        if (loc != null) {
            Locale locale = new Locale(loc);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        }


        // Initialize the Mobile Ads SDK.§

        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));

    }


    @Override
    protected void onStart() {
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1500);

        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
