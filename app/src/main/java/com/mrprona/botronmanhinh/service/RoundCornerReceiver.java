package com.mrprona.botronmanhinh.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mrprona.botronmanhinh.PrefConfig;

/**
 * Created by BinhTran on 8/16/17.
 */


public class RoundCornerReceiver extends BroadcastReceiver {


    private static void enableService(Context context) {
        Intent intent = new Intent(context, CornerService.class);
        intent.setAction("com.mrprona92.roundcorner.RESTARD_SERVICE");
        context.startService(intent);
    }

    private static boolean checkingSettingEnable(Context context, String str) {
        try {
            boolean z = PrefConfig.getInstance().getRememberSetting(context);
            new StringBuilder("Value getBoolPreferences ").append(str).append(" = ").append(z);
            return z;
        } catch (Throwable th) {
            return false;
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (context != null && intent != null) {
            String action = intent.getAction();
            if (action != null) {
                int obj = -1;
                switch (action) {
                    case "com.mrprona92.roundcorner.RESTARD_SERVICE":
                        obj = -999;
                        break;
                    case "android.intent.action.BOOT_COMPLETED":
                        obj = 1;
                        break;
                }
                switch (obj) {
                    case -999:
                        if (checkingSettingEnable(context, PrefConfig.PREFS_REMEMBER_SETTING)) {
                            enableService(context);
                            return;
                        }
                        return;
                    case 1:
                        if (checkingSettingEnable(context, PrefConfig.PREFS_REMEMBER_SETTING)) {
                            enableService(context);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }
}