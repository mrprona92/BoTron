package com.mrprona.botronmanhinh;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * AppConfig to retrieve/set configuration of all modules.
 *
 * @author Nextop
 */
public class PrefConfig {


    public static final String PREFS_REMEMBER_SETTING = "__Remeber_Setting";

    public static final String PREFS_REMEMBER_FIRSTTIME = "__Remeber_First_Time";

    public static final String PREFS_REMEMBER_NOTIFICATION = "__Remeber_Notification";

    public static final String PREFS_REMEMBER_ROUND_VALUE = "__Remeber_Round_Value";

    public static final String PREFS_REMEMBER_ROUND_COLOR = "__Remember_Round_Color";

    public static final String PREFS_REMEMBER_ROUND_COLOR_POSITION = "__Remember_Round_Color_Position";
    public static final String PREFS_REMEMBER_ROUND_COLOR_ALPHAPOSITION = "__Remember_Round_Color_AlphaPosition";

    public static final String PREFS_REMEMBER_ROUND_4GOC_TOPRIGHT = "__Remeber_Round_4Value1";

    public static final String PREFS_REMEMBER_ROUND_4GOC_TOPLEFT = "__Remeber_Round_4Value2";

    public static final String PREFS_REMEMBER_ROUND_4GOC_BOTRIGHT = "__Remeber_Round_4Value3";

    public static final String PREFS_REMEMBER_ROUND_4GOC_BOTLEFT = "__Remeber_Round_4Value4";


    static final PrefConfig sInstance = new PrefConfig();
    private SharedPreferences mSecurePref;

    public static PrefConfig getInstance() {
        return sInstance;
    }

    public SharedPreferences getSharedPreferences(Context context) {

        if (mSecurePref == null)
            mSecurePref = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return mSecurePref;
    }

    /**
     * Return 2-strings username/password that are remembered from
     * the last successful login.
     *
     * @return
     */


    public void setRememberSetting(Context context, Boolean isReload) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putBoolean(PREFS_REMEMBER_SETTING, isReload);
        edt.commit();
    }


    public void setRememberFirstTime(Context context, Boolean isReload) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putBoolean(PREFS_REMEMBER_FIRSTTIME, isReload);
        edt.commit();
    }




    public boolean getRememberFirstTime(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getBoolean(PREFS_REMEMBER_FIRSTTIME, false);
    }


    public boolean getRememberSetting(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getBoolean(PREFS_REMEMBER_SETTING, true);
    }


    public boolean[] getRemembered4Coner(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);

        boolean topRight = prefs.getBoolean(PREFS_REMEMBER_ROUND_4GOC_TOPRIGHT, true);
        boolean topLeft = prefs.getBoolean(PREFS_REMEMBER_ROUND_4GOC_TOPLEFT, true);
        boolean botRight = prefs.getBoolean(PREFS_REMEMBER_ROUND_4GOC_BOTRIGHT, true);
        boolean botLeft = prefs.getBoolean(PREFS_REMEMBER_ROUND_4GOC_BOTLEFT, true);

        return new boolean[]{topRight, topLeft, botRight, botLeft};
    }

    public void setRemember4Coner(Context context, boolean[] remember4coner) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putBoolean(PREFS_REMEMBER_ROUND_4GOC_TOPRIGHT, remember4coner[0]);
        edt.putBoolean(PREFS_REMEMBER_ROUND_4GOC_TOPLEFT, remember4coner[1]);
        edt.putBoolean(PREFS_REMEMBER_ROUND_4GOC_BOTRIGHT, remember4coner[2]);
        edt.putBoolean(PREFS_REMEMBER_ROUND_4GOC_BOTLEFT, remember4coner[3]);
        edt.commit();
    }

    public void setRememberNotification(Context context, boolean notification) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putBoolean(PREFS_REMEMBER_NOTIFICATION, notification);
        edt.commit();
    }

    public boolean getRememberNotification(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getBoolean(PREFS_REMEMBER_NOTIFICATION, false);
    }


    public void setRememberRoundValue(Context context, int roundValue) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putInt(PREFS_REMEMBER_ROUND_VALUE, roundValue);
        edt.commit();
    }

    public int getRememberRoundValue(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getInt(PREFS_REMEMBER_ROUND_VALUE, 0);
    }

    public void setRememberColorValue(Context context, int colorValue) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putInt(PREFS_REMEMBER_ROUND_COLOR, colorValue);
        edt.commit();
    }

    public int getRememberRoundColor(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getInt(PREFS_REMEMBER_ROUND_COLOR, 0);
    }


    public void setRememberColorPosition(Context context, int colorValue) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putInt(PREFS_REMEMBER_ROUND_COLOR_POSITION, colorValue);
        edt.commit();
    }

    public int getRememberColorPosition(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getInt(PREFS_REMEMBER_ROUND_COLOR_POSITION, 0);
    }


    public void setRememberColorAPosition(Context context, int colorValue) {
        SharedPreferences prefs = getSharedPreferences(context);
        Editor edt = prefs.edit();
        edt.putInt(PREFS_REMEMBER_ROUND_COLOR_ALPHAPOSITION, colorValue);
        edt.commit();
    }

    public int getRememberColorAPosition(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getInt(PREFS_REMEMBER_ROUND_COLOR_ALPHAPOSITION, 0);
    }
}
