package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public abstract class PrefSchema implements Schema {
    private SharedPreferences prefs;

    protected void init(Context context, String name) {
        prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    @Override
    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    @Override
    public void putFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    @Override
    public void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    @Override
    public void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    @Override
    public String getString(String key) {
        return prefs.getString(key, "");
    }

    @Override
    public float getFloat(String key) {
        return prefs.getFloat(key, 0);
    }

    @Override
    public int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    @Override
    public long getLong(String key) {
        return prefs.getLong(key, 0);
    }

    @Override
    public Set<String> getStringSet(String key) {
        return prefs.getStringSet(key, new HashSet<String>());
    }
}
