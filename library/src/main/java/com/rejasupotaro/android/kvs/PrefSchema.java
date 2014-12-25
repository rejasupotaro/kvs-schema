package com.rejasupotaro.android.kvs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public abstract class PrefSchema extends Schema {
    private SharedPreferences prefs;

    protected void init(Context context, String tableName) {
        prefs = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
    }

    @Override
    protected void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    @Override
    protected void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    @Override
    protected void putFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    @Override
    protected void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    @Override
    protected void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    @Override
    protected void putStringSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    @Override
    protected boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    @Override
    protected String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    @Override
    protected float getFloat(String key, float defValue) {
        return prefs.getFloat(key, defValue);
    }

    @Override
    protected int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    @Override
    protected long getLong(String key, long defValue) {
        return prefs.getLong(key, defValue);
    }

    @Override
    protected Set<String> getStringSet(String key, Set<String> defValue) {
        return prefs.getStringSet(key, new HashSet<String>());
    }

    @Override
    protected boolean has(String key) {
        return prefs.contains(key);
    }

    @Override
    protected void remove(String key) {
        prefs.edit().remove(key).apply();
    }

    @Override
    public void clear() {
        prefs.edit().clear().apply();
    }
}
