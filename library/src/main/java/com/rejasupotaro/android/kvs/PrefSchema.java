package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PrefSchema {
    private SharedPreferences prefs;

    protected void init(Context context, String name) {
        prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    protected void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    protected void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    protected void putFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    protected void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    protected void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    protected void putStringSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    protected boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    protected String getString(String key) {
        return prefs.getString(key, "");
    }

    protected float getFloat(String key) {
        return prefs.getFloat(key, 0);
    }

    protected int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    protected long getLong(String key) {
        return prefs.getLong(key, 0);
    }

    protected Set<String> getStringSet(String key) {
        return prefs.getStringSet(key, new HashSet<String>());
    }
}
