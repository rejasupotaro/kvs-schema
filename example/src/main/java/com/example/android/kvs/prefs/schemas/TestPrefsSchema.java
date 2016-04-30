package com.example.android.kvs.prefs.schemas;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table(name = "test")
public abstract class TestPrefsSchema {
    @Key("long_value")
    long longValue;
    @Key("string_value")
    String stringValue;
    @Key("int_value")
    int intValue;
    @Key("boolean_value")
    boolean booleanValue;
    @Key("float_value")
    float floatValue;
    @Key("string_set_value")
    Set<String> stringSetValue;
}

