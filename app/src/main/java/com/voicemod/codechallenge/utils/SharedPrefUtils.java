package com.voicemod.codechallenge.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextMenu;

public class SharedPrefUtils {

    private static final String PREF_APP = "pref_app";
    public static final String PREF_FIRST_RUN = "PREF_FIRST_RUN";
    public static final String PREF_DURATION = "PREF_DURATION";
    public static final String PREF_DURATION_VALUE = "PREF_DURATION_VALUE";
    public static final String PREF_SIZE = "PREF_SIZE";
    public static final String PREF_SIZE_VALUE = "PREF_SIZE_VALUE";
    public static final String PREF_QUALITY = "PREF_QUALITY";

    private SharedPrefUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    static public boolean isFirstRun(Context context){
        if (context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getBoolean(PREF_FIRST_RUN, true)){
            context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putBoolean(PREF_FIRST_RUN, false).apply();
            return true;
        }
        return false;
    }

    static public void setDefaultPreferences(Context context){
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putBoolean(PREF_QUALITY, true).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putBoolean(PREF_DURATION, false).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(PREF_DURATION_VALUE, 1).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putBoolean(PREF_SIZE, false).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(PREF_SIZE_VALUE, 1).apply();
    }

    /**
     * Gets boolean data.
     *
     * @param context the context
     * @param key     the key
     * @return the boolean data
     */
    static public boolean getBooleanData(Context context, String key) {

        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    /**
     * Gets int data.
     *
     * @param context the context
     * @param key     the key
     * @return the int data
     */
    static public int getIntData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key, 0);
    }

    /**
     * Gets string data.
     *
     * @param context the context
     * @param key     the key
     * @return the string data
     */
    // Get Data
    static public String getStringData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, null);
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    // Save Data
    static public void saveData(Context context, String key, String val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(key, val).apply();
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    static public void saveData(Context context, String key, int val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, val).apply();
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    static public void saveData(Context context, String key, boolean val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, val)
                .apply();
    }

    static public SharedPreferences.Editor getSharedPrefEditor(Context context, String pref) {
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit();
    }

    static public void saveData(SharedPreferences.Editor editor) {
        editor.apply();
    }


}