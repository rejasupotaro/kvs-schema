package com.rejasupotaro.android.kvs;

import java.util.Set;

public interface Schema {
    public void putBoolean(String key, boolean value);
    public void putString(String key, String value);
    public void putFloat(String key, float value);
    public void putInt(String key, int value);
    public void putLong(String key, long value);
    public void putStringSet(String key, Set<String> value);

    public boolean getBoolean(String key, boolean defValue);
    public String getString(String key, String defValue);
    public float getFloat(String key, float defValue);
    public int getInt(String key, int defValue);
    public long getLong(String key, long defValue);
    public Set<String> getStringSet(String key, Set<String> defValue);

    public boolean has(String key);

    public void remove(String key);

    public void clear();
}
