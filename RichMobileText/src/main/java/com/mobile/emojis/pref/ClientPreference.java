package com.mobile.emojis.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Shared preference.
 */
public class ClientPreference {

    private SharedPreferences sharedPreferences;
    private static ClientPreference prefs;

    /**
     * initialize preferences.
     *
     * @param mContext
     */
    private void initPreference(final Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(mContext.getPackageName() + "_MobileEmojis", Context.MODE_PRIVATE);
    }

    /**
     * get static instance of this class.
     *
     * @return
     */
    public static ClientPreference getInstance() {
        if (prefs == null) {
            prefs = new ClientPreference();
        }
        return prefs;
    }

    /**
     * put string value.
     *
     * @param context
     * @param key
     * @param value
     */
    public void putString(final Context context, final String key, final String value) {
        if (sharedPreferences == null) {
            initPreference(context);
        }
        if (sharedPreferences != null) {
            final Editor editing = sharedPreferences.edit();
            editing.remove(key);
            editing.putString(key, value);
            editing.commit();
        }
    }

    /**
     * get string value.
     *
     * @param context
     * @param key
     * @return
     */
    public String getString(final Context context, final String key) {
        if (sharedPreferences == null) {
            initPreference(context);
        }
        return sharedPreferences.getString(key, null);
    }

    /**
     * Remove Key.
     *
     * @param context
     * @param key
     */
    public void remove(final Context context, final String key) {
        if (sharedPreferences == null) {
            initPreference(context);
        }
        if (sharedPreferences != null) {
            final Editor editing = sharedPreferences.edit();
            editing.remove(key);
            editing.commit();
        }
    }

    /**
     * Clear Shared preferences.
     */
    public void clear() {
        if (sharedPreferences != null) {
            final Editor editing = sharedPreferences.edit();
            editing.clear();
            editing.commit();
        }
    }
}
