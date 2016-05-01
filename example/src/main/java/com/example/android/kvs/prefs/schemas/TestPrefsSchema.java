package com.example.android.kvs.prefs.schemas;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table(name = "test")
public abstract class TestPrefsSchema {
    @Key(name = "long_value")
    long longValue;
    @Key(name = "string_value")
    String stringValue;
    @Key(name = "int_value")
    int intValue;
    @Key(name = "boolean_value")
    boolean booleanValue;
    @Key(name = "float_value")
    float floatValue;
    @Key(name = "string_set_value")
    Set<String> stringSetValue;
}

