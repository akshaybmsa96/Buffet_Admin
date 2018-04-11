package yporders.com.yourspizza.yporders.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;

/**
 * Created by akshaybmsa96 on 30/12/17.
 */

public class PreferencedData {

    static final String PREF_DELIVERY_CENTRE = "Delivery Centre Name";
    static final String PREF_LOGGED_IN_STATUS = "Login Status";
    static final String PREF_NEW_ORDER = "New Order Alert";


    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedIn(Context ctx, Boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_LOGGED_IN_STATUS, status);
        editor.commit();
    }

    public static Boolean getLoggedIn(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_LOGGED_IN_STATUS,false);
    }

    public static void setPrefDeliveryCentre(Context ctx, String name)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DELIVERY_CENTRE, name);
        editor.commit();
    }

    public static String getPrefDeliveryCentre(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_DELIVERY_CENTRE,"");
    }

    public static void setPrefNewOrder(Context ctx, String value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NEW_ORDER, value);
        editor.commit();
    }

    public static String getPrefNewOrder(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_NEW_ORDER,"");
    }

    public static void clearPref(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_DELIVERY_CENTRE);
        editor.remove(PREF_LOGGED_IN_STATUS);
        editor.remove(PREF_NEW_ORDER);
        editor.commit();
    }

}
