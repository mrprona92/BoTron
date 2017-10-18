package com.mrprona.botronmanhinh.service;

import android.accessibilityservice.AccessibilityService;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.mrprona.botronmanhinh.MainActivity;
import com.mrprona.botronmanhinh.PrefConfig;
import com.mrprona.botronmanhinh.R;
import com.mrprona.botronmanhinh.RoundView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Foreground service. Creates a head view.
 * The pending intent allows to go back to the settings activity.
 */
public class CornerService extends IntentService {

    private final static int FOREGROUND_ID = 999;

    private RoundView mCornerLayer;

    private Context mContext;

    private WindowManager mWindowManager;

    private PrefConfig mSharePreference;

    private static Timer timer = new Timer();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CornerService(String name) {
        super(name);
    }

    public CornerService() {
        super("MyServerOrWhatever");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCornerLayer = new RoundView(this);

        mSharePreference = PrefConfig.getInstance();

        final WindowManager.LayoutParams params;
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mCornerLayer, params);

        initHeadLayer();


        mContext = this;
        // android.os.Debug.waitForDebugger();
    }


    private final IBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }

    public void doServiceStuff() {
        /*AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                //Log.d("yourTag", "long running service task");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initHeadLayer();
                    }
                });
                return null;
            }
        };
        task.execute();*/
        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
    }


    private class mainTask extends TimerTask
    {
        public void run()
        {
            toastHandler.sendEmptyMessage(0);
        }
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
           initHeadLayer();
        }
    };



    // create an inner Binder class
    public class MyBinder extends Binder {
        public CornerService getService() {
            return CornerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //logServiceStarted();

        PendingIntent pendingIntent = createPendingIntent();
        Notification notification = createNotification(pendingIntent);

        startForeground(FOREGROUND_ID, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        destroyHeadLayer();
        stopForeground(true);
        //logServiceEnded();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private void initHeadLayer() {
        if (mCornerLayer != null) {
            mCornerLayer.setRoundedCorner(mSharePreference.getRemembered4Coner(this));
            mCornerLayer.setColor(mSharePreference.getRememberRoundColor(this));
            mCornerLayer.setCornerRadius(mSharePreference.getRememberRoundValue(this));
        }
    }

    private void destroyHeadLayer() {
        mWindowManager.removeView(mCornerLayer);
        mCornerLayer = null;
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private Notification createNotification(PendingIntent intent) {
        return new Notification.Builder(this)
                .setContentTitle(getText(R.string.app_name))
                .setContentText(getText(R.string.str_content_notification))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(intent)
                .build();
    }

    private void logServiceStarted() {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }

    private void logServiceEnded() {
        Toast.makeText(this, "Service ended", Toast.LENGTH_SHORT).show();
    }


    private void destroyIntent() {
        Intent intent = new Intent(this.mContext, getClass());
        intent.setPackage(getPackageName());
        ((AlarmManager) this.mContext.getSystemService(ALARM_SERVICE)).set(3, SystemClock.elapsedRealtime() + 1000, PendingIntent.getService(mContext, 1, intent, Intent.FILL_IN_ACTION));
    }

}
