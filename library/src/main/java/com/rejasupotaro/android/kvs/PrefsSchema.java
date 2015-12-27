package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public abstract class PrefsSchema {
    private SharedPreferences prefs;

    protected void init(Context context, String tableName) {
        this.prefs = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
    }

    protected void init(SharedPreferences prefs) {
        this.prefs = prefs;
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

    protected boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    protected String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    protected float getFloat(String key, float defValue) {
        return prefs.getFloat(key, defValue);
    }

    protected int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    protected long getLong(String key, long defValue) {
        return prefs.getLong(key, defValue);
    }

    protected Set<String> getStringSet(String key, Set<String> defValue) {
        return prefs.getStringSet(key, new HashSet<String>());
    }

    protected boolean has(String key) {
        return prefs.contains(key);
    }

    protected void remove(String key) {
        prefs.edit().remove(key).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
