package com.mrprona.botronmanhinh;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mrprona.botronmanhinh.color.ColorPicker;
import com.mrprona.botronmanhinh.color.OpacityBar;
import com.mrprona.botronmanhinh.color.SVBar;
import com.mrprona.botronmanhinh.dialog.SubmitBugDialog;
import com.mrprona.botronmanhinh.service.CornerService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Matching UI
    @BindView(R.id.switchSetting)
    Switch mSettingSwitch;

    @BindView(R.id.switchNotification)
    Switch mNotificationSwitch;

    @BindView(R.id.lblSetting0)
    TextView lblSetting;

    @BindView(R.id.lblRounder2)
    TextView lblRounder2;


    @BindView(R.id.svbar)
    SVBar svBar;

    @BindView(R.id.sbRound)
    SeekBar sbRound;


    @BindView(R.id.opacitybar)
    OpacityBar opacityBar;


    @BindView(R.id.picker)
    ColorPicker mColorSeekBar;

    @BindView(R.id.lblRoundValue)
    TextView lblRoundValue;

    @BindView(R.id.lblTitleColor)
    TextView lblTitleColor;

    @BindView(R.id.lblRounderSettinged)
    TextView lblRounderSettinged;


    PrefConfig mPreconfig;

    ConfirmDialog mConfirmDialog;

    CornerService myService;


    private Intent serviceIntent;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mAdView = (AdView) findViewById(R.id.ad_view);
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0")
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        mPreconfig = PrefConfig.getInstance();

        serviceIntent = new Intent(this, CornerService.class);

        SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(this);

        initControl();

        mIsBound = false;

        isFirstTimeInit = true;

        if (!mPreconfig.getRememberFirstTime(this)) {
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(getString(R.string.dialog_sorry))
                    .setContentText(getString(R.string.dialog_sorry_content))
                    .setCustomImage(R.drawable.cat)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            mPreconfig.setRememberFirstTime(getApplicationContext(), true);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }


        if (mPreconfig.getRememberSetting(this)) {
            startHeadService();
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initUIFromPre();
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0").build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0").build());
            }

        });
        mInterstitialAd.show();
    }


    @OnClick(R.id.switchSetting)
    public void actionSetting(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblSetting.setText(mSettingSwitch.isChecked() ? getString(R.string.str_setting_on) : getString(R.string.str_setting_off));
                mPreconfig.setRememberSetting(getApplicationContext(), mSettingSwitch.isChecked());
            }
        });


    }

    @OnClick(R.id.btn4Rounder)
    public void actionSetting4Coner(View view) {
        mConfirmDialog.show();
    }


    @OnClick(R.id.switchNotification)
    public void actionNotification(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPreconfig.setRememberNotification(getApplicationContext(), mNotificationSwitch.isChecked());
            }
        });
    }


    public void initControl() {
        sbRound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblRoundValue.setText(progress + "");
                        mPreconfig.setRememberRoundValue(getApplicationContext(), progress);
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mColorSeekBar.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                if (isFirstTimeInit) {
                    isFirstTimeInit = false;
                    return;
                }
                mPreconfig.setRememberColorValue(getApplicationContext(), color);
                lblTitleColor.setTextColor(color);
                //mPreconfig.setRememberColorAPosition(getApplicationContext(), mColorSeekBar.getAlphaBarPosition());
            }
        });
    }


    private boolean isFirstTimeInit;


    public void initUiforSettinged4() {
        boolean[] settingList = mPreconfig.getRemembered4Coner(this);
        String[] mapTypeNames = getResources().getStringArray(R.array.setting_round);

        int i = 0;
        String content = "";
        for (boolean m : settingList) {
            if (m) {
                content += mapTypeNames[i] + ", ";
            }
            if (i == 3) {
                if(content.length()<2){
                    lblRounderSettinged.setText(content);
                    return;
                }
                int lastCommand = content.length() - 2;
                content = content.substring(0, lastCommand);
            }
            i++;
        }
        lblRounderSettinged.setText(content);
    }

    private void initUIFromPre() {
        boolean[] list4Coner = mPreconfig.getRemembered4Coner(this);

        mConfirmDialog = new ConfirmDialog(this, 0, list4Coner);
        mConfirmDialog.setOnConfirmDialogListener(new ConfirmDialog.ConfirmDialogListener() {
            @Override
            public void onSelect(int indexButton, boolean[] listCheck) {
                if (indexButton == 1) {
                    mPreconfig.setRemember4Coner(getApplicationContext(), listCheck);
                }
            }
        });

        initUiforSettinged4();

        mSettingSwitch.setChecked(mPreconfig.getRememberSetting(this));
        mNotificationSwitch.setChecked(mPreconfig.getRememberNotification(this));
        lblRoundValue.setText(mPreconfig.getRememberRoundValue(this) + "");

        sbRound.setProgress(mPreconfig.getRememberRoundValue(this));
        mColorSeekBar.addSVBar(svBar);
        mColorSeekBar.addOpacityBar(opacityBar);
        //mColorSeekBar.setAlphaBarPosition(mPreconfig.getRememberColorAPosition(this));
        int color = mPreconfig.getRememberRoundColor(this);
        mColorSeekBar.setColor(color);
        lblTitleColor.setTextColor(color);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("BINH", "onSharedPreferenceChanged() called with: sharedPreferences = [" + sharedPreferences + "], key = [" + key + "]");
        if (PrefConfig.PREFS_REMEMBER_SETTING.equals(key)) {
            boolean enabled = sharedPreferences.getBoolean(key, false);
            if (enabled) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 1234);
                    } else {
                        startHeadService();
                    }
                }
            } else {
                stopHeadService();
            }
        } else {
            boolean enabled = sharedPreferences.getBoolean(PrefConfig.PREFS_REMEMBER_SETTING, false);
            if (enabled) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 1234);
                    } else {
                        if (myService == null) {
                            startHeadService();
                        } else {
                            Handler mHander = new Handler();
                            mHander.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myService.doServiceStuff();
                                }
                            }, 1000);
                        }
                    }
                } else {
                    stopHeadService();
                }
            }
        }

        if (PrefConfig.PREFS_REMEMBER_ROUND_4GOC_BOTLEFT.equals(key) || PrefConfig.PREFS_REMEMBER_ROUND_4GOC_BOTRIGHT.equals(key) || PrefConfig.PREFS_REMEMBER_ROUND_4GOC_TOPLEFT.equals(key) || PrefConfig.PREFS_REMEMBER_ROUND_4GOC_TOPRIGHT.equals(key)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initUiforSettinged4();
                }
            });
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((CornerService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    };

    boolean mIsBound = false;

    private void startHeadService() {
        //  if (!isMyServiceRunning(CornerService.class)) {
        startService(serviceIntent);
        // bind to the service
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        mIsBound = true;
        //  }
    }

    private void stopHeadService() {
        if (isMyServiceRunning(CornerService.class)) {
            if (mIsBound && myService != null) {
                try {
                    unbindService(serviceConnection);
                    mIsBound = false;
                } catch (IllegalArgumentException e) {
                    Log.d("BINH", "force cl" + e.getMessage());
                }
            }
            stopService(serviceIntent);
        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            startHeadService();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.llRate)
    public void onClickRate(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final RatingDialog ratingDialog = new RatingDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AppTheme))
                        .icon(getDrawable(R.drawable.cat))
                        .title(getString(R.string.str_rating_content1))
                        .threshold(3)
                        .titleTextColor(R.color.black)
                        .positiveButtonText(getString(R.string.str_rating_content2))
                        .negativeButtonText("Never")
                        .positiveButtonTextColor(R.color.black)
                        .negativeButtonTextColor(R.color.black)
                        .formTitle(getString(R.string.str_rating_content3))
                        .formHint(getString(R.string.str_rating_content2))
                        .formSubmitText("Submit")
                        .formCancelText("Cancel")
                        .ratingBarColor(R.color.cmn_yellow)
                        .playstoreUrl("https://play.google.com/store/apps/details?id=com.mrprona.botronmanhinh")
                        .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                            @Override
                            public void onFormSubmitted(String feedback) {
                                Intent send = new Intent(Intent.ACTION_SENDTO);
                                String uriText = "mailto:" + Uri.encode("@gmail.com") +
                                        "?subject=" + Uri.encode("[ProdoStudios] ORound Send feedback") +
                                        "&body=" + Uri.encode(feedback);
                                Uri uri = Uri.parse(uriText);

                                send.setData(uri);
                                startActivity(Intent.createChooser(send, "Send mail..."));
                            }
                        })
                        .build();

                ratingDialog.show();
            }
        });
    }

    @OnClick(R.id.llReport)
    public void onClickReport(){
        SubmitBugDialog mSubmit = new SubmitBugDialog(this);
        mSubmit.show();
    }
}
