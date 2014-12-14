package com.rejasupotaro.android.kvs;

import java.util.Set;

public interface Schema {
    public void putBoolean(String key, boolean value);
    public void putString(String key, String value);
    public void putFloat(String key, float value);
    public void putInt(String key, int value);
    public void putLong(String key, long value);
    public void putStringSet(String key, Set<String> value);

    public boolean getBoolean(String key);
    public String getString(String key);
    public float getFloat(String key);
    public int getInt(String key);
    public long getLong(String key);
    public Set<String> getStringSet(String key);
}
