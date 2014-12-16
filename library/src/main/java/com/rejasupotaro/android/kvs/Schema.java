package com.rejasupotaro.android.kvs;

import java.util.Set;

public abstract class Schema {
    protected abstract void putBoolean(String key, boolean value);
    protected abstract void putString(String key, String value);
    protected abstract void putFloat(String key, float value);
    protected abstract void putInt(String key, int value);
    protected abstract void putLong(String key, long value);
    protected abstract void putStringSet(String key, Set<String> value);

    protected abstract boolean getBoolean(String key, boolean defValue);
    protected abstract String getString(String key, String defValue);
    protected abstract float getFloat(String key, float defValue);
    protected abstract int getInt(String key, int defValue);
    protected abstract long getLong(String key, long defValue);
    protected abstract Set<String> getStringSet(String key, Set<String> defValue);

    protected abstract boolean has(String key);

    protected abstract void remove(String key);

    public abstract void clear();
}
